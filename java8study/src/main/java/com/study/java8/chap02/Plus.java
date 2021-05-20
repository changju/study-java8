package com.study.java8.chap02;

import java.util.function.Function;

public class Plus implements Function<Integer, Integer>{

	@Override
	public Integer apply(Integer arg0) {
		// TODO Auto-generated method stub
		return arg0 + 10;
	}
}
