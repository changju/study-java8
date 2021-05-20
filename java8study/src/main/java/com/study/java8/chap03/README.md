# The Java8
## 2부 함수형 인터페이스와 람다 표현식
### 4. 람다 표현식


  - 람다
    - (인자 리스트) -> {바디}
  - 인자 리스트
    - 인자가 없을 때 : ()
    - 인자가 한개일 때 : (one) 또는 one
    - 인자가 여러개 일 때 : (one, two)
    - 인자의 타입은 생략 가능, 컴파일러가 추론(infer) 하지만 명시할 수도 있다. (Integer one, Integer two)
    - 화살표 오른쪽에 함수 본문을 정의한다.
    - 여러 줄인 경우에 {} 를 사용해서 묶는다.
    - 한 줄인 경우에 생략 가능, return도 생략 가능.

  - 변수 캡처 (Variable Capture)
    - 로컬 변수 캡처
      - final이거나 effective final 인 경우에만 참조할 수 있다.
      - 그렇지 않을 경우 concurrency 문제가 생길 수 있어서 컴파일러가 방지한다.

    - effective final
      - 이것도 역시 자바 8 부터 지원하는 기능으로 "사실상" final 인 변수
      - final 키워드 사용하지 않은 변수를 익명 클래스 구현체 또는 람다에서 참조할 수 있다.

    - 익명 클래스 구현체와 달리 '쉐도윙' 하지 않는다.
      - 익명 클래스는 새로 스콥을 만들지만, 람다는 람다를 감싸고 있는 스콥과 같다.
  - 참고
    - https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html#shadowing
    - https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html
