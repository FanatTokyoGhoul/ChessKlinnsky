package chess_object.figure;

public class Rook extends Figure {
    private final String icon;

    public Rook(boolean isWhite) {
        this.icon = isWhite ? "♜":"♖";
    }

    @Override
    public String toString() {
        return icon;
    }
}
