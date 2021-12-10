package com.study.java8.chap14;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class main {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		
		// 아래와 같이 정의도 가능하지만, 함수형 인터페이스 이기 때문에 람다로 정의도 가능하다.
		Callable<String> hello1 = new Callable<String>() {
			
			@Override
			public String call() throws Exception {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		// 함수형 인터페이스 이기 때문에 간단하게 정의 가능하다.
		Callable<String> hello = () -> {
			Thread.sleep(2000L);
			return "Hello";
		};
		
		Future<String> submit = executorService.submit(hello);
		System.out.println(submit.isDone());
		System.out.println("Started!!!!");
		
		// cancel 후 get을 하여 가져올 수 없다. cancel 상태에서 만약 get을 호출하면 Exception이 발생한다.
		//submit.cancel(true); // 현재 진행 중인 작업을 인터럽트 하며 종료
		//submit.cancel(false); // 현재 진행중인 작업을 기다리며 종료
		
		// Future 의 get() 은 결과 값을 가져올때까지 기다리는 블록킹 콜이다.
		// 마냥 기다릴 수 없으니 isDone() 으로 완료 여부를 알 수 있다.
		String retVal = submit.get(); // 결과 값을 가져올때까지 기다리는 블록킹 콜이다.
		
		System.out.println("End!!!!");
		//executorService.shutdown();
		
		
		// 함수형 인터페이스 이기 때문에 간단하게 정의 가능하다.
		Callable<String> hello2 = () -> {
			Thread.sleep(1000L);
			return "Hello2";
		};
		
		// 함수형 인터페이스 이기 때문에 간단하게 정의 가능하다.
		Callable<String> hello3 = () -> {
			Thread.sleep(2000L);
			return "Hello3";
		};
		
		// 함수형 인터페이스 이기 때문에 간단하게 정의 가능하다.
		Callable<String> hello4 = () -> {
			Thread.sleep(3000L);
			return "Hello4";
		};
		
		// 한꺼번에 실행을 하였다.
		// 리스트로 들어간 Callable 이 전부 실행이 완료 된 후 끝마치게 된다.
		// Thread pool 을 4개를 줘야 invokeAll이 정상수행이 된다.
		ExecutorService executorService2 = Executors.newFixedThreadPool(4);
		List<Future<String>> futures = executorService2.invokeAll(Arrays.asList(hello2, hello3, hello4));
		System.out.println("invokeAll");
		for(Future<String> f : futures) {
			System.out.println(f.get());
		}
		
		//invokeAny 는 모든것을 블럭하지 않고 가장 먼저 끝나는 녀석이 있으면 끝낸다.
		String s = executorService2.invokeAny(Arrays.asList(hello2, hello3, hello4));
		System.out.println("invokeAny");
		System.out.println(s);
		
		executorService.shutdown();
		executorService2.shutdown();

	}

}
