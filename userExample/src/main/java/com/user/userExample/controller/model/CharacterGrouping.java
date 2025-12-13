package com.user.userExample.controller.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CharacterGrouping {

    public static void main(String[] args) {
        String inputString = "hello world";



Map<Character, List<Character>> map=        inputString.chars().mapToObj(c -> (char) c).collect(Collectors.groupingBy(c->c));


        // Group characters by their value
        Map<Character, List<Character>> groupedCharacters = inputString.chars() // Get an IntStream of character codes
                .mapToObj(c -> (char) c) // Convert IntStream to Stream<Character>
                .collect(Collectors.groupingBy(c -> c)); // Group by the character itself

        // Print the result
        groupedCharacters.forEach((character, list) ->
                System.out.println("Character: '" + character + "', Occurrences: " + list.size()));
    }
}
