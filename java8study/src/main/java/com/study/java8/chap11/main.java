package com.study.java8.chap11;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class main {

	public static void main(String[] args) {

		Instant instant = Instant.now();
		System.out.println("Instant.now(): "+instant); // UTC & GMT 기준시에서 가져온다.

		// 현재 PC의 Default zone 을 가져와 설정한다.
		ZoneId zone = ZoneId.systemDefault();
		System.out.println("ZoneId.systemDefault(): " + zone);
		ZonedDateTime zoneDateTime = instant.atZone(zone);
		System.out.println("ZonedDateTime: " + zoneDateTime);
		
		////////////////////////////////////////////////////////////////////////////////////////////////
		// LocalDateTime.now(): 현재 시스템 Zone에 해당하는(로컬) 일시를 리턴한다.
		LocalDateTime now = LocalDateTime.now();
		System.out.println(now);
		
		////////////////////////////////////////////////////////////////////////////////////////////////
		// LocalDateTime.of(int, Month, int, int, int, int): 로컬의 특정 일시를 리턴한다.
		LocalDateTime bth = LocalDateTime.of(1982, Month.AUGUST, 17, 1, 1);
		System.out.println(bth);
		
		///////////////////////////////////////////////////////////////////////////////////////////////
		// 특정 Zone의 특정 일시를 리턴
		ZonedDateTime nowInKOrea = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
		System.out.println(nowInKOrea);

		// 기간의 차이를 표현하는 방법
		// Period : 휴먼용 시간 비교
		LocalDate today = LocalDate.now();
		LocalDate thisYearBirthday = LocalDate.of(2021, Month.JANUARY, 15);
		Period period = Period.between(today, thisYearBirthday);
		System.out.println("===PERIOD 휴먼용 시간 비교시 ====");
		System.out.println(period.getDays());

		Period until = today.until(thisYearBirthday);
		System.out.println(until.get(ChronoUnit.DAYS));

		// Period :휴먼용 시간 비교
		// Duration : 기계 시간 비교
		// 두 초간의 몇 초가 차이나는지 확인.
		Instant nowInstant = Instant.now();
		Instant plus = nowInstant.plus(10, ChronoUnit.SECONDS);
		System.out.println("===PERIOD 기계가 사용하는 비교시 ===");;
		Duration between = Duration.between(nowInstant, plus);
		System.out.println(between.getSeconds());

		// 현재 시간을 패턴에 맞게 포맷팅하기.
		LocalDateTime nowDateTime = LocalDateTime.now();
		System.out.println(nowDateTime);
		DateTimeFormatter MMddyyyy = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		System.out.println(nowDateTime.format(MMddyyyy));

		LocalDate parse = LocalDate.parse("07/15/1982", MMddyyyy);
		LocalDate plusDate = parse.plus(10, ChronoUnit.DAYS);
		System.out.println(parse);

		// 레거시 API 지원
		Date date = new Date();
		Instant instants = date.toInstant();
		Date newDate = Date.from(instants);
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		LocalDateTime dateTime = gregorianCalendar.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		ZonedDateTime zndDtm = gregorianCalendar.toInstant().atZone(ZoneId.systemDefault());
		GregorianCalendar oldGregorianCalendar = GregorianCalendar.from(zndDtm);

		// 레거시 TimeZone 지원
		ZoneId zoneId = TimeZone.getTimeZone("PST").toZoneId();
		TimeZone tz = TimeZone.getTimeZone(zoneId);

	}

}
