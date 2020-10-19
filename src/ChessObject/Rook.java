package ChessObject;

public class Rook implements Figure {
    private boolean isWhite;

    public Rook(boolean isWhite) {
        this.isWhite = isWhite;
    }

    @Override
    public String toString() {
        return isWhite ? "♜":"♖";
    }
}
