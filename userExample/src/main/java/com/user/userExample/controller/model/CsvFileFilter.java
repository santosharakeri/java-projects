package com.user.userExample.controller.model;


    import org.apache.poi.ss.usermodel.Cell;
    import org.apache.poi.ss.usermodel.Row;
    import org.apache.poi.ss.usermodel.Sheet;
    import org.apache.poi.ss.usermodel.Workbook;
    import org.apache.poi.xssf.usermodel.XSSFWorkbook;

    import java.io.BufferedReader;
    import java.io.FileOutputStream;
    import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
    import java.util.LinkedHashSet;
    import java.util.List;
    import java.util.Set;
    import java.util.regex.Matcher;
    import java.util.regex.Pattern;
    import java.util.stream.Collectors;

public class CsvFileFilter {

        public static void main(String[] args) {
            String csvFilePath = "C:/Personal/Medical Shops/8banw-j1npd.csv"; // Replace with your CSV file path
            String filterString = "customer"; // Replace with the string to filter by

            List<String> filteredLines = filterCsvByString(csvFilePath, filterString);

            if (filteredLines.isEmpty()) {
                System.out.println("No lines found containing '" + filterString + "'.");
            } else {
                System.out.println("Filtered lines containing '" + filterString + "':");
                /*for (String line : filteredLines) {
                    System.out.println(line+": "+extractPhoneNumbers(line));
                }*/
                writeRecordsToExcel(filteredLines);
            }
        }

        public static List<String> filterCsvByString(String filePath, String searchString) {
            List<String> matchingLines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Check if the current line contains the search string
                    if (line.toLowerCase().contains(searchString.toLowerCase())) {

                        matchingLines.add(removeExtraCommas(line));
                        //System.out.println(line);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading CSV file: " + e.getMessage());
            }
            return matchingLines;
        }
        public static String removeExtraCommas(String str) {
            str = str.replaceAll("^(,)|(,$)", "");
            str = str.replaceAll(",+", ",");
            return str;
        }
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
    /*public static List<String> extractPhoneNumbers(String text) {
        List<String> phoneNumbers = new ArrayList<>();
        // This regex attempts to capture common phone number patterns.
        // It looks for sequences of digits, potentially separated by spaces, hyphens, or parentheses,
        // and can also include an optional '+' for international codes.
        //String regex = "\\+?\\d{1,3}[\\s.-]?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}";
        String regex = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        *//*if(!matcher.find()) {
            Pattern p = Pattern.compile("exp_(\\d-\\d+)-(\\d+)");
            matcher = p.matcher(text);
        }*//*
        while (matcher.find()) {
            phoneNumbers.add(matcher.group());

        }
        return phoneNumbers;
    }*/

    public static void writeRecordsToExcel(List<String> filteredLines) {
        // Create a new workbook (for .xlsx format)
        Workbook workbook = new XSSFWorkbook();

        // Create a new sheet named "Sheet1"
        Sheet sheet = workbook.createSheet("Sheet1");

        // Create a row at index 0 (first row)
        Row row = sheet.createRow(0);

        // Create cells in the row and set their values
        Cell cell1 = row.createCell(0); // First cell (column A)
        cell1.setCellValue("Name");

        Cell cell2 = row.createCell(1); // Second cell (column B)
        cell2.setCellValue("Contact");
        System.out.println("filteredLines.size(): " + filteredLines.size());
        int rowIndex = 0;
        String contactStr = "";
        List<String> contacts = new ArrayList<>();
        for (String line : filteredLines) {
            System.out.println("Line " + line);
            // Create another row at index 1 (second row)
            Row dataRow = sheet.createRow(rowIndex++);
            contacts= extractContactNumbers(line);
            line = removeContacts(line, contacts);
            contactStr = contacts.stream().collect(Collectors.joining(", "));
            // Populate data in the second row
            Cell dataCell1 = dataRow.createCell(0);
            dataCell1.setCellValue(line);

            Cell dataCell2 = dataRow.createCell(1);
            dataCell2.setCellValue(contactStr); // Numeric value
        }


        // Write the workbook to a file
        try (FileOutputStream outputStream = new FileOutputStream("C:/Personal/Medical Shops/customerContacts8.xlsx")) {
            workbook.write(outputStream);
            System.out.println("Excel file created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close(); // Close the workbook
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String removeContacts(String line, List<String> contacts) {
        for (String contact : contacts) {
            line = line.replace(contact, "");

        }

        return removeDuplicateWords(line);
    }
    public static String removeDuplicateWords(String text) {
        if (text == null || text.trim().isEmpty()) {
            return "";
        }

        // Split by whitespace, convert to lowercase for comparison, trim
        String[] words = text.toLowerCase().split("\\,+");

        // Use LinkedHashSet to maintain insertion order and remove duplicates
        Set<String> uniqueWords = new LinkedHashSet<>();
        for (String word : words) {
            uniqueWords.add(word.trim());
        }

        // Join back with original casing preserved (optional enhancement)
        return String.join(" ", uniqueWords);
    }

}
