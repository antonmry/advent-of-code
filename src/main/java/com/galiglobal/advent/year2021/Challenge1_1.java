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
import java.util.stream.IntStream;

public class Challenge1_1 {

    public static int getPrevMeasurementCount(String input) {

        var arr = Arrays.stream(
                input.split(System.getProperty("line.separator"))).map(String::trim).map(Integer::valueOf).toList();

        return IntStream.range(0, arr.size()).map(i -> switch (i) {
            case 0 -> 0;
            default -> (arr.get(i - 1) < arr.get(i)) ? 1 : 0;
        }).sum();
    }

    // This alternative works, but it's a quite ugly and risky solution
    private static int previous = -1;

    public static int getPrevMeasurementCountWithStatus(String input) {

        return Arrays.stream(input.split(System.getProperty("line.separator"))).map(String::trim).map(Integer::valueOf).mapToInt(i -> {
            try {
                if (previous < 0)
                    return 0;
                if (i > previous)
                    return 1;
                return 0;
            }
            finally {
                previous = i;
            }
        }).sum();
    }
}
