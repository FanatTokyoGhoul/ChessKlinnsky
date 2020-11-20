package chess_object.figure;

public class Knight extends Figure{
    private final String icon;

    public Knight(boolean isWhite) {
        this.icon = isWhite ? "♞":"♘";
    }

    @Override
    public String toString() {
        return icon;
    }
}
