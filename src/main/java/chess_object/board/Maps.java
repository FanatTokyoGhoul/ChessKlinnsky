package chess_object.board;


import chess_object.figure.Figure;

import java.util.HashMap;
import java.util.Map;

public class Maps {

    private Map<String, Figure> figures = new HashMap<>();
    private Map<Figure, String> cellFigures = new HashMap<>();

    public Maps(Map<String, Figure> figures, Map<Figure, String> cellFigures) {
        this.figures = figures;
        this.cellFigures = cellFigures;
    }

    public Maps() {
    }

    public Map<String, Figure> getFigures() {
        return figures;
    }

    public Map<Figure, String> getCells() {
        return cellFigures;
    }

    public Maps copy(){
        Map<String, Figure> copyFigures = new HashMap<>(getFigures());
        Map<Figure, String> copyCells = new HashMap<>(getCells());

        return new Maps(copyFigures ,copyCells);
    }
}
