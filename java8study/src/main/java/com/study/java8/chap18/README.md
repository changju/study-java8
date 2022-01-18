# Java8
## 18부 Collector
### 1. Stream.collect()
  - Stream.collect()는 데이터의 중간 처리 후 마지막에 원하는 형태로 변환해 주는 역할을 합니다. collector 는 아래와 같은 기능들을 제공하며 이외에도 많은 기능들을 제공합니다.
    - stream 요소들을 List, Set, Map 자료형으로 변환
    - stream 요소들의 결합(joining)
    - stream 요소들의 통계(최대, 최소, 평균값 등...)
    - stream 요소들의 그룹화와 분할

### 2. Collectors
  - 미리 정의된 모든 구현은 Collectors 클래스에서 찾을 수 있습니다. 가독성을 높이기 위해 다음과 같이 정적 import를 사용하는 것이 일반적입니다.
  ```java
    import static java.util.stream.Collectors.*;
  ```
  또는 아래와 같이 사용할 Collector만 개별로 선언하여 사용하기도 합니다.
  ```java
    import static java.util.stream.Collectors.toList;
    import static java.util.stream.Collectors.toMap;
    import static java.util.stream.Collectors.toSet;
  ```
  실습 예제에서는 아래의 List를 가지고 실습을 진행합니다.
  ```java
    List<String> sampleList = Arrays.asList("a", "bb", "ccc", "dddd", "a");
  ```
#### 2.1 Collectors.toList()
  - 모든 Stream의 요소를 List 인스턴스로 수집하는 데 사용할 수 있습니다. 해당 메서드는 특정한 List를 구현하는 것이 아니며 이것을 좀 더 잘 제어하려면 toCollection을 대신 사용할 수 있습니다.
  아래는 Stream의 모든 요소를 List 인스턴스로 수집하는 예제입니다.
  ```java
    List<String> result1 = sampleList .stream().collect(toList());
  ```

#### 2.2 Collectors.toSet()
  - 모든 Stream의 요소를 Set 인스턴스로 수집하는 데 사용할 수 있습니다. 해당 메서드는 특정한 Set를 구현하는 것이 아니며 이것을 좀 더 잘 제어하려면 toCollection을 대신 사용할 수 있습니다.
  아래는 Stream의 모든 요소를 Set 인스턴스로 수집하는 예제입니다. Set에는 중복 요소가 없어 서로 같은 요소가 포함된 경우 결과에 한번만 나타납니다.
  ```java
    Set<String> result = sampleList.stream.collect(toSet());
    assertThat(result).hasSize(4);
  ```

#### 2.3 Collectors.toCollection()
  - 위에서 설명했듯 toList, toSet, Collector는 특정한 List, Set의 구현을 지정할 수 없습니다. 특정 Collection을 구현하려면 toCollection collector를 사용하면 됩니다.
  ```java
    List<String> lists = sampleList.stream().collect(toCollection(LinkedList::new));
    Set<String> lists = sampleList.stream().collect(toCollection(TreeSet::new));
  ```
    변경 불가능한 컬렉션에서는 작동하지 않습니다. 이 경우 사용자 정의 수집기 구현을 작성하거나 collectAndThen을 사용해야 합니다. 

#### 2.4 Collectors.toMap()
  - toMap Collector는 stream요소를 Map 인스턴스로 수집하는 데 사용할 수 있습니다. 이렇게 하려면 두 가지 기능을 제공해야 합니다. 
    - keyMapper
    - valueMapper
      - keyMapper는 Stream요소에서 Map의 Key를 추출하는 데 사용되며 valueMapper는 지정된 키와 관련된 값을 추출하는데 사용됩니다. 
    아래는 문자열을 key로 저장하고, 길이를 value로 하여 Map에 요소를 수집하는 예제입니다.
  ```java
    Map<String, Integer> result = sampleList.stream().collect(toMap(Function.identity(), String::length))
  ```
  - Function.identity()는 stream에 전달된 요소의 값을 그대로 반환합니다.

  - 만약 collection에 중복 요소가 포함되어 있으면 toSet과 달리 toMap은 중복을 자동으로 필터링하지 않습니다. 따라서 중복 키가 보이면 즉시 IllegalStateException이 발생합니다.
  ```java
    List<String> listWithDuplicates = Arrays.asList("a", "bb", "c", "d", "bb");
    assertThatThrownBy(() -> { listWithDuplicates.stream().collect(toMap(Function.identity(), String::length));}).isInstanceOf(IllegalStateException.class);
  ```
   key 충돌이 있는 경우 다른 signature와 함께 toMap을 사용해야 합니다.
  ```java
    Map<String, Integer> result = sampleList.stream().collect(toMap(Function.identity(), String::length, (item, identicalItem) -> item));
  ```
  여기서 세 번째 인자는 BinaryOperator로 충돌 처리 방법을 지정할 수 있습니다. 이 경우 동일한 문자열의 길이가 항상 동일하다는 것을 알기 때문에 두 충돌 값 중 하나만 선택합니다.

