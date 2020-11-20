package chess_object.figure;

public class Pawn extends Figure {
    private final String icon;

    public Pawn(boolean isWhite) {
        this.icon = isWhite ? "♟":"♙";
    }

    @Override
    public String toString() {
        return icon;
    }
}
