package com.study.java8.chap10;

import java.util.Calendar;
import java.util.Date;

import java.util.GregorianCalendar;
public class main {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		// 이전의 Date객체는 mutable 하기 때문에 thread safe하지 않다.
		Date date = new Date();
		
		// 기계용 시간
		long time  = date.getTime();
		System.out.println(date);
		System.out.println(time);
		
		Thread.sleep(1000 * 3);
		
		Date after3Seconds = new Date();
		System.out.println(after3Seconds);
		after3Seconds.setTime(time);
		System.out.println(after3Seconds);
		
		// 버그 발생할 여지가 많다. (타입 안정성이 없고, 월이 0부터 시작한다거나..)
		// 아래 형식은 Type safe 하지 않다고 한다.
		Calendar cjleeBirthDay = new GregorianCalendar(1990, 02 , 17);
		
		
	}

}
