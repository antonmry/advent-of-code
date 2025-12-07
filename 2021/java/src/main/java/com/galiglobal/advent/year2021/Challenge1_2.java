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
import java.util.stream.IntStream;

public class Challenge1_2 {

    public static int getSumThreeSlideWindow(String input) {
        var inputList = getInputList(input);

        return IntStream.range(0, inputList.size() - 2).map(i -> switch (i) {
            case 0 -> 0;
            default -> (inputList.get(i - 1) + inputList.get(i) + inputList.get(i + 1) < inputList.get(i) + inputList.get(i + 1) + inputList.get(i + 2)) ? 1 : 0;
        }).sum();
    }

    private static List<Integer> getInputList(String input) {
        return Arrays.stream(input.split(System.getProperty("line.separator")))
                .map(String::trim)
                .map(Integer::valueOf)
                .toList();
    }
}
