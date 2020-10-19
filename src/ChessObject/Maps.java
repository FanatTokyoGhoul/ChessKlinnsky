package ChessObject;

import Graph.GraphUtils;

import java.util.HashMap;
import java.util.Map;

public class Maps {
    private Map<Integer, Figure> blackFigure = new HashMap<>();
    private Map<Integer, Figure> whiteFigure = new HashMap<>();
    private Map<Figure, Integer> cellWhiteFigure = new HashMap<>();
    private Map<Figure, Integer> cellBlackFigure =  new HashMap<>();
    private Map<Integer, Figure> figures = new HashMap<>();
    private Map<Figure, Integer> cellFigures = new HashMap<>();

    public Maps() {
        test();
        //createChess();
    }

    private void test(){
        addFigureOnBoardWhite(new Pawn(true), 28);
        addFigureOnBoardBlack(new Pawn(false), 40);
    }

    public void moveFigure(Figure figure, int edge){
        if(cellBlackFigure.get(figure) != null) {
            int nowStay = cellBlackFigure.get(figure);
            cellBlackFigure.remove(figure);
            cellFigures.remove(figure);
            blackFigure.remove(nowStay);
            figures.remove(nowStay);
            cellBlackFigure.put(figure, edge);
            cellFigures.put(figure, edge);
            blackFigure.put(edge,figure);
            figures.put(edge,figure);
        }else {
            int nowStay = cellWhiteFigure.get(figure);
            cellWhiteFigure.remove(figure);
            cellFigures.remove(figure);
            whiteFigure.remove(nowStay);
            figures.remove(nowStay);
            cellWhiteFigure.put(figure, edge);
            cellFigures.put(figure, edge);
            whiteFigure.put(edge,figure);
            figures.put(edge,figure);
        }
    }

    private void createChess(){
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

    public Map<Integer, Figure> getBlackFigure() {
        return blackFigure;
    }

    public Map<Integer, Figure> getWhiteFigure() {
        return whiteFigure;
    }

    public Map<Figure, Integer> getCellWhiteFigure() {
        return cellWhiteFigure;
    }

    public Map<Figure, Integer> getCellBlackFigure() {
        return cellBlackFigure;
    }

    public Map<Integer, Figure> getFigures() {
        return figures;
    }

    public Map<Figure, Integer> getCellFigures() {
        return cellFigures;
    }
}
