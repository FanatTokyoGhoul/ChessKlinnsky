package chess_object;

import constant.Constant;
import graph.AdjListChessDigraph;
import graph.Digraph;

import java.util.*;

/**
 * Service class in which the movement of chess pieces is implemented
 **/

public class ChessUtils {



    public static boolean isMate(Figure king, Maps maps, AdjListChessDigraph digraph) {
        Map<Figure, String> cellAttackingSide = maps.getCellBlackFigure() != null ? maps.getCellBlackFigure() : maps.getCellWhiteFigure();
        Map<Figure, String> cellDefenderSide = maps.getCellBlackFigure() != null ? maps.getCellWhiteFigure() : maps.getCellBlackFigure();


        Figure attackedKnight = null;
        List<Figure> attackFigure = new ArrayList<>();

        for (Map.Entry<Figure, String> keyAndValue : cellAttackingSide.entrySet()) {
            if (cellIsAttacked(keyAndValue.getKey(), cellAttackingSide.get(keyAndValue.getKey()), digraph, maps, cellDefenderSide.get(king))) {
                if (keyAndValue.getKey() instanceof Knight) {
                    attackedKnight = keyAndValue.getKey();
                }
                attackFigure.add(keyAndValue.getKey());
            }
        }

        if (attackFigure.size() == 0) {
            return false;
        }

        if (attackFigure.size() > 1) {
            return true;
        }

        if (attackedKnight != null) {
            for (Map.Entry<Figure, String> keyAndValue : cellDefenderSide.entrySet()) {
                return !cellIsAttacked(keyAndValue.getKey(), cellDefenderSide.get(keyAndValue.getKey()), digraph, maps, cellAttackingSide.get(attackedKnight));
            }
        }

        List<String> edgeWhereTheKingCanGo = checkEdgeWhereTheKingCanGo(maps, digraph, cellDefenderSide.get(king));

        for (Map.Entry<Figure, String> keyAndValue : cellAttackingSide.entrySet()) {
            edgeWhereTheKingCanGo.removeIf(edge -> cellIsAttacked(keyAndValue.getKey(), cellAttackingSide.get(keyAndValue.getKey()), digraph, maps, edge));
        }

        if (edgeWhereTheKingCanGo.size() > 0) {
            return false;
        }

        List<String> directionAttackKing = checkDirection(attackFigure.get(0), cellAttackingSide.get(attackFigure.get(0)), digraph, maps, cellDefenderSide.get(king));

        if(directionAttackKing.size() == 0){
            return false;
        }

        for (Map.Entry<Figure, String> keyAndValue : cellDefenderSide.entrySet()) {
            for(String edge: directionAttackKing) {
                if(cellIsAttacked(keyAndValue.getKey(), cellDefenderSide.get(keyAndValue.getKey()), digraph, maps, edge)){
                    return false;
                }
            }
        }

        return true;
    }

    private static List<String> checkDirection(Figure figure, String nowStay, AdjListChessDigraph digraph, Maps maps, String nextStay){
        if (figure instanceof Rook) {
            return getDirectionHorizontalAndVerticalLines(maps, digraph, nextStay, nowStay);
        } else if (figure instanceof Bishop) {
            return getDirectionDiagonalLines(maps, digraph, nextStay, nowStay);
        } else if (figure instanceof Queen) {
            List<String> bufferedList = getDirectionDiagonalLines(maps, digraph, nextStay, nowStay);
            if(bufferedList.size() > 0){
                return bufferedList;
            }else {
                return getDirectionHorizontalAndVerticalLines(maps, digraph, nextStay, nowStay);
            }
        }
        return new ArrayList<>();
    }

    private static boolean cellIsAttacked(Figure figure, String nowStay, AdjListChessDigraph digraph, Maps maps, String nextStay) {
        if (figure instanceof Pawn) {
            return digraph.getCellDiagonal(nowStay).get(new Direction(0)).equals(nextStay)
                    || digraph.getCellDiagonal(nowStay).get(new Direction(1)).equals(nextStay);
        } else if (figure instanceof Knight) {
            return knightCanMove(nowStay,nextStay,digraph,maps);
        } else if (figure instanceof Rook) {
            return checkHorizontalAndVerticalLines(maps, digraph, nextStay, nowStay);
        } else if (figure instanceof Bishop) {
            return checkDiagonalLines(maps, digraph, nextStay, nowStay);
        } else if (figure instanceof Queen) {
            return checkHorizontalAndVerticalLines(maps, digraph, nextStay, nowStay) || checkDiagonalLines(maps, digraph, nextStay, nowStay);
        }
        return false;
    }



