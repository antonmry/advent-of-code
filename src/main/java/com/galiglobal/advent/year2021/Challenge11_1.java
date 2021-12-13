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
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Challenge11_1 {

    private record octopus(int x, int y, int level, boolean increased, boolean flashed) {
    }

    public static int countTotalFlashes(String input, int flashes) {
        return 0;
    }

    private static List<List<String>> getInputList(String input) {
        return Arrays.stream(input.split(System.getProperty("line.separator")))
            .map(s -> s.chars()
                .mapToObj(c -> String.valueOf((char) c))
                .toList())
            .toList();
    }
}
