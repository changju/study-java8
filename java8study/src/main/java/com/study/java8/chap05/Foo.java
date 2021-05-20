package com.study.java8.chap05;

public interface Foo {
	
	// 모든 인터페이스는 public 이다. 
	void printName();

	/**
	 * @implSpec 이 구현체는 getName()으로 가져온 문자열을 대문자로 바꿔 출력한다.
	 */
	default void printNameUpperCase() {
		// 구현체의 getName() 을 호출한다.
		System.out.print(getName().toUpperCase());
	}
	
// Object가 제공하는 기능(equals, hasCode)는 기본 메소드로 제공할 수 없다.
//	default String toString() {
//		
//	}

	static void printAnything() {
		System.out.print("printAnything");
	}

	String getName();
}
