package model;

public class Space {

    private Integer currentNumber;
    private boolean fixed;

    public Space(Integer currentNumber, boolean fixed) {
        this.currentNumber = currentNumber;
        this.fixed = fixed;
    }

    public Integer getCurrentNumber() {
        return currentNumber;
    }

    public boolean setCurrentNumber(Integer currentNumber) {
        if (isFixed()) {
            return false;
        }
        this.currentNumber = currentNumber;
        return true;
    }

    public boolean isFixed() {
        return fixed;
    }

    public boolean clearNumber() {
        if (isFixed()) {
            return false;
        }
        currentNumber = null;
        return true;
    }
}
