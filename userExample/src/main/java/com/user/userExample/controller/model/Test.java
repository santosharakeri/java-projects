package com.user.userExample.controller.model;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Test {

    public static void main(String[] args) {
        /*//List nums = Arrays.asList(0,0,1,1,1,2,2,3,3,4);
        //Output: 5, nums = [0,1,2,3,4,_,_,_,_,_]
        //Set set = new
        //nums.stream().distinct().forEach(System.out::println);

        //Input: nums = [0,1,0,3,12]
       // Output: [1,3,12,0,0]
        List<Integer> nums2 = Arrays.asList(0,1,0,3,12);
       //List<Integer> l = nums2.stream().filter(i->(i!=0)).collect(Collectors.toList());
       //System.out.println(l);
       List<Integer> newList = new ArrayList<Integer>();
        for (Integer item  :nums2){
            if(item != 0){
                newList.add(item);
            }
        }
        while (newList.size()< nums2.size()){
            newList.add(0);
        }
        System.out.println(newList);
    }

    int n1=0;
    int n2=1;
    int n3=n1+n2;*/
        List<String> list = new ArrayList<>();
        list.add("abc");
        list.add("tad");
        list.add("cad");
        list.add("tad");
        list.add("bca");
        list.add("dac");
        list.add("tads");
        list.add("cas");
        Map<String, List<String>> map = new ConcurrentHashMap<String, List<String>>();
        for(String s : list){
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String tempStr = new  String(chars);
            if(map.get(tempStr) == null){
                List l2 = new ArrayList();
                list.add(s);
                map.put(tempStr, l2);
            }else{

                List tempList = map.get(tempStr);
                tempList.add(s);
                map.put(tempStr, tempList);
            }
        }
        System.out.println(map);


    }

    static boolean isAnagram(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        Arrays.sort(chars1);
        Arrays.sort(chars2);
        return Arrays.equals(chars1,chars2);
    }



}
