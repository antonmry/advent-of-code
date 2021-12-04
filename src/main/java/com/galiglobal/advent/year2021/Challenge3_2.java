package com.galiglobal.advent.year2021;

import java.util.ArrayList;
import java.util.Arrays;

public class Challenge3_2 {

    public static long getLifeSupportRating(String input) {

        var inputList = Arrays
            .stream(input.split(System.getProperty("line.separator")))
            .map(String::trim)
            .toList();

        var oxygenGeneratorRateList = new ArrayList<>(inputList);
        var CO2ScrubberRatingList = new ArrayList<>(inputList);

        var length = inputList.get(0).length();

        // TODO: more functional approach?
        // TODO: this class needs serious refactoring but it's too late today
        for (int x = 0; x < length; x++) {

            // OxygenGeneratorRate
            var one = 0;
            var zero = 0;

            for (String s : oxygenGeneratorRateList) {
                if (s.charAt(x) == '0') zero++;
                else one++;
            }

            if (oxygenGeneratorRateList.size() > 1)
                if (zero <= one) {
                    for (int z = oxygenGeneratorRateList.size() - 1; z >= 0; z--) {
                        if (oxygenGeneratorRateList.get(z).charAt(x) == '0')
                            oxygenGeneratorRateList.remove(z);
                    }
                } else {
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
                if (s.charAt(x) == '0') zero++;
                else one++;
            }

            if (CO2ScrubberRatingList.size() > 1)
                if (zero <= one) {
                    for (int z = CO2ScrubberRatingList.size() - 1; z >= 0; z--) {
                        if (CO2ScrubberRatingList.get(z).charAt(x) == '1') CO2ScrubberRatingList.remove(z);
                    }
                } else {
                    for (int z = CO2ScrubberRatingList.size() - 1; z >= 0; z--) {
                        if (CO2ScrubberRatingList.get(z).charAt(x) == '0') CO2ScrubberRatingList.remove(z);
                    }
                }
        }

        var oxygenGeneratorRate = Long.parseLong(oxygenGeneratorRateList.get(0), 2);
        var CO2ScrubberRating = Long.parseLong(CO2ScrubberRatingList.get(0), 2);
        return oxygenGeneratorRate * CO2ScrubberRating;
    }
}

