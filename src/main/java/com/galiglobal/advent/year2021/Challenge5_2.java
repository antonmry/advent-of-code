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
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Challenge5_2 {

    private static final Pattern p = Pattern.compile("\\d+");

    public static long countDangerousPoints(String input) {

        final List<Line> lines = getLines(input);
        final List<Point> points = getPoints(lines);
        return countDuplicates(points);
    }

    private static List<Line> getLines(String input) {
        return Arrays
                .stream(input.split(System.getProperty("line.separator")))
                .map(s -> p.matcher(s)
                        .results()
                        .map(MatchResult::group)
                        .collect(Collectors.collectingAndThen(
                                Collectors.toList(),
                                Line::createLine)))
                .toList();
    }

    private static List<Point> getPoints(List<Line> lines) {
        return lines.stream()
                .map(l -> {
                    // TODO: refactor, IntStream makes it too complex

                    // Vertical lines
                    if (l.x1 == l.x2)
                        if (l.y1 < l.y2) {
                            final List<Point> points = IntStream.rangeClosed(l.y1, l.y2)
                                    .mapToObj(y -> new Point(l.x1, y))
                                    .toList();
                            return points;
                        }
                        else {
                            final List<Point> points = IntStream.rangeClosed(l.y2, l.y1)
                                    .mapToObj(y -> new Point(l.x1, y))
                                    .toList();
                            return points;
                        }

                    // Horizontal lines
                    if (l.y1 == l.y2)
                        if (l.x1 < l.x2) {
                            final List<Point> points = IntStream.rangeClosed(l.x1, l.x2)
                                    .mapToObj(x -> new Point(x, l.y1))
                                    .toList();
                            return points;
                        }
                        else {
                            final List<Point> points = IntStream.rangeClosed(l.x2, l.x1)
                                    .mapToObj(x -> new Point(x, l.y1))
                                    .toList();
                            return points;
                        }

                    // An entry like 1,1 -> 3,3 covers points 1,1, 2,2, and 3,3.
                    // An entry like 9,7 -> 7,9 covers points 9,7, 8,8, and 7,9.
                    if (Math.abs(l.x1 - l.x2) == Math.abs(l.y1 - l.y2))
                        if (l.x1 < l.x2) {
                            final List<Point> points = IntStream.rangeClosed(l.x1, l.x2)
                                    .mapToObj(x -> new Point(x, l.y1 + (x - l.x1)))
                                    .toList();
                            return points;
                        }
                        else {
                            final List<Point> points = IntStream.rangeClosed(l.x2, l.x1)
                                    .mapToObj(x -> new Point(x, l.y2 + (x - l.x2)))
                                    .toList();
                            return points;
                        }

                    /*
                     * // An entry like 9,7 -> 7,9 covers points 9,7, 8,8, and 7,9.
                     * if ((l.x1 == l.y2) && (l.x2 == l.y1))
                     * if (l.x1 < l.x2) {
                     * final List<Point> points = IntStream.rangeClosed(l.x1, l.x2)
                     * .mapToObj(x -> new Point(x, l.y1 - x))
                     * .toList();
                     * return points;
                     * } else {
                     * final List<Point> points = IntStream.rangeClosed(l.x2, l.x1)
                     * .mapToObj(x -> new Point(x, l.y2 - x))
                     * .toList();
                     * return points;
                     * }
                     */

                    // Ignore other cases
                    return Collections.<Point> emptyList();
                })
                .flatMap(List::stream)
                .toList();
    }

    private static long countDuplicates(List<Point> points) {
        return points.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()))
                .values()
                .stream()
                .filter(x -> x > 1)
                .count();
    }

    private record Line(int x1, int y1, int x2, int y2) {
        public static Line createLine(List<String> l) {
            return new Line(
                Integer.parseInt(l.get(0)),
                Integer.parseInt(l.get(1)),
                Integer.parseInt(l.get(2)),
                Integer.parseInt(l.get(3))
            );
        }
    }

    private record Point(int x, int y) {
    }
}
