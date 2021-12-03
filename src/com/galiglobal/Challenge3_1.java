package com.galiglobal;

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
                if (s.charAt(x) == '0') zero++;
                else one++;
            }

            if (zero < one) gammaRate = gammaRate + (int) Math.pow(2, length - 1 - x);
            // We could do a XOR the gammaRate, more efficient
            if (zero > one) epsilonRate = epsilonRate + (int) Math.pow(2, length - 1 - x);
        }
        return gammaRate * epsilonRate;
    }
}

