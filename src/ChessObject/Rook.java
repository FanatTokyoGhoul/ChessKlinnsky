package ChessObject;

public class Rook implements Figure {
    private boolean isBlack;

    public Rook(boolean isBlack) {
        this.isBlack = isBlack;
    }

    @Override
    public String toString() {
        return isBlack ? "♜":"♖";
    }
}
