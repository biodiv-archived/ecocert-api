package com.strand.ecocert.util;

import java.security.MessageDigest;
import java.util.Base64;

import com.strand.ecocert.data.constants.MoistureContentCalculationType;

public class Utility {
	
	public static float getAggregateValue(float[] column, MoistureContentCalculationType type) {
		float retValue = 0;
		int columnSize = column.length;
		switch (type) {
			case SUM:
				for(int i=0; i<columnSize; i++) 
					retValue += column[i];
				break;
			case AVERAGE:
				for(int i=0; i<columnSize; i++) 
					retValue += column[i];
				retValue /= columnSize;
				break;
			case COUNT:
				retValue = columnSize;
				break;
			case MIN:
				retValue = Float.MAX_VALUE;
				for(int i=0; i<columnSize; i++) 
					retValue = retValue > column[i] ? column[i] : retValue;
				break;
			case MAX:
				retValue = -Float.MAX_VALUE + 1;
				for(int i=0; i<columnSize; i++) 
					retValue = retValue < column[i] ? column[i] : retValue;
				break;
			default:
				break;
		}
		return retValue;
	}
	
	public static String digestPassword(String plainTextPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(plainTextPassword.getBytes("UTF-8"));
            byte[] passwordDigest = md.digest();
            return new String(Base64.getEncoder().encode(passwordDigest));
        } catch (Exception e) {
            throw new RuntimeException("Exception encoding password", e);
        }
    }
}
