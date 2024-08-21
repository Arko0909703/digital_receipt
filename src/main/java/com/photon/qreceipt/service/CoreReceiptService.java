package com.photon.qreceipt.service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import com.photon.qreceipt.entity.*;
import com.photon.qreceipt.repository.*;
import com.photon.qreceipt.response.*;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;
import org.fit.pdfdom.PDFDomTree;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.photon.qreceipt.request.CoreReceiptRequest;
import com.photon.qreceipt.request.CustomerMasterRequest;

import com.photon.qreceipt.request.UserDetailRequest;

@Service
public class CoreReceiptService {
	@Autowired
	private CoreReceiptRepository coreReceiptRepository;

	@Autowired
	private QreceiptPromoRepository qreceiptPromoRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private NpsMasterRepository npsMasterRepository;

	@Autowired
	private StoreMasterRepository storeMasterRepository;

	@Autowired
	private QrCustomerMasterRepository qrCustomerMasterRepository;

	@Autowired
	private TaxDetailsRepository taxDetailsRepository;

	@Autowired
	private LicenseMasterRepository licenseMasterRepository;

	@Autowired
	private FooterDetailsRepository footerDetailsRepository;

	@Autowired
	private QreceiptCustomerRepository qreceiptCustomerRepository;

	@Autowired
	private ItemDetailsRepository itemDetailsRepository;

	@Autowired
	private ReceiptHeaderRepository headerDetailsRepository;

	@Autowired
	private RegisterMasterRepository registerMasterRepository;

	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private ClientBrandSettingRepository clientBrandSettingRepository;

	@Value("${file.location}")
	private String location;

	@Value("${receipt.url}")
	private String url;

	private final int RECEIPT_EXPIRY_TIME_IN_MINUTES = 2;

