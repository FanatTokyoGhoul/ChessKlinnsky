package ChessObject;

public class Knight implements Figure{
    private boolean isWhite;

    public Knight(boolean isBlack) {
        this.isWhite = isBlack;
    }

    @Override
    public String toString() {
        return isWhite ? "♞":"♘";
    }
}
