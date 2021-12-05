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
import java.util.stream.Stream;

public class Challenge4_2 {

    public static int getFinalScore(String input) {
        List<String> inputList = getInputList(input);
        Board winnerBoard = getLastWinnerBoard(getRandomOrder(inputList), getBoards(inputList)).orElseThrow();
        return calculateSumUnmarked(winnerBoard) * winnerBoard.lastNumber;
    }

    private static List<String> getInputList(String input) {
        return Arrays
                .stream(input.split(System.getProperty("line.separator")))
                .map(String::trim)
                .toList();
    }

    private static Stream<Integer> getRandomOrder(List<String> inputList) {
        return Arrays.stream(inputList.get(0).split(","))
                .map(Integer::valueOf);
    }

    private static List<Board> getBoards(List<String> inputList) {
        return IntStream.range(0, (inputList.size() - 1) / 6)
                .mapToObj(i -> IntStream.range(2, 7)
                        .mapToObj(j -> inputList.get(i * 6 + j))
                        .map(Challenge4_2::getRow)
                        .toList())
                .map(b -> new Board(b, false, -1, false))
                .toList();
    }

    private static Optional<Board> getLastWinnerBoard(Stream<Integer> randomOrder, List<Board> boards) {
        return randomOrder
                .reduce(boards,
                        Board::updateBoard,
                        Board::returnNextElement)
                .stream()
                .filter(b -> b.isLastWinner).findFirst();
    }

    private static int calculateSumUnmarked(Board winnerBoard) {
        return winnerBoard.cells.stream()
                .flatMap(List::stream)
                .filter(cell -> !cell.marked)
                .mapToInt(cell -> cell.value)
                .sum();
    }

    private static List<Cell> getRow(String boardLine) {
        return Arrays.stream(boardLine.replace("  ", " ").split(" "))
                .map(v -> new Cell(Integer.parseInt(v), false))
                .toList();
    }

    private record Cell(int value, boolean marked) {
    }

    private record Board(List<List<Cell>> cells, boolean isWinner, int lastNumber, boolean isLastWinner) {

        public static List<Board> returnNextElement(List<Board> subtotal, List<Board> element) {
            if (Board.getLastWinner(subtotal).isPresent()) return subtotal; // There is already a last winner
            return element;
        }

        public static List<Board> updateBoard(List<Board> subtotal, int element) {

            if (countNotWinners(subtotal) == 0) return subtotal; // All are already winners

            final List<Board> subtotalUpdated = subtotal.stream()
                .map(ll -> ll.cells.stream()
                    .map(l -> l.stream()
                        .map(c -> {
                            if (c.value == element) return new Cell(c.value, true);
                            return c;
                        }).toList()
                    ).toList())
                .map(llc -> new Board(llc, isWinner(llc), element, false))
                .toList();

            if (countNotWinners(subtotalUpdated) == 0) { // It was the last winner, update the list of boards
                return IntStream.range(0, subtotal.size())
                    .mapToObj(i -> {
                        if (subtotal.get(i).isWinner != subtotalUpdated.get(i).isWinner) {
                            return new Board(subtotalUpdated.get(i).cells, true, subtotalUpdated.get(i).lastNumber, true);
                        }
                        return subtotalUpdated.get(i);
                    }).toList();
            }
            return subtotalUpdated;
        }

        public static Optional<Board> getLastWinner(List<Board> boards) {
            return boards.stream().filter(b -> b.isLastWinner).findFirst();
        }

        private static boolean isWinner(List<List<Cell>> cells) {
            return cells.stream().anyMatch(lc -> (lc.stream().filter(c -> c.marked).count() == 5)) ||
                IntStream.range(0, 5).anyMatch(j -> (cells.stream().filter(lc -> lc.get(j).marked).count() == 5));
        }

        private static long countNotWinners(List<Board> subtotal) {
            return subtotal.stream().filter(b -> !b.isWinner).count();
        }
    }
}
