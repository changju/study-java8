package com.study.java8.chap16;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class main {

	// CompletetableFuture 를 사용하여 여러 작업들을 조합하는 방법에 대하여 알아본다.
	// 이전 Future는 콜백을 줄 수가 없었기 때문에 비동기적인 작업에 대한 처리 두개를 연결하는게 쉽지 않았다.
	// 두 Future 같에 의존성이 있을 경우..
	public static void main(String[] args) throws InterruptedException, ExecutionException {

		//////////////////////////////////////////////////////////////////////////
		// hello 끝난다음에 world를 수행해야 할 경우.(의존 관계가 있는경우)
		CompletableFuture<String> fHello = CompletableFuture.supplyAsync(() -> {
			System.out.println("Hello " + Thread.currentThread().getName());
			return "Hello";
		});

		CompletableFuture<String> fWorld = CompletableFuture.supplyAsync(() -> {
			System.out.println("World " + Thread.currentThread().getName());
			return "World";
		});

		// fHello의 후 리턴 값을 전달 한다. fWorld 를 인자로 넣으면 에러 걸린다 이유는 함수의 형태가 안맞기 때문이다.
		// method reference 로 변경
		fHello.thenCompose(s -> getWorld(s));

		// 입력 값, 출력값이 한개인 mehtod reference 로 변경 후
		CompletableFuture<String> future = fHello.thenCompose(main::getWorld);
		System.out.println(future.get());

		////////////////////////////////////////////
		// 의존성이 없지만, 둘 다 값을 가져왔을 경우 처리를 하고 싶을때.
		// hello의 결과 world의 결과 즉, 두 결과 모두 완료가 되었을때 처리한다.
		CompletableFuture<String> cFuture1 = fHello.thenCombine(fWorld, (h, w) -> {
			return h + ".1." + w;
		});

		CompletableFuture<String> cFuture2 = fHello.thenCombine(fWorld, (h, w) -> h + ".2." + w);

		System.out.println("cFuture1: " + cFuture1.get());
		System.out.println("cFuture2: " + cFuture2.get());

		////////////////////////////////////////
		// task가 둘 이상일때 여러 task 를 모두 합쳐서 처리한다.
		// allOf에 넘긴 task 가 전부 끝났을때 처리.
		CompletableFuture<String> aoHello = CompletableFuture.supplyAsync(() -> {
			System.out.println("Hello " + Thread.currentThread().getName());
			return "Hello";
		});

		CompletableFuture<String> aoWorld = CompletableFuture.supplyAsync(() -> {
			System.out.println("World " + Thread.currentThread().getName());
			return "World";
		});

		// aoFuture의 thenAccept 하여 null 출력 후 .get으로 다시한번 호출.
		// 모든 TASK 들의 결과가 동일한 타입이라는 보장이 없고, 그중에 어떤것은 에러가 발생했을 수 있기 때문에 결과가 무의미하여 결과가
		// NULL이다.
		CompletableFuture<Void> aoFuture = CompletableFuture.allOf(aoHello, aoWorld).thenAccept(System.out::println);
		System.out.println(aoFuture.get());

		// 이 결과 값을 NULL이 아닌 모두 받고 싶을때.. 아래와 같이 처리
		// .thenApply 호출 시점에는 모든 Future의 처리가 끝났을 경우이다.
		List<CompletableFuture<String>> futures = Arrays.asList(aoHello, aoWorld);
		CompletableFuture[] futuresArray = futures.toArray(new CompletableFuture[futures.size()]);
		CompletableFuture<List<String>> results = CompletableFuture.allOf(futuresArray)
				.thenApply(v -> {
					// return futures.stream().map(f -> f.join()).collect(Collectors.toList());
					return futures.stream().map(CompletableFuture::join).collect(Collectors.toList());

		});
		List<String> objs = results.get();
		results.get().forEach(System.out::println);

		// 둘다 호출을 하는데, 둘 중 하나 먼저 호출되어 끝나는 것 처리한다.
		CompletableFuture<Void> future3 = CompletableFuture.anyOf(aoHello, aoWorld).thenAccept((s) -> {
			System.out.println("VVV " + s);
		});
		future3.get();
		
		// 둘다 호출을 하는데, 둘 중 하나 먼저 호출되어 끝나는 것 처리한다.
		CompletableFuture<Void> future4 = CompletableFuture.anyOf(aoHello, aoWorld).thenAccept(System.out::println);
		future3.get();

		// 비동기 처리작업을 하는데 에러가 발생하면..
		boolean throwError = true;
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
			if (throwError) {
				throw new IllegalArgumentException();
			}
			System.out.println("Hello" + Thread.currentThread().getName());
			return "hello";

		}).exceptionally(ex -> {
			System.out.println(ex);
			return "Error Default";
		});

		System.out.println(hello.get());

		// 비동기 처리작업을 하는데 에러가 발생하면, 좀 더 제너럴한 Handle로 처리하기.
		// 정상적인 경우, 에러를 발생했을 경우 처럼 두 경우 모두 Handle 내에서 정의하여 처리 가능하다.
		// BIFunction 이 들어오고 첫번째는 정상적인 경우, 두 번째인 경우 비정상적인 경우가 들어온다.
		CompletableFuture<String> hello2 = CompletableFuture.supplyAsync(() -> {
			if (throwError) {
				throw new IllegalArgumentException();
			}
			System.out.println("Hello2" + Thread.currentThread().getName());
			return "hello2";

		}).handle((result, ex) -> {
			// 에러일 경우
			if (ex != null) {
				System.out.println(ex);
				return "ERROR!";
			}
			return result;
		});

		System.out.println(hello2.get());
	}

	private static CompletableFuture<String> getWorld(String message) {
		return CompletableFuture.supplyAsync(() -> {
			System.out.println("World " + Thread.currentThread().getName());
			return message + " World";
		});
	}

}
