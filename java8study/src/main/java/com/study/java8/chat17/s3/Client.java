package com.study.java8.chat17.s3;

import com.study.java8.chat17.s3.PriceFinder;

public class Client {
	public static void main(String[] args) {
		PriceFinder priceFinder = new PriceFinder();
		long start = System.nanoTime();
		System.out.println(priceFinder.findPrices("Mac"));
		long duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("완료 시간(stream): " + duration + " msecs");
		

	}
}
