package com.study.java8.chap4;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class main {

	public static void main(String[] args) {
		
		// UnaryOperator 입력과 출력이 같은 것 기술
		UnaryOperator<String> ugreeting = (s) -> "hi"  + s;
		System.out.println("--- " + ugreeting.apply("hello"));
		
		Greeting greeting = new Greeting();
		
		// 함수형 인터페이스는 동작하는 함수의 형식(함수 심볼) 이 맞으면 ok 이다.
		// UnaryOperator : 입력값과 결과값이 같은 경우 사용
		// 참조 : 형식이 같은 함수를 참조한다.
		// static 메서드 참조
		UnaryOperator<String> hiMethodOperator = Greeting::hi;
		
		// 인스턴스 메서드 참조
		UnaryOperator<String> h = greeting::hello;
		
		// apply 를 해야 실제로 실행이 된다.
		System.out.println(h.apply("changju"));
		
		// 입력값은 없지만, 결과값이 있는경우.(아래  .get 을 해야 만들어진다, 왜냐 참조를 하는 것 이기 때문에)
		Supplier<Greeting> newGreeting= Greeting::new;
		
		Greeting gree = newGreeting.get();
		System.out.println(gree.getName());
		
		// 문자열을 받는 생성자 참조
		Function<String, Greeting> cjleeGretting = Greeting::new;
		Greeting ggg = cjleeGretting.apply("cjlee");
		System.out.println(ggg.getName());
		
		String[] names = {"C", "B", "A"};
		
		// @Contract(pure=true)
		// public int compareToIgnoreCase(String str)
		// names[0] 를 참조하여 다음문자를 비교한다.
		Arrays.sort(names, String::compareToIgnoreCase );
		System.out.println(Arrays.toString(names));
		

	}

}
