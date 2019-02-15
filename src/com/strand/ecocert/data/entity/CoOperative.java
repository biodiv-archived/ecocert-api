package com.strand.ecocert.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table (name = "co_operative")
@XmlRootElement
@NamedQueries({
	@NamedQuery( 
			name = "CoOperative.findByCoOperativeId",
			query = "select c from CoOperative c where c.coOperativeId = :coOperativeId"
			)
})
@JsonIgnoreProperties(ignoreUnknown=true)
public class CoOperative {
	
	@Id
	@Column (name="co_operative_id", nullable=false)
	private int coOperativeId;
	
	@Column(name = "co_operative_name", nullable=false)
	private String coOperativeName;
	
	public void setCoOperativeId(int coOperativeId) {
		this.coOperativeId = coOperativeId;
	}
	public int getCoOperativeId() {
		return coOperativeId;
	}

	public void setCoOperativeName(String coOperativeName) {
		this.coOperativeName = coOperativeName;
	}
	public String getCoOperativeName() {
		return coOperativeName;
	}
}