#### 2.5 Collectors.collectingAndThen
  - CollectingAndThen은 수집이 끝난 직후 결과에 대해 다른 작업을 수행할 수 있는 특수한 collector입니다.
  아래 예제는 stream 요소를 List인스턴스로 수집 한 다음 결과를 ImmutableList 인스턴스로 변환합니다.
  ```java
    List<String> result = sampleList.stream().collect(collectingAndThen(toList(), ImmutableList::copyOf));
  ```
#### 2.6 Collectors.joining()
  - Joining collector는 Stream<String>의 요소를 결합하는데 사용할 수 있습니다.
  ```java
    String result = sampleList.stream().collect(Collectors.joining());
    assertThat(result).isEqualTo("abbcccdddda");
  ```
  - 사용자 정의 구분 기호, 접두사, 접미사를 지정할 수도 있습니다.
  ```java
    List<String> sampleList = Arrays.asList("a", "bb", "ccc", "dddd", "a");
    String result = sampleList.stream().collect(Collectors.joining(" "));
    assertThat(result).isEqualTo("a bb ccc dddd a");
  ```
  - 다음과 같이 사용할 수 있습니다.
  ```java
    String result = sampleList.stream().collect(Collectors.joining(" ","PRE-", "-POST"));
    assertThat(result).isEqualTo("PRE-a bb ccc dddd a-POST");
  ```

#### 2.7 Collectors.counting()
  - Counting은 모든 스트림 요소를 간단히 카운팅 할 수있는 Collector입니다.
  ```java
    Long result = sampleList.stream().collect(counting());
    assertThat(result).isEqualTo(5);
  ```
  간략하게 다음과 같이 작성도 할 수 있습니다.
  ```java
    Long result = sampleList.stream().count();
  ```

#### 2.8 Collectors.summarizingDouble/Long/Int()
  - SummarizingDouble/Long/Int 는 추출된 요소 stream에서 숫자 데이터에 대한 통계 정보를 포함하는 특수 클래스를 리턴하는 collector입니다.
  ```java
    DoubleSummaryStatistics result = sampleList.stream().collect(summarizingDouble(String::length));
    assertThat(result.getAverage()).isEqualTo(2.2);
    assertThat(result.getCount()).isEqualTo(5);
    assertThat(result.getMax()).isEqualTo(4);
    assertThat(result.getMin()).isEqualTo(1);
    assertThat(result.getSum()).isEqualTo(11);
  ```
#### 2.9 Collectors.averagingDouble/Long/Int()
  - AveragingDouble/Long/Int 는 추출된 요소의 평균을 반환하는 Collector 입니다.
  ```java
    Double result = sampleList.stream().collect(averagingDouble(String::length));
    assertThat(result).isEqualTo(2.2);
  ```

#### 2.10 Collectors.summingDouble/Long/Int()
  - SummingDouble/Long/Int는 추출 된 요소의 합계를 반환하는 Collector입니다.
  ```java
    Double result = sampleList.stream().collect(summingDouble(String::length));
    assertThat(result).isEqualTo(11);
  ```
#### 2.11 Collectors.maxBy()/minBy()
  - MaxBy / MinBy Collector는 제공된 Comparator 인스턴스에 따라 Stream의 가장 큰 / 가장 작은 요소를 반환합니다.
  ```java
    Optional<String> result = sampleList.stream().collect(maxBy(Comparator.naturalOrder()));
    assertThat(result).hasValue("dddd");
  ```
  ```java
    Optional<String> result = sampleList.stream().collect(minBy(Comparator.naturalOrder()));
    assertThat(result).hasValue("a");
  ```

#### 2.12 Collectors.groupingBy()
  - GroupingBy collector는 일부 속성별 객체를 그룹화하고 결과를 Map 인스턴스에 저장하는 데 사용됩니다.
    문자열 길이를 그룹화하고 그룹화 결과를 Set 인스턴스에 저장하는 예제입니다.
  ```java
    Map<Integer, Set<String>> result = sampleList.stream().collect(groupingBy(String::length, toSet()));
    // result => {1=[a], 2=[bb], 3=[ccc], 4=[dddd]}
  ```
  - groupingBy 메소드의 두 번째 인수는 Collector이며 원하는 Collector를 자유롭게 사용할 수 있습니다.

#### 2.13 Collectors.partitioningBy()
  - PartitioningBy는 Predicate 인스턴스를 허용하고 스트림 요소를 Boolean 값을 키로, 컬렉션을 값으로 저장하는 Map 인스턴스로 수집합니다. "true" 키 아래에서 주어진 술어와 일치하는 요소 컬렉션을 찾을 수 있으며 "false"키 아래에서 주어진 술어와 일치하지 않는 요소 컬렉션을 찾을 수 있습니다.
  ```java
    Map<Boolean, List<String>> result = sampleList.stream().collect(partitioningBy(s -> s.length() > 2));
    // result => {false=[a, bb, a], true=[ccc, dddd]}
  ```