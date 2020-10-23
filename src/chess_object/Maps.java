package chess_object;


import constant.Constant;

import java.util.HashMap;
import java.util.Map;

public class Maps {
    private Map<String, Figure> blackFigure = new HashMap<>();
    private Map<String, Figure> whiteFigure = new HashMap<>();
    private Map<Figure, String> cellWhiteFigure = new HashMap<>();
    private Map<Figure, String> cellBlackFigure = new HashMap<>();
    private Map<String, Figure> figures = new HashMap<>();
    private Map<Figure, String> cellFigures = new HashMap<>();

    public Maps() {
        test();
        //createChess();
    }

    private void test() {
        addFigureOnBoardWhite(new King(true), "f5");
        addFigureOnBoardWhite(new Pawn(true), "f6");
        addFigureOnBoardWhite(new Pawn(true), "f4");
        addFigureOnBoardWhite(new Pawn(true), "e5");
        addFigureOnBoardWhite(new Pawn(true), "e4");
        addFigureOnBoardWhite(new Pawn(true), "g4");
        addFigureOnBoardWhite(new Pawn(true), "g5");
        addFigureOnBoardWhite(new Pawn(true), "e3");
        addFigureOnBoardWhite(new Pawn(true), "g6");
        addFigureOnBoardWhite(new Pawn(true), "e6");
        addFigureOnBoardWhite(new Pawn(true), "d4");
        addFigureOnBoardWhite(new Pawn(true), "h4");
        addFigureOnBoardBlack(new Bishop(false), "h1");
        addFigureOnBoardWhite(new Rook(true), "f1");
    }

    public Figure getWhiteKing(){
        for(Map.Entry<Figure, String> keyAndValue:cellWhiteFigure.entrySet()){
            if(keyAndValue.getKey() instanceof King){
                return keyAndValue.getKey();
            }
        }
        return null;
    }

    public Figure getBlackKing(){
        for(Map.Entry<Figure, String> keyAndValue:cellBlackFigure.entrySet()){
            if(keyAndValue.getKey() instanceof King){
                return keyAndValue.getKey();
            }
        }
        return null;
    }

    public void setFigure(Figure figure, String edge) {
        if (cellBlackFigure.get(figure) != null) {
            cellBlackFigure.put(figure, edge);
            cellFigures.put(figure, edge);
            blackFigure.put(edge, figure);
        }else{
            cellWhiteFigure.put(figure, edge);
            cellFigures.put(figure, edge);
            whiteFigure.put(edge, figure);
        }
        figures.put(edge, figure);
    }

    public void moveFigure(Figure figure, String edge) {
        if (cellBlackFigure.get(figure) != null) {
            String nowStay = cellBlackFigure.get(figure);
            cellBlackFigure.remove(figure);
            cellFigures.remove(figure);
            blackFigure.remove(nowStay);
            figures.remove(nowStay);
            cellBlackFigure.put(figure, edge);
            cellFigures.put(figure, edge);
            blackFigure.put(edge, figure);
            figures.put(edge, figure);
        } else {
            String nowStay = cellWhiteFigure.get(figure);
            cellWhiteFigure.remove(figure);
            cellFigures.remove(figure);
            whiteFigure.remove(nowStay);
            figures.remove(nowStay);
            cellWhiteFigure.put(figure, edge);
            cellFigures.put(figure, edge);
            whiteFigure.put(edge, figure);
            figures.put(edge, figure);
        }
    }

