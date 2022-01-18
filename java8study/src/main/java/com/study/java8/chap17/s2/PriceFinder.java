package com.study.java8.chap17.s2;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

import com.study.java8.chap17.s2.Shop;

// https://pjh3749.tistory.com/280
public class PriceFinder {
	private final List<Shop> shops = Arrays.asList(new Shop("CoolPang"), new Shop("HMarket"), new Shop("12th Street"),
			new Shop("YouMakePrice"), new Shop("FBay"));

	// 방법 1
	public List<String> findPrices(String product) {
		return shops.stream().map(shop -> String.format("%s 가격은 %.2f", shop.getName(), shop.getPrice(product)))
				.collect(Collectors.toList());
	}

	// 방법 2
	// 병렬 스트림 블로킹 호출
	public List<String> findPrices2(String product) {
		return shops.parallelStream().map(shop -> String.format("%s 가격은 %.2f", shop.getName(), shop.getPrice(product)))
				.collect(Collectors.toList());
	}

	// 방법 3
	/*
	 * 상점의 최저가를 String형 CompletableFuture를 반환하는 메서드를 만들었습니다. (반환형이 String의 리스트가 아닌
	 * CompletableFuture의 리스트입니다!). 추후에 CompletableFuture의 join 메서드로 반환받은 리스트를 조합해
	 * 결과를 냅니다. CompletableFuture의 join 은 Future의 get과 비슷합니다. 하지만 예외처리가 내부적으로 되어 있다는
	 * 차이가 있습니다. 결과적으로 방법2 병렬 스트림과 별 차이 없거나 더 느린결과를 볼 수 있습니다. 코드의 첫 문단에서 stream을 열었고
	 * 두 번째 return 문단에서도 stream을 열었습니다. 스트림의 게으른 특성 때문에 순차적으로 계산이 되기 때문에 속도가 원하는 만큼
	 * 빨라지지 않습니다.
	 */
	public List<String> findPrices3(String product) {
		List<CompletableFuture<String>> priceFutures = shops.stream()
				.map(shop -> CompletableFuture
						.supplyAsync(() -> String.format("%s 가격은 %.2f", shop.getName(), shop.getPrice(product))) )
				.collect(Collectors.toList());
		return priceFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
	}

	/*
	 * 데몬 스레드를 true로 주었습니다. 자바에서 일반 스레드가 실행중이면 자바 프로그램은 종료되지 않습니다. 일반 스레드가 한 없이 기다리게
	 * 되어 종료되지 않는 일이 벌어지면 문제가 생길 수 있습니다. 반면 데몬 스레드는 자바 프로그램이 종료될 때 강제로 실행이 종료될 수
	 * 있습니다. 성능은 같으므로 무한히 기다리는 것을 방지하기 위해 데몬 스레드로 설정을 해줍니다.
	 */

	private final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), (Runnable r) -> {
		Thread t = new Thread(r);
		t.setDaemon(true);
		return t;
	});

	// 두 번째 인자를 ThreadPoolFactory라는 인자를 넣어주어야 합니다. 람다식을 쓰지 않고 표현하면 아래와 같습니다.
	private final Executor executor2 = Executors.newFixedThreadPool(Math.min(shops.size(), 100), new ThreadFactory() {
		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		}
	});

	// 방법3 (Completable Future)의 성능 향상하기
	// 위에서 알아본 Executor로 CompletableFuture를 최적화 할 수 있습니다.
	// CompletableFuture 호출 (Executor 의 사용)
	/*
		CompletableFuture의 supplyAsync 메서드 마지막 인자로 설정한 executor를 넣어봅니다. 그리고 상점을 90여개를 추가를 합니다.
		결과는 놀랍게도(?) CompletableFuture의 executor로 스레드풀의 개수를 지정하여 1초만에 끝나게 됩니다.
		기존 방법 3의 CompletableFuture의 순차실행을 막고 스레드 별로 할당하여 일을 진행한 결과 1초가 걸리는 작업 90개를 90개의 스레드가 각각 실행을 하여 1초의 결과를 낳습니다. 즉 전체적인 계산이 블록되지 않게 한 것입니다.
		그렇다고 특정 스레드 개수가 중요한 게 아니라 애플리케이션에 맞는 숫자를 할당하는 것이 중요하겠죠.
	 * */
	public List<String> findPrices4(String product) {
		List<CompletableFuture<String>> priceFutures = shops.stream()
				.map(shop -> CompletableFuture.supplyAsync(
						() -> String.format("%s 가격은 %.2f", shop.getName(), shop.getPrice(product)), executor)
				).collect(Collectors.toList());
		return priceFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
	}

}
