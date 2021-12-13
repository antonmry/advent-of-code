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

public class Challenge11_1 {

    private record Octopus(int level, boolean increased, boolean flashed) {
    }

    public static int countTotalFlashes(String input, int steps) {

        final List<List<Octopus>> inputList = getInputList(input);

        List<List<Octopus>> lists = new ArrayList<>(); // TODO: refactor

        for (int i = 0; i < steps; i++) {

            if (i == 0) lists = inputList;

            long countFlashed = lists.stream().map(ol -> ol.stream()
                .filter(o -> o.flashed)).count();

            lists = lists.stream()
                .map(ol -> ol.stream()
                    .map(o -> {
                        // TODO: move to Octopus
                        if (((o.flashed == false) || (o.increased == false)) && (o.level == 9))
                            return new Octopus(0, true, true);
                        return new Octopus(o.level + 1, true, o.flashed());
                    }).toList()
                ).toList();

            long countFlashedNew = lists.stream().map(ol -> ol.stream()
                .filter(o -> o.flashed)).count();

            if (countFlashedNew > countFlashed) {
                // Increase neighbours and flash them if they are 9
                // This is a loop

            }

            printOctopuses(lists);
        }
        return 0;
    }

    private static List<List<Octopus>> getInputList(String input) {
        return Arrays.stream(input.split(System.getProperty("line.separator")))
            .map(s -> s.chars()
                .mapToObj(c -> new Octopus(c - '0', false, false))
                .toList())
            .toList();
    }

    private static void printOctopuses(List<List<Octopus>> octopusList) {
        System.out.println();
        octopusList.stream()
            .peek(ol -> ol.stream().forEach(o -> System.out.print(o.level)))
            .forEach(ol -> System.out.println());
        System.out.println();
    }

}
