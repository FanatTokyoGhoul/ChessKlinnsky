package chess_object.figure;

public class Queen extends Figure{
    private final String icon;

    public Queen(boolean isWhite) {
        this.icon = isWhite ? "♛":"♕";
    }

    @Override
    public String toString() {
        return icon;
    }
}
