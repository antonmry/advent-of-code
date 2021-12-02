package com.galiglobal;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Challenge2_2 {

    public record Command(String direction, int value) {
    }

    public record Location(int position, int depth, int aim) {
    }

    public static Location l = new Location(0, 0, 0);

    public static int getPositionDepthMultiply(String input) {
        Arrays.
            stream(input.split(System.getProperty("line.separator"))).
            map(String::trim).
            map(s -> new Command(s.substring(0, s.indexOf(' ')), Integer.valueOf(s.substring(s.indexOf(' ') + 1)))).
            map(c -> switch (c.direction) {
                case "forward" -> new Location(l.position + c.value, l.depth + c.value * l.aim, l.aim);
                case "up" -> new Location(l.position, l.depth, l.aim - c.value);
                case "down" -> new Location(l.position, l.depth, l.aim + c.value);
                default -> l;
            }).
            forEach(location -> l = location);

        return l.position * l.depth;
    }
}

