package chess_object;

public class Queen implements Figure{
    private boolean isWhite;

    public Queen(boolean isWhite) {
        this.isWhite = isWhite;
    }

    @Override
    public String toString() {
        return isWhite ? "♛":"♕";
    }
}
