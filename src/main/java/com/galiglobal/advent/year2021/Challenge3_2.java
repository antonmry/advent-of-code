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

public class Challenge3_2 {

    public static long getLifeSupportRating(String input) {

        // TODO: this class needs serious refactoring but it's too late today
        var inputList = Arrays
                .stream(input.split(System.getProperty("line.separator")))
                .map(String::trim)
                .toList();

        var oxygenGeneratorRateList = new ArrayList<>(inputList);
        var CO2ScrubberRatingList = new ArrayList<>(inputList);

        var length = inputList.get(0).length();

        // TODO: more functional approach?
        for (int x = 0; x < length; x++) {

            // OxygenGeneratorRate
            var one = 0;
            var zero = 0;

            for (String s : oxygenGeneratorRateList) {
                if (s.charAt(x) == '0')
                    zero++;
                else
                    one++;
            }

            if (oxygenGeneratorRateList.size() > 1)
                if (zero <= one) {
                    for (int z = oxygenGeneratorRateList.size() - 1; z >= 0; z--) {
                        if (oxygenGeneratorRateList.get(z).charAt(x) == '0')
                            oxygenGeneratorRateList.remove(z);
                    }
                }
                else {
                    for (int z = oxygenGeneratorRateList.size() - 1; z >= 0; z--) {
                        if (oxygenGeneratorRateList.get(z).charAt(x) == '1')
                            oxygenGeneratorRateList.remove(z);
                    }
                }

            // CO2 Scrubber Rating
            // TODO: too much duplicated code, I know
            one = 0;
            zero = 0;

            for (String s : CO2ScrubberRatingList) {
                if (s.charAt(x) == '0')
                    zero++;
                else
                    one++;
            }

            if (CO2ScrubberRatingList.size() > 1)
                if (zero <= one) {
                    for (int z = CO2ScrubberRatingList.size() - 1; z >= 0; z--) {
                        if (CO2ScrubberRatingList.get(z).charAt(x) == '1')
                            CO2ScrubberRatingList.remove(z);
                    }
                }
                else {
                    for (int z = CO2ScrubberRatingList.size() - 1; z >= 0; z--) {
                        if (CO2ScrubberRatingList.get(z).charAt(x) == '0')
                            CO2ScrubberRatingList.remove(z);
                    }
                }
        }

        var oxygenGeneratorRate = Long.parseLong(oxygenGeneratorRateList.get(0), 2);
        var CO2ScrubberRating = Long.parseLong(CO2ScrubberRatingList.get(0), 2);
        return oxygenGeneratorRate * CO2ScrubberRating;
    }
}
