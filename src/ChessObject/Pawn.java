package ChessObject;

public class Pawn implements Figure {
    private boolean isWhite;

    public Pawn(boolean isWhite) {
        this.isWhite = isWhite;
    }

    @Override
    public String toString() {
        return isWhite ? "♟":"♙";
    }
}
