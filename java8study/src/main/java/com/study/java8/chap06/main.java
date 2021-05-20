package com.study.java8.chap06;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> names = new ArrayList<>();
		names.add("changju");
		names.add("gulgulri");
		names.add("tobi");
		names.add("boo");

		// Consumer 리턴 타입이 없다, 인자가 하나면 (s) 가 아니어도 된다.
		names.forEach(s -> {
			System.out.println(s);
		});

		// Consumer 리턴 타입이 없다.
		// method reference
		names.forEach(System.out::println);
		
		Spliterator<String> spliterator1 = names.spliterator();
		
		// 절만으로 쪼갠다.
		Spliterator<String> spliterator2 = spliterator1.trySplit();
		
		System.out.println("===============");
		System.out.println("===============");
		while(spliterator1.tryAdvance(System.out::println));
		System.out.println("===============");
		System.out.println("===============");
		while(spliterator2.tryAdvance(System.out::println));
		// Function<T, R> // 값을 하나 받아서 하나의 값을 리턴을 한다.
		//names.stream().map(String::toUpperCase).filter(s -> s.startsWith("K")).collect(Collectors.toSet());
		System.out.println("===============");
		names.removeIf(s -> s.startsWith("g"));
		
		long k = names.stream().map(String::toUpperCase).filter(s->s.startsWith("CH")).count();
		System.out.println("k: " + k);
		
		// Consumer 리턴 타입이 없다.
		// 함수형 인터페이스는 함수 형식이 맞으면 OK 이다.
		// forEach : 종료 오퍼레이션.
		names.forEach(System.out::println);
		names.forEach(main::outputs);
		
		// Comparator 함수 인터페이스..
		// * 많이 해깔리는 포인트임!!!
		// 이경우는 임의 객체의 인스턴스 메소드 참조하는 경우이다. 형태는 "타입::인스턴스 메소드" 를 따른다.
		// 즉 위의 o1이 "C" 라는 임의의 객체로 되어지고 인자로 "B"가 들어온다.
		//       o1에 "B" 라는 임의의 객체로 되어지고 인자로 "A"가 들어온다.
		Comparator<String> compareToIgnoreCase = String::compareToIgnoreCase;
		names.sort(compareToIgnoreCase.reversed()); 
	}
	
	public static void outputs(String str) {
		System.out.println(str);
	}

}
