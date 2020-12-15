package player;

import chess.board.Direction;
import chess.figure.Figure;

import java.util.HashMap;
import java.util.Map;

public class RealPlayer extends Player{
    public RealPlayer(String name, Direction moveUp) {
        super(name, moveUp);
    }

    public RealPlayer(String name, Direction moveUp, Map<Figure, String> cells, Map<String, Figure> figures) {
        super(name, moveUp, cells, figures);
    }

    @Override
    public Player copy() {
        Map<String, Figure> copyFigures = new HashMap<>(getFigures());
        Map<Figure, String> copyCells = new HashMap<>(getCells());

        return new RealPlayer(getName(), moveDirection, copyCells, copyFigures);
    }
}
