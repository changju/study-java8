package com.study.java8.chap7;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class main {

	//
	public static void main(String[] args) {
		// STREAM이란 연속된 데이터를 처리를 위한 오퍼레이션의 모임.
		// STREAM 이란 데이터를 담고있는 저장소가 아니다.
		
		List<String> names = new ArrayList<>();
		names.add("changju");
		names.add("gulgulri");
		names.add("tobi");
		names.add("boo");
		
		// 스트림이 지나가는 것일뿐이지. 변경하는것이아니다.
		// 중계형 은 기본적으로 lazy 하다.
		// 중계형 오퍼레이터는 터미널 오퍼레티터가 오기까지 수행하지 않는다.
		// 중계형 오퍼레이터는 여러 오퍼레이션을 넣을 수 있고, 마지막에 종료형 오퍼레이터가 있어야 실행이 된다.
		// 0 또는 다수의 중개 오퍼레이션 (intermediate operation)과 한개의 종료 오퍼레이션 (terminal operation)으로 구성한다.
		Stream<String> stringStream = names.stream().map(String::toUpperCase);
		
		// collect 가 종료형 Operator이다.
		List<String> collect = names.stream().map(String::toUpperCase).collect(Collectors.toList());
		
		System.out.println("===============");
		System.out.println("===============");
		collect.forEach(System.out::println);
		
		System.out.println("===============");
		System.out.println("===============");
		names.forEach(System.out::println);
		
		System.out.println("===============");
		System.out.println("===============");
		List<String> collect2 = names.parallelStream().map(String::toUpperCase).collect(Collectors.toList());
		collect2.forEach(System.out::println);
		
		// 다른 Thread 에서 실행이 되는 것이다.
		// 데이터가 방대할 경우 병렬 스트림이 유용하다.
		List<String> collect3 = names.parallelStream().map((s) -> {
			System.out.println( s + " " + Thread.currentThread().getName() );
			return s.toUpperCase();
		}).collect(Collectors.toList());
		collect3.forEach(System.out::println);
		
	}

}
