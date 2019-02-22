package com.strand.ecocert.data.users;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

import com.strand.ecocert.data.constants.Gender;
import com.strand.ecocert.data.constants.UserType;

@Entity
@Table (name = "user_table", uniqueConstraints= {
		@UniqueConstraint(columnNames = {"user_name"}),
		@UniqueConstraint(columnNames = {"email"})
})
@NamedQueries({
    @NamedQuery(name = User.FIND_BY_USERNAME_PASSWORD, query = "SELECT u FROM Farmer u WHERE u.userName = :userName AND u.password = :password")
})
@XmlRootElement
@Inheritance( strategy = InheritanceType.JOINED)
public class User {

	public static final String FIND_BY_USERNAME_PASSWORD = "FIND_BY_USERNAME_PASSWORD";
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column( name = "user_id", nullable=false)
	private int userId;
	
	@Column( name = "user_name", nullable=false)
	private String userName;
	
	@Column( name = "password", nullable=false)
	private String password;
	
	@Column( name = "date_of_birth")
	private Date dateOfBirth;
	
	@Column( name = "gender", nullable=false)
	private Gender gender;
	
	@Column( name = "cell_number")
	private String cellNumber;

	@Column( name = "email")
	private String email;

	@Column( name = "village")
	private String village;
	
	@Column( name = "sub_country")
	private String subCountry;
	
	@Column( name = "user_type")
	@Enumerated
	private UserType userType;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public String getCellNumber() {
		return cellNumber;
	}
	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
}
