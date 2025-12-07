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

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Challenge10_2 {

    private final static Predicate<String> isClosingChar = s -> List.of(")", "]", "}", ">").contains(s);
    private final static Predicate<String> isOpeningChar = s -> List.of("(", "[", "{", "<").contains(s);
    private final static BiPredicate<String, String> isWrongParentheses = (start, end) -> ("(").equals(start) && !(")".equals(end));
    private final static BiPredicate<String, String> isWrongSquareBrackets = (start, end) -> ("[").equals(start) && !("]".equals(end));
    private final static BiPredicate<String, String> isWrongRoundBrackets = (start, end) -> ("{").equals(start) && !("}".equals(end));
    private final static BiPredicate<String, String> isWrongAngleBrackets = (start, end) -> ("<").equals(start) && !(">".equals(end));

    public static long getCompletionMiddleScore(String input) {

        var completionPoints = getInputList(input).stream()
                .map(Challenge10_2::getRemainingLine)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(s -> s.stream()
                        .mapToLong(Challenge10_2::getCompletionCharPoints)
                        .reduce(0, (subtotal, element) -> 5 * subtotal + element))
                .sorted()
                .toList();

        return completionPoints.get(completionPoints.size() / 2);
    }

    private static List<List<String>> getInputList(String input) {
        return Arrays.stream(input.split(System.getProperty("line.separator")))
                .map(s -> s.chars()
                        .mapToObj(c -> String.valueOf((char) c))
                        .toList())
                .toList();
    }

    private static Optional<List<String>> getRemainingLine(List<String> line) {

        LinkedList<String> q = new LinkedList<>();

        final Optional<String> incorrectLine = line.stream()
                .peek(match -> {
                    if (isOpeningChar.test(match))
                        q.add(match);
                })
                .filter(isClosingChar)
                .filter(match -> isWrongParentheses.or(isWrongRoundBrackets.or(isWrongAngleBrackets.or(isWrongSquareBrackets)))
                        .test(q.pollLast(), match))
                .findFirst();

        if (incorrectLine.isEmpty()) {
            List<String> remainingChars = q.stream()
                    .map(Challenge10_2::getEndingChar)
                    .collect(Collectors.toList());

            Collections.reverse(remainingChars);
            return Optional.of(remainingChars);
        }

        return Optional.empty();
    }

    private static String getEndingChar(String c) {
        return switch (c) {
            case "(" -> ")";
            case "[" -> "]";
            case "{" -> "}";
            case "<" -> ">";
            default -> throw new UnsupportedOperationException();
        };
    }

    private static int getCompletionCharPoints(String c) {
        return switch (c) {
            case ")" -> 1;
            case "]" -> 2;
            case "}" -> 3;
            case ">" -> 4;
            default -> throw new UnsupportedOperationException();
        };
    }
}
