package com.strand.ecocert.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@XmlRootElement
@Table (name = "collection_center", uniqueConstraints= {
		@UniqueConstraint(columnNames= {"cc_code"})
})
@NamedQueries({
	@NamedQuery( 
			name = "CollectionCenter.findByCcCode",
			query = "select c from CollectionCenter c where c.ccCode = :ccCode"
			)
})
@JsonIgnoreProperties(ignoreUnknown=true)
public class CollectionCenter {
	
	@Id
	@Column (name = "cc_id")
	private int ccId;
	
	@Column (name = "cc_code", nullable=false)
	private int ccCode;
	
	@Column (name = "cc_name", nullable=false)
	private String ccName;
	
	@Column (name = "type")
	private String type;
	
	@Column (name = "village")
	private String village;
	
	@Column (name = "sub_country")
	private String subCountry;
	
	@Column (name = "latitude")
	private float latitude;
	
	@Column( name = "longitude")
	private float longitude;
	
	@Column( name = "altitude")
	private float altitude;

	@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn (name = "co_operative_id", referencedColumnName="co_operative_id")
	private CoOperative coOperative;
	
	public int getCcId() {
		return ccId;
	}
	public void setCcId(int ccId) {
		this.ccId = ccId;
	}
	
	public int getCcCode() {
		return ccCode;
	}
	public void setCcCode(int ccCode) {
		this.ccCode = ccCode;
	}
	
	public String getCcName() {
		return ccName;
	}
	public void setCcName(String ccName) {
		this.ccName = ccName;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
	public float getAltitude() {
		return altitude;
	}
	public void setAltitude(float altitude) {
		this.altitude = altitude;
	}
	
	public CoOperative getCoOperative() {
		return coOperative;
	}
	public void setCoOperative(CoOperative coOperative) {
		this.coOperative = coOperative;
	}
}
