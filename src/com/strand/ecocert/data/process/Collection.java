package com.strand.ecocert.data.process;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.strand.ecocert.data.constants.CollectionStatus;
import com.strand.ecocert.data.entity.CollectionCenter;
import com.strand.ecocert.data.users.Farmer;

@Entity
@Table (name="collection")
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown=true)
public class Collection {

	@Id
	@GeneratedValue ( strategy = GenerationType.AUTO )
	@Column (name="collection_id", nullable=false)
	private int collectionId;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name = "farmer", referencedColumnName="membership_id")
	private Farmer farmer;
	
	@ManyToOne(cascade=CascadeType.PERSIST, targetEntity=CollectionCenter.class)
	@JoinColumn (name = "cc_code", referencedColumnName="cc_code")
	private CollectionCenter collectionCenter;
	
	/*
	 * This weight is measured in kilogram.
	 */
	@Column (name = "quantity", nullable=false)
	private float quantity;
	
	@Column (name = "moisture_content", nullable=false)
	private float moistureContent;
	
	@Column (name = "date")
	private Date date;
	
	@Column (name = "timestamp")
	private Timestamp timestamp;
	
	@Column (name = "status", nullable=false)
	@Enumerated
	private CollectionStatus status;
	
	@Column (name = "batch_id")
	private long batchId;
	
	public void setCollectionId(int collectionId) {
		this.collectionId = collectionId;
	}
	public int getCollectionId() {
		return collectionId;
	}
	
	public void setFarmer(Farmer farmer) {
		this.farmer = farmer;
	}
	public Farmer getFarmer() {
		return farmer;
	}
	
	public void setCollectionCenter(CollectionCenter collectionCenter) {
		this.collectionCenter = collectionCenter;
	}
	public CollectionCenter getCollectionCenter() {
		return collectionCenter;
	}
	
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	public float getMoistureContent() {
		return moistureContent;
	}
	public void setMoistureContent(float moistureContent) {
		this.moistureContent = moistureContent;
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	public CollectionStatus getStatus() {
		return status;
	}
	public void setStatus(CollectionStatus status) {
		this.status = status;
	}
	public long getBatchId() {
		return batchId;
	}
	public void setBatchId(long batchId) {
		this.batchId = batchId;
	}
}
