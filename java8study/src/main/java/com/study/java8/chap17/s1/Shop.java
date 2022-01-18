package com.study.java8.chap17.s1;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Supplier;

public class Shop {
	private final String name;
	private final Random random;

	public Shop(String name) {
		this.name = name;
		random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
	}

	public double getPrice(String product) {
		return calculatePrice(product);
	}

	private double calculatePrice(String product) {
		delay();
		return random.nextDouble() * product.charAt(0) + product.charAt(1);
	}

	public String getName() {
		return name;
	}

	public static void delay() {
		int delay = 1000;
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public Future<Double> getAsyncPrice(String product) {
		CompletableFuture<Double> futurePrice = new CompletableFuture<>();
		new Thread(() -> {
			try {
				double price = calculatePrice(product);
				futurePrice.complete(price);
			} catch (Exception e) {
				futurePrice.completeExceptionally(e);
			}
		}).start();
		return futurePrice;
	}

	public Future<Double> getPrice2(String price) {
		return CompletableFuture.supplyAsync(() -> calculatePrice(price));
	}
	
	public Future<Double> getPrice3(String price) {
		return CompletableFuture.supplyAsync(supplyCjlee(10.1, 10.2));
	}
	
	public Supplier<Double> supplyCjlee(double a1, double a2) {
		return () -> {
			return a1 + a2;
		};
		
	}

}
