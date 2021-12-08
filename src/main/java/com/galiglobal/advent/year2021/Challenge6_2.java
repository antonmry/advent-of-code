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
import java.util.function.Function;
import java.util.stream.Collectors;

public class Challenge6_2 {

    public static long countFish(String input, int day) {
        final Map<Integer, Long> inputAccumulatedMap = getInputAccumulatedMap(input);
        return countFishPerDay(inputAccumulatedMap, day).values().stream().mapToLong(Long::longValue).sum();
    }

    private static Map<Integer, Long> getInputAccumulatedMap(String input) {
        return Arrays.stream(input.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.groupingBy(Function.identity(),
                        Collectors.counting()));
    }

    private static Map<Integer, Long> countFishPerDay(Map<Integer, Long> inputList, int day) {
        if (day == 0)
            return inputList;

        return countFishPerDay(
                inputList.entrySet().stream()
                        .map(e -> switch (e.getKey()) {
                        case 0 -> List.of(Map.entry(6, e.getValue()), Map.entry(8, e.getValue()));
                        case 1 -> List.of(Map.entry(0, e.getValue()));
                        case 2 -> List.of(Map.entry(1, e.getValue()));
                        case 3 -> List.of(Map.entry(2, e.getValue()));
                        case 4 -> List.of(Map.entry(3, e.getValue()));
                        case 5 -> List.of(Map.entry(4, e.getValue()));
                        case 6 -> List.of(Map.entry(5, e.getValue()));
                        case 7 -> List.of(Map.entry(6, e.getValue()));
                        default -> List.of(Map.entry(7, e.getValue())); // 8
                        })
                        .flatMap(List::stream)
                        .collect(Collectors.groupingBy(Map.Entry::getKey,
                                Collectors.summingLong(Map.Entry::getValue))),
                day - 1);
    }

}
