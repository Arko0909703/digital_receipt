package com.photon.qreceipt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "register_master")
public class RegisterMaster {
    @Id
    @Column(name = "register_id", columnDefinition = "VARCHAR(20)")
    private String registerId;

    @Column(name = "store_id", columnDefinition = "VARCHAR(20)")
    private String storeId;

    @Column(name = "register_nick", columnDefinition = "VARCHAR(10)")
    private String registerNick;
}
