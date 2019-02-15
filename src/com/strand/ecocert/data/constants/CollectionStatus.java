package com.strand.ecocert.data.constants;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType( name = "status")
@XmlEnum
public enum CollectionStatus {
	@XmlEnumValue("NotCollected")
	NOT_COLLECTED("NotCollected"),
	@XmlEnumValue("Collected")
	COLLECTED("Collected"),
	@XmlEnumValue("Transferred")
	TRANSFERRED("Transferred");
	
	private final String value;
	
	CollectionStatus(String v){
		value = v;
	}
	
	public String value() {
		return value;
	}
	public static CollectionStatus fromValue(String value) {
		for(CollectionStatus status : CollectionStatus.values()) {
			if(status.value.equals(value))
				return status;
		}
		throw new IllegalArgumentException(value);
	}
}