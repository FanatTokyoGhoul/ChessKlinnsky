package ChessObject;

public class King implements Figure{
    private boolean isBlack;

    public King(boolean isBlack) {
        this.isBlack = isBlack;
    }

    @Override
    public String toString() {
        return isBlack ? "♚":"♔";
    }
}
