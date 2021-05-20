package com.study.java8.chap01;


// 함수형 인터페이스는 인터페이스가 하나만 있어야 한다.
@FunctionalInterface
public interface RunSomething {
	
	// 추상 메서드가 1개이면 그것이 함수형 인터페이스이다.
	void doIt();

	// java8 부터 새로운 기능 (인터페이스 임에도 불구하고 정의가 가능하다)
	static void printName() {
		System.out.println("cjlee printname");
	}

	default void printAge() {
		System.out.println("39");
	}
	
}
