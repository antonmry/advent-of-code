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
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Challenge2_1 {

    public record Command(String direction, int value) {
    }

    public static int getPositionDepthMultiply(String input) {
        Supplier<Stream<Command>> commandStream = () -> Arrays.stream(input.split(System.getProperty("line.separator"))).map(String::trim)
                .map(s -> new Command(s.substring(0, s.indexOf(' ')), Integer.valueOf(s.substring(s.indexOf(' ') + 1))));

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
