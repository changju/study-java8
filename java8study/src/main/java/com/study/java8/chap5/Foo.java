package com.study.java8.chap5;

public interface Foo {
	
	// 모든 인터페이스는 public 이다. 
	void printName();

	/**
	 * @implSpec 이 구현체는 getName()으로 가져온 문자열을 대문자로 바꿔 출력한다.
	 */
	default void printNameUpperCase() {
		System.out.print(getName().toUpperCase());
	}

	static void printAnything() {
		System.out.print("printAnything");
	}

	String getName();
}
