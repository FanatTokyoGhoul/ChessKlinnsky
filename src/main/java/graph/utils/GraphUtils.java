package graph.utils;

import chess_object.board.Direction;
import graph.AdjListChessDigraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
/** Class with different methods for working with the graph**/
public class GraphUtils {
    //Method for read graph from file
    public static AdjListChessDigraph digraphParser(){
        AdjListChessDigraph digraph = new AdjListChessDigraph();
        try {
            int counter = 0;
            Scanner scanFile = new Scanner(new FileReader(new File("Graph/Graph.txt")));
            while (scanFile.hasNextLine()){
                String[] arrayString = scanFile.nextLine().split(" ");
                counter++;

                for(int i = 1; i < arrayString.length; i++){
                    String[] edge = arrayString[i].split(";");
                    digraph.addEdge(arrayString[0], edge[2], new Direction(Integer.parseInt(edge[1])), "d".equals(edge[0]));
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        digraph.createDiagonal();
        return digraph;
    }

}
