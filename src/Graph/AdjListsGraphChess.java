package Graph;

import ChessObject.Figure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class AdjListsGraphChess extends AdjListsGraph {
    private Map<Integer, Figure> blackFigure = new HashMap<>();
    private Map<Integer, Figure> whiteFigure = new HashMap<>();
    private Map<Figure, Integer> CellWhiteFigure = new HashMap<>();
    private Map<Figure, Integer> CellBlackFigure =  new HashMap<>();

    public AdjListsGraphChess() {

    }

    private void parser(){
        try {
           Scanner scanFile = new Scanner(new FileReader(new File("./Graph/Graph.txt")));
            while (scanFile.hasNextLine()){
                String[] arrayString = scanFile.nextLine().split(" ");

                for(int i = 1; i < arrayString.length - 2; i++){
                    addAdge(Integer.parseInt(arrayString[0]), Integer.parseInt(arrayString[i]));
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
