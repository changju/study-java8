package com.study.java8.chap1;

// 함수형 인터페이스는 인터페이스가 하나만 있어야 한다.
@FunctionalInterface
public interface RunSomethingRetArgs {
	int doIt(int num);

	static void printName() {
		System.out.println("cjlee printname");
	}

	default void printAge() {
		System.out.println("39");
	}
	
}
