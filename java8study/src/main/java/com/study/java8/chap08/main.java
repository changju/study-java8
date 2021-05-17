package com.study.java8.chap08;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 *  직접 정의한 함수 인터페이스 말고도, JAVA에서 기본으로 제공하는 함수형 인터페이스를 알아본다.
 * Java에서 미리 정의해 놓은 것이 있다. 
 * 
   Function<T, R> // 값을 하나 받아서 하나의 값을 리턴을 한다.
	BiFunction<T, U, R> // T,U 를 입력값으로 받은 후 R로 리턴한다.
	Consumer<T> // 리턴이 없다
	Supplier<T>  : Supplier 함수적 인터페이스는 매개값은 없고 리턴값이 있는 getXXX() 메소드를 가지고 있다.
	Predicate<T> // Boolean 값으로 리턴이 된다.
	UnaryOperator<T> // 입력하는 값과 리턴하는 값이 같을 경우 사용한다.
	BinaryOperator<T> // 입력값이 3개일 경우 사용을한다.

 * */

//stream api 의 예제
public class main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<OnlineClass> springClasses = new ArrayList<>();
		springClasses.add(new OnlineClass(1, "spring boot", true));
		springClasses.add(new OnlineClass(2, "spring data jpa", true));
		springClasses.add(new OnlineClass(3, "spring mvc", false));
		springClasses.add(new OnlineClass(4, "spring core", false));
		springClasses.add(new OnlineClass(5, "rest api development", false));
		
		final String myName = "cjlee";
		
		System.out.println("spring으로 시작하는 수업");
		
		// 컨베어밸트에 onlineclass type의 인스턴스가 지나간다.
		// stream 타입으로 리턴하지 않는 것은 종료 오퍼레이션이다.
		springClasses.stream()
		.filter(oc -> oc.getTitle().startsWith("spring"))
		.forEach(oc -> {
			System.out.println(myName);
			System.out.println(oc.getId());
			}
		);
		
		
		System.out.println("close 되지 않는 수업");
		springClasses.stream()
		.filter(oc -> !oc.getClosed())
		.forEach(oc -> System.out.println(oc.getId()));
		
		/*
		springClasses.stream()
		.filter(Predicate.not(OnlineClass::isClosed))
		.forEach(oc -> System.out.println(oc.getId()));
		*/
		
		
		// map은 onlineclass 를 가지고 컨테이너밸트에 들어오지만, 나가는 것을 변경 할 수 있다.
		// 여기서는 oc to string(getTitle) 이다.
		// map 이후 foreach 에는 문자열이 들어온다.
		System.out.println("수업 이름만 모아서 스트림 만들기");
		springClasses.stream()
		.map(oc -> oc.getTitle())
		.forEach(System.out::println);
		
		List<OnlineClass> javaClasses = new ArrayList<>();
		javaClasses.add(new OnlineClass(6, "The java, test", true));
		javaClasses.add(new OnlineClass(7, "The java, code manipulation", true));
		javaClasses.add(new OnlineClass(8, "The java, t8 to 11", false));
		
		// cjleeEvents는 컨테이너밸트에  list 객체가 흘러가지만 이 객체를 모두 플랫 시킬것이다.(안에 있는 내용을 모두 꺼낼것이다.)
		List<List<OnlineClass>> cjleeEvents = new ArrayList<>();
		cjleeEvents.add(springClasses);
		cjleeEvents.add(javaClasses);
		
		System.out.println("두 수업 목록에 들어있는 모든 수업 아이디 출력");
		cjleeEvents.stream()
		 .flatMap(Collection::stream) // 리스트에 있는 OnlineClass를 flat 시킨다.
		 .forEach((oc) -> System.out.println(oc.getId()));
		
		System.out.println("10부터 1씩 증가하는 무제한 스트림 중에서 앞에 10개 빼고 최대 10개 까지만");
		Stream.iterate(10, i-> i + 1)
		.skip(10)
		.limit(10)
		.forEach(System.out::println);
		
		System.out.println("자바 수업 중에 Test가 들어있는 수업이 있는지 확인");
		boolean test = javaClasses.stream().anyMatch(oc -> oc.getTitle().contains("Test"));
		System.out.println(test);
		
		System.out.println("스프링 수업 중에 제목이 spring이 들어간 제목만 모아서 List로 만들기");
		List<Object> spring = springClasses.stream()
		  .filter(oc -> oc.getTitle().contains("spring"))
		  .map(OnlineClass::getTitle) //.map(oc -> oc.getTitle()) // OnlineClass를 받아서 문자열로 바꾼 것
		                              // map 은 기본으로 함수의 형식이 <R> Stream<R> map(Function<? super T, ? extends R> var1) 이다.
		  							  // 따라서 함수의 쓰임이 정해질때 ?가 채워지게 되는 것 이다.
		  						      // 여기서는 getTitle의 Return이 String 이기 때문에 String Type 으로 되는 것이다.
		  .collect(Collectors.toList());
		
		spring.forEach(System.out::println);
		
	}

}
