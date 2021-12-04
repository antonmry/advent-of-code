package com.galiglobal.advent.year2021;

import java.util.Arrays;

public class Challenge2_2 {

    public record Location(String lastDirection, int lastValue, int position, int depth, int aim) {
        public int getResult() {
            return position * depth;
        }
    }

    public final static Location l = new Location("", 0, 0, 0, 0);

    public static int getPositionDepthMultiply(String input) {
        return Arrays.
            stream(input.split(System.getProperty("line.separator"))).
            map(String::trim).
            map(s -> new Location(s.substring(0, s.indexOf(' ')), Integer.valueOf(s.substring(s.indexOf(' ') + 1)), 0, 0, 0)).
            reduce(l, (s, e) -> switch (e.lastDirection) {
                case "forward" -> new Location("", 0, s.position + e.lastValue, s.depth + e.lastValue * s.aim, s.aim);
                case "up" -> new Location("", 0, s.position, s.depth, s.aim - e.lastValue);
                case "down" -> new Location("", 0, s.position, s.depth, s.aim + e.lastValue);
                default -> l;
            }).getResult();
    }
}

