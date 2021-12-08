/*
 *  Copyright 2021 The original authors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.galiglobal.advent.year2021;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Challenge8_2 {

    public static long sumOutputValues(String input) {

        return Arrays
            .stream(input.split(System.getProperty("line.separator")))
            .mapToInt(s -> buildDictionary(s))
            .sum();
    }

    private static int buildDictionary(String inputLine) {
        final List<String> inputList = Arrays.stream(inputLine
                .substring(0, inputLine.indexOf("|"))
                .split(" "))
            .toList();

        final Map<Integer, String> partialDictionary = inputList.stream()
            .map(s -> switch (s.length()) {
                case 2 -> Map.entry(1, s);
                case 4 -> Map.entry(4, s);
                default -> null;
            })
            .filter(v -> v != null) // TODO: do we need this?
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        final Map<String, String> dictionary = inputList.stream()
            .map(Challenge8_2::sort)
            .map(s -> switch (s.length()) {
                case 2 -> Map.entry(s, "1");
                case 3 -> Map.entry(s, "7");
                case 4 -> Map.entry(s, "4");
                case 5 -> findFiveLetters(s, partialDictionary);
                case 6 -> findSixLetters(s, partialDictionary);
                case 7 -> Map.entry(s, "8");
                default -> throw new RuntimeException();
            })
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        final String[] s = inputLine
            .substring(inputLine.indexOf("|") + 2)
            .split(" "); // TODO

        return Integer.parseInt(Arrays.stream(s)
            .map(Challenge8_2::sort)
            .map(v -> dictionary.get(v))
            .collect(Collectors.joining())
        );
    }

    private static Map.Entry<String, String> findSixLetters(String digit, Map<Integer, String> dictionary) {
        if (getLettersInCommon(digit, dictionary.get(4)) == 4)
            return Map.entry(digit, "9");
        if (getLettersInCommon(digit, dictionary.get(1)) == 1)
            return Map.entry(digit, "6");
        return Map.entry(digit, "0");
    }

    private static Map.Entry<String, String> findFiveLetters(String digit, Map<Integer, String> dictionary) {

        if (getLettersInCommon(digit, dictionary.get(4)) == 2)
            return Map.entry(digit, "2");
        if (getLettersInCommon(digit, dictionary.get(1)) == 2)
            return Map.entry(digit, "3");
        return Map.entry(digit, "5");
    }

    private static int getLettersInCommon(String s1, String s2) {
        return Math.toIntExact(
            s1.chars()
                .mapToObj(c -> String.valueOf((char) c))
                .filter(c -> s2.contains(c))
                .count()
        );
    }

    private static String sort(String s) {
        return s.chars()
            .sorted()
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }

}