	public BaseResponse upload(MultipartFile file, String uploadedby, String storeId, String registerId,
			String businessDate, String saasId) {
		BaseResponse res = new BaseResponse();
		String pageContent = "";
		String invoiceNumber = null;
		try {

			ClientReceiptSetting setting = clientBrandSettingRepository.findByClientIdAndBrandId(Long.valueOf(saasId),
					1);
			PdfReader pdfReader = new PdfReader(file.getBytes());
			int pages = pdfReader.getNumberOfPages();

			for (int i = 1; i <= pages; i++) {
				pageContent = pageContent.concat(PdfTextExtractor.getTextFromPage(pdfReader, i));
			}
			pdfReader.close();
			String[] lines = pageContent.split("\\R");

			if ((setting.getInvoicePosition()).intValue() <= lines.length) {
				invoiceNumber = lines[(setting.getInvoicePosition()).intValue() - 1];
				invoiceNumber = (invoiceNumber.replaceAll(setting.getStringToBeRemoved(), "")).trim();
			}
			String invoiceDate = null;
			if ((setting.getInvoiceDate()) <= lines.length) {
				String[] line = (lines[setting.getInvoiceDate() - 1].trim()).split(" ");
				if (line.length > 1) {
					invoiceDate = lines[setting.getInvoiceDate() - 1].trim().replace(line[0], "").trim();
					invoiceDate = invoiceDate.replace(line[line.length - 1], "").trim();
					invoiceDate = invoiceDate.replace("Staff :", "");
					invoiceDate = invoiceDate.replace("Date :", "");
				}
			}
			String header1 = null;
			if ((setting.getHeader1Position()).intValue() <= lines.length)
				header1 = lines[setting.getHeader1Position().intValue() - 1].trim();
			String header2 = null;
			if ((setting.getHeader2Position()).intValue() <= lines.length)
				header2 = lines[setting.getHeader2Position().intValue() - 1].trim();
			String header3 = null;
			if ((setting.getHeader3Position()).intValue() <= lines.length)
				header3 = lines[setting.getHeader3Position().intValue() - 1].trim();
			String header4 = null;
			if ((setting.getHeader4Position()).intValue() <= lines.length)
				header4 = lines[setting.getHeader4Position().intValue() - 1].trim();
			String header5 = null;
			if ((setting.getHeader5Position()).intValue() <= lines.length)
				header5 = lines[setting.getHeader5Position().intValue() - 1].trim();
			String header6 = null;
			if ((setting.getHeader6Position()).intValue() <= lines.length)
				header6 = lines[setting.getHeader6Position().intValue() - 1].trim();
			String header7 = null;
			if ((setting.getHeader7Position()).intValue() <= lines.length)
				header7 = lines[setting.getHeader7Position().intValue() - 1].trim();
			String header8 = null;
			if ((setting.getHeader8Position()).intValue() <= lines.length)
				header8 = lines[setting.getHeader8Position().intValue() - 1].trim();
			ReceiptHeader headerDetail = new ReceiptHeader();
			headerDetail.setHeader1(header1);
			headerDetail.setHeader2(header2);
			headerDetail.setHeader3(header3);
			headerDetail.setHeader4(header4);
			headerDetail.setHeader5(header5);
			headerDetail.setHeader6(header6);
			headerDetail.setHeader7(header7);
			headerDetail.setHeader8(header8);
			headerDetail.setInvoiceNumber(invoiceNumber);

			headerDetail.setInvoiceDate(invoiceDate);
			headerDetailsRepository.save(headerDetail);
			// Item description capture starts
			int itemStartPosition = setting.getItemStartPosition().intValue();
			Integer lineNumber = 0;
			for (int pos = itemStartPosition; pos < lines.length; pos++) {
				if (lines[pos].contains(setting.getItemEndString())) {
					break;
				}
				String netPrice = "";
				String unitPrice = "";
				String[] itemInfo = lines[pos].split(" ");
				if (itemInfo.length > 1) {
					if (isNumeric(itemInfo[0])) {
						netPrice = itemInfo[(itemInfo.length) - 1].trim();
						lineNumber++;
					}

					ItemDetails item = new ItemDetails();
					item.setInvoiceNumber(invoiceNumber);
					item.setNetPrice(netPrice);
					if (isNumeric(itemInfo[0]))
						unitPrice = itemInfo[(itemInfo.length) - 1].trim();
					item.setUnitPrice(unitPrice);
					String itemName = "";
					itemName = lines[pos].replaceAll(unitPrice, "").replaceAll(netPrice, "");
					item.setItemName(lines[pos].replaceAll(itemInfo[(itemInfo.length) - 1], "").trim());
					String qty = "";
					if (isNumeric(itemInfo[0]))
						qty = itemInfo[0];
					item.setItemQty(qty);
					item.setLineNumber(lineNumber);
					itemDetailsRepository.save(item);
				}

			}
			// Tax Details capture starts
			boolean taxStartCapture = false;
			for (int pos = itemStartPosition; pos < lines.length; pos++) {
				if (lines[pos].contains(setting.getTaxEndString())) {
					break;
				}
				if (lines[pos].contains(setting.getTaxStartString())) {
					taxStartCapture = true;
					pos++;
				}

				if (taxStartCapture) {
					String[] taxInfo = lines[pos].split(" ");
					if (taxInfo.length > 0) {
						TaxDetails tax = new TaxDetails();
						tax.setInvoiceNumber(invoiceNumber);
						tax.setTaxPercent(taxInfo[1].trim());
						tax.setTaxType(taxInfo[0].trim());
						tax.setTaxTotal(taxInfo[(taxInfo.length) - 1].trim());
						taxDetailsRepository.save(tax);
					}
				}

			}

			// Footer Details capture starts
			boolean footerDetailsCapture = false;
			int pos = 0;
			for (pos = itemStartPosition; pos < lines.length; pos++) {

				if (lines[pos].contains(setting.getFooterStartPoistion())) {
					footerDetailsCapture = true;
					pos++;
					break;
				}
			}
			if (footerDetailsCapture) {
				FooterDetails footer = new FooterDetails();
				footer.setFooter1(lines[pos].trim());
				if ((pos + 2) < lines.length)
					footer.setFooter2(lines[pos + 1].trim());
				if ((pos + 3) < lines.length)
					footer.setFooter3(lines[pos + 2].trim());
				if ((pos + 4) < lines.length)
					footer.setFooter4(lines[pos + 3].trim());
				if ((pos + 5) < lines.length)
					footer.setFooter5(lines[pos + 4].trim());
				if ((pos + 6) < lines.length)
					footer.setFooter6(lines[pos + 5].trim());
				if ((pos + 7) < lines.length)
					footer.setFooter7(lines[pos + 6].trim());
				if ((pos + 8) < lines.length)
					footer.setFooter8(lines[pos + 7].trim());

				footer.setInvoiceNumber(invoiceNumber);

				footerDetailsRepository.save(footer);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		CoreReceiptEntity coreReceiptEntity = new CoreReceiptEntity();
		coreReceiptEntity.setBusinessDate(businessDate);
		coreReceiptEntity.setReceiptContent(pageContent);
		coreReceiptEntity.setRegisterId(registerId);
		coreReceiptEntity.setSaasId(saasId);
		coreReceiptEntity.setStoreId(storeId);
		coreReceiptEntity.setUploadedBy(uploadedby);
		coreReceiptEntity.setCreateDate(LocalDateTime.now());
		coreReceiptEntity.setInvoiceNumber(invoiceNumber);
		String fileExtension = "";
		Optional<String> filetype = Optional.ofNullable(file.getOriginalFilename())
				.filter(f -> f.contains("."))
				.map(f -> f.substring(file.getOriginalFilename().lastIndexOf(".") + 1));
		if (filetype.isPresent()) {
			fileExtension = filetype.get();
		}
		coreReceiptEntity.setFileLocation(createFile(saasId + storeId + registerId, fileExtension));
		coreReceiptRepository.save(coreReceiptEntity);

		try {

			File savePdf = new File(getFile(saasId + storeId + registerId, fileExtension));
			file.transferTo(savePdf);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		res.setStatus("Success");
		res.setErrorMessage("Data save successfully..!");
		return res;
	}

	public BaseResponse uploadPhoton(MultipartFile file, String uploadedby, String storeId, String registerId,
			String businessDate, String saasId) {
		BaseResponse res = new BaseResponse();
		String pageContent = "";
		String invoiceNumber = null;
		try {

			ClientReceiptSetting setting = clientBrandSettingRepository.findByClientIdAndBrandId(Long.valueOf(saasId),
					1);
			PdfReader pdfReader = new PdfReader(file.getBytes());
			int pages = pdfReader.getNumberOfPages();

			for (int i = 1; i <= pages; i++) {
				pageContent = pageContent.concat(PdfTextExtractor.getTextFromPage(pdfReader, i));
			}
			pdfReader.close();
			String[] lines = pageContent.split("\\R");

			if ((setting.getInvoicePosition()).intValue() <= lines.length) {
				invoiceNumber = lines[(setting.getInvoicePosition()).intValue() - 1];
				invoiceNumber = (invoiceNumber.replaceAll(setting.getStringToBeRemoved(), "")).trim();
			}
			String invoiceDate = null;
			if ((setting.getInvoiceDate()) <= lines.length) {
				String[] line = (lines[setting.getInvoiceDate() - 1].trim()).split(" ");
				if (line.length > 1)
					invoiceDate = lines[setting.getInvoiceDate() - 1].trim().replace(line[0], "").trim();
			}
			String header1 = null;
			if ((setting.getHeader1Position()).intValue() <= lines.length)
				header1 = lines[setting.getHeader1Position().intValue() - 1].trim();
			String header2 = null;
			if ((setting.getHeader2Position()).intValue() <= lines.length)
				header2 = lines[setting.getHeader2Position().intValue() - 1].trim();
			String header3 = null;
			if ((setting.getHeader3Position()).intValue() <= lines.length)
				header3 = lines[setting.getHeader3Position().intValue() - 1].trim();
			String header4 = null;
			if ((setting.getHeader4Position()).intValue() <= lines.length)
				header4 = lines[setting.getHeader4Position().intValue() - 1].trim();
			String header5 = null;
			if ((setting.getHeader5Position()).intValue() <= lines.length)
				header5 = lines[setting.getHeader5Position().intValue() - 1].trim();
			String header6 = null;
			if ((setting.getHeader6Position()).intValue() <= lines.length)
				header6 = lines[setting.getHeader6Position().intValue() - 1].trim();
			String header7 = null;
			if ((setting.getHeader7Position()).intValue() <= lines.length)
				header7 = lines[setting.getHeader7Position().intValue() - 1].trim();
			String header8 = null;
			if ((setting.getHeader8Position()).intValue() <= lines.length)
				header8 = lines[setting.getHeader8Position().intValue() - 1].trim();
			ReceiptHeader headerDetail = new ReceiptHeader();
			headerDetail.setHeader1(header1);
			headerDetail.setHeader2(header2);
			headerDetail.setHeader3(header3);
			headerDetail.setHeader4(header4);
			headerDetail.setHeader5(header5);
			headerDetail.setHeader6(header6);
			headerDetail.setHeader7(header7);
			headerDetail.setHeader8(header8);
			headerDetail.setInvoiceNumber(invoiceNumber);

			headerDetail.setInvoiceDate(invoiceDate);
			headerDetailsRepository.save(headerDetail);
			// Item description capture starts
			int itemStartPosition = setting.getItemStartPosition().intValue();
			for (int pos = itemStartPosition; pos < lines.length; pos++) {
				if (lines[pos].contains(setting.getItemEndString())) {
					break;
				}

				ItemDetails item = new ItemDetails();
				item.setInvoiceNumber(invoiceNumber);
				String[] itemInfo = lines[pos].split(" ");
				if (itemInfo.length > 1) {
					item.setNetPrice(itemInfo[(itemInfo.length) - 1].trim());
					item.setItemName(lines[pos].replaceAll(itemInfo[(itemInfo.length) - 1], "").trim());
					item.setUnitPrice(itemInfo[(itemInfo.length) - 1].trim());
					String qty = "1";
					if (isNumeric(itemInfo[0]))
						qty = itemInfo[0];
					item.setItemQty(qty);
					itemDetailsRepository.save(item);
				}

			}
			// Tax Details capture starts
			boolean taxStartCapture = false;
			for (int pos = itemStartPosition; pos < lines.length; pos++) {
				if (lines[pos].contains(setting.getTaxEndString())) {
					break;
				}
				if (lines[pos].contains(setting.getTaxStartString())) {
					taxStartCapture = true;
					pos++;
				}

				if (taxStartCapture) {
					String[] taxInfo = lines[pos].split(" ");
					if (taxInfo.length > 0) {
						TaxDetails tax = new TaxDetails();
						tax.setInvoiceNumber(invoiceNumber);
						tax.setTaxPercent(taxInfo[1].trim());
						tax.setTaxType(taxInfo[0].trim());
						tax.setTaxTotal(taxInfo[(taxInfo.length) - 1].trim());
						taxDetailsRepository.save(tax);
					}
				}

			}

			// Footer Details capture starts
			boolean footerDetailsCapture = false;
			int pos = 0;
			for (pos = itemStartPosition; pos < lines.length; pos++) {

				if (lines[pos].contains(setting.getFooterStartPoistion())) {
					footerDetailsCapture = true;
					pos++;
					break;
				}
			}
			if (footerDetailsCapture) {
				FooterDetails footer = new FooterDetails();
				footer.setFooter1(lines[pos].trim());
				if ((pos + 2) < lines.length)
					footer.setFooter2(lines[pos + 1].trim());
				if ((pos + 3) < lines.length)
					footer.setFooter3(lines[pos + 2].trim());
				if ((pos + 4) < lines.length)
					footer.setFooter4(lines[pos + 3].trim());
				if ((pos + 5) < lines.length)
					footer.setFooter5(lines[pos + 4].trim());
				if ((pos + 6) < lines.length)
					footer.setFooter6(lines[pos + 5].trim());
				if ((pos + 7) < lines.length)
					footer.setFooter7(lines[pos + 6].trim());
				if ((pos + 8) < lines.length)
					footer.setFooter8(lines[pos + 7].trim());

				footer.setInvoiceNumber(invoiceNumber);

				footerDetailsRepository.save(footer);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		CoreReceiptEntity coreReceiptEntity = new CoreReceiptEntity();
		coreReceiptEntity.setBusinessDate(businessDate);
		coreReceiptEntity.setReceiptContent(pageContent);
		coreReceiptEntity.setRegisterId(registerId);
		coreReceiptEntity.setSaasId(saasId);
		coreReceiptEntity.setStoreId(storeId);
		coreReceiptEntity.setUploadedBy(uploadedby);
		coreReceiptEntity.setCreateDate(LocalDateTime.now());
		coreReceiptEntity.setInvoiceNumber(invoiceNumber);
		String fileExtension = "";
		Optional<String> filetype = Optional.ofNullable(file.getOriginalFilename())
				.filter(f -> f.contains("."))
				.map(f -> f.substring(file.getOriginalFilename().lastIndexOf(".") + 1));
		if (filetype.isPresent()) {
			fileExtension = filetype.get();
		}
		String fileName = createFile(saasId + storeId + registerId, fileExtension);
		coreReceiptEntity.setFileLocation(fileName);
		coreReceiptRepository.save(coreReceiptEntity);

		try {
			File savePdf = new File(fileName);
			file.transferTo(savePdf);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		res.setErrorMessage("Data upload successfully!!");
		res.setStatus("Success");
		return res;
	}

	@Transactional
	public BaseResponse Kfcupload(MultipartFile file, String uploadedby, String storeId, String registerId,
			String businessDate, String saasId) {
		BaseResponse res = new BaseResponse();
		String pageContent = "";
		String invoiceNumber = null;
		try {

			ClientReceiptSetting setting = clientBrandSettingRepository.findByClientIdAndBrandId(Long.valueOf(saasId),
					1);
			PdfReader pdfReader = new PdfReader(file.getBytes());
			int pages = pdfReader.getNumberOfPages();

			for (int i = 1; i <= pages; i++) {
				pageContent = pageContent.concat(PdfTextExtractor.getTextFromPage(pdfReader, i));
			}
			pdfReader.close();
			String[] lines = pageContent.split("\\R");

			if ((setting.getInvoicePosition()).intValue() <= lines.length) {
				invoiceNumber = lines[(setting.getInvoicePosition()).intValue() - 1];
				invoiceNumber = (invoiceNumber.replaceAll(setting.getStringToBeRemoved(), "")).trim();
			}
			String invoiceDate = null;
			if ((setting.getInvoiceDate()) <= lines.length) {
				String[] line = (lines[setting.getInvoiceDate() - 1].trim()).split(" ");
				if (line.length > 1)
					invoiceDate = lines[setting.getInvoiceDate() - 1].trim().replace(line[0], "").trim();
			}
			String header1 = null;
			if ((setting.getHeader1Position()).intValue() <= lines.length)
				header1 = lines[setting.getHeader1Position().intValue() - 1].trim();
			String header2 = null;
			if ((setting.getHeader2Position()).intValue() <= lines.length)
				header2 = lines[setting.getHeader2Position().intValue() - 1].trim();
			String header3 = null;
			if ((setting.getHeader3Position()).intValue() <= lines.length)
				header3 = lines[setting.getHeader3Position().intValue() - 1].trim();
			String header4 = null;
			if ((setting.getHeader4Position()).intValue() <= lines.length)
				header4 = lines[setting.getHeader4Position().intValue() - 1].trim();
			String header5 = null;
			if ((setting.getHeader5Position()).intValue() <= lines.length)
				header5 = lines[setting.getHeader5Position().intValue() - 1].trim();
			String header6 = null;
			if ((setting.getHeader6Position()).intValue() <= lines.length)
				header6 = lines[setting.getHeader6Position().intValue() - 1].trim();
			String header7 = null;
			if ((setting.getHeader7Position()).intValue() <= lines.length)
				header7 = lines[setting.getHeader7Position().intValue() - 1].trim();
			String header8 = null;
			if ((setting.getHeader8Position()).intValue() <= lines.length)
				header8 = lines[setting.getHeader8Position().intValue() - 1].trim();
			ReceiptHeader headerDetail = new ReceiptHeader();
			headerDetail.setHeader1(header1);
			headerDetail.setHeader2(header2);
			headerDetail.setHeader3(header3);
			headerDetail.setHeader4(header4);
			headerDetail.setHeader5(header5);
			headerDetail.setHeader6(header6);
			headerDetail.setHeader7(header7);
			headerDetail.setHeader8(header8);
			headerDetail.setInvoiceNumber(invoiceNumber);

			headerDetail.setInvoiceDate(invoiceDate);
			headerDetailsRepository.save(headerDetail);
			// Item description capture starts
			int itemStartPosition = setting.getItemStartPosition().intValue();
			for (int pos = itemStartPosition; pos < lines.length; pos++) {
				if (lines[pos].contains(setting.getItemEndString())) {
					break;
				}

				ItemDetails item = new ItemDetails();
				item.setInvoiceNumber(invoiceNumber);
				String[] itemInfo = lines[pos].split(" ");
				if (itemInfo.length > 1) {
					item.setNetPrice(itemInfo[(itemInfo.length) - 1].trim());
					item.setItemName(lines[pos].replaceAll(itemInfo[(itemInfo.length) - 1], "").trim());
					item.setUnitPrice(itemInfo[(itemInfo.length) - 1].trim());
					String qty = "1";
					if (isNumeric(itemInfo[0]))
						qty = itemInfo[0];
					item.setItemQty(qty);
					itemDetailsRepository.save(item);
				}

			}
			// Tax Details capture starts
			boolean taxStartCapture = false;
			for (int pos = itemStartPosition; pos < lines.length; pos++) {
				if (lines[pos].contains(setting.getTaxEndString())) {
					break;
				}
				if (lines[pos].contains(setting.getTaxStartString())) {
					taxStartCapture = true;
					pos++;
				}

				if (taxStartCapture) {
					String[] taxInfo = lines[pos].split(" ");
					if (taxInfo.length > 0) {
						TaxDetails tax = new TaxDetails();
						tax.setInvoiceNumber(invoiceNumber);
						tax.setTaxPercent(taxInfo[1].trim());
						tax.setTaxType(taxInfo[0].trim());
						tax.setTaxTotal(taxInfo[(taxInfo.length) - 1].trim());
						taxDetailsRepository.save(tax);
					}
				}

			}

			// Footer Details capture starts
			boolean footerDetailsCapture = false;
			int pos = 0;
			for (pos = itemStartPosition; pos < lines.length; pos++) {

				if (lines[pos].contains(setting.getFooterStartPoistion())) {
					footerDetailsCapture = true;
					pos++;
					break;
				}
			}
			if (footerDetailsCapture) {
				String footer1 = lines[pos].trim();
				String footer2 = lines[pos + 1].trim();
				String footer3 = lines[pos + 2].trim();
				String footer4 = lines[pos + 3].trim();
				String footer5 = lines[pos + 4].trim();
				String footer6 = lines[pos + 5].trim();
				String footer7 = lines[pos + 6].trim();
				String footer8 = lines[pos + 7].trim();
				FooterDetails footer = new FooterDetails();
				footer.setInvoiceNumber(invoiceNumber);
				footer.setFooter1(footer1);
				footer.setFooter2(footer2);
				footer.setFooter3(footer3);
				footer.setFooter4(footer4);
				footer.setFooter5(footer5);
				footer.setFooter6(footer6);
				footer.setFooter7(footer7);
				footer.setFooter8(footer8);
				footerDetailsRepository.save(footer);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		CoreReceiptEntity coreReceiptEntity = new CoreReceiptEntity();
		coreReceiptEntity.setBusinessDate(businessDate);
		coreReceiptEntity.setReceiptContent(pageContent);
		coreReceiptEntity.setRegisterId(registerId);
		coreReceiptEntity.setSaasId(saasId);
		coreReceiptEntity.setStoreId(storeId);
		coreReceiptEntity.setUploadedBy(uploadedby);
		coreReceiptEntity.setCreateDate(LocalDateTime.now());
		coreReceiptEntity.setInvoiceNumber(invoiceNumber);
		coreReceiptRepository.save(coreReceiptEntity);

		try {
			String fileExtension = "";
			Optional<String> filetype = Optional.ofNullable(file.getOriginalFilename())
					.filter(f -> f.contains("."))
					.map(f -> f.substring(file.getOriginalFilename().lastIndexOf(".") + 1));
			if (filetype.isPresent()) {
				fileExtension = filetype.get();
			}
			File savePdf = new File(getFile(saasId + storeId + registerId, fileExtension));
			file.transferTo(savePdf);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		res.setStatus("Success");
		return res;
	}

	public String getFile(String clientName, String fileExtension) {
		return location.concat(createFile(clientName, fileExtension));
	}

	public String createFile(String clientName, String fileExtension) {
		Date dateTime = new Date();
		String dateTimeFormat = "yyyyMMddhhmmss";
		DateFormat dateFormat = new SimpleDateFormat(dateTimeFormat);
		return clientName.concat(dateFormat.format(dateTime).concat("." + fileExtension));
	}

	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			double d = Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public String getToken(String deviceId) {
		String token = "";
		TokenEntity tokenEntity = tokenRepository.findByDeviceId(deviceId);
		if (tokenEntity != null) {
			token = tokenEntity.getToken();
		} else {
			token = UUID.randomUUID().toString().replace("-", "");
			tokenEntity = new TokenEntity();
			tokenEntity.setDeviceId(deviceId);
			tokenEntity.setToken(token);
			tokenRepository.save(tokenEntity);

		}
		return token;
	}

	public TokenEntity generateToken(String deviceId) {
		String token = "";
		TokenEntity tokenEntity = tokenRepository.findByDeviceId(deviceId);
		if (tokenEntity != null) {
			token = tokenEntity.getToken();
		} else {
			token = UUID.randomUUID().toString().replace("-", "");
			tokenEntity = new TokenEntity();
			tokenEntity.setDeviceId(deviceId);
			tokenEntity.setToken(token);
			tokenRepository.save(tokenEntity);

		}
		return tokenEntity;
	}

	public QRResponse getQRDetails(String nick, CoreReceiptRequest request) {
		String registerId = coreReceiptRepository.getRegisterIDByRegisterNick(nick);
 		request.setRegisterId(registerId);
		return getQRDetails(request);
	}

	public QRResponse getQRDetails(CoreReceiptRequest request) {
		QRResponse response = new QRResponse();
		CoreReceiptEntity entity = coreReceiptRepository.getReceipt(request.getStoreId(), request.getRegisterId(),
				request.getBusinessDate(), request.getSaasId());
		LicenseMaster licenseEntity = licenseMasterRepository.getLicense(request.getSaasId());
		ClientEntity company = clientRepository.getClient(request.getSaasId());
		if (licenseEntity != null) {
			int i = request.getBusinessDate().compareTo(this.getStringFromLocalDate(licenseEntity.getExpiryDate()));
			if (i > 0) {
				response.setMessage(licenseEntity.getStatus());
				return response;
			}
		}
		if (entity != null) {
			if (entity.getReceiptDelivered() != null && entity.getReceiptDelivered().equalsIgnoreCase("Y"))
				return response;
			List<ReceiptHeader> headerDetails = headerDetailsRepository.findByInvoiceNumber(entity.getInvoiceNumber());
			if (headerDetails != null) {
				response.setReceiptUrl(url.concat(entity.getFileLocation()));
			}
			ClientEntity client = null;
			Optional<ClientEntity> optinalClient = clientRepository.findById(request.getSaasId());
			if (optinalClient.isPresent()) {
				client = optinalClient.get();
				response.setClientPromo(client.getCleintPromo());
			}
			if (entity.getDeviceId() == null) {
				entity.setDeviceId(request.getDeviceId());
				coreReceiptRepository.save(entity);
			}

			LocalDateTime now = LocalDateTime.now();
			// Check if the user is allowed to scan
			// 1. If device from which the pdf was first scanned is different from the
			// current one
			// 2. If the last receipt is uploaded is 2 minute older than the current time
			if (!entity.getDeviceId().equals(request.getDeviceId())) {
				response.setReceiptUrl(null);
				response.setMessage("You are not authorized to scan!!");
				return response;
			}
			if (now.minusMinutes(RECEIPT_EXPIRY_TIME_IN_MINUTES).compareTo(entity.getCreateDate()) > 0) {
				response.setReceiptUrl(null);
				response.setMessage("Session Timeout!!");
				return response;
			}
			response.setCompanyName(company.getClientName());
			response.setPdfName(entity.getFileLocation());
			response.setQrceiptPromo(qreceiptPromoRepository.getQreceiptPromo(request.getStoreId(),
					request.getRegisterId(), request.getSaasId()));
			response.setMessage("Scan Successfully");
			response.setReceiptId(entity.getReceiptId());
		}

		// everything is successful. So set the scanned to Y
		if(entity != null) {
			entity.setReceiptScannedStatus("Y");
			coreReceiptRepository.save(entity);
		}

		return response;
	}

	public BaseResponse saveUserDetails(UserDetailRequest request) {
		BaseResponse response = new BaseResponse();
		QreceiptCustomerEntity entity = new QreceiptCustomerEntity();
		entity.setBusinessDate(request.getBusinessDate());
		entity.setEmail(request.getEmail());
		entity.setMobileNo(request.getMobileNo());
		entity.setReceiptId(request.receiptId);

		response.setErrorMessage("Saved successfully..!");
		response.setStatus("true");

		return response;
	}

	public CoreReceiptResponse getReceipt(CoreReceiptRequest request) {
		CoreReceiptResponse response = new CoreReceiptResponse();
		CoreReceiptEntity entity = coreReceiptRepository.getReceipt(request.getStoreId(), request.getRegisterId(),
				request.getBusinessDate(), request.getSaasId());
		if (entity != null) {

			BeanUtils.copyProperties(entity, response);
			response.setStatus("Success");
			ClientEntity client = null;
			Optional<ClientEntity> optinalClient = clientRepository.findById(request.getSaasId());
			if (optinalClient.isPresent()) {
				client = optinalClient.get();
				response.setClientLogo(client.getClientLogo());
				response.setClientName(client.getClientName());
				response.setClientUrl(client.getClientUrl());
			}
			List<ItemDetails> itemDetails = itemDetailsRepository.findByInvoiceNumber(entity.getInvoiceNumber());
			List<ItemDetailsResponse> itemDetailsResponse = new ArrayList<>();
			for (ItemDetails itemDetail : itemDetails) {
				ItemDetailsResponse itemDetailResponse = new ItemDetailsResponse();
				BeanUtils.copyProperties(itemDetail, itemDetailResponse);
				itemDetailsResponse.add(itemDetailResponse);
			}
			List<TaxDetails> taxdetails = taxDetailsRepository.findByInvoiceNumber(entity.getInvoiceNumber());
			List<TaxDetailsResponse> taxDetailsResponse = new ArrayList<>();
			for (TaxDetails taxdetail : taxdetails) {
				TaxDetailsResponse taxDetailResponse = new TaxDetailsResponse();
				taxDetailResponse.setTaxAmount(taxdetail.getTaxTotal());
				taxDetailResponse.setTaxPercent(taxdetail.getTaxPercent());
				taxDetailResponse.setTaxType(taxdetail.getTaxType());
				taxDetailsResponse.add(taxDetailResponse);
			}
			List<ReceiptHeader> headerDetails = headerDetailsRepository.findByInvoiceNumber(entity.getInvoiceNumber());
			if (headerDetails != null) {
				HeaderDetailsResponse headerDetailResponse = new HeaderDetailsResponse();
				BeanUtils.copyProperties(headerDetails.get(headerDetails.size() - 1), headerDetailResponse);
				headerDetailResponse.setDocumentUrl(url.concat(entity.getFileLocation()));
				response.setHeaderDetailsResponse(headerDetailResponse);
			}

			FooterDetailsResponse footerDetailsResponse = new FooterDetailsResponse();
			FooterDetails footerDetails = footerDetailsRepository.findByInvoiceNumber(entity.getInvoiceNumber());
			if (footerDetails != null) {
				BeanUtils.copyProperties(footerDetails, footerDetailsResponse);
			}
			response.setItemDetailsResponse(itemDetailsResponse);
			response.setFooterDetailsResponse(footerDetailsResponse);
			response.setTaxDetailsResponse(taxDetailsResponse);
		} else {
			response.setErrorMessage("No record found");
			response.setStatus("Failure");
		}
		return response;
	}

	public BaseResponse downloadReceiptStatus(CoreReceiptRequest request) {
		BaseResponse response = new BaseResponse();
		CoreReceiptEntity entity = coreReceiptRepository.getReceiptByDeviceId(request.getStoreId(),
				request.getRegisterId(),
				request.getBusinessDate(), request.getSaasId(), request.getDeviceId());
		if (entity != null) {
			entity.setReceiptDownloadStatus("Y");
			coreReceiptRepository.save(entity);
			response.setErrorMessage("Status update successfully.!");
			response.setStatus("Success");
		} else {
			response.setErrorMessage("No record found");
			response.setStatus("Failure");
		}

		return response;
	}

	public LocalDateTime getLocalDateTimeFromString(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return LocalDateTime.parse(date, formatter);
	}

	public LocalDateTime getExpiryDate(String datetime, int expirydate) {
		LocalDateTime ldt = this.getLocalDateTimeFromString(datetime);
		LocalDate ldate = ldt.toLocalDate();
		LocalTime ltime = ldt.toLocalTime();
		LocalDate newdate = ldate.plusDays(expirydate);
		return LocalDateTime.of(newdate, ltime);
	}

	public String getStringFromLocalDate(LocalDate ldt) {
		if (ldt == null) {
			return LocalDate.parse(LocalDate.now().toString()).toString();
		}
		return LocalDate.parse(ldt.toString()).toString();
	}

	public BaseResponse saveQrCustomerDetails(CustomerMasterRequest request) {
		BaseResponse response = new BaseResponse();
		QrCustomerMaster entity = new QrCustomerMaster();
		BeanUtils.copyProperties(request, entity);
		qrCustomerMasterRepository.save(entity);
		response.setStatus("true");
		response.setErrorMessage("Data saved successfully");
		return response;
	}

	public StoreResponse getStoreDetail(String storeId) {
		StoreMaster entity = storeMasterRepository.getStoreDetail(storeId);
		StoreResponse response = new StoreResponse();
		BeanUtils.copyProperties(entity, response);
		return response;
	}

	public RegisterDetailsResponse getRegisterDetails(String registerNick) {
		RegisterDetails entity = registerMasterRepository.getRegisterDetails(registerNick);
		RegisterDetailsResponse response = new RegisterDetailsResponse();
		BeanUtils.copyProperties(entity, response);
		return response;
	}

	public BaseResponse uploadDocument(MultipartFile file, String uploadedby, String storeId, String registerId,
			String businessDate, String saasId, String choice, String mobileNumber, String countryCode) {
		BaseResponse res = new BaseResponse();
		String pageContent = "";
		String invoiceNumber = null;
		try {

			ClientReceiptSetting setting = clientBrandSettingRepository.findByClientIdAndBrandId(Long.valueOf(saasId),
					1);
			PdfReader pdfReader = new PdfReader(file.getBytes());
			int pages = pdfReader.getNumberOfPages();

			for (int i = 1; i <= pages; i++) {
				pageContent = pageContent.concat(PdfTextExtractor.getTextFromPage(pdfReader, i));
			}
			pdfReader.close();
			String[] lines = pageContent.split("\\R");

			if ((setting.getInvoicePosition()).intValue() <= lines.length) {
				invoiceNumber = lines[(setting.getInvoicePosition()).intValue() - 1];
				invoiceNumber = (invoiceNumber.replaceAll(setting.getStringToBeRemoved(), "")).trim();
			}
			String invoiceDate = null;
			if ((setting.getInvoiceDate()) <= lines.length) {
				String[] line = (lines[setting.getInvoiceDate() - 1].trim()).split(" ");
				if (line.length > 1) {
					invoiceDate = lines[setting.getInvoiceDate() - 1].trim().replace(line[0], "").trim();
					invoiceDate = invoiceDate.replace(line[line.length - 1], "").trim();
					invoiceDate = invoiceDate.replace("Staff :", "");
					invoiceDate = invoiceDate.replace("Date :", "");
				}
			}
			String header1 = null;
			if ((setting.getHeader1Position()).intValue() <= lines.length)
				header1 = lines[setting.getHeader1Position().intValue() - 1].trim();
			String header2 = null;
			if ((setting.getHeader2Position()).intValue() <= lines.length)
				header2 = lines[setting.getHeader2Position().intValue() - 1].trim();
			String header3 = null;
			if ((setting.getHeader3Position()).intValue() <= lines.length)
				header3 = lines[setting.getHeader3Position().intValue() - 1].trim();
			String header4 = null;
			if ((setting.getHeader4Position()).intValue() <= lines.length)
				header4 = lines[setting.getHeader4Position().intValue() - 1].trim();
			String header5 = null;
			if ((setting.getHeader5Position()).intValue() <= lines.length)
				header5 = lines[setting.getHeader5Position().intValue() - 1].trim();
			String header6 = null;
			if ((setting.getHeader6Position()).intValue() <= lines.length)
				header6 = lines[setting.getHeader6Position().intValue() - 1].trim();
			String header7 = null;
			if ((setting.getHeader7Position()).intValue() <= lines.length)
				header7 = lines[setting.getHeader7Position().intValue() - 1].trim();
			String header8 = null;
			if ((setting.getHeader8Position()).intValue() <= lines.length)
				header8 = lines[setting.getHeader8Position().intValue() - 1].trim();
			ReceiptHeader headerDetail = new ReceiptHeader();
			headerDetail.setHeader1(header1);
			headerDetail.setHeader2(header2);
			headerDetail.setHeader3(header3);
			headerDetail.setHeader4(header4);
			headerDetail.setHeader5(header5);
			headerDetail.setHeader6(header6);
			headerDetail.setHeader7(header7);
			headerDetail.setHeader8(header8);
			headerDetail.setInvoiceNumber(invoiceNumber);

			headerDetail.setInvoiceDate(invoiceDate);
			headerDetailsRepository.save(headerDetail);
			// Item description capture starts
			int itemStartPosition = setting.getItemStartPosition().intValue();
			Integer lineNumber = 0;
			for (int pos = itemStartPosition; pos < lines.length; pos++) {
				if (lines[pos].contains(setting.getItemEndString())) {
					break;
				}
				String netPrice = "";
				String unitPrice = "";
				String[] itemInfo = lines[pos].split(" ");
				if (itemInfo.length > 1) {
					if (isNumeric(itemInfo[0])) {
						netPrice = itemInfo[(itemInfo.length) - 1].trim();
						lineNumber++;
					}

					ItemDetails item = new ItemDetails();
					item.setInvoiceNumber(invoiceNumber);
					item.setNetPrice(netPrice);
					if (isNumeric(itemInfo[0]))
						unitPrice = itemInfo[(itemInfo.length) - 1].trim();
					item.setUnitPrice(unitPrice);
					String itemName = "";
					itemName = lines[pos].replaceAll(unitPrice, "").replaceAll(netPrice, "");
					item.setItemName(lines[pos].replaceAll(itemInfo[(itemInfo.length) - 1], "").trim());
					String qty = "";
					if (isNumeric(itemInfo[0]))
						qty = itemInfo[0];
					item.setItemQty(qty);
					item.setLineNumber(lineNumber);
					itemDetailsRepository.save(item);
				}

			}
			// Tax Details capture starts
			boolean taxStartCapture = false;
			for (int pos = itemStartPosition; pos < lines.length; pos++) {
				if (lines[pos].contains(setting.getTaxEndString())) {
					break;
				}
				if (lines[pos].contains(setting.getTaxStartString())) {
					taxStartCapture = true;
					pos++;
				}

				if (taxStartCapture) {
					String[] taxInfo = lines[pos].split(" ");
					if (taxInfo.length > 0) {
						TaxDetails tax = new TaxDetails();
						tax.setInvoiceNumber(invoiceNumber);
						tax.setTaxPercent(taxInfo[1].trim());
						tax.setTaxType(taxInfo[0].trim());
						tax.setTaxTotal(taxInfo[(taxInfo.length) - 1].trim());
						taxDetailsRepository.save(tax);
					}
				}

			}

			// Footer Details capture starts
			boolean footerDetailsCapture = false;
			int pos = 0;
			for (pos = itemStartPosition; pos < lines.length; pos++) {

				if (lines[pos].contains(setting.getFooterStartPoistion())) {
					footerDetailsCapture = true;
					pos++;
					break;
				}
			}
			if (footerDetailsCapture) {
				FooterDetails footer = new FooterDetails();
				footer.setFooter1(lines[pos].trim());
				if ((pos + 2) < lines.length)
					footer.setFooter2(lines[pos + 1].trim());
				if ((pos + 3) < lines.length)
					footer.setFooter3(lines[pos + 2].trim());
				if ((pos + 4) < lines.length)
					footer.setFooter4(lines[pos + 3].trim());
				if ((pos + 5) < lines.length)
					footer.setFooter5(lines[pos + 4].trim());
				if ((pos + 6) < lines.length)
					footer.setFooter6(lines[pos + 5].trim());
				if ((pos + 7) < lines.length)
					footer.setFooter7(lines[pos + 6].trim());
				if ((pos + 8) < lines.length)
					footer.setFooter8(lines[pos + 7].trim());

				footer.setInvoiceNumber(invoiceNumber);

				footerDetailsRepository.save(footer);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		CoreReceiptEntity coreReceiptEntity = new CoreReceiptEntity();
		coreReceiptEntity.setBusinessDate(businessDate);
		coreReceiptEntity.setReceiptContent(pageContent);
		coreReceiptEntity.setRegisterId(registerId);
		coreReceiptEntity.setSaasId(saasId);
		coreReceiptEntity.setStoreId(storeId);
		coreReceiptEntity.setUploadedBy(uploadedby);
		coreReceiptEntity.setCreateDate(LocalDateTime.now());
		coreReceiptEntity.setInvoiceNumber(invoiceNumber);
		String fileExtension = "";
		Optional<String> filetype = Optional.ofNullable(file.getOriginalFilename())
				.filter(f -> f.contains("."))
				.map(f -> f.substring(file.getOriginalFilename().lastIndexOf(".") + 1));
		if (filetype.isPresent()) {
			fileExtension = filetype.get();
		}
		coreReceiptEntity.setFileLocation(createFile(saasId + storeId + registerId, fileExtension));
		coreReceiptEntity.setMobileNumber(mobileNumber);
		coreReceiptEntity.setChoice(choice);
		coreReceiptEntity.setCountryCode(countryCode.substring(countryCode.indexOf("(") + 1, countryCode.indexOf(")")));
		coreReceiptRepository.save(coreReceiptEntity);

		try {
			File savePdf = new File(getFile(saasId + storeId + registerId, fileExtension));
			file.transferTo(savePdf);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		res.setStatus("Success");
		res.setErrorMessage("Data save successfully..!");
		return res;

	}

}
