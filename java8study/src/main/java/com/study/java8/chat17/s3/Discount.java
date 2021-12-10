package com.study.java8.chat17.s3;

public class Discount {

	public enum Code {
		NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

		private final int percentage;

		Code(int percentage) {
			this.percentage = percentage;
		}
	}

	public static String applyDiscount(Quote quote) {
		return quote.getShopName() + " price is " + Discount.apply(quote.getPrice(), quote.getDiscountCode());
	}

	private static double apply(double price, Code code) {
		delay();
		return format(price * (100 - code.percentage) / 100);
	}
	
	private static double format(double d) {
		return d;
	}

	public static void delay() {
		int delay = 1000;
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
