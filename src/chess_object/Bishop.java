package chess_object;

public class Bishop implements Figure{
    private boolean isWhite;

    public Bishop(boolean isWhite) {
        this.isWhite = isWhite;
    }

    @Override
    public String toString() {
        return isWhite ? "♝":"♗";
    }
}
