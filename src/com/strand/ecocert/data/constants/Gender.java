package com.strand.ecocert.data.constants;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType( name = "gender")
@XmlEnum
public enum Gender {
	@XmlEnumValue("Male")
	MALE("Male"),
	@XmlEnumValue("Female")
	FEMALE("Female"),
	@XmlEnumValue("Other")
	OTHER("Other");
	
	private final String value;
	
	Gender(String v){
		value = v;
	}
	
	public String value() {
		return value;
	}
	public static Gender fromValue(String value) {
		for(Gender gender : Gender.values()) {
			if(gender.value.equals(value))
				return gender;
		}
		throw new IllegalArgumentException(value);
	}
}
