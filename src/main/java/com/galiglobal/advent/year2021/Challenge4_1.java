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
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class Challenge4_1 {

    public static long getFinalScore(String input) {

        var inputList = Arrays
                .stream(input.split(System.getProperty("line.separator")))
                .map(String::trim)
                .toList();

        var randomOrder = Arrays.stream(inputList.get(0).split(","))
                .map(s -> Integer.valueOf(s));

        final var numberOfBoards = (inputList.size() - 1) / 6;
        var boards = IntStream.range(0, numberOfBoards)
                .mapToObj(i -> IntStream.range(2, 7)
                        .mapToObj(j -> inputList.get(i * 6 + j))
                        .map(s -> getRow(s))
                        .toList())
                .map(b -> new Board(b, false, -1))
                .toList();

        var boardsResult = randomOrder
                .reduce(boards,
                        (subtotal, element) -> Board.updateBoard(subtotal, element),
                        Board::returnNextElement);

        final var board = boardsResult.stream().filter(b -> b.winner).findFirst();
        final int sumUnmarked = board.stream()
                .map(v -> v.cells)
                .flatMap(List::stream)
                .flatMap(List::stream)
                .filter(cell -> !cell.marked)
                .mapToInt(cell -> cell.value)
                .sum();

        final int lastElement = board.stream().mapToInt(l -> l.lastNumber).findFirst().getAsInt();

        return sumUnmarked * lastElement;
    }

    private static List<Cell> getRow(String s) {
        return Arrays.stream(s.replace("  ", " ").split(" "))
                .map(v -> new Cell(Integer.valueOf(v.trim()), false))
                .toList();
    }

    private record Cell(int value, boolean marked) {
    }

    private record Board(List<List<Cell>> cells, boolean winner, int lastNumber) {

        public static List<Board> returnNextElement(List<Board> subtotal, List<Board> element) {
            if (Board.getWinner(subtotal).isPresent()) return subtotal; // There is already a winner
            return element;
        }

        public static List<Board> updateBoard(List<Board> subtotal, int element) {

            if (getWinner(subtotal).isPresent()) return subtotal;

            return subtotal.stream()
                .map(ll -> ll.cells.stream()
                    .map(l -> l.stream()
                        .map(c -> {
                            if (c.value == element) return new Cell(c.value, true);
                            return c;
                        }).toList()
                    ).toList())
                .map(llc -> new Board(llc, isWinner(llc), element)).toList();
        }

        public static Optional<Board> getWinner(List<Board> boards) {
            return boards.stream().filter(b -> b.winner).findFirst();
        }

        private static boolean isWinner(List<List<Cell>> cells) {
            return cells.stream().anyMatch(lc -> (lc.stream().filter(c -> c.marked).count() == 5)) ||
                IntStream.range(0, 5).anyMatch(j -> (cells.stream().filter(lc -> lc.get(j).marked).count() == 5));
        }
    }
}
