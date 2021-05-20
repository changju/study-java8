package com.study.java8.chap05;
/*
public interface Foo {
	
	
	default void printNameUpperCase() {
		System.out.print(getName().toUpperCase());
	}

	static void printAnything() {
		System.out.print("printAnything");
	}

	String getName();
}
  
 */
public class DefaultFoo implements Foo {
	
	String name;

	public DefaultFoo(String name) {
		this.name = name;
	}

	@Override
	public void printName() {
		System.out.println(this.name);
	}

	// 구현체에서 재정의 할 수 있다. (해도되고 안해도되고..)
	@Override
	public void printNameUpperCase() {
		System.out.println(getName().toUpperCase());
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}
}