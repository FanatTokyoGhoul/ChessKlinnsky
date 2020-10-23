package player;

import chess_object.Maps;
import graph.Digraph;


public interface Player {
    void move(Digraph board, Maps maps);
}
