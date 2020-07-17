package com.autozi.common.utils.chart;

import java.util.Random;


public class ColorUtils {
	private static String[] colors =  new String[] { "0xC79810", "0xD01F3C", "0x356AA0", "0x87421F", "0xCC3399", "0x80a033", "0x9933CC" };

	public static String[] getColors() {
		return colors;
	}
	public static String getColor() {
		int randomInt = new Random().nextInt(colors.length);
		return colors[randomInt];
	}
}
