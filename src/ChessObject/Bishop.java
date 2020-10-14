package ChessObject;

public class Bishop implements Figure{
    private boolean isBlack;

    public Bishop(boolean isBlack) {
        this.isBlack = isBlack;
    }

    @Override
    public String toString() {
        return isBlack ? "♝":"♗";
    }
}
