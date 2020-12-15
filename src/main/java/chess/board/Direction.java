package chess.board;

import java.util.Objects;

public class Direction {

    public static int UP = 0;
    public static int UP_RIGHT = 1;
    public static int DOWN_RIGHT = 2;
    public static int DOWN = 3;
    public static int DOWN_LEFT = 4;
    public static int UP_LEFT = 5;


    private final int direction;

    public Direction(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direction direction1 = (Direction) o;
        return direction == direction1.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction);
    }
}
