package com.study.java8.chap13;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* Executors
 * 고수준 (High-Level) Concurrency 프로그래밍
   쓰레드를 만들고 관리하는 작업을 애플리케이션에서 분리.
   그런 기능을 Executors에게 위임.
*/

public class main {
	public static void main(String[] args) {
		
		// Thread를 하나만 쓰는 executor
		// ExecutorService 는 다음작업이 들어올때까지 계속해서 기다리기 때문에 명시적으로 shutdown을 해야한다.
		// ExecutorService의 실행은 execute 나 submit 을 해야 한다.
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.execute(() -> {
			System.out.println("Thread : " + Thread.currentThread().getName());
		});
		
		// shutdown()은 graceful shutdown 이다. 
		// graceful shutdown은 마지막까지 잘 끝내고 shutdown시킨다.
		executorService.shutdown();
		
		// shutdownNow()는 Ungraceful shutdown 이다 강제적으로 바로 죽인다.
		executorService.shutdownNow();
		
		///////////////////////////////////////////////////////////
		/// Thread는 2개이지만 작업은 5개를 보낸다.
		/// Thread 2개를 가지고 5개의 작업을 처리한다.
		/// 2개 외의 작업은 Blocking Queue에 쌓이게 된다.
		/// 예로 들어 아래 5개 작업 동시처리시 2개는 작업 진행, 3개는 Blocking Queue에 있게됨.
		ExecutorService executorService1 = Executors.newFixedThreadPool(2);
		executorService1.submit(getRunnable("Hello1"));
		executorService1.submit(getRunnable("Hello2"));
		executorService1.submit(getRunnable("Hello3"));
		executorService1.submit(getRunnable("Hello4"));
		executorService1.submit(getRunnable("Hello5"));
		executorService1.shutdown();
		
		////////////////////////////////////////////////////////
		/// ScheduledExecutorService 는 ExecutorService 를 상속받았다.
		/// 스케쥴 5초 후 출력
		ScheduledExecutorService scdExecutorService = Executors.newSingleThreadScheduledExecutor();
		scdExecutorService.schedule(getRunnable("Hello"), 5, TimeUnit.SECONDS);
		scdExecutorService.shutdown();
		
		////////////////////////////////////////////////////////
		/// 스케쥴 1초 후 2초 간격으로 출력
		ScheduledExecutorService scdExecutorService1 = Executors.newSingleThreadScheduledExecutor();
		scdExecutorService1.scheduleAtFixedRate(getRunnable("Hello~~"), 1, 2, TimeUnit.SECONDS);
		//scdExecutorService1.shutdown();
		
		// 결과값을 가져오려면 Runnable은 안되고, Callable 을 사용하면 가능하다.
	}
	
	public static Runnable getRunnable(String message) {
		return () -> System.out.println(message + Thread.currentThread().getName());
	}

}
