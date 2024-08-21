package com.photon.qreceipt.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="customer_media")
public class CustomerMedia {
	
	@Id	
	@Column(name = "saas_id", columnDefinition = "VARCHAR(20)")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String saasId;
	
	@Column(name ="video_ref", columnDefinition = "VARCHAR(250)")
	private String videoRef;

	@Column(name ="leaflet_ref", columnDefinition = "VARCHAR(250)")
	private String leafletRef;
	
	@Column(name ="coupon_ref", columnDefinition = "VARCHAR(250)")
	private String couponRef;

	@Column(name ="promotional_message", columnDefinition = "VARCHAR(250)")
	private String promotionalMessage;
	
}
