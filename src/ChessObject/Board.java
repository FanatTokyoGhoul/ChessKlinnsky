package ChessObject;

import Graph.AdjListsGraph;
import Graph.Digraph;
import Graph.Graph;
import Graph.GraphUtils;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private Graph board = new AdjListsGraph();
    private Map<Integer, Figure> blackFigure = new HashMap<>();
    private Map<Integer, Figure> whiteFigure = new HashMap<>();
    private Map<Figure, Integer> cellWhiteFigure = new HashMap<>();
    private Map<Figure, Integer> cellBlackFigure =  new HashMap<>();
    private Map<Integer, Figure> figures = new HashMap<>();
    private Map<Figure, Integer> cellFigures = new HashMap<>();

    public Board() {
        createChess();
    }

    private void createChess(){
        board = GraphUtils.digraphParser();
        addFigureOnBoardWhite(new Pawn(true), 16);
        addFigureOnBoardWhite(new Pawn(true), 17);
        addFigureOnBoardWhite(new Pawn(true), 18);
        addFigureOnBoardWhite(new Pawn(true), 19);
        addFigureOnBoardWhite(new Pawn(true), 20);
        addFigureOnBoardWhite(new Pawn(true), 21);
        addFigureOnBoardWhite(new Pawn(true), 22);
        addFigureOnBoardWhite(new Pawn(true), 23);
        addFigureOnBoardWhite(new Pawn(true), 24);
        addFigureOnBoardWhite(new Rook(true), 15);
        addFigureOnBoardWhite(new Rook(true), 9);
        addFigureOnBoardWhite(new Knight(true), 8);
        addFigureOnBoardWhite(new Knight(true), 4);
        addFigureOnBoardWhite(new Queen(true), 1);
        addFigureOnBoardWhite(new Knight(true), 3);
        addFigureOnBoardWhite(new Bishop(true), 0);
        addFigureOnBoardWhite(new Bishop(true), 2);
        addFigureOnBoardWhite(new Bishop(true), 6);
        addFigureOnBoardBlack(new Pawn(false), 81);
        addFigureOnBoardBlack(new Pawn(false), 71);
        addFigureOnBoardBlack(new Pawn(false), 61);
        addFigureOnBoardBlack(new Pawn(false), 51);
        addFigureOnBoardBlack(new Pawn(false), 41);
        addFigureOnBoardBlack(new Pawn(false), 53);
        addFigureOnBoardBlack(new Pawn(false), 65);
        addFigureOnBoardBlack(new Pawn(false), 77);
        addFigureOnBoardBlack(new Pawn(false), 89);
        addFigureOnBoardBlack(new Rook(false), 88);
        addFigureOnBoardBlack(new Rook(false), 82);
        addFigureOnBoardBlack(new Knight(false), 83);
        addFigureOnBoardBlack(new Knight(false), 87);
        addFigureOnBoardBlack(new Queen(false), 84);
        addFigureOnBoardBlack(new King(false), 86);
        addFigureOnBoardBlack(new Bishop(false), 85);
        addFigureOnBoardBlack(new Bishop(false), 74);
        addFigureOnBoardBlack(new Bishop(false), 63);
    }

    private void addFigureOnBoardBlack(Figure figure, int edge){
        blackFigure.put(edge, figure);
        cellBlackFigure.put(figure,edge);
        figures.put(edge, figure);
        cellFigures.put(figure,edge);
    }

    private void addFigureOnBoardWhite(Figure figure, int edge){
        whiteFigure.put(edge, figure);
        cellWhiteFigure.put(figure,edge);
        figures.put(edge, figure);
        cellFigures.put(figure,edge);
    }

    @Override
    public String toString() {
        String[] f = new String[91];
        for (int i = 0; i < f.length; i++){
            if(this.figures.get(i) == null){
                f[i] = " ";
                continue;
            }
            f[i] = this.figures.get(i).toString();
        }
        return "          11 _ 11\n" +
                "        10 _/" + f[85] + "\\_ 10\n" +
                "       9 _/" + f[84] + "\\_/" + f[86] + "\\_ 9\n" +
                "     8 _/" + f[83] + "\\_/" + f[74] + "\\_/" + f[87] + "\\_ 8\n" +
                "   7 _/" + f[82] + "\\_/" + f[73] + "\\_/" + f[75] + "\\_/" + f[88] + "\\_ 7\n" +
                "   _/" + f[81] + "\\_/" + f[72] + "\\_/" + f[63] + "\\_/" + f[76] + "\\_/" + f[89] + "\\_\n" +
                "6 /" + f[80] + "\\_/" + f[71] + "\\_/" + f[62] + "\\_/" + f[64] + "\\_/" + f[77] + "\\_/" + f[90] + "\\ 6\n" +
                "  \\_/" + f[70] + "\\_/" + f[61] + "\\_/" + f[52] + "\\_/" + f[65] + "\\_/" + f[78] + "\\_/\n" +
                "5 /" + f[69] + "\\_/" + f[60] + "\\_/" + f[51] + "\\_/" + f[53] + "\\_/" + f[66] + "\\_/" + f[79] + "\\ 5\n" +
                "  \\_/" + f[59] + "\\_/" + f[50] + "\\_/" + f[41] + "\\_/" + f[54] + "\\_/" + f[67] + "\\_/\n" +
                "4 /" + f[58] + "\\_/" + f[49] + "\\_/" + f[40] + "\\_/" + f[42] + "\\_/" + f[55] + "\\_/" + f[68] + "\\ 4\n" +
                "  \\_/" + f[48] + "\\_/" + f[39] + "\\_/" + f[30] + "\\_/" + f[43] + "\\_/" + f[56] + "\\_/\n" +
                "3 /" + f[47] + "\\_/" + f[38] + "\\_/" + f[29] + "\\_/" + f[31] + "\\_/" + f[44] + "\\_/" + f[57] + "\\ 3\n" +
                "  \\_/" + f[37] + "\\_/" + f[28] + "\\_/" + f[20] + "\\_/" + f[32] + "\\_/" + f[45] + "\\_/\n" +
                "2 /" + f[36] + "\\_/" + f[27] + "\\_/" + f[19] + "\\_/" + f[21] + "\\_/" + f[33] + "\\_/" + f[46] + "\\ 2\n" +
                "  \\_/" + f[26] + "\\_/" + f[18] + "\\_/" + f[12] + "\\_/" + f[22] + "\\_/" + f[34] + "\\_/\n" +
                "1 /" + f[25] + "\\_/" + f[17] + "\\_/" + f[11] + "\\_/" + f[13] + "\\_/" + f[23] + "\\_/" + f[35] + "\\ 1\n" +
                "  \\_/" + f[16] + "\\_/" + f[10] + "\\_/" + f[6] + "\\_/"+ f[14] + "\\_/" + f[24] + "\\_/\n" +
                "  a \\_/" + f[9] + "\\_/" + f[5] + "\\_/" + f[7] + "\\_/" + f[15] + "\\_/ l\n" +
                "    b \\_/" + f[4] + "\\_/" + f[2] + "\\_/" + f[8] + "\\_/ k\n" +
                "      c \\_/" + f[1] + "\\_/" + f[3] + "\\_/ i\n" +
                "        d \\_/" + f[0] + "\\_/ h\n" +
                "          e \\_/ g\n" +
                "             f";
    }
}
