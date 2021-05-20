# The Java8
## 3부 인터페이스의 변
### 7. 자바 8 API의 기본메소드와 스태틱 메소드

  - 자바 8에서 추가한 기본 메소드로 인한 API 변화
    - Iterable의 기본 메소드
      - forEach()
      - spliterator()
    - Collection의 기본 메소드
      - stream() / parallelStream()
      - removeIf(Predicate)
      - spliterator()
    - Comparator의 기본 메소드 및 스태틱 메소드
      - reversed()
      - thenComparing()
      - static reverseOrder() / naturalOrder()
      - static nullsFirst() / nullLast()
      - static comparing()
    - 참고
      - https://docs.oracle.com/javase/8/docs/api/java/util/Spliterator.html
      - https://docs.oracle.com/javase/8/docs/api/java/lang/Iterable.html
      - https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html
      - https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html

    
