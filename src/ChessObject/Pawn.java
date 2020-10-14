package ChessObject;

public class Pawn implements Figure {
    private boolean isBlack;

    public Pawn(boolean isBlack) {
        this.isBlack = isBlack;
    }

    @Override
    public String toString() {
        return isBlack ? "♟":"♙";
    }
}
