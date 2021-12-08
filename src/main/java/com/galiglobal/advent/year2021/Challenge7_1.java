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

public class Challenge7_1 {

    private static List<Integer> inputList;

    public static long calculateFuel(String input) {
        inputList = getInputList(input);
        return calculateFuel(inputList.stream().max(Integer::compareTo).orElseThrow());
    }

    private static List<Integer> getInputList(String input) {
        return Arrays.stream(input.split(","))
                .map(Integer::parseInt)
                .toList();
    }

    // Recursive solution: why not? Just for fun
    private static long calculateFuel(Integer position) {
        final long fuel = inputList.stream().mapToInt(i -> Math.abs(i - position)).sum();
        if (position == 0)
            return fuel;
        final long nextFuel = calculateFuel(position - 1);
        return (nextFuel < fuel) ? nextFuel : fuel;
    }
}
