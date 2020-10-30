package com.study.java8.chap5;

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