    private static List<String> checkEdgeWhereTheKingCanGo(Maps maps, AdjListChessDigraph digraph, String nowStay) {
        Map<String, Figure> friends = maps.getBlackFigure().get(nowStay) == null ? maps.getWhiteFigure():maps.getBlackFigure();
        List<String> edgeWhereTheKingCanGo = new ArrayList<>();

        for(Map.Entry<Direction, String> keyAndValue : digraph.getCellHorizontalAndVertical(nowStay).entrySet()){
            if(friends.get(keyAndValue.getValue()) == null){
                edgeWhereTheKingCanGo.add(keyAndValue.getValue());
            }
        }

        for(Map.Entry<Direction, String> keyAndValue : digraph.getCellDiagonal(nowStay).entrySet()){
            if(friends.get(keyAndValue.getValue()) == null){
                edgeWhereTheKingCanGo.add(keyAndValue.getValue());
            }
        }

        return edgeWhereTheKingCanGo;
    }


    /**
     * In this method, we find out which instance of the class we got and, depending on this,
     * select the logic of the figure's move
     **/
    public static boolean moveFigure(String move, AdjListChessDigraph digraph, Maps maps) {
        move = move.toLowerCase();
        String[] moves = move.split(" ");

        if (Constant.toEdge(moves[1]) == null || Constant.toEdge(moves[0]) == null) { //Check in case we are given a
            System.out.println("You have selected a non-existing coordinate.");       //coordinate which does not exist
            return false;                                                             //whose coordinate does not exist
        }

        Figure figure = maps.getFigures().get(moves[0]);

        if (figure instanceof Pawn) {
            return movePawn(figure, moves, digraph, maps);
        } else if (figure instanceof Knight) {
            return moveKnight(figure, moves, digraph, maps);
        } else if (figure instanceof Rook) {
            return moveRook(figure, moves, digraph, maps);
        } else if (figure instanceof Bishop) {
            return moveBishop(figure, moves, digraph, maps);
        } else if (figure instanceof Queen) {
             return moveQueen(figure, moves, digraph, maps);
        } else if (figure instanceof King) {
            return moveKing(figure, moves, digraph, maps);
        }

        System.out.println("You have chosen an empty cell!!");
        return false;
    }

    /**
     * Logic for the king. For black and white, the logic is not very different.
     * The bottom line is that we check all possible directions in which the king
     * can go with the condition that the length of the step e is greater than 1
     **/

    private static boolean moveKing(Figure figure, String[] moves, AdjListChessDigraph digraph, Maps maps) {
        if (checkDiagonalLines(maps, digraph, moves[1], moves[0], true) ||
                checkHorizontalAndVerticalLines(maps, digraph, moves[1], moves[0], true)) {
            if (maps.getBlackFigure().get(moves[1]) != null || maps.getWhiteFigure().get(moves[1]) == null) {
                maps.moveFigure(figure, moves[1]);
                return true;
            } else {
                System.out.println("Your figure is standing here.");
                return false;
            }
        } else {
            System.out.println("The King cannot go here.");
            return false;
        }
    }

    /**
     * The logic of the queen is no different from the king. Only without the condition that the length is less than 1
     **/

    private static boolean moveQueen(Figure figure, String[] moves, AdjListChessDigraph digraph, Maps maps) {
        if (checkDiagonalLines(maps, digraph, moves[1], moves[0]) || checkHorizontalAndVerticalLines(maps, digraph, moves[1], moves[0])) {
            if (maps.getBlackFigure().get(moves[1]) != null || maps.getWhiteFigure().get(moves[1]) == null) {
                maps.moveFigure(figure, moves[1]);
                return true;
            } else {
                System.out.println("Your figure is standing here.");
                return false;
            }
        } else {
            System.out.println("The Queen cannot go here.");
            return false;
        }
    }

    /**
     * The logic does not differ from the queen only without checking for horizontal steps
     **/

    private static boolean moveBishop(Figure figure, String[] moves, AdjListChessDigraph digraph, Maps maps) {
        if (checkDiagonalLines(maps, digraph, moves[1], moves[0])) {
            if (maps.getBlackFigure().get(moves[1]) != null || maps.getWhiteFigure().get(moves[1]) == null) {
                maps.moveFigure(figure, moves[1]);
                return true;
            } else {
                System.out.println("Your figure is standing here.");
                return false;
            }
        } else {
            System.out.println("The Rook cannot go here.");
            return false;
        }
    }

    /**
     * Checking diagonal lines for moveability. Simply by the brute force method,
     * we iterate over all the problems in which the figure can reach and if oa can reach the same coordinate,
     * then we return true. isKing is limited to the ability to walk more than 1 time. Needed for the king.
     **/

