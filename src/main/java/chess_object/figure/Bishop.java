package chess_object.figure;

public class Bishop extends Figure{
    private final String icon;

    public Bishop(boolean isWhite) {
        this.icon = isWhite ? "♝":"♗";
    }

    @Override
    public String toString() {
        return icon;
    }
}
