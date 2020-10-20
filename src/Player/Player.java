package Player;

import chess_object.Figure;
import graph.Digraph;

import java.util.Map;

public interface Player {
    void move(Digraph board, Map<Integer, Figure> white, Map<Integer, Figure> black);
}
