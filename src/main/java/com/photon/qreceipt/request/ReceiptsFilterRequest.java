package com.photon.qreceipt.request;

public class ReceiptsFilterRequest {
    private String startBusinessDate;
    private String endBusinessDate;
    private String storeId;
    private String downloadStatus;
    private String scannedStatus;

    private int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setStartBusinessDate(String startBusinessDate) {
        this.startBusinessDate = startBusinessDate;
    }

    public void setEndBusinessDate(String endBusinessDate) {
        if (endBusinessDate == null) {
            this.endBusinessDate = startBusinessDate;
        } else {
            this.endBusinessDate = endBusinessDate;
        }
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public void setDownloadStatus(String downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

    public void setScannedStatus(String scannedStatus) {
        this.scannedStatus = scannedStatus;
    }

    public String getStartBusinessDate() {
        return startBusinessDate;
    }

    public String getEndBusinessDate() {
        return endBusinessDate;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getDownloadStatus() {
        return downloadStatus;
    }

    @Override
    public String toString() {
        return "ReceiptsFilterRequest{" +
                "startBusinessDate='" + startBusinessDate + '\'' +
                ", endBusinessDate='" + endBusinessDate + '\'' +
                ", storeId='" + storeId + '\'' +
                ", downloadStatus='" + downloadStatus + '\'' +
                ", scannedStatus='" + scannedStatus + '\'' +
                '}';
    }

    public String getScannedStatus() {
        return scannedStatus;
    }
}
