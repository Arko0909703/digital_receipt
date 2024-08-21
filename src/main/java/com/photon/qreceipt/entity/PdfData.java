package com.photon.qreceipt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="leaflet_master")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PdfData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name ="saas_id", columnDefinition = "VARCHAR(20)")
	private String saasId;
	@Column(name ="name", columnDefinition = "VARCHAR(30)", unique = true)
	private String name;
	private String type;
	@Column(name ="pdf_path", columnDefinition = "VARCHAR(150)")
	private String pdfPath;

	
}

