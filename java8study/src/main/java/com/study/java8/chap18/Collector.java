package com.study.java8.chap18;
import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

// https://daddyprogrammer.org/post/1163/java-collectors/
public class Collector {
	public static void main(String[] args) {
		// 실습 예제어스 다음의 List 를 가지고 실습을 진행한다.
		List<String> sampleList = Arrays.asList("a", "bb", "cc", "dddd", "a");
		
		//////////////////////////////////////////////////////////////////////////////
		// 2.1 Collectors.toList()
		//  모든 Stream의 요소를 List 인스턴스로 수집하는 데 사용할 수 있습니다. 해당 메서드는 특정한 List를 구현하는 것이 아니며 이것을 좀 더 잘 제어하려면 toCollection을 대신 사용할 수 있습니다.
		//  아래는 Stream의 모든 요소를 List 인스턴스로 수집하는 예제입니다.
		List<String> result1 = sampleList.stream().collect(toList());
		//////////////////////////////////////////////////////////////////////////////
		
		//////////////////////////////////////////////////////////////////////////////
		// 2.2 Collectors.toSet()
		//  모든 Stream의 요소를 Set 인스턴스로 수집하는 데 사용할 수 있습니다. 해당 메서드는 특정한 Set를 구현하는 것이 아니며 이것을 좀 더 잘 제어하려면 toCollection을 대신 사용할 수 있습니다.
		//  아래는 Stream의 모든 요소를 Set 인스턴스로 수집하는 예제입니다. Set에는 중복 요소가 없어 서로 같은 요소가 포함된 경우 결과에 한번만 나타납니다.
		Set<String> result2 = sampleList.stream().collect(toSet());
		//assertThat(result2).hasSize(4);
		//////////////////////////////////////////////////////////////////////////////
		
		//////////////////////////////////////////////////////////////////////////////
		// 2.3 Collectors.toCollection()
		// 위에서 설명했듯 toList, toSet, Collector는 특정한 List, Set의 구현을 지정할 수 없습니다. 특정 Collection을 구현하려면 toCollection collector를 사용하면 됩니다.
		List<String> lists = sampleList.stream().collect(toCollection(LinkedList::new));
		Set<String> sets = sampleList.stream().collect(toCollection(TreeSet::new));
		//////////////////////////////////////////////////////////////////////////////
		
		//////////////////////////////////////////////////////////////////////////////
		// 2.4 Collectors.toMap()
		//  만약 collection에 중복 요소가 포함되어 있으면 toSet과 달리 toMap은 중복을 자동으로 필터링하지 않습니다. 따라서 중복 키가 보이면 즉시 IllegalStateException이 발생합니다.
		List<String> sampleList2 = Arrays.asList("a", "bb", "cc", "dddd", "f");
		Map<String, Integer> result3 = sampleList2.stream().collect(toMap(Function.identity(), String::length));
		
		//  key 충돌이 있는 경우 다른 signature와 함께 toMap을 사용해야 합니다. 
		//  여기서 세 번째 인자는 BinaryOperator로 충돌 처리 방법을 지정할 수 있습니다. 이 경우 동일한 문자열의 길이가 항상 동일하다는 것을 알기 때문에 두 충돌 값 중 하나만 선택합니다.
		Map<String, Integer> result4 = sampleList.stream().collect(toMap(Function.identity(), String::length, (item, identicalItem) -> identicalItem));

		result4.forEach((a,b) -> {
			System.out.println("k: " + a);
			System.out.println("v: " + b);
		});
		//////////////////////////////////////////////////////////////////////////////
		
		//////////////////////////////////////////////////////////////////////////////
		// 2.5 Collectors.collectingAndThen
		//  CollectingAndThen은 수집이 끝난 직후 결과에 대해 다른 작업을 수행할 수 있는 특수한 collector입니다. 아래 예제는 stream 요소를 List인스턴스로 수집 한 다음 결과를 ImmutableList 인스턴스로 변환합니다.
		//List<String> result5 = sampleList.stream().collect(collectingAndThen(toList(), ImmutableList::copyOf));
		//////////////////////////////////////////////////////////////////////////////
		
		
		//////////////////////////////////////////////////////////////////////////////
		// 2.6 Collectors.joining()
		//   - Joining collector는 Stream의 요소를 결합하는데 사용할 수 있습니다.
		String result5 = sampleList.stream().collect(Collectors.joining());
		//assertThat(result).isEqualTo("abbcccdddda");
		
		//   - 사용자 정의 구분 기호, 접두사, 접미사를 지정할 수도 있습니다.
		String result6 = sampleList.stream().collect(Collectors.joining(" "));
		//assertThat(result).isEqualTo("a bb ccc dddd a");
		
		//   - 다음과 같이 사용할 수 있습니다.
		String result7 = sampleList.stream().collect(Collectors.joining(" ","PRE-", "-POST"));
		//assertThat(result).isEqualTo("PRE-a bb ccc dddd a-POST");
		//////////////////////////////////////////////////////////////////////////////

		//////////////////////////////////////////////////////////////////////////////
		// 2.7 Collectors.counting()
		//  - Counting은 모든 스트림 요소를 간단히 카운팅 할 수 있는 Collector 입니다.
		Long result8 = sampleList.stream().collect(counting());
		//assertThat(result).isEqualTo(5);
		
		//  - 간단하게 다음과 같이작성도 할 수 있습니다.
		Long result9 = sampleList.stream().count();
		//////////////////////////////////////////////////////////////////////////////

		//////////////////////////////////////////////////////////////////////////////
		// 2.8 Collectors.summarizingDouble/Long/Int()
		//  - SummarizingDouble/Long/Int 는 추출된 요소 stream에서 숫자 데이터에 대한 통계 정보를 포함하는 특수 클래스를 리턴하는 collector입니다.
		DoubleSummaryStatistics result10 = sampleList.stream().collect(summarizingDouble(String::length));
		//assertThat(result10.getAverage()).isEqualTo(2.2);
		//assertThat(result10.getCount()).isEqualTo(5);
		//assertThat(result10.getMax()).isEqualTo(4);
		//assertThat(result10.getMin()).isEqualTo(1);
		//assertThat(result10.getSum()).isEqualTo(11);
		//////////////////////////////////////////////////////////////////////////////
		
		//////////////////////////////////////////////////////////////////////////////
		// 2.9 Collectors.averagingDouble/Long/Int()
		//  - AveragingDouble/Long/Int 는 추출된 요소의 평균을 반환하는 Collector 입니다.
		Double result11 = sampleList.stream().collect(averagingDouble(String::length));
		//assertThat(result11).isEqualTo(2.2);
		//////////////////////////////////////////////////////////////////////////////
		
		//////////////////////////////////////////////////////////////////////////////
		// 2.10 Collectors.summingDouble/Long/Int()
		//  - SummingDouble/Long/Int는 추출 된 요소의 합계를 반환하는 Collector입니다.
		Double result12 = sampleList.stream().collect(summingDouble(String::length));
		//assertThat(result12).isEqualTo(11);
		//////////////////////////////////////////////////////////////////////////////
		
		
		//////////////////////////////////////////////////////////////////////////////
		// 2.11 Collectors.maxBy()/minBy()
		//  - MaxBy / MinBy Collector는 제공된 Comparator 인스턴스에 따라 Stream의 가장 큰 / 가장 작은 요소를 반환합니다.
		Optional<String> result13 = sampleList.stream().collect(maxBy(Comparator.naturalOrder()));
		//assertThat(result13).hasValue("dddd");
		//////////////////////////////////////////////////////////////////////////////
		
		
		//////////////////////////////////////////////////////////////////////////////
		// 2.12 Collectors.groupingBy()
		//  - GroupingBy collector는 일부 속성별 객체를 그룹화하고 결과를 Map 인스턴스에 저장하는 데 사용됩니다. 문자열 길이를 그룹화하고 그룹화 결과를 Set 인스턴스에 저장하는 예제입니다.
		Map<Integer, Set<String>> result14 = sampleList.stream().collect(groupingBy(String::length, toSet()));
		// result14 => {1=[a], 2=[bb], 3=[ccc], 4=[dddd]}
		//////////////////////////////////////////////////////////////////////////////
		
		//////////////////////////////////////////////////////////////////////////////
		// 2.13 Collectors.partitioningBy()
		//  - PartitioningBy는 Predicate 인스턴스를 허용하고 스트림 요소를 Boolean 값을 키로, 컬렉션을 값으로 저장하는 Map 인스턴스로 수집합니다. "true" 키 아래에서 주어진 술어와 일치하는 요소 컬렉션을 찾을 수 있으며 "false"키 아래에서 주어진 술어와 일치하지 않는 요소 컬렉션을 찾을 수 있습니다.
		Map<Boolean, List<String>> result15 = sampleList.stream().collect(partitioningBy(s -> s.length() > 2));
		// result15 => {false=[a, bb, a], true=[ccc, dddd]}
		//////////////////////////////////////////////////////////////////////////////
	}
}
