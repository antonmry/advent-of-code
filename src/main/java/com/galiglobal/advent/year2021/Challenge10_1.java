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
import java.util.Map;

public class Challenge10_1 {

    private final static Map<String, Integer> illegalCharPoints = Map.of(
        ")", 3,
        "]", 57,
        "}", 1197,
        ">", 25137);

    public static long getTotalSyntaxErrorScore(String input) {

        final List<List<String>> inputList = getInputList(input);

        return inputList.stream()
            .mapToLong(Challenge10_1::getTotal2)
            .sum();
    }

    private static long getTotal2(List<String> line) {
        return getSyntaxErrorScore(0, line, "");
    }

    private static long getSyntaxErrorScore(int score, List<String> line, String lastMatch) {
        if (line.size() == 0) return score;

        if (List.of("(", "[", "{", "<").contains(line.get(0))) {
            final long syntaxErrorScore = getSyntaxErrorScore(score, line.subList(1, line.size() - 2), line.get(0));

            return (syntaxErrorScore > 0) ? syntaxErrorScore :
                getSyntaxErrorScore(score, line.subList(1, line.size() - 2), lastMatch);
        }

        String match = line.get(0);
        String poll = lastMatch;

        if (("(").equals(poll) && !(")".equals(match))) {
            return illegalCharPoints.get(match);
        }

        if (("[").equals(poll) && !("]".equals(match))) {
            return illegalCharPoints.get(match);
        }

        if (("{").equals(poll) && !("}".equals(match))) {
            return illegalCharPoints.get(match);
        }

        if (("<").equals(poll) && !(">".equals(match))) {
            return illegalCharPoints.get(match);
        }

        return getSyntaxErrorScore(score, line.subList(1, line.size() - 1), line.get(0));
    }

    private static long getTotal(List<String> line) {
        LinkedList<String> q = new LinkedList<>();
        long total = 0;

        for (String match : line) {
            if (List.of("(", "[", "{", "<").contains(match))
                q.add(match);
            else {
                final String poll = q.pollLast();

                if (poll == null) {
                    total += illegalCharPoints.get(match);
                    break;
                }

                if (("(").equals(poll) && !(")".equals(match))) {
                    total += illegalCharPoints.get(match);
                    break;
                }

                if (("[").equals(poll) && !("]".equals(match))) {
                    total += illegalCharPoints.get(match);
                    break;
                }

                if (("{").equals(poll) && !("}".equals(match))) {
                    total += illegalCharPoints.get(match);
                    break;
                }

                if (("<").equals(poll) && !(">".equals(match))) {
                    total += illegalCharPoints.get(match);
                    break;
                }
            }
        }
        return total;
    }

    private static List<List<String>> getInputList(String input) {
        return Arrays.stream(input.split(System.getProperty("line.separator")))
            .map(s -> s.chars() // TODO: easiest way?
                .mapToObj(c -> String.valueOf((char) c))
                .toList())
            .toList();
    }
}
