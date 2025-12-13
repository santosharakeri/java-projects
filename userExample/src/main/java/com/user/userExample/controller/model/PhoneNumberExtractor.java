package com.user.userExample.controller.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

public class PhoneNumberExtractor {

    public static List<String> extractPhoneNumbers(String text) {
        List<String> phoneNumbers = new ArrayList<>();
        // This regex attempts to capture common phone number patterns.
        // It looks for sequences of digits, potentially separated by spaces, hyphens, or parentheses,
        // and can also include an optional '+' for international codes.
        String regex = "\\+?\\d{1,3}[\\s.-]?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            phoneNumbers.add(matcher.group());
        }
        return phoneNumbers;
    }

    public static void main(String[] args) {
        String text1 = "My number is 123-456-7890. Call me at +1 (555) 123-4567.";
        String text2 = "Another number: 987 654 3210. Or try 001-234-567-8901.";
        String text3 = "No phone numbers here.";

        System.out.println("Phone numbers in text1: " + extractPhoneNumbers(text1));
        System.out.println("Phone numbers in text2: " + extractPhoneNumbers(text2));
        System.out.println("Phone numbers in text3: " + extractPhoneNumbers(text3));
    }
}