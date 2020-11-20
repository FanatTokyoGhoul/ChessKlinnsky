package player;

import chess_object.board.Direction;
import chess_object.figure.Figure;

import java.util.HashMap;
import java.util.Map;

public class BotPlayer extends Player{
    public BotPlayer(String name, Direction moveUp) {
        super(name, moveUp);
    }

    public BotPlayer(String name, Direction moveUp, Map<Figure, String> cells, Map<String, Figure> figures) {
        super(name, moveUp, cells, figures);
    }

    @Override
    public Player copy() {
        Map<String, Figure> copyFigures = new HashMap<>(getFigures());
        Map<Figure, String> copyCells = new HashMap<>(getCells());

        return new BotPlayer(getName(), moveDirection, copyCells, copyFigures);
    }
}
