package com.strand.ecocert.util;

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
}
