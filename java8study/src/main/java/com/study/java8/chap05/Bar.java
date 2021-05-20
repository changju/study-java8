package com.study.java8.chap05;

public interface Bar extends Foo{
	
	// 인터페이스를 상속받는 인터페이스에서 default 메서드를 다시 추상 메소드로 변경할 수 있다
	// 아래와 같이 선언을 하게 되면, Bar 를 impl 하는 클래스는 printNameUpperCase 를 모두 정의해야 하게된다.
	void printNameUpperCase();
}
