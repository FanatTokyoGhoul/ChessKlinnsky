package chess_object;

public class King implements Figure{
    private boolean isWhite;

    public King(boolean isWhite) {
        this.isWhite = isWhite;
    }

    @Override
    public String toString() {
        return isWhite ? "♚":"♔";
    }
}
