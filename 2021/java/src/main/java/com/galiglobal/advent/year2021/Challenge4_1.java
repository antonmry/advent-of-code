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

public class Challenge4_1 {

    public static int getFinalScore(String input) {

        List<String> inputList = getInputList(input);
        Board winnerBoard = getWinnerBoard(getRandomOrder(inputList), getBoards(inputList)).orElseThrow();
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
                        .map(Challenge4_1::getRow)
                        .toList())
                .map(b -> new Board(b, false, -1))
                .toList();
    }

    private static Optional<Board> getWinnerBoard(Stream<Integer> randomOrder, List<Board> boards) {
        return randomOrder
                .reduce(boards,
                        Board::updateBoard,
                        Board::returnNextElement)
                .stream()
                .filter(b -> b.winner).findFirst();
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

    private record Board(List<List<Cell>> cells, boolean winner, int lastNumber) {

        public static List<Board> returnNextElement(List<Board> subtotal, List<Board> element) {
            if (Board.getWinner(subtotal).isPresent()) return subtotal; // There is already a winner
            return element;
        }

        public static List<Board> updateBoard(List<Board> subtotal, int element) {

            if (getWinner(subtotal).isPresent()) return subtotal; // There is already a winner

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
