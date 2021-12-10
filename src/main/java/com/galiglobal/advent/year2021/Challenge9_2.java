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
        final List<Point> lowPoints = getLowPoints(inputList);
        return multiplyThreeBiggerBasinsCount(inputList, lowPoints);
    }

    private static List<List<Integer>> getInputList(String input) {
        return Arrays.stream(input.split(System.getProperty("line.separator")))
                .map(s -> s.chars()
                        .mapToObj(c -> String.valueOf((char) c))
                        .map(Integer::parseInt)
                        .toList())
                .toList();
    }

    private static List<Point> getLowPoints(List<List<Integer>> inputList) {
        final List<Point> lowPoints = IntStream.range(0, inputList.size())
                .mapToObj(y -> IntStream.range(0, inputList.get(y).size())
                        .mapToObj(x -> getLowPoint(inputList, x, y))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList())
                .flatMap(List::stream)
                .toList();
        return lowPoints;
    }

    private static Optional<Point> getLowPoint(List<List<Integer>> points, int x, int y) {
        // This is ugly, ugly... I know
        if (((y > 0) && (points.get(y).get(x) >= points.get(y - 1).get(x))) || // Up
                ((y < points.size() - 1) && (points.get(y).get(x) >= points.get(y + 1).get(x))) || // Down
                ((x > 0) && (points.get(y).get(x) >= points.get(y).get(x - 1))) || // Left
                ((x < points.get(y).size() - 1) && (points.get(y).get(x) >= points.get(y).get(x + 1)))) // Right
            return Optional.empty();

        return Optional.of(new Point(x, y, points.get(y).get(x)));
    }

    private static Integer multiplyThreeBiggerBasinsCount(List<List<Integer>> inputList, List<Point> lowPoints) {
        return lowPoints.stream().map(l -> {
            final List<Point> emptyBasins = new ArrayList<>();
            return getBasins(inputList, emptyBasins, l);
        })
                .map(List::size)
                .sorted()
                .skip(lowPoints.size() - 3)
                .reduce(1, (a, b) -> a * b);
    }

    private static List<Point> getBasins(List<List<Integer>> points, List<Point> basin, Point point) {

        HashSet<Point> basinTemp = new HashSet<>(Stream.concat(basin.stream(), Stream.of(point)).toList());

        // TODO: we should avoid DRI here, I keep it to show the recursive call to getBasins
        basinTemp.addAll(
                lowerUp(point, points).stream()
                        .filter(p -> !basinTemp.contains(p))
                        .map(p -> getBasins(points, basinTemp.stream().toList(), p))
                        .flatMap(List::stream)
                        .toList());

        basinTemp.addAll(
                lowerDown(point, points).stream()
                        .filter(p -> !basinTemp.contains(p))
                        .map(p -> getBasins(points, basinTemp.stream().toList(), p))
                        .flatMap(List::stream)
                        .toList());

        basinTemp.addAll(
                lowerLeft(point, points).stream()
                        .filter(p -> !basinTemp.contains(p))
                        .map(p -> getBasins(points, basinTemp.stream().toList(), p))
                        .flatMap(List::stream)
                        .toList());

        basinTemp.addAll(
                lowerRigth(point, points).stream()
                        .filter(p -> !basinTemp.contains(p))
                        .map(p -> getBasins(points, basinTemp.stream().toList(), p))
                        .flatMap(List::stream)
                        .toList());

        return basinTemp.stream().toList();
    }

    // TODO: improve this
    private static Optional<Point> lowerUp(Point p, List<List<Integer>> points) {
        if ((p.y > 0) && (points.get(p.y - 1).get(p.x) < 9))
            return Optional.of(new Point(p.x, p.y - 1, points.get(p.y - 1).get(p.x)));
        return Optional.empty();
    }

    private static Optional<Point> lowerDown(Point p, List<List<Integer>> points) {
        if ((p.y < points.size() - 1) && (points.get(p.y + 1).get(p.x) < 9))
            return Optional.of(new Point(p.x, p.y + 1, points.get(p.y + 1).get(p.x)));
        return Optional.empty();
    }

    private static Optional<Point> lowerLeft(Point p, List<List<Integer>> points) {
        if ((p.x > 0) && (points.get(p.y).get(p.x - 1) < 9))
            return Optional.of(new Point(p.x - 1, p.y, points.get(p.y).get(p.x - 1)));
        return Optional.empty();
    }

    private static Optional<Point> lowerRigth(Point p, List<List<Integer>> points) {
        if ((p.x < points.get(p.y).size() - 1) && (points.get(p.y).get(p.x + 1) < 9))
            return Optional.of(new Point(p.x + 1, p.y, points.get(p.y).get(p.x + 1)));
        return Optional.empty();
    }

}
