package ChessObject;

import Graph.Digraph;

import java.util.HashMap;
import java.util.Map;

public class ChessUtils {

    private static final Map<String, Integer> toEdge = new HashMap<>();

    static {
        toEdge.put("d4", 28);
        toEdge.put("f1", 0);
        toEdge.put("f4", 12);
        toEdge.put("f5", 20);
        toEdge.put("f6", 30);
        toEdge.put("g5", 31);
        toEdge.put("g6", 42);
        toEdge.put("e5", 29);
        toEdge.put("e6", 40);
    }

    public static Boolean moveFigure(String move, Digraph digraph, Maps maps, boolean isBlack) {
        move = move.toLowerCase();
        String[] moves = move.split(" ");
        Figure figure = maps.getFigures().get(toEdge.get(moves[0]));
        if (figure instanceof Pawn) {
            return movePawn(figure, moves, digraph, maps, isBlack);
        }
        System.out.println("You have chosen an empty cell!!");
        return false;
    }

    private static Boolean movePawn(Figure figure, String[] moves, Digraph digraph, Maps maps, boolean isBlack) {

        if (isBlack) {
            return moveBlackPawn(figure, moves, digraph, maps);
        } else {
            return moveWhitePawn(figure, moves, digraph, maps);
        }
    }

    private static Boolean moveBlackPawn(Figure figure, String[] moves, Digraph digraph, Maps maps) {
        int nowStay;
        int nextStay;
        if (maps.getCellBlackFigure().get(figure) == null) {
            System.out.println("This is not your figure!");
            return false;
        }
        nowStay = maps.getCellBlackFigure().get(figure);
        int checkNextStay = toEdge.get(moves[1]);
        if (moves[0].charAt(0) == moves[1].charAt(0)) {
            nextStay = digraph.getDown(nowStay);
        } else if (checkNextStay == digraph.getDownRight(digraph.getDown(nowStay))) {
            nextStay = digraph.getDownRight(digraph.getDown(nowStay));
        } else if (checkNextStay == digraph.getDownLeft(digraph.getDown(nowStay))) {
            nextStay = digraph.getDownLeft(digraph.getDown(nowStay));
        } else {
            nextStay = -1;
        }
        if (nextStay != -1 && moves[0].charAt(0) == moves[1].charAt(0)) {
            if (maps.getWhiteFigure().get(nextStay) == null && maps.getBlackFigure().get(nextStay) == null) {
                maps.moveFigure(figure, nextStay);
                System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
                return true;
            } else {
                System.out.println("Your figure or someone else's is standing here.");
                return false;
            }
        } else if (nextStay != -1 && maps.getWhiteFigure().get(nextStay) != null) {
            if (maps.getBlackFigure().get(nextStay) == null) {
                maps.moveFigure(figure, nextStay);
                System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
                return true;
            } else {
                System.out.println("Your figure is standing here.");
                return false;
            }
        }
        System.out.println("The pawn cannot go here");
        return false;
    }

    private static Boolean moveWhitePawn(Figure figure, String[] moves, Digraph digraph, Maps maps) {
        int nowStay;
        int nextStay;
        if (maps.getCellWhiteFigure().get(figure) == null) {
            System.out.println("This is not your figure!");
            return false;
        }
        nowStay = maps.getCellWhiteFigure().get(figure);
        int checkNextStay = toEdge.get(moves[1]);
        if (moves[0].charAt(0) == moves[1].charAt(0)) {
            nextStay = digraph.getUp(nowStay);
        } else if (checkNextStay == digraph.getUpRight(digraph.getUp(nowStay))) {
            nextStay = digraph.getUpRight(digraph.getUp(nowStay));
        } else if (checkNextStay == digraph.getUpLeft(digraph.getUp(nowStay))) {
            nextStay = digraph.getUpLeft(digraph.getUp(nowStay));
        } else {
            nextStay = -1;
        }
        if (nextStay != -1 && moves[0].charAt(0) == moves[1].charAt(0)) {
            if (maps.getBlackFigure().get(nextStay) == null && maps.getWhiteFigure().get(nextStay) == null) {
                maps.moveFigure(figure, nextStay);
                System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
                return true;
            } else {
                System.out.println("Your figure or someone else's is standing here.");
                return false;
            }
        } else if (nextStay != -1 && maps.getBlackFigure().get(nextStay) != null) {
            if (maps.getWhiteFigure().get(nextStay) == null) {
                maps.moveFigure(figure, nextStay);
                System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
                return true;
            } else {
                System.out.println("Your figure is standing here.");
                return false;
            }
        }
        System.out.println("The pawn cannot go here");
        return false;
    }
}
