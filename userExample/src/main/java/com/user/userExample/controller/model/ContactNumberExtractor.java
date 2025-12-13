package com.user.userExample.controller.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactNumberExtractor {

    // Main regex pattern to match various phone number formats
    private static final String PHONE_PATTERN =
            "(\\+91[\\s-]?)?[6-9]\\d{9}|" +           // +91 9876543210 or 9876543210
                    "(\\d{3}[-.\\s]?)?\\d{3}[-.\\s]?\\d{4}|" + // 987-654-3210 or 987.654.3210
                    "1[-.\\s]?\\d{3}[-.\\s]?\\d{3}[-.\\s]?\\d{4}|" + // US format: 1-800-419-6966
                    "\\d{10}|" +                            // 10 digits: 9876543210
                    "0\\d{10}|" +                           // Landline: 09876543210
                    "\\d{3}[-]?\\d{3}[-]?\\d{4}";           // 987-654-3210

    private static final Pattern pattern = Pattern.compile(PHONE_PATTERN);

    public static List<String> extractContactNumbers(String text) {
        List<String> numbers = new ArrayList<>();
        if (text == null || text.trim().isEmpty()) {
            return numbers;
        }

        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String number = matcher.group();
            // Clean and standardize the number
            String cleanedNumber = cleanNumber(number);
            if (!numbers.contains(cleanedNumber)) {
                numbers.add(cleanedNumber);
            }
        }
        return numbers;
    }

    private static String cleanNumber(String number) {
        // Remove all non-digit characters except +91
        String cleaned = number.replaceAll("[^0-9+]", "");

        // Standardize Indian numbers
        if (cleaned.startsWith("91") || cleaned.startsWith("+91")) {
            cleaned = cleaned.replace("+91", "+91");
        } else if (cleaned.length() == 10 && cleaned.matches("[6-9]\\d{9}")) {
            cleaned = "+91" + cleaned;
        } else if (cleaned.length() == 11 && cleaned.startsWith("0")) {
            cleaned = "+91" + cleaned.substring(1);
        }

        return cleaned;
    }

    // Main method with test cases
    public static void main(String[] args) {
        String[] testCases = {
                /*"Contact: +917892702589, +91 7353039395, 779-584-3040",
                "Call 9876543210 or 09876543210 or 1-800-419-6969",
                "Numbers: 735-303-9395, +919538421464, 8904973562",
                "Ganesh Nagar Customer: 9449517668, 9880550524",
                "No numbers here just text",
                "+91-789-270-2589, 735.303.9395, 1 860 500 4971"*/
                "Golgumat,Customer,Golgumat Customer,+917892702589,",
                "Ganesh Nagar Customer,40,Ganesh Nagar Customer Telmisarton 40,735-303-9395,",
                "Darreppa Nagond Halli Customer,Vijayapura,Darreppa Nagond Halli Customer Relestate Vijayapura,+919538421464,",
                "Susan Real,Customer,Susan Real Estate Customer,779-584-3040,",
                "Jagu Real-estate,Customer,Jagu Real-estate Vijayapura Customer,779-590-3855,",
                "Mahesh Real-estate,Customer,Mahesh Real-estate Vijayapura Customer,890-497-3562,"
        };

        for (String test : testCases) {
            System.out.println("Input: " + test);
            List<String> numbers = extractContactNumbers(test);
            System.out.println("Extracted: " + numbers);
            System.out.println("---");
        }
    }
}
