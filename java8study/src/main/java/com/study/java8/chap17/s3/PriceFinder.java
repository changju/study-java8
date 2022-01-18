package com.study.java8.chap17.s3;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

import com.study.java8.chap17.s2.Shop;
import com.study.java8.chap17.s3.Quote;

public class PriceFinder {


	private final List<Shop> shops = Arrays.asList(new Shop("CoolPang"), new Shop("HMarket"), new Shop("12th Street"),
			new Shop("YouMakePrice"), new Shop("FBay"));
	
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
						.supplyAsync(() -> String.format("%s 가격은 %.2f", shop.getName(), shop.getPrice(product))))
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

	// 블로킹 호출
	// 처음으로 블로킹으로 작업을 호출해보겠습니다.
	public List<String> findPrices(String product) {
		return shops.stream().map(shop -> shop.getPrice(product)).map(Quote::parse).map(Discount::applyDiscount)
				.collect(Collectors.toList());
	}
	
	/*
	 조금은 복잡해 보일 수 있는 코드 입니다. 하나씩 살펴봅시다.
	 첫 번째 map에서는 상점의 가격을 가져오는 (딜레이 1초) 코드입니다. executor를 주어 모든 작업이 1초에 끝나게 되겠죠. 
	 그것을 (future) thenApply에서 Quote의 parse를 호출합니다.
	 thenApply는 Completablefuture의 동작이 완료가 되어야 적용이 됩니다. 
	 쉽게 말해 synchronous mapping 이라고 생각하면 됩니다.
	 마지막 map에서는 할인가격을 가져오는 원격 실행 (딜레이 1초)이 필요합니다. 
	 이것을 연쇄적인 비동기로 만들 수 있습니다. thenCompose를 쓰게 된다면 두 번째 CompletableFuture는 첫 번째 CompletableFuture의 결과를 계산의 입력으로 받습니다.
	 결과적으로 List<CompletableFuture> 을 받습니다. 그리고 마지막으로 CompletableFuture의 Join을 호출하여 결과를 모아줍니다. 2초가 걸리네요.
	 * 
	조합하기
	thenCompose(): 두 작업이 서로 이어서 실행하도록 조합
	thenCombine(): 두 작업을 독립적으로 실행하고 둘 다 종료 했을 때 콜백 실행
	allOf(): 여러 작업을 모두 실행하고 모든 작업 결과에 콜백 실행
	anyOf(): 여러 작업 중에 가장 빨리 끝난 하나의 결과에 콜백 실행

	 * */
	public List<String> findPrices2(String product) {
		List<CompletableFuture<String>> priceFutures = shops.stream()
				.map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
				.map(future -> future.thenApply(Quote::parse))
				.map(future -> future.thenCompose(
						quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)))
				.collect(Collectors.toList());
		return priceFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
	}

}
