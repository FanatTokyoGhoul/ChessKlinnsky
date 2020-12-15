import chess.board.Board;
import chess.services.GameService;
import gson.services.GsonService;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        GameService gs = new GameService();
        gs.startGame();
    }
}
