package ChessObject;

public class King implements Figure{
    private boolean isWhite;

    public King(boolean isBlack) {
        this.isWhite = isBlack;
    }

    @Override
    public String toString() {
        return isWhite ? "♚":"♔";
    }
}
