package chess_object;

public class Knight implements Figure{
    private boolean isWhite;

    public Knight(boolean isWhite) {
        this.isWhite = isWhite;
    }

    @Override
    public String toString() {
        return isWhite ? "♞":"♘";
    }
}
