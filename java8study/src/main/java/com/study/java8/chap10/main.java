package com.study.java8.chap10;

import java.util.Calendar;
import java.util.Date;

import java.util.GregorianCalendar;
public class main {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		// 이전의 Date객체는 mutable 하기 때문에 thread safe하지 않다.
		Date date = new Date();
		
		// Date 에서 time 을 가져오는 것이 이상하다.
		// 기계용 시간
		long time  = date.getTime(); // timestamp(ms) 기계용 시간을 제공함.
		System.out.println(date);
		System.out.println(time);
		
		Thread.sleep(1000 * 3);
		
		// 아래 처럼 mutable 객체이다 (객체를 변경 할 수 있다, 즉, 멀티 스레드 환경에서 쓰기는 쉽지 않다)
		// Thread 두 개가 있다면, 같이 접근하여 변경을 하게되면 문제가 될 수 있다.
		Date after3Seconds = new Date();
		System.out.println(after3Seconds);
		after3Seconds.setTime(time);
		System.out.println(after3Seconds);
		
		// 버그 발생할 여지가 많다. (타입 안정성이 없고, 월이 0부터 시작한다거나..)
		// 아래 형식은 Type safe 하지 않다고 한다.
		Calendar cjleeBirthDay = new GregorianCalendar(1990, 02 , 17);
		
		
	}

}
