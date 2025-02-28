package model;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static util.BoardTemplate.BOARD_TEMPLATE;

public class Board {

    private List<List<Space>> spaces;

    public Board(String spaces) {
        for (int i = 0; i < 9; i++) {
            List<Space> row = new ArrayList<>();
            int colLimit = 9 + 9 * i;
            for (int col = 0 + 9 * i; col < colLimit; col++) {
                Integer number = spaces.charAt(col) == '.' ? null : Integer.parseInt(spaces.substring(col, col + 1));
                Space space = new Space(number, nonNull(number));
                row.add(space);
            }
            this.spaces.add(row);
        }
    }

    public boolean changeSpaceNumber(int row, int col, int value) {
        return spaces.get(row).get(col).setCurrentNumber(value);
    }

    public boolean removeSpaceNumber(int row, int col) {
        return spaces.get(row).get(col).clearNumber();
    }

    public boolean isFinished() {
        return !getBoardStatus().equals(GameStatus.COMPLETE) && !hasErrors();
    }

    public GameStatus getBoardStatus() {
        if (isNull(spaces)) {
            return GameStatus.NOT_STARTED;
        }

        boolean empty = false;
        int row = 0;
        while(!empty) {
            for (Space space : spaces.get(row)) {
                empty = isNull(space.getCurrentNumber());
                if (empty) {
                    break;
                }
            }
            row++;
        }

        if (empty) {
            return GameStatus.INCOMPLETE;
        }
        return GameStatus.COMPLETE;
    }

    public boolean hasErrors() {
        boolean rows = checkRows();
        boolean cols = checkColumns();
        boolean groups = checkGroups();

        return !(rows && cols && groups);
    }

    private boolean checkRows() {
        boolean error = false;
        for (int row = 0; row < 9; row++) {
            List<Integer> numberQuantity = List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
            for (Space space : spaces.get(row)) {
                Integer number = space.getCurrentNumber();
                if (isNull(number)) {
                    continue;
                }
                numberQuantity.set(number, numberQuantity.get(number) + 1);
            }

            error = numberQuantity.stream().anyMatch(n -> n > 1);
            if (error) break;
        }
        return !error;
    }

    private boolean checkColumns() {
        boolean error = false;
        for (int col = 0; col < 9; col++) {
            List<Integer> numberQuantity = List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
            for (int row = 0; row < 9; row++) {
                Integer number = spaces.get(row).get(col).getCurrentNumber();
                if (isNull(number)) {
                    continue;
                }
                numberQuantity.set(number, numberQuantity.get(number) + 1);
            }

            error = numberQuantity.stream().anyMatch(n -> n > 1);
            if (error) break;
        }
        return !error;
    }

    private boolean checkGroups() {
        boolean error = false;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                error = checkGroup(row, col);
                if (error) {
                    break;
                }
            }
            if (error) {
                break;
            }
        }

        return !error;
    }

    private boolean checkGroup(int groupRow, int groupColumn) {
        List<Integer> numberQuantity = List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        for (int row = 0 + 3 * groupRow; row < 3 + 3 * groupRow; row++) {
            for (int col = 0 + 3 * groupColumn; col < 3 + 3 * groupColumn; col++) {
                Integer number = spaces.get(row).get(col).getCurrentNumber();
                if (isNull(number)) {
                    continue;
                }
                numberQuantity.set(number, numberQuantity.get(number) + 1);
            }
        }
        return numberQuantity.stream().anyMatch(n -> n > 1);
    }

    public String getCurrentBoard() {
        List<Integer> allSpaces = new ArrayList<>();
        spaces.forEach(row -> {
            row.forEach(col -> {
                allSpaces.add(col.getCurrentNumber());
            });
        });
        return String.format(BOARD_TEMPLATE, allSpaces);
    }

    public List<List<Space>> getSpaces() {
        return spaces;
    }

    public void setSpaces(List<List<Space>> spaces) {
        this.spaces = spaces;
    }
}
