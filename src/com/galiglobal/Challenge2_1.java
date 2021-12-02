package com.galiglobal;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Challenge2_1 {

    public record Command(String direction, int value) {
    }

    public static int getPositionDepthMultiply(String input) {
        Supplier<Stream<Command>> commandStream = () -> Arrays.
            stream(input.split(System.getProperty("line.separator"))).
            map(String::trim).
            map(s -> new Command(s.substring(0, s.indexOf(' ')), Integer.valueOf(s.substring(s.indexOf(' ') + 1))));

        return getPosition(commandStream.get()) * getDepth(commandStream.get());
    }

    private static int getPosition(Stream<Command> commandStream) {
        return commandStream.mapToInt(s -> switch (s.direction) {
            case "forward" -> s.value;
            default -> 0;
        }).sum();
    }

    private static int getDepth(Stream<Command> commandStream) {
        return commandStream.mapToInt(s -> switch (s.direction) {
            case "down" -> s.value;
            case "up" -> -1 * s.value;
            default -> 0;
        }).sum();
    }
}
