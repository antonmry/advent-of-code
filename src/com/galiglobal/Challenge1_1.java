package com.galiglobal;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Challenge1_1 {

    public static int getPrevMeasurementCount(String input) {

        var arr = Arrays.stream(
                input.split(System.getProperty("line.separator"))
            ).
            map(String::trim).
            map(Integer::valueOf).
            toList();

        return IntStream.range(0, arr.size()).
            map(i -> switch (i) {
                    case 0 -> 0;
                    default -> (arr.get(i - 1) < arr.get(i)) ? 1 : 0;
                }
            ).sum();
    }

    // This alternative works, but it's a quite ugly and risky solution
    private static int previous = -1;

    public static int getPrevMeasurementCountWithStatus(String input) {

        return Arrays.stream(input.split(System.getProperty("line.separator"))).
            map(String::trim).
            map(Integer::valueOf).
            mapToInt(i -> {
                try {
                    if (previous < 0) return 0;
                    if (i > previous) return 1;
                    return 0;
                } finally {
                    previous = i;
                }
            }).sum();
    }
}
