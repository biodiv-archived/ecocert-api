package com.strand.ecocert.data.constants;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType (name = "farmStatus")
@XmlEnum
public enum FarmStatus {
	@XmlEnumValue("Organic")
	ORGANIC("Organic"),
	@XmlEnumValue("Transferred")
	TRANSFERRED("Transferred"),
	@XmlEnumValue("InCoversion")
	IN_CONVERSION("InCoversion"),
	@XmlEnumValue("Violated")
	VIOLATED("Violated"),
	@XmlEnumValue("Left")
	LEFT("Left");
	
	private final String value;
	
	FarmStatus(String v) {
		value = v;
	}
	
	public String value() {
		return value;
	}
	
	public static FarmStatus fromValue(String value) {
		for(FarmStatus farmStatus : FarmStatus.values()) {
			if (farmStatus.value.equals(value))
				return farmStatus;
		}
		throw new IllegalArgumentException(value);
	}
}
