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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Challenge9_2 {

    private record Point(int x, int y, int height) {
    }

    public static long multiplyThreeLargestBasins(String input) {
        final List<List<Integer>> inputList = getInputList(input);

        final List<Point> lowPoints = IntStream.range(0, inputList.size())
                .mapToObj(y -> IntStream.range(0, inputList.get(y).size())
                        .mapToObj(x -> getLowPoint(inputList, x, y))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList())
                .flatMap(List::stream)
                .toList();

        return lowPoints.stream().map(l -> {
            final List<Point> emptyBasins = new ArrayList<>();
            return getBasins(inputList, emptyBasins, l);
        })
                .map(List::size)
                .sorted()
                .skip(lowPoints.size() - 3)
                .reduce(1, (a, b) -> a * b);
    }

    private static List<Point> getBasins(List<List<Integer>> inputList, List<Point> basin, Point p) {

        if (basin.contains(p))
            return basin;

        HashSet<Point> basinTemp = new HashSet<>(Stream.concat(basin.stream(), Stream.of(p)).toList());

        // Up
        if ((p.y > 0) && (inputList.get(p.y - 1).get(p.x) < 9) && !basinTemp.contains(inputList.get(p.y - 1).get(p.x)))
            basinTemp.addAll(getBasins(inputList, basinTemp.stream().toList(), new Point(p.x, p.y - 1, inputList.get(p.y - 1).get(p.x))));

        // Down
        if ((p.y < inputList.size() - 1) && (inputList.get(p.y + 1).get(p.x) < 9) && !basinTemp.contains(inputList.get(p.y + 1).get(p.x) < 9))
            basinTemp.addAll(getBasins(inputList, basinTemp.stream().toList(), new Point(p.x, p.y + 1, inputList.get(p.y + 1).get(p.x))));

        // left
        if ((p.x > 0) && (inputList.get(p.y).get(p.x - 1) < 9) && !basinTemp.contains(inputList.get(p.y).get(p.x - 1)))
            basinTemp.addAll(getBasins(inputList, basinTemp.stream().toList(), new Point(p.x - 1, p.y, inputList.get(p.y).get(p.x - 1))));

        // right
        if ((p.x < inputList.get(p.y).size() - 1) && (inputList.get(p.y).get(p.x + 1) < 9) && !basinTemp.contains(inputList.get(p.y).get(p.x + 1)))
            basinTemp.addAll(getBasins(inputList, basinTemp.stream().toList(), new Point(p.x + 1, p.y, inputList.get(p.y).get(p.x + 1))));

        return basinTemp.stream().toList();
    }

    private static List<List<Integer>> getInputList(String input) {
        return Arrays.stream(input.split(System.getProperty("line.separator")))
                .map(s -> s.chars() // TODO: easiest way?
                        .mapToObj(c -> String.valueOf((char) c))
                        .map(Integer::parseInt)
                        .toList())
                .toList();
    }

    private static Optional<Point> getLowPoint(List<List<Integer>> inputList, int x, int y) {
        // up
        if ((y > 0) && (inputList.get(y).get(x) >= inputList.get(y - 1).get(x)))
            return Optional.empty();

        // above
        if ((y < inputList.size() - 1) && (inputList.get(y).get(x) >= inputList.get(y + 1).get(x)))
            return Optional.empty();

        // left
        if ((x > 0) && (inputList.get(y).get(x) >= inputList.get(y).get(x - 1)))
            return Optional.empty();

        // right
        if ((x < inputList.get(y).size() - 1) && (inputList.get(y).get(x) >= inputList.get(y).get(x + 1)))
            return Optional.empty();

        return Optional.of(new Point(x, y, inputList.get(y).get(x)));
    }
}
