package com.study.java8.chap09;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;

import com.study.java8.chap09.OnlineClass;

public class main {

	// optional 은 값이 비어있을 수 도 있고, 아니면 값 하나를 담고 있을 수 도 있다.
	// 그 자체로 값이 비어있는지 확인 할 수 있는 컨테이너 성격의 것들은 Optional 로 감싸지 말자
	//   Collection, Map, Stream Array, Optionsl 은 Optional로 감싸지 말자.(두번 감싸기 때문) 
	public static void main(String[] args) {
		List<OnlineClass> springClasses = new ArrayList<>();
		springClasses.add(new OnlineClass(1, "spring boot", true));
		springClasses.add(new OnlineClass(2, "spring data jpa", true));
		springClasses.add(new OnlineClass(3, "spring mvc", false));
		springClasses.add(new OnlineClass(4, "spring core", false));
		springClasses.add(new OnlineClass(5, "rest api development", false));
		
		//Optional.of(10) 도 가능하지만, OptionalInt.of(10) 을 사용함으로써, boxing unboxing 이 없도록 사용할 수 있기에 이를 권장한다.
		OptionalInt oi = OptionalInt.of(10); 
		
		// Optional 리턴하는 종료형 오퍼레이션. spring이 있을 수 도 있고 없을 수 도 있기 때문에 Optional이 잘 어울린다.
		Optional<OnlineClass> springOptional = springClasses.stream()
		.filter(oc -> oc.getTitle().startsWith("spring"))
		.findFirst();
		
		boolean present = springOptional.isPresent();
		System.out.println(present);
		
		//Optional 의 내부의 값을 꺼내오기, 비어있는 옵셔널일 경우 문제가 발생하기때문에 ifPresent 로 처리를 하는게 좋다.
		OnlineClass onlineClass = springOptional.get();
		System.out.println(onlineClass.getTitle());
		
		// Optional에 값이 있다면 다음 과정을 수행한다, 값이 있는지 체크하는 코드 없이 수행이 가능하다.
		// if문으로 체크하거나 꺼내는 코드 없이 가능하다.
		springOptional.ifPresent(oc -> {
			System.out.println(oc.getTitle());
			}
		);
		System.out.println("next start!!");
		springOptional.ifPresent(OnlineClass::getTitle);
		System.out.println("next end!!");
		
		Optional<OnlineClass> springOptional2 = springClasses.stream()
		.filter(oc -> oc.getTitle().startsWith("spring"))
		.findFirst();
		
		// Optional 내부에 값이 있으면 가져오고, 없으면 createNewClass 를 실행한다. 
		// 단, 어이없게 createNewClass는 반드시 실행이 된다. 그러나 객체는 있다면 가져온다.
		// 이러한 어이없는 상황이 안일어 났으면 할때 orElseGet 을 실행할 수 있다.
		OnlineClass onlineClass2 = springOptional2.orElse(createNewClass());
		System.out.println(onlineClass2.getTitle());

		Optional<OnlineClass> springOptional3 = springClasses.stream()
		.filter(oc -> oc.getTitle().startsWith("spring"))
		.findFirst();
		
		// Optional에 실제로 없으면 실행한다.
		OnlineClass onlineClass3 = springOptional3.orElseGet(() -> createNewClass());
		System.out.println(onlineClass3.getTitle());
		
		OnlineClass onlineClass4 = springOptional3.orElseGet(main::createNewClass);
		System.out.println(onlineClass4.getTitle());

		OnlineClass onlineClass5 = springOptional3.orElseThrow(() -> {
			return new IllegalArgumentException();
		});
		
		System.out.println(onlineClass5.getTitle());
		
		OnlineClass onlineClass6 = springOptional3.orElseThrow(IllegalArgumentException::new);
		
		System.out.println(onlineClass6.getTitle());
		/*
		 * 인자값 하나를 받아서 true, false를 리턴을 해준다.
		   Predicate<String> startWithChangju = (s) -> s.startsWith("cjlee");
		 * */
		// 아래 코드는 filter 함수에서 람다 함수에서 입력 인자로써 객체가 된다는 것을 의미한다.
		// 생각을 해보면 입력 인자의 객체의 isClose를 호출하여 boolean 값을 리턴 한다는 의미다.
		Optional<OnlineClass>  onlineClass7 = springOptional3.filter(OnlineClass::isClosed);
		
		// 입력 인자를 직접 명시하여 사용할때는 아래와 같이 사용한다.
		Optional<OnlineClass>  onlineClass8 = springOptional3.filter(oc -> oc.isClosed());
		System.out.println(onlineClass7.isPresent());
		
		// map이 Function 이고 public Integer getId() 는 인자가 없다.
		// 좀 이상하게 보일 수 있지만, Function 의 첫 번째 인자는 OnlineClass 이기에 리턴에 형식만 맞으면 된다.
		Optional<Integer> integer = springOptional3.map(OnlineClass::getId);
		
		// optional  을 리턴하면 flatMap을 사용한다.
		Optional<Optional<Progress>> inPregress = springOptional3.map(OnlineClass::getProgress);
		//Optional<Progress> inPregress2 = inPregress.orElseThrow(IllegalArgumentException::new);
		Optional<Progress> inPregress2 = inPregress.orElse(Optional.empty());
		
		// 한번 더 껍질을 까준다.
		Optional<Progress> flatPregress = springOptional3.flatMap(OnlineClass::getProgress);
		
		// stream  의 플랫맵은  인풋이 하나이지만, 아웃풋이 여러개일때 사용한다.
		
		
		
	}

	public static OnlineClass createNewClass() {
		System.out.println("createNewClass");
		return new OnlineClass(10, "NEW CLASS", true);
	}

}
