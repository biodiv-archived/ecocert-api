package com.strand.ecocert.data.process;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.strand.ecocert.data.entity.Factory;

@Entity
@Table (name="batch_production")
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown=true)
public class BatchProduction {

	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	@Column (name = "batch_id")
	private int batchId;
	
	@JoinColumn( name = "factory_id", referencedColumnName = "factory_id")
	private Factory factoryId;
	
	@Column (name = "weight", nullable=false)
	private float weight;
	
	@Column (name = "moistureContent", nullable=false)
	private float moistureContent;
	
	@Column (name = "transfer_time_stamp", nullable=false)
	private Timestamp transferTimestamp;
	
	@OneToMany (targetEntity=Collection.class)
	private List<Collection> productions;

	
	/**
	 * Setter and getter methods. 
	 */
	
	public int getBatchId() {
		return batchId;
	}

	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getMoistureContent() {
		return moistureContent;
	}

	public void setMoistureContent(float moistureContent) {
		this.moistureContent = moistureContent;
	}

	public Timestamp getTransferTimestamp() {
		return transferTimestamp;
	}

	public void setTransferTimestamp(Timestamp transferTimestamp) {
		this.transferTimestamp = transferTimestamp;
	}

	public List<Collection> getproductions() {
		return productions;
	}

	public void setproductions(List<Collection> productons) {
		this.productions = productons;
	}

	public Factory getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(Factory factoryId) {
		this.factoryId = factoryId;
	}
}
