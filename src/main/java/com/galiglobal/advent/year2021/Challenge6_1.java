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

public class Challenge6_1 {

    public static long countFish(String input, int day) {
        final List<Integer> inputList = getInputList(input);
        return countFishPerDay(inputList, day).size();
    }

    private static List<Integer> getInputList(String input) {
        return Arrays.stream(input.split(","))
                .map(Integer::parseInt)
                .toList();
    }

    // TODO: we choose the recursive solution but it isn't efficient here
    private static List<Integer> countFishPerDay(List<Integer> inputList, int day) {
        if (day == 0)
            return inputList;

        return countFishPerDay(
                inputList.stream().map(f -> switch (f) {
                case 0 -> List.of(6, 8);
                default -> List.of(f - 1);
                })
                        .flatMap(List::stream)
                        .toList(),
                day - 1);
    }
}
