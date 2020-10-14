package ChessObject;

public class Queen implements Figure{
    private boolean isBlack;

    public Queen(boolean isBlack) {
        this.isBlack = isBlack;
    }

    @Override
    public String toString() {
        return isBlack ? "♛":"♕";
    }
}
