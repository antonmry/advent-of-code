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
import java.util.Optional;
import java.util.stream.IntStream;

public class Challenge9_1 {

    public static long getRiskLevelsSum(String input) {

        final List<List<Integer>> inputList = getInputList(input);

        return IntStream.range(0, inputList.size())
                .map(y -> IntStream.range(0, inputList.get(y).size())
                        .mapToObj(x -> getLowPoint(inputList, x, y))
                        .filter(l -> l.isPresent()) // TODO: is it possible to combine filter and map?
                        .mapToInt(Optional::get)
                        .map(v -> v + 1)
                        .sum())
                .sum();
    }

    private static List<List<Integer>> getInputList(String input) {
        return Arrays.stream(input.split(System.getProperty("line.separator")))
                .map(s -> s.chars() // TODO: easiest way?
                        .mapToObj(c -> String.valueOf((char) c))
                        .map(Integer::parseInt)
                        .toList())
                .toList();
    }

    private static Optional<Integer> getLowPoint(List<List<Integer>> inputList, int x, int y) {
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

        return Optional.of(inputList.get(y).get(x));
    }
}
