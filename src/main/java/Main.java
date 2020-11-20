import chess_object.board.Board;
import chess_object.utils.GameUtils;

public class Main {
    public static void main(String[] args) {
        Board board =  new Board();
        GameUtils.game(board);
    }
}
