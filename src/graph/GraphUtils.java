package graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
/** Class with different methods for working with the graph**/
public class GraphUtils {
    //Method for read graph from file
    public static Digraph digraphParser(){
        Digraph digraph = new AdjListsDigraph();
        try {
            Scanner scanFile = new Scanner(new FileReader(new File("Graph/Graph.txt")));
            while (scanFile.hasNextLine()){
                String[] arrayString = scanFile.nextLine().split(" ");

                for(int i = 1; i < arrayString.length; i++){
                    digraph.addAdge(Integer.parseInt(arrayString[0]) - 1, Integer.parseInt(arrayString[i]) - 1);
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return digraph;
    }
}