    private void createChess() {
        addFigureOnBoardWhite(new Pawn(true), Constant.toChessCoord(16));
        addFigureOnBoardWhite(new Pawn(true), Constant.toChessCoord(17));
        addFigureOnBoardWhite(new Pawn(true), Constant.toChessCoord(18));
        addFigureOnBoardWhite(new Pawn(true), Constant.toChessCoord(19));
        addFigureOnBoardWhite(new Pawn(true), Constant.toChessCoord(20));
        addFigureOnBoardWhite(new Pawn(true), Constant.toChessCoord(21));
        addFigureOnBoardWhite(new Pawn(true), Constant.toChessCoord(22));
        addFigureOnBoardWhite(new Pawn(true), Constant.toChessCoord(23));
        addFigureOnBoardWhite(new Pawn(true), Constant.toChessCoord(24));
        addFigureOnBoardWhite(new Rook(true), Constant.toChessCoord(15));
        addFigureOnBoardWhite(new Rook(true), Constant.toChessCoord(9));
        addFigureOnBoardWhite(new Knight(true), Constant.toChessCoord(8));
        addFigureOnBoardWhite(new Knight(true), Constant.toChessCoord(4));
        addFigureOnBoardWhite(new Queen(true), Constant.toChessCoord(1));
        addFigureOnBoardWhite(new King(true), Constant.toChessCoord(3));
        addFigureOnBoardWhite(new Bishop(true), Constant.toChessCoord(0));
        addFigureOnBoardWhite(new Bishop(true), Constant.toChessCoord(2));
        addFigureOnBoardWhite(new Bishop(true), Constant.toChessCoord(6));
        addFigureOnBoardBlack(new Pawn(false), Constant.toChessCoord(81));
        addFigureOnBoardBlack(new Pawn(false), Constant.toChessCoord(71));
        addFigureOnBoardBlack(new Pawn(false), Constant.toChessCoord(61));
        addFigureOnBoardBlack(new Pawn(false), Constant.toChessCoord(51));
        addFigureOnBoardBlack(new Pawn(false), Constant.toChessCoord(41));
        addFigureOnBoardBlack(new Pawn(false), Constant.toChessCoord(53));
        addFigureOnBoardBlack(new Pawn(false), Constant.toChessCoord(65));
        addFigureOnBoardBlack(new Pawn(false), Constant.toChessCoord(77));
        addFigureOnBoardBlack(new Pawn(false), Constant.toChessCoord(89));
        addFigureOnBoardBlack(new Rook(false), Constant.toChessCoord(88));
        addFigureOnBoardBlack(new Rook(false), Constant.toChessCoord(82));
        addFigureOnBoardBlack(new Knight(false), Constant.toChessCoord(83));
        addFigureOnBoardBlack(new Knight(false), Constant.toChessCoord(87));
        addFigureOnBoardBlack(new Queen(false), Constant.toChessCoord(84));
        addFigureOnBoardBlack(new King(false), Constant.toChessCoord(86));
        addFigureOnBoardBlack(new Bishop(false), Constant.toChessCoord(85));
        addFigureOnBoardBlack(new Bishop(false), Constant.toChessCoord(74));
        addFigureOnBoardBlack(new Bishop(false), Constant.toChessCoord(63));
    }

    private void addFigureOnBoardBlack(Figure figure, String edge) {
        blackFigure.put(edge, figure);
        cellBlackFigure.put(figure, edge);
        figures.put(edge, figure);
        cellFigures.put(figure, edge);
    }

    private void addFigureOnBoardWhite(Figure figure, String edge) {
        whiteFigure.put(edge, figure);
        cellWhiteFigure.put(figure, edge);
        figures.put(edge, figure);
        cellFigures.put(figure, edge);
    }

    public void restart(){
        blackFigure.clear();
        whiteFigure.clear();
        figures.clear();
        cellBlackFigure.clear();
        cellWhiteFigure.clear();
        cellFigures.clear();
        createChess();
    }

    public Map<String, Figure> getBlackFigure() {
        return blackFigure;
    }

    public Map<String, Figure> getWhiteFigure() {
        return whiteFigure;
    }

    public Map<Figure, String> getCellWhiteFigure() {
        return cellWhiteFigure;
    }

    public Map<Figure, String> getCellBlackFigure() {
        return cellBlackFigure;
    }

    public Map<String, Figure> getFigures() {
        return figures;
    }

    public Map<Figure, String> getCellFigures() {
        return cellFigures;
    }
}
