/**
 * 
 */
package com.putri.fauziah.usamapapp;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public final class NumberUtil {
	private NumberUtil() {
		
	}

	public static String toCurrDigitGrouping(String number){
		BigDecimal dec = new BigDecimal(number);
		NumberFormat formatter = new DecimalFormat("#,###");
		return formatter.format(dec);
	}
}
