package com.user.userExample.controller.model;

import java.util.Optional;

public class NonRepeatingCharCheck {
    public static void main(String[] args) {
        String s = "he quick brown fox jumps over the lazy dog";
        Object ch = getNonRepeatingChar(s);
        System.out.println(ch);
    }

    private static Object getNonRepeatingChar(String s) {
        for(int i=0;i<s.length()/2;i++){
            Character ch = s.charAt(i);
            if(s.indexOf(ch) == s.lastIndexOf(ch) ){
                return ch;
            }
        }
        return Optional.empty();
    }
}
