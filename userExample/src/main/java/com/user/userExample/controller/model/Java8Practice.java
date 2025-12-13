package com.user.userExample.controller.model;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Java8Practice {
    public static void main(String[] args) {
        //create list
       /* List<String> users = Arrays.asList("raj", "ramesh","umesh","kalmesh");
        users.stream().filter(str->str.length()<=3).forEach(str->System.out.println(str));
*/
         /*int[] arr = {10,15,8,49,25,98,32};

        Map<Boolean, List<Integer>> list = Arrays.stream(arr).boxed()
                .collect(Collectors.partitioningBy(num -> num % 2 == 0));
        System.out.println(list.get(true));*/

        /*List<Integer> myList = Arrays.asList(10,15,8,49,25,98,32);
       Map map= myList.stream()
                .map(s -> s + "") // Convert integer to String
                .collect(Collectors.partitioningBy(s -> (s+"").startsWith("1")));
       System.out.println(map);*/
       /* List<Integer> myList = Arrays.asList(10,15,8,49,25,98,98,32,15);
        Set<Integer> set = new HashSet();
        List duplicate = myList.stream()
                .filter(n -> !set.add(n)).collect(Collectors.toList());
        System.out.println(duplicate);
        myList.stream().distinct().forEach(System.out::print);*/

        /*List<Integer> myList = Arrays.asList(10,15,8,49,25,98,98,32,15);
        myList.stream()
                .findAny()
                .ifPresent(System.out::println);*/

       /* List<Integer> myList = Arrays.asList(10,15,8,49,25,98,98,32,15);
        Optional max =  myList.stream()
                .max(Integer::compare);
        System.out.println(max.get());*/

       /* String input = "Java articles are Awesome";
        Character result = input.chars() // Stream of String
                .mapToObj(s -> Character.toLowerCase(Character.valueOf((char) s))) // First convert to Character object and then to lowercase
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting())) //Store the chars in map with count
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() == 1L)
                .map(entry -> entry.getKey())
                .findFirst()
                .get();
        System.out.println(result);

        Character result2 = input.chars() // Stream of String
                .mapToObj(s -> Character.toLowerCase(Character.valueOf((char) s))) // First convert to Character object and then to lowercase
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting())) //Store the chars in map with count
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 1L)
                .map(entry -> entry.getKey())
                .findFirst()
                .get();
        System.out.println(result2);*/
       /* List<Integer> myList = Arrays.asList(10,15,8,49,25,98,98,32,15);

        myList.stream()
                .sorted().distinct()
                .forEach(System.out::println);*/

        /*System.out.println("Current Local Date: " + java.time.LocalDate.now());
        //Used LocalDate API to get the date
        System.out.println("Current Local Time: " + java.time.LocalTime.now());
        //Used LocalTime API to get the time
        System.out.println("Current Local Date and Time: " + java.time.LocalDateTime.now());
        //Used LocalDateTime API to get both date and time

        Runtime runtime = Runtime.getRuntime();
        System.out.println("runtime: "+runtime.toString());
        // Get the number of processors available to the JVM
        int numberOfProcessors = runtime.availableProcessors();
        System.out.println("Number of processors: " + numberOfProcessors);*/
        /*List<String> names = Arrays.asList("AA", "BB", "AA", "CC");
        Integer f = Collections.frequency(names, "AA");
        System.out.println(f);*/
        /*int [] array = new int [] {1,2,3,4,5,6,7,8,9,10,10};
        Set sum = Arrays.stream(array).distinct().collect();
        System.out.println(sum);*/

    }
}
