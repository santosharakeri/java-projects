package com.user.userExample.controller.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoveZeroToEndOfList {
    public static void main(String[] args) {
        List<Integer> list= Arrays.asList(1,5,0,4,0,3,6,0,7);
        List<Integer> tempList=new ArrayList<>();

        list.forEach(a-> {
            if(a!=0){
                tempList.add(a);
            }
        });
        for(int i=tempList.size();i<list.size();i++){
            tempList.add(0);
        }
        System.out.println(tempList);
    }
}
