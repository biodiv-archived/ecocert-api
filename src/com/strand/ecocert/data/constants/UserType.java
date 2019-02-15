package com.strand.ecocert.data.constants;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType (name = "farmStatus")
@XmlEnum
public enum UserType {
	@XmlEnumValue("Farmer")
	FARMER("Farmer"),
	@XmlEnumValue("Collector")
	COLLECTOR("Collector"),
	@XmlEnumValue("Inspector")
	INSPECTOR("Inspector"),
	@XmlEnumValue("CoOperative")
	CO_OPERATIVE("CoOperative"),
	@XmlEnumValue("FactoryWorker")
	FACTORY_WORKER("FactoryWorker"),
	@XmlEnumValue("Other")
	OTHER("Other");
	
	private final String value;
	
	UserType(String v) {
		value = v;
	}
	
	public String value() {
		return value;
	}
	
	public static UserType fromValue(String value) {
		for(UserType userType : UserType.values()) {
			if (userType.value.equals(value))
				return userType;
		}
		throw new IllegalArgumentException(value);
	}
}
