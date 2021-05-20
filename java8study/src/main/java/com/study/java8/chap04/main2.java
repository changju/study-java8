package com.study.java8.chap04;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.function.ToIntBiFunction;
public class main2 {

/*
@FunctionalInterface
public interface ToIntBiFunction<T, U> {
	int applyAsInt(T var1, U var2);
}
*/
	
    public static void main(String[] args) {
        ToIntBiFunction<String, String> comp;
        
        comp = (a, b) -> a.compareToIgnoreCase(b);
        print(comp.applyAsInt("Java8", "JAVA8"));
        
        // 인스턴스 메서드 참조
        comp = String::compareToIgnoreCase;
        print(comp.applyAsInt("Java8", "JAVA8"));
    }
    
    public static void print(int order) {
        if(order < 0) { System.out.println("a가 먼저 옵니다"); }
        else if(order == 0) { System.out.println("동일한 문자열입니다."); }
        else { System.out.println("a가 나중에 옵니다."); }
    }

}
