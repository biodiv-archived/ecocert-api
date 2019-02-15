package com.strand.ecocert.data.constants;

public enum MoistureContentCalculationType {
	SUM("Sum"),
	COUNT("Count"),
	MIN("Min"),
	MAX("Max"),
	AVERAGE("Average"),
	MANUAL("Manual");
	
	private final String value;
	
	MoistureContentCalculationType(String v) {
		value = v;
	}
	
	public String value() {
		return value;
	}
	
	public static MoistureContentCalculationType fromValue(String value) {
		for(MoistureContentCalculationType calculateType : MoistureContentCalculationType.values()) {
			if (calculateType.value.equals(value))
				return calculateType;
		}
		throw new IllegalArgumentException(value);
	}
}