    private static boolean checkDiagonalLines(Maps maps, AdjListChessDigraph digraph, String nextStay, String nowStay) {
        return checkDiagonalLines(maps, digraph, nextStay, nowStay, false);
    }

    private static boolean checkDiagonalLines(Maps maps, AdjListChessDigraph digraph, String nextStay, String nowStay, boolean isKing) {
        Map<String, Figure> figures = maps.getFigures();

        boolean limiter;

        String bufferedNowStay;

        for (Map.Entry<Direction, String> keyAndValue : digraph.getCellDiagonal(nowStay).entrySet()) {
            int direction = keyAndValue.getKey().getDirection();
            bufferedNowStay = nowStay;
            do {
                bufferedNowStay = digraph.getCellDiagonal(bufferedNowStay).get(new Direction(direction));
                limiter = !isKing;
                if(bufferedNowStay == null){
                    break;
                }
                if (bufferedNowStay.equals(nextStay)) {
                    return true;
                }
            } while (figures.get(bufferedNowStay) == null && limiter);
        }

        return false;
    }

    /**
     * The logic is no different from the officer. Only vertical and porousotal lines are checked
     **/

    private static boolean moveRook(Figure figure, String[] moves, AdjListChessDigraph digraph, Maps maps) {

        if (checkHorizontalAndVerticalLines(maps, digraph, moves[1], moves[0])) {
            if (maps.getBlackFigure().get(moves[1]) != null || maps.getWhiteFigure().get(moves[1]) == null) {
                maps.moveFigure(figure, moves[1]);
                return true;
            } else {
                System.out.println("Your figure is standing here.");
                return false;
            }
        } else {
            System.out.println("The Rook cannot go here.");
            return false;
        }
    }


    /**
     * Same as for diagonal lines, only for vertical and horizontal
     **/
    private static boolean checkHorizontalAndVerticalLines(Maps maps, AdjListChessDigraph digraph, String nextStay, String nowStay) {
        return checkHorizontalAndVerticalLines(maps, digraph, nextStay, nowStay, false);
    }

    private static boolean checkHorizontalAndVerticalLines(Maps maps, AdjListChessDigraph digraph, String nextStay, String nowStay, boolean isKing) {
        Map<String, Figure> figures = maps.getFigures();

        boolean limiter;
        String bufferedNowStay;

        for (Map.Entry<Direction, String> keyAndValue : digraph.getCellHorizontalAndVertical(nowStay).entrySet()) {
            int direction = keyAndValue.getKey().getDirection();
            bufferedNowStay = nowStay;
            do {
                bufferedNowStay = digraph.getCellHorizontalAndVertical(bufferedNowStay).get(new Direction(direction));
                limiter = !isKing;
                if(bufferedNowStay == null){
                     break;
                }
                if (bufferedNowStay.equals(nextStay)) {
                    return true;
                }
            } while (figures.get(bufferedNowStay) == null && limiter);
        }

        return false;
    }

    /**
     * The knight can only move to 12 defined points. So all these points are just checked here
     **/

    private static boolean moveKnight(Figure figure, String[] moves, AdjListChessDigraph digraph, Maps maps) {
        if(knightCanMove(moves[0],moves[1],digraph,maps)){
            maps.moveFigure(figure, moves[1]);
            System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
        }
        return false;
    }

    private static boolean knightCanMove(String start, String end, AdjListChessDigraph digraph, Maps maps){

        Map<String, Figure> attack = maps.getBlackFigure().get(start) == null ? maps.getWhiteFigure() : maps.getBlackFigure();

        Map<Direction, String> isNow = digraph.getCellHorizontalAndVertical(start);
        String next;
        int counterFirst = 0;
        int counterSecond = 1;

        for (Map.Entry<Direction, String> keyAndValue : isNow.entrySet()) {
            next = keyAndValue.getValue();
            if (end.equals(digraph.getCellDiagonal(next).get(new Direction(counterFirst)))
                    && attack.get(digraph.getCellDiagonal(next).get(new Direction(counterFirst))) == null) {
                return true;
            } else if (end.equals(digraph.getCellDiagonal(next).get(new Direction(counterSecond)))
                    && attack.get(digraph.getCellDiagonal(next).get(new Direction(counterSecond))) == null) {
                return true;
            }
            counterFirst++;
            counterSecond++;
            counterSecond = counterSecond > 5 ? 0 : counterSecond;
        }
        return false;
    }

    /**
     * The logic of the black and white pawns differs only in the direction of movement.
     * Also, a pawn can move two cells in certain places and turn into any piece if it reaches the end
     **/

