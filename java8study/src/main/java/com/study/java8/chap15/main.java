package com.study.java8.chap15;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class main {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		Future<String> future = executorService.submit(() -> "hello");
		String retVal = future.get();
		executorService.shutdown();
		System.out.println(retVal);
		
		// CompletableFuture 는 Fork join poll에 있는 common thread를 사용하게 된다.
		
		CompletableFuture<String> cFuture1 = new CompletableFuture<String>();
		cFuture1.complete("cjlee1"); // cFuture 의 기본값을 명시적으로 cjlee 로 하였고, 끝내게 된 것이다.
		System.out.println(cFuture1.get());
		
		// static factory 방식 , cFuture 의 기본값을 명시적으로 cjlee 로 하였고, 끝내게 된 것이다.
		CompletableFuture<String> cFuture2 = CompletableFuture.completedFuture("cjlee2");
		System.out.println(cFuture2.get());
		
		// return 이 없는 것, future 만 정의한 것이기 때문에 아무런 일이 발생하지 않는다.  실행을 하게 하기 위해서는 get 같은 것으로 호출을 해줘야 한다.
		// 아래 CompletaFunture 는 Runnable
		CompletableFuture<Void> cFuture3 = CompletableFuture.runAsync(() -> {
			System.out.println("Hello " + Thread.currentThread().getName());
			
		});
		cFuture3.get();
		
		// return 이 있는 것, future 만 정의한 것이기 때문에 아무런 일이 발생하지 않는다.  get 같은 것으로 호출을 해줘야 한다.
		CompletableFuture<String> cFuture4 = CompletableFuture.supplyAsync(() -> {
			System.out.println("Hello4 " + Thread.currentThread().getName());
			return "Hello4";
		});
		
		System.out.println(cFuture4.get());
		
		
		// return 이 있는 것, future 만 정의한 것이기 때문에 아무런 일이 발생하지 않는다.  get 같은 것으로 호출을 해줘야 한다.
		// thenApply 콜백, 우리가 받은 값을 다른 값으로 변경하는 것.
		// java5 future에서는 callback 을 .get 호출 이전에 정의가 불가능 했으나, java8 의 CompletableFuture는 가능
		// thenApply 에는 리턴이 있는 Function 이 들어간다. thenApply는 main thread 이다.
		CompletableFuture<String> cFuture5 = CompletableFuture.supplyAsync(() -> {
			System.out.println("Hello5 " + Thread.currentThread().getName()); // ForkJoinPool worker thread
			return "Hello5";
		}).thenApply((s) -> {
			System.out.println("thenApply5 " + Thread.currentThread().getName()); // main thread
			return s.toUpperCase();
		});
		
		System.out.println("return cFuture5: " + cFuture5.get() +", threadId: " + Thread.currentThread().getName());
		
		// return 이 있는 것, future 만 정의한 것이기 때문에 아무런 일이 발생하지 않는다.  get 같은 것으로 호출을 해줘야 한다.
		// thenAccept 콜백, 리턴이 없는 Consumer 가 들어간다.
		CompletableFuture<Void> cFuture6 = CompletableFuture.supplyAsync(() -> {
			System.out.println("Hello6 " + Thread.currentThread().getName());
			return "Hello5";
		}).thenAccept((s) -> {
			System.out.println(s.toUpperCase());
			System.out.println("thenApply6 " + Thread.currentThread().getName());
		});
		
		System.out.println("return cFuture6: " + cFuture6.get()); // return 이 없기 때문에 null 리턴 됨.
		
		// return 이 있는 것, future 만 정의한 것이기 때문에 아무런 일이 발생하지 않는다.  get 같은 것으로 호출을 해줘야 한다.
		// thenRun 콜백, 인자값으로 Runnable 이다. 하여, 인자값 및 리턴값이 없다.
		CompletableFuture<Void> cFuture7 = CompletableFuture.supplyAsync(() -> {
			System.out.println("Hello7 " + Thread.currentThread().getName());
			return "Hello5";
		}).thenRun(() -> {
			System.out.println("thenRun " + Thread.currentThread().getName());
		});
		System.out.println("return cFuture7: " + cFuture7.get());
		
		// fork join poll 이 아닌 main thread가 만든 thread pool로  사용하기.
		ExecutorService executorService2 = Executors.newFixedThreadPool(4);
		
		CompletableFuture<Void> cFuture8 = CompletableFuture.supplyAsync(() -> {
			System.out.println("Hello8 " + Thread.currentThread().getName());
			return "Hello5";
		}, executorService2).thenRun(() -> {
			System.out.println("thenRun Hello8: " + Thread.currentThread().getName()); // main thread.
		});
		System.out.println(cFuture8.get());
		executorService2.shutdown();
		
		// fork join poll 이 아닌 만든것을 직접 사용하기.
		ExecutorService executorService3 = Executors.newFixedThreadPool(4);
		CompletableFuture<Void> cFuture9 = CompletableFuture.runAsync(() -> {
			System.out.println("Hello9 " + Thread.currentThread().getName());
		}, executorService3).thenRun(() -> {
			System.out.println("thenRun Hello9: " + Thread.currentThread().getName()); // main thread
		});
		System.out.println(cFuture9.get());
		executorService3.shutdown();
		
		// fork join poll 이 아닌 만든것을 직접 사용하기.
		// callback 또한 main 이 만든 특정, thread poll에 있는 thread 에서 사용하게 하기..
		ExecutorService executorService4 = Executors.newFixedThreadPool(4);
		CompletableFuture<Void> cFuture10 = CompletableFuture.runAsync(() -> {
			System.out.println("Hello10 " + Thread.currentThread().getName());
		}, executorService4).thenRunAsync(() -> {
			System.out.println("thenRunAsync Hello10: " + Thread.currentThread().getName()); // main thread 가 아님.
		}, executorService4);
		System.out.println(cFuture10.get());
		executorService4.shutdown();
		
	}

}
