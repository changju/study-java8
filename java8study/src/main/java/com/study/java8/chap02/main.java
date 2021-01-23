package com.study.java8.chap02;

import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/*
 *  직접 정의한 함수 인터페이스 말고도, JAVA에서 기본으로 제공하는 함수형 인터페이스를 알아본다.
 * Java에서 미리 정의해 놓은 것이 있다. 
 * 
    Function<T, R> // 값을 하나 받아서 하나의 값을 리턴을 한다.
	BiFunction<T, U, R> // T,U 를 입력값으로 받은 후 R로 리턴한다.
	Consumer<T> // 리턴이 없다
	Supplier<T> : Supplier 함수적 인터페이스는 매개값은 없고 리턴값이 있는 getXXX() 메소드를 가지고 있다.
	Predicate<T> // Boolean 값으로 리턴이 된다.
	UnaryOperator<T> // 입력하는 값과 리턴하는 값이 같을 경우 사용한다.
	BinaryOperator<T> // 같은 타입의 (인자 2개, 리턴1개)  경우 사용 한다.

 * */
public class main {
	public static void main(String[] args) {
		Plus plus = new Plus();
		System.out.println(plus.apply(10));

		Function<Integer, Integer> plus20 = (i) -> {
			return i + 20;
		};
		System.out.println(plus20.apply(20));

		Function<Integer, Integer> plus30 = (i) -> i + 10;
		System.out.println(plus30.apply(30));
		
		Function<Integer, Integer> multiply2 = (i) -> i * 2;
		
		// 두 함수를 조합하기.(default method이다) multiply2 수행 후 multiply2의 리턴 값으로 plus20 를 수행 한다.
		Function<Integer, Integer> multiply2AndPlus10 = plus20.compose(multiply2);
		System.out.println("[Compose]");
		System.out.println(multiply2AndPlus10.apply(2));
		
		// 두 함수를 조합하기.(default method이다) plus20 수행 후 리턴 값으로 multiply2 를 수행 한다.
		System.out.println("[andThen]");
		System.out.println(plus20.andThen(multiply2).apply(2));
		
		// 리턴 타입이 없다.
		Consumer<Integer> printT = (i) -> System.out.println(i);
		System.out.println("Consumer: 10");
		printT.accept(10);
		
		// 받아오고자하는 값을 정의를 한다.
		Supplier<Integer> get10 = () -> 10;
		System.out.println("Supplier: 10");
		System.out.println(get10.get());
		
		// 인자값 하나를 받아서 true, false를 리턴을 해준다.
		Predicate<String> startWithChangju = (s) -> s.startsWith("cjlee");
		System.out.println("Predicate: cjlee");
		System.out.println(startWithChangju.test("cjlee"));
		
		// 입력값, 출력값이 같을때는 깔끔하게 UnaryOperator 를 사용 할 수 있다.
		UnaryOperator<Integer> multiplyUnaryOperator = (i) -> i * 2;
		System.out.println("UnaryOperator: 10");
		System.out.println(multiplyUnaryOperator.apply(10));
		
		// 3개의 타입 (인자 2개, 리턴1개) 가 같을꺼라는 가정하에 제공
		BinaryOperator<Integer> multiplyUnaryOperators = (i1, i2) -> i1 * i2;
		System.out.println(multiplyUnaryOperators.apply(10, 20));
		
	}
}
