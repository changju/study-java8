package com.study.java8.chap17.s2;

public class Client {
	public static void main(String[] args) {
		PriceFinder priceFinder = new PriceFinder();
		long start = System.nanoTime();
		System.out.println(priceFinder.findPrices("Mac"));
		long duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("완료 시간(stream): " + duration + " msecs");
		
		start = System.nanoTime();
		System.out.println(priceFinder.findPrices2("Mac"));
		duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("완료 시간(parallelStream): " + duration + " msecs");
		
		
		start = System.nanoTime();
		System.out.println(priceFinder.findPrices3("Mac"));
		duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("완료 시간(parallelStream): " + duration + " msecs");
		
		
		start = System.nanoTime();
		System.out.println(priceFinder.findPrices4("Mac"));
		duration = (System.nanoTime() - start) / 1_000_000;
		System.out.println("완료 시간(parallelStream): " + duration + " msecs");
	}
}
