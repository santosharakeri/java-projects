package com.user.userExample.controller.model;

public class CheckPolyndrom {
    public static void main(String[] args) {
        String inputString = "gadag";
        System.out.println("Is polyndrom: "+isPolyndrom(inputString));

    }

    public static boolean isPolyndrom(String str){
        StringBuilder sb = new StringBuilder();
        for(int  i=str.length()-1;i>0;i--){
            sb.append(str.charAt(i));
        }
        return sb.toString().equals(str);
    }
}
