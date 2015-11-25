package com.fredtm.resources.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class FredNumberFormat {

	private static String NUMBER_FORMAT_DECIMAL  = "#,##0.00";

	public static String formatTimeDecimal(double number,int scale,String prefix,String suffix){
		return formatNumberValue(new DecimalFormat(NUMBER_FORMAT_DECIMAL),number, scale, prefix, suffix);
	}
	
	
	public static String formatTimeFixed(double number,String prefix,String suffix){
		return formatNumberValue(NumberFormat.getIntegerInstance(),number, 0, prefix, suffix);
	}

	private static String formatNumberValue(NumberFormat numberFormat,double number,int scale, String prefix, String suffix) {
		StringBuilder builder = new StringBuilder();
		
		double doubleValue = new BigDecimal(number).setScale(scale, RoundingMode.HALF_UP).doubleValue();
		
		String formatted = numberFormat.format(doubleValue);
		
		builder.append(prefix);
		builder.append(formatted);
		builder.append(suffix);
		return builder.toString();
	}
	

	
	
}
