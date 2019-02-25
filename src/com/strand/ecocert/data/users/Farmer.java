package com.strand.ecocert.data.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.strand.ecocert.data.constants.UserType;
import com.strand.ecocert.data.entity.CollectionCenter;

@Entity
@XmlRootElement
@Table (name = "farmer", uniqueConstraints= {
		@UniqueConstraint(columnNames= {"membership_id"})
})
@NamedQueries({
	@NamedQuery( 
			name = "Farmer.findByMembershipId", 
			query = "select f from Farmer f where f.membershipId = :membershipId"
			)
})
@JsonIgnoreProperties(ignoreUnknown=true)
public class Farmer extends User{
	
	@Column(name = "membership_id", nullable=false)
	private String membershipId;
	
	@Column( name = "farm_code", nullable = false)
	private String farmCode;
	
	@Column( name = "farm_number", nullable = false)
	private int farmNumber;
		
	@Column (name = "num_of_coffee_plots", nullable = false)
	private int numOfPlots;

	@Column (name = "num_of_coffee_trees", nullable = false)
	private int numOfCoffeeTrees;
	
	@Column (name = "area_under_coffee", nullable = false)
	private double areaUnderCoffee;

	@Column (name = "total_area", nullable = false)
	private double totalArea;
	
	@Column (name = "latitude")
	private double latitude;
	
	@Column (name = "langitude")
	private double langitude;
	
	@JoinColumn(name = "cc_code", referencedColumnName="cc_code")
	private CollectionCenter collectionCenter;

	public String getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}

	public String getFarmCode() {
		return farmCode;
	}

	public void setFarmCode(String farmCode) {
		this.farmCode = farmCode;
	}

	public int getFarmNumber() {
		return farmNumber;
	}

	public void setFarmNumber(int farmNumber) {
		this.farmNumber = farmNumber;
	}

	public int getNumOfPlots() {
		return numOfPlots;
	}

	public void setNumOfPlots(int numOfPlots) {
		this.numOfPlots = numOfPlots;
	}

	public int getNumOfCoffeeTrees() {
		return numOfCoffeeTrees;
	}

	public void setNumOfCoffeeTrees(int numOfCoffeeTrees) {
		this.numOfCoffeeTrees = numOfCoffeeTrees;
	}

	public double getAreaUnderCoffee() {
		return areaUnderCoffee;
	}

	public void setAreaUnderCoffee(double areaUnderCoffee) {
		this.areaUnderCoffee = areaUnderCoffee;
	}

	public double getTotalArea() {
		return totalArea;
	}

	public void setTotalArea(double totalArea) {
		this.totalArea = totalArea;
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

	public CollectionCenter getCollectionCenter() {
		return collectionCenter;
	}

	public void setCollectionCenter(CollectionCenter collectionCenter) {
		this.collectionCenter = collectionCenter;
	}
	
	@Override
	public UserType getUserType() {
		return UserType.FARMER;
	}
	
	@Override
	public void setUserType(UserType userType) {
		super.setUserType(UserType.FARMER);
	}
}
