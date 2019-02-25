package com.strand.ecocert.data.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.strand.ecocert.data.constants.UserType;
import com.strand.ecocert.data.entity.CollectionCenter;

@Entity
@Table (name = "cc_person")
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown=true)
public class CCPerson extends User{
	
	@JoinColumn(name = "cc_code", referencedColumnName="cc_code")
	private CollectionCenter collectionCenter;
	
	@Column( name = "role")
	private String role;
	
	public CollectionCenter getCollectionCenter() {
		return collectionCenter;
	}
	public void setCollectionCenter(CollectionCenter collectionCenter) {
		this.collectionCenter = collectionCenter;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public UserType getUserType() {
		return UserType.COLLECTOR;
	}
	@Override
	public void setUserType(UserType userType) {
		super.setUserType(UserType.COLLECTOR);
	}
}
