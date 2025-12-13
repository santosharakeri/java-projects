package com.user.userExample.controller.model;

public class PanagramCheck {
    public static void main(String[] args) {
        String s = "The quick brown fox jumps over the lazy dog";
        Boolean panagramFlag = isPanagram(s.toLowerCase());
        System.out.println(panagramFlag);
    }

    private static Boolean isPanagram(String s) {
        Boolean panagramFlag = false;
        for(int i='a'; i<='z';i++){
            for(int j=0;j<s.length();j++){
                if(i == s.charAt(j)){
                    panagramFlag = true;
                    break;
                }

                }
            if(!panagramFlag){
                return false;
            }
        }
        return panagramFlag;
    }
}
