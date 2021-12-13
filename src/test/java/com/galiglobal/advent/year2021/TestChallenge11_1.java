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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestChallenge11_1 {

    @Test
    void testExample() {
        assertEquals(204, Challenge11_1.countTotalFlashes(example, 10));
        assertEquals(1656, Challenge11_1.countTotalFlashes(example, 100));
    }

    @Test
    void testChallenge() {
        assertEquals(-1, Challenge11_1.countTotalFlashes(challenge, 100));
    }

    private static final String example = """
        5483143223
        2745854711
        5264556173
        6141336146
        6357385478
        4167524645
        2176841721
        6882881134
        4846848554
        5283751526""";

    private static final String challenge = """
        5723573158
        3154748563
        4783514878
        3848142375
        3637724151
        8583172484
        7747444184
        1613367882
        6228614227
        4732225334""";
}
