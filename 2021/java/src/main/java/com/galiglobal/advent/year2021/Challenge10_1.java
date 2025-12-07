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
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Challenge10_1 {

    private final static Predicate<String> isClosingChar = s -> List.of(")", "]", "}", ">").contains(s);
    private final static Predicate<String> isOpeningChar = s -> List.of("(", "[", "{", "<").contains(s);
    private final static BiPredicate<String, String> isWrongParentheses = (start, end) -> ("(").equals(start) && !(")".equals(end));
    private final static BiPredicate<String, String> isWrongSquareBrackets = (start, end) -> ("[").equals(start) && !("]".equals(end));
    private final static BiPredicate<String, String> isWrongRoundBrackets = (start, end) -> ("{").equals(start) && !("}".equals(end));
    private final static BiPredicate<String, String> isWrongAngleBrackets = (start, end) -> ("<").equals(start) && !(">".equals(end));

    public static long getTotalSyntaxErrorScore(String input) {
        return getInputList(input).stream()
                .mapToLong(Challenge10_1::getTotal)
                .sum();
    }

    private static List<List<String>> getInputList(String input) {
        return Arrays.stream(input.split(System.getProperty("line.separator")))
                .map(s -> s.chars()
                        .mapToObj(c -> String.valueOf((char) c))
                        .toList())
                .toList();
    }

    private static long getTotal(List<String> line) {

        // I tried a recursive approach to avoid state, it's possible but too complex
        LinkedList<String> q = new LinkedList<>();

        return line.stream()
                .peek(match -> {
                    if (isOpeningChar.test(match))
                        q.add(match);
                })
                .filter(isClosingChar)
                .filter(match -> isWrongParentheses.or(isWrongRoundBrackets.or(isWrongAngleBrackets.or(isWrongSquareBrackets)))
                        .test(q.pollLast(), match)) // Note the Queue is update with this filter
                .map(Challenge10_1::getIllegalCharPoints)
                .filter(i -> i > 0)
                .findFirst()
                .orElse(0);
    }

    private static int getIllegalCharPoints(String c) {
        return switch (c) {
            case ")" -> 3;
            case "]" -> 57;
            case "}" -> 1197;
            case ">" -> 25137;
            default -> 0;
        };
    }
}
