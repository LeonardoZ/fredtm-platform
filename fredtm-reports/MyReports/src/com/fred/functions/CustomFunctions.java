package com.fred.functions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import net.sf.jasperreports.functions.annotations.Function;
import net.sf.jasperreports.functions.annotations.FunctionCategories;
import net.sf.jasperreports.functions.annotations.FunctionParameter;
import net.sf.jasperreports.functions.annotations.FunctionParameters;

@FunctionCategories({ com.fred.functions.FredCustomFunctions.class })
public class CustomFunctions {

	private static String NUMBER_FORMAT_DECIMAL  = "#,##0.00";
	private static String NUMBER_FORMAT_NO_DECIMAL  = "#,##0";
	
	@Function("FORMAT_WITH_DECIMAL")
	@FunctionParameters({ @FunctionParameter("number"),
			@FunctionParameter("scale"), 
			@FunctionParameter("prefix"),
			@FunctionParameter("suffix") 
	})
	public static String formatTimeDecimal(double number,int scale,String prefix,String suffix){
		return formatNumberValue(NUMBER_FORMAT_DECIMAL,number, scale, prefix, suffix);
	}
	
	
	@Function("FORMAT_WITHOUT_DECIMAL")
	@FunctionParameters({ @FunctionParameter("number"),
			@FunctionParameter("prefix"),
			@FunctionParameter("suffix") 
	})
	public static String formatTimeFixed(double number,String prefix,String suffix){
		return formatNumberValue(NUMBER_FORMAT_NO_DECIMAL,number, 0, prefix, suffix);
	}

	private static String formatNumberValue(String format,double number,int scale, String prefix, String suffix) {
		StringBuilder builder = new StringBuilder();
		
		double doubleValue = new BigDecimal(number).setScale(scale, RoundingMode.HALF_UP).doubleValue();
		
		DecimalFormat df = new DecimalFormat(NUMBER_FORMAT_DECIMAL);
		String formatted = df.format(doubleValue);
		
		builder.append(prefix);
		builder.append(formatted);
		builder.append(suffix);
		return builder.toString();
	}
	
}