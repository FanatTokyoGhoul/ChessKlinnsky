package Player;

import ChessObject.Figure;
import Graph.Digraph;

import java.util.Map;

public interface Player {
    void move(Digraph board, Map<Integer, Figure> white, Map<Integer, Figure> black);
}
