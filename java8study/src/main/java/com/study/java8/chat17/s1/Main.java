package com.study.java8.chat17.s1;

import java.util.concurrent.Future;

public class Main {
	public static void main(String[] args) {
		Shop shop = new Shop("Jay Shop");
		long start = System.nanoTime();
		Future<Double> futurePrice = shop.getAsyncPrice("Jay's Mac");
		doSomethingElse();
		try {
			System.out.println("Price is " + futurePrice.get());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void doSomethingElse() { // do something else }
		
	}


}
