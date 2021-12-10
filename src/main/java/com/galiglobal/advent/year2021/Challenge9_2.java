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
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class Challenge9_2 {

    private record Point(int x, int y, int height) {
    }

    private enum LastDirection {
        LEFT,
        RIGTH,
        UP,
        DOWN,
        ANY
    }

    public static long multiplyThreeLargestBasins(String input) {
        final List<List<Integer>> inputList = getInputList(input);

        final List<Point> lowPoints = IntStream.range(0, inputList.size())
            .mapToObj(y -> IntStream.range(0, inputList.get(y).size())
                .mapToObj(x -> getLowPoint(inputList, x, y))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList()
            ).flatMap(List::stream)
            .toList();

        //Create basin
        final List<List<Point>> basins = lowPoints.stream().map(l -> {
                final List<Point> emptyBasins = List.of();
                return getBasins(inputList, emptyBasins, l, LastDirection.ANY);
            })
            .toList();

        return 0;
    }

    private static List<Point> getBasins(List<List<Integer>> inputList, List<Point> basin, Point p, LastDirection lastDirection) {

        System.out.println("p = " + p);
        if (!isEndOfBasin(inputList, p, lastDirection)) return List.of(p);
        System.out.println("p not returned = " + p);

        //final List<Point> basinTemp = Stream.concat(basin.stream(), Stream.of(p)).toList();
        //List<Point> basinTemp = new ArrayList<>(Stream.concat(basin.stream(), Stream.of(p)).toList());
        List<Point> basinTemp = new ArrayList<>(basin.stream().toList());

        // Up
        if ((p.y > 0) && (inputList.get(p.y - 1).get(p.x) < 9) && !lastDirection.equals(LastDirection.DOWN))
            basinTemp.addAll(getBasins(inputList, basinTemp, new Point(p.x, p.y - 1, inputList.get(p.y - 1).get(p.x)), LastDirection.DOWN));

        // Down
/*
        if ((p.y < inputList.size() - 1) && (inputList.get(p.y + 1).get(p.x) < 9) && !lastDirection.equals(LastDirection.UP))
            basinTemp.addAll(getBasins(inputList, basin, new Point(p.x, p.y + 1, inputList.get(p.y + 1).get(p.x)), LastDirection.UP));
*/

        return basinTemp;
/*
        // up
        if ((p.y > 0) && (inputList.get(p.y - 1).get(p.x) < 9))
            return getBasins(inputList, basin, new Point(p.x, p.y - 1, inputList.get(p.y - 1).get(p.x)));

        // above
        if ((p.y < inputList.size() - 1) && (inputList.get(p.y + 1).get(p.x) < 9))
            return getBasins(inputList, basin, new Point(p.x, p.y + 1, inputList.get(p.y + 1).get(p.x)));

        // left
        if ((p.x > 0) && (inputList.get(p.y).get(p.x - 1) < 9))
            return getBasins(inputList, basin, new Point(p.x - 1, p.y, inputList.get(p.y).get(p.x - 1)));

        // right
        if ((p.x > inputList.get(p.y).size() - 1) && (inputList.get(p.y).get(p.x + 1) < 9))
            return getBasins(inputList, basin, new Point(p.x + 1, p.y, inputList.get(p.y).get(p.x + 1)));

        basin.add(p); // TODO: immutable list!!
        return basin;
*/
    }

    private static boolean isEndOfBasin(List<List<Integer>> inputList, Point p, LastDirection lastDirection) {
        // Up
        if ((p.y > 0) && (inputList.get(p.y - 1).get(p.x) < 9) && !lastDirection.equals(LastDirection.DOWN)) return true;

        // Down
        if ((p.y < inputList.size() - 1) && (inputList.get(p.y + 1).get(p.x) < 9) && !lastDirection.equals(LastDirection.UP))
            return true;

        //Left
        if ((p.x > 0) && (inputList.get(p.y).get(p.x - 1) < 9) && !lastDirection.equals(LastDirection.RIGTH))
            return true;

        // Right
        if ((p.x > inputList.get(p.y).size() - 1) && (inputList.get(p.y).get(p.x + 1) < 9) && !lastDirection.equals(LastDirection.LEFT))
            return true;

        return false;
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