    private static boolean movePawn(Figure figure, String[] moves, AdjListChessDigraph digraph, Maps maps) {
        Map<String, Figure> attack = maps.getBlackFigure().get(moves[0]) == null ? maps.getWhiteFigure() : maps.getBlackFigure();
        boolean isDoubleMove;
        if (maps.getBlackFigure().get(moves[0]) == null) {
            isDoubleMove = Constant.toEdge(moves[0]) >= 16 && Constant.toEdge(moves[0]) <= 25;
        } else {
            isDoubleMove = Constant.toEdge(moves[0]) == 82 || Constant.toEdge(moves[0]) == 72 || Constant.toEdge(moves[0]) == 62
                    || Constant.toEdge(moves[0]) == 52 || Constant.toEdge(moves[0]) == 64 || Constant.toEdge(moves[0]) == 76 || Constant.toEdge(moves[0]) == 88;
        }

        Map<Direction, String> isNow = digraph.getCellHorizontalAndVertical(moves[0]);
        String next = isNow.get(new Direction(Direction.UP));

        if (next.equals(moves[1]) && attack.get(next) == null) {
            maps.moveFigure(figure, next);
            System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
            if (digraph.getCellHorizontalAndVertical(next).get(new Direction(Direction.UP)) == null) {
                maps.setFigure(pawnConversion(maps.getWhiteFigure().get(next) == null), next);
            }
            return true;
        }

        isNow = digraph.getCellHorizontalAndVertical(next);
        next = isNow.get(new Direction(Direction.UP));

        if (next.equals(moves[1]) && attack.get(next) == null && isDoubleMove) {
            maps.moveFigure(figure, next);
            System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
            return true;
        }

        isNow = digraph.getCellDiagonal(moves[0]);
        next = isNow.get(new Direction(0));

        if (next.equals(moves[1]) && attack.get(next) == null) {
            maps.moveFigure(figure, next);
            System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
            return true;
        }

        next = isNow.get(new Direction(1));

        if (next.equals(moves[1]) && attack.get(next) == null) {
            maps.moveFigure(figure, next);
            System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
            return true;
        }

        return false;
    }

    /**
     * Here is the choice of the piece that the pawn turns into
     **/

    private static Figure pawnConversion(boolean isBlack) {
        System.out.print("Choose a shape from the list:\n" +
                "    Queen\n    Bishop\n    Rook\n    Knight\nYour Choose: ");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            switch (scanner.nextLine()) {
                case "Rook":
                    if (isBlack) {
                        return new Rook(false);
                    } else {
                        return new Rook(true);
                    }
                case "Bishop":
                    if (isBlack) {
                        return new Bishop(false);
                    } else {
                        return new Bishop(true);
                    }
                case "Queen":
                    if (isBlack) {
                        return new Queen(false);
                    } else {
                        return new Queen(true);
                    }
                case "Knight":
                    if (isBlack) {
                        return new Knight(false);
                    } else {
                        return new Knight(true);
                    }
            }
        }
    }

    private static List<String> getDirectionHorizontalAndVerticalLines(Maps maps, AdjListChessDigraph digraph, String nextStay, String nowStay) {
        Map<String, Figure> figures = maps.getFigures();

        List<String> list = new ArrayList<>();
        String bufferedNowStay;

        for (Map.Entry<Direction, String> keyAndValue : digraph.getCellHorizontalAndVertical(nowStay).entrySet()) {
            int direction = keyAndValue.getKey().getDirection();
            bufferedNowStay = nowStay;
            do {
                list.add(bufferedNowStay);
                bufferedNowStay = digraph.getCellHorizontalAndVertical(bufferedNowStay).get(new Direction(direction));
                if(bufferedNowStay == null){
                    break;
                }
                if (bufferedNowStay.equals(nextStay)) {
                    return list;
                }
            } while (figures.get(bufferedNowStay) == null);
        }

        return null;
    }

    private static List<String> getDirectionDiagonalLines(Maps maps, AdjListChessDigraph digraph, String nextStay, String nowStay) {
        Map<String, Figure> figures = maps.getFigures();

        List<String> list = new ArrayList<>();
        String bufferedNowStay;

        for (Map.Entry<Direction, String> keyAndValue : digraph.getCellDiagonal(nowStay).entrySet()) {
            int direction = keyAndValue.getKey().getDirection();
            bufferedNowStay = nowStay;
            do {
                list.add(bufferedNowStay);
                bufferedNowStay = digraph.getCellDiagonal(bufferedNowStay).get(new Direction(direction));
                if(bufferedNowStay == null){
                    list.clear();
                    break;
                }
                if (bufferedNowStay.equals(nextStay)) {
                    return list;
                }
            } while (figures.get(bufferedNowStay) == null);
        }

        return null;
    }
}
