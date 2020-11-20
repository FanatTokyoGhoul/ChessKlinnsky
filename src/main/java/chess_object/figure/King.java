package chess_object.figure;

public class King extends Figure{
    private final String icon;

    public King(boolean isWhite) {
        this.icon = isWhite ? "♚":"♔";
    }

    @Override
    public String toString() {
        return icon;
    }
}
