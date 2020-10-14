package ChessObject;

public class Knight implements Figure{
    private boolean isBlack;

    public Knight(boolean isBlack) {
        this.isBlack = isBlack;
    }

    @Override
    public String toString() {
        return isBlack ? "♞":"♘";
    }
}
