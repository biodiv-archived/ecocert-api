package com.strand.ecocert.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table (name = "factory")
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown=true)
public class Factory {

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	@Column (name = "factory_id", nullable=false)
	private int factoryId;
	
	@Column (name = "factory_name")
	private String factoryName;
	
	@Column (name = "village")
	private String village;
	
	@Column (name = "sub_country")
	private String subCountry;
	
	@Column (name = "latitude", nullable = false)
	private double latitude;
	
	@Column (name = "langitude", nullable = false)
	private double langitude;
	
	@ManyToOne
	@JoinColumn (name="co_operative_id", referencedColumnName="co_operative_id")
	private CoOperative coOperative;
	
	/**
	 * 
	 * Getter and setter method for factory
	 */

	public int getFactoryId() {
		return factoryId;
	}
	
	public void setFactoryId(int factoryId) {
		this.factoryId = factoryId;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	
	public String getSubCountry() {
		return subCountry;
	}
	public void setSubCountry(String subCountry) {
		this.subCountry = subCountry;
	}
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLangitude() {
		return langitude;
	}
	public void setLangitude(double langitude) {
		this.langitude = langitude;
	}
	
	public CoOperative getCoOperative() {
		return coOperative;
	}

	public void setCoOperative(CoOperative coOperative) {
		this.coOperative = coOperative;
	}
}
