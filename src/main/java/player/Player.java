package player;

import chess_object.board.Direction;
import chess_object.figure.Figure;
import chess_object.figure.King;

import java.util.HashMap;
import java.util.Map;


public abstract class Player {



    private String name;
    private Map<Figure, String> cells = new HashMap<>();
    private Map<String, Figure> figures = new HashMap<>();
    protected Direction moveDirection;

    public Player(String name, Direction moveUp) {
        this.name = name;
        this.moveDirection = moveUp;
    }

    public Player(String name, Direction moveUp, Map<Figure, String> cells, Map<String, Figure> figures) {
        this.name = name;
        this.moveDirection = moveUp;
        this.cells = cells;
        this.figures = figures;
    }

    public String getName() {
        return name;
    }

    public Map<Figure, String> getCells() {
        return cells;
    }

    public Map<String, Figure> getFigures() {
        return figures;
    }

    public Direction getMoveDirection() {
        return moveDirection;
    }

    public Figure getKing(){
        for(Map.Entry<String, Figure> keyAndValue:figures.entrySet()){
            if(keyAndValue.getValue() instanceof King){
                return keyAndValue.getValue();
            }
        }
        return null;
    }

    public abstract Player copy();
}
