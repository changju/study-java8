package com.study.java8.chap3;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		main m = new main();
		m.run();

	}

	private void run() {
		int baseNumber = 10; // 사실상 final 이다(로컬클래스, 익명클래스, 람다 동일 적용). final int baseNumber = 10; 과 같다. 

		// [로컬 클래스], 스콥은 로컬 클래스가 우선이다.
		class LocalClass {
			void printBaseNumber() {
				int baseNumber = 11; 
				System.out.println(baseNumber);
			}
		}
		
		
		// [익명 클래스], 스콥은 익명 클래스가 우선이다. (아래 아규먼트의 baseNumber가 우선)
		Consumer<Integer> integerConsumer = new Consumer<Integer>() {

			@Override
			public void accept(Integer baseNumber) {
				System.out.println(baseNumber);
			}
		};
		
		// [람다], 람다의 스콥은 lambda 를 가지고 있는 run 함수와 같다.
		// run 함수 내 동일한 이름의 변수를 정의 할 수 없다.
		IntConsumer printInt = (i) -> { // (baseNumber) 로 하면 에러가 발생한다. 왜냐~ 스콥이 같기 때문이다.
			// 람다에서 로컬변수 참조
			System.out.println(i + baseNumber);
		};

		printInt.accept(10);

	}

}
