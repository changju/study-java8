package com.study.java8.chap01;

public class main {

	public static void main(String[] args) {

		// 익명 내부 클래스 anonymous inner class
		RunSomething runSomethingInnerClass = new RunSomething() {
			@Override
			public void doIt() {
				// TODO Auto-generated method stub
				
			}
		};
		//////////////////////////////////////////////////////
		//////////////////////////////////////////////////////
		int baseNumber = 10;
		//pure function(순수 함수) 가 아니다.
		RunSomething runSomethingInnerClass2 = new RunSomething() {
			
			@Override
			public void doIt() {
				// baseNumber가 final 이라 가정을 하고 사용을 하고 있는 것이다.
				//System.out.println(baseNumber++);
				
			}
		};
		//////////////////////////////////////////////////////
		//////////////////////////////////////////////////////
		
		runSomethingInnerClass2.doIt();
		runSomethingInnerClass2.doIt();

		// 함수형 인터페이스를 구현할때 쓸 수 있는 람다형태의 표현식
		RunSomething runSomething1 = () -> System.out.println("hello");
		RunSomething runSomething2 = () -> {
			System.out.println("hello2");
			System.out.println("hello1");
		};
		
		runSomething1.doIt();
		runSomething2.doIt();
		
		// 
		RunSomethingRetArgs runSomethingRetArgs1 = (num) -> {
			return num + 10;
		};
		
		RunSomethingRetArgs runSomethingRetArgs2 = (num) -> num + 10;
		
		// 함수형 프로그래밍은 값은 값이 들어갔으면 같은 값이 나와야 한다.
		System.out.println(runSomethingRetArgs1.doIt(10));
		System.out.println(runSomethingRetArgs2.doIt(10));

	}

}
