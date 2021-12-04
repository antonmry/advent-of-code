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

public class Challenge3_1 {

    public static int getPowerConsumption(String input) {

        var inputList = Arrays
                .stream(input.split(System.getProperty("line.separator")))
                .map(String::trim)
                .toList();

        var length = inputList.get(0).length();
        var gammaRate = 0;
        var epsilonRate = 0;

        // TODO: more functional approach?
        for (int x = 0; x < length; x++) {
            var one = 0;
            var zero = 0;

            for (String s : inputList) {
                if (s.charAt(x) == '0')
                    zero++;
                else
                    one++;
            }

            if (zero < one)
                gammaRate = gammaRate + (int) Math.pow(2, length - 1 - x);
            // We could do a XOR the gammaRate, more efficient
            if (zero > one)
                epsilonRate = epsilonRate + (int) Math.pow(2, length - 1 - x);
        }
        return gammaRate * epsilonRate;
    }
}
