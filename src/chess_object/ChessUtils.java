package chess_object;

import constant.Constant;
import graph.Digraph;

import java.util.*;

/**
 * Service class in which the movement of chess pieces is implemented
 **/

public class ChessUtils {

    public static boolean isMate(boolean isBlack, Maps maps, Digraph digraph) {
        Figure king = null;
        Map<Figure, Integer> cellAttackingSide = isBlack ? maps.getCellBlackFigure() : maps.getCellWhiteFigure();
        Map<Integer, Figure> attackingSide = isBlack ? maps.getBlackFigure() : maps.getWhiteFigure();
        Map<Figure, Integer> cellDefenderSide = isBlack ? maps.getCellWhiteFigure() : maps.getCellBlackFigure();
        Map<Integer, Figure> defenderSide = isBlack ? maps.getWhiteFigure() : maps.getBlackFigure();


        for (Map.Entry<Figure, Integer> keyAndValue : cellDefenderSide.entrySet()) {
            if (keyAndValue.getKey() instanceof King) {
                king = keyAndValue.getKey();
                break;
            }
        }

        Figure attackedKnight = null;
        List<Figure> attackFigure = new ArrayList<>();

        for (Map.Entry<Figure, Integer> keyAndValue : cellAttackingSide.entrySet()) {
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
            for (Map.Entry<Figure, Integer> keyAndValue : cellDefenderSide.entrySet()) {
                return !cellIsAttacked(keyAndValue.getKey(), cellDefenderSide.get(keyAndValue.getKey()), digraph, maps, cellAttackingSide.get(attackedKnight));
            }
        }

        List<Integer> edgeWhereTheKingCanGo = checkEdgeWhereTheKingCanGo(maps, digraph, cellDefenderSide.get(king));

        for (Map.Entry<Figure, Integer> keyAndValue : cellAttackingSide.entrySet()) {
            edgeWhereTheKingCanGo.removeIf(edge -> cellIsAttacked(keyAndValue.getKey(), cellAttackingSide.get(keyAndValue.getKey()), digraph, maps, edge));
        }

        if (edgeWhereTheKingCanGo.size() > 0) {
            return false;
        }

        List<Integer> directionAttackKing = checkDirection(attackFigure.get(0), cellAttackingSide.get(attackFigure.get(0)), digraph, maps, cellDefenderSide.get(king));

        for (Map.Entry<Figure, Integer> keyAndValue : cellDefenderSide.entrySet()) {
            for(Integer edge: directionAttackKing) {
                if(cellIsAttacked(keyAndValue.getKey(), cellDefenderSide.get(keyAndValue.getKey()), digraph, maps, edge)){
                    return false;
                }
            }
        }

        return true;
    }

    private static List<Integer> checkDirection(Figure figure, int nowStay, Digraph digraph, Maps maps, int nextStay){
        if (figure instanceof Rook) {
            return getDirectionHorizontalAndVerticalLines(maps, digraph, nextStay, nowStay);
        } else if (figure instanceof Bishop) {
            return getDirectionDiagonalLines(maps, digraph, nextStay, nowStay);
        } else if (figure instanceof Queen) {
            List<Integer> bufferedList = getDirectionDiagonalLines(maps, digraph, nextStay, nowStay);
            if(bufferedList.size() > 0){
                return bufferedList;
            }else {
                return getDirectionHorizontalAndVerticalLines(maps, digraph, nextStay, nowStay);
            }
        }
        return null;
    }

    private static boolean cellIsAttacked(Figure figure, int nowStay, Digraph digraph, Maps maps, int nextStay) {
        if (figure instanceof Pawn) {
            return digraph.getUpRight(digraph.getUp(nowStay)) == nextStay || digraph.getUpLeft(digraph.getUp(nowStay)) == nextStay;
        } else if (figure instanceof Knight) {
            return knightCanMove(digraph, nowStay, nextStay);
        } else if (figure instanceof Rook) {
            return checkHorizontalAndVerticalLines(maps, digraph, nextStay, nowStay);
        } else if (figure instanceof Bishop) {
            return checkDiagonalLines(maps, digraph, nextStay, nowStay);
        } else if (figure instanceof Queen) {
            return checkHorizontalAndVerticalLines(maps, digraph, nextStay, nowStay) || checkDiagonalLines(maps, digraph, nextStay, nowStay);
        }
        return false;
    }

    private static List<Integer> checkEdgeWhereTheKingCanGo(Maps maps, Digraph digraph, int nowStay) {
        List<Integer> edgeWhereTheKingCanGo = new ArrayList<>();

        if (digraph.getUp(nowStay) != -1 && maps.getFigures().get(digraph.getUp(nowStay)) == null) {
            edgeWhereTheKingCanGo.add(digraph.getUp(nowStay));
        }
        if (digraph.getUpRight(nowStay) != -1 && maps.getFigures().get(digraph.getUpRight(nowStay)) == null) {
            edgeWhereTheKingCanGo.add(digraph.getUpRight(nowStay));
        }
        if (digraph.getDownRight(nowStay) != -1 && maps.getFigures().get(digraph.getDownRight(nowStay)) == null) {
            edgeWhereTheKingCanGo.add(digraph.getDownRight(nowStay));
        }
        if (digraph.getDownLeft(nowStay) != -1 && maps.getFigures().get(digraph.getDownLeft(nowStay)) == null) {
            edgeWhereTheKingCanGo.add(digraph.getDownLeft(nowStay));
        }
        if (digraph.getUpLeft(nowStay) != -1 && maps.getFigures().get(digraph.getUpLeft(nowStay)) == null) {
            edgeWhereTheKingCanGo.add(digraph.getUpLeft(nowStay));
        }
        if (digraph.getDown(nowStay) != -1 && maps.getFigures().get(digraph.getDown(nowStay)) == null) {
            edgeWhereTheKingCanGo.add(digraph.getDown(nowStay));
        }
        if (digraph.getDown(digraph.getDownLeft(nowStay)) != -1 && maps.getFigures().get(digraph.getDown(digraph.getDownLeft(nowStay))) == null) {
            edgeWhereTheKingCanGo.add(digraph.getDown(digraph.getDownLeft(nowStay)));
        }
        if (digraph.getDown(digraph.getDownRight(nowStay)) != -1 && maps.getFigures().get(digraph.getDown(digraph.getDownRight(nowStay))) == null) {
            edgeWhereTheKingCanGo.add(digraph.getDown(digraph.getDownRight(nowStay)));
        }
        if (digraph.getUpRight(digraph.getDownRight(nowStay)) != -1 && maps.getFigures().get(digraph.getUpRight(digraph.getDownRight(nowStay))) == null) {
            edgeWhereTheKingCanGo.add(digraph.getUpRight(digraph.getDownRight(nowStay)));
        }
        if (digraph.getUpLeft(digraph.getDownLeft(nowStay)) != -1 && maps.getFigures().get(digraph.getUpLeft(digraph.getDownLeft(nowStay))) == null) {
            edgeWhereTheKingCanGo.add(digraph.getUpLeft(digraph.getDownLeft(nowStay)));
        }
        if (digraph.getUpRight(digraph.getUp(nowStay)) != -1 && maps.getFigures().get(digraph.getUpRight(digraph.getUp(nowStay))) == null) {
            edgeWhereTheKingCanGo.add(digraph.getUpRight(digraph.getUp(nowStay)));
        }
        if (digraph.getUpLeft(digraph.getUp(nowStay)) != -1 && maps.getFigures().get(digraph.getUpLeft(digraph.getUp(nowStay))) == null) {
            edgeWhereTheKingCanGo.add(digraph.getUpLeft(digraph.getUp(nowStay)));
        }
        return edgeWhereTheKingCanGo;
    }

    /**
     * In this method, we find out which instance of the class we got and, depending on this,
     * select the logic of the figure's move
     **/
    public static boolean moveFigure(String move, Digraph digraph, Maps maps, boolean isBlack) {
        move = move.toLowerCase();
        String[] moves = move.split(" ");

        if (Constant.toEdge(moves[1]) == null || Constant.toEdge(moves[0]) == null) { //Check in case we are given a
            System.out.println("You have selected a non-existing coordinate.");       //coordinate which does not exist
            return false;                                                             //whose coordinate does not exist
        }

        Figure figure = maps.getFigures().get(Constant.toEdge(moves[0]));

        if (figure instanceof Pawn) {
            return movePawn(figure, moves, digraph, maps, isBlack);
        } else if (figure instanceof Knight) {
            return moveKnight(figure, moves, digraph, maps, isBlack);
        } else if (figure instanceof Rook) {
            return moveRook(figure, moves, digraph, maps, isBlack);
        } else if (figure instanceof Bishop) {
            return moveBishop(figure, moves, digraph, maps, isBlack);
        } else if (figure instanceof Queen) {
            return moveQueen(figure, moves, digraph, maps, isBlack);
        } else if (figure instanceof King) {
            return moveKing(figure, moves, digraph, maps, isBlack);
        }

        System.out.println("You have chosen an empty cell!!");
        return false;
    }

    /**
     * Logic for the king. For black and white, the logic is not very different.
     * The bottom line is that we check all possible directions in which the king
     * can go with the condition that the length of the step e is greater than 1
     **/

    private static boolean moveKing(Figure figure, String[] moves, Digraph digraph, Maps maps, boolean isBlack) {
        if (isBlack) {
            return moveBlackKing(figure, moves, digraph, maps);
        } else {
            return moveWhiteKing(figure, moves, digraph, maps);
        }
    }

    private static boolean moveWhiteKing(Figure figure, String[] moves, Digraph digraph, Maps maps) {
        if (maps.getCellBlackFigure().get(figure) != null) {
            System.out.println("This is not your figure!");
            return false;
        }
        int nextStay = Constant.toEdge(moves[1]);
        int nowStay = maps.getCellWhiteFigure().get(figure);
        if (checkDiagonalLines(maps, digraph, nextStay, nowStay, true) || checkHorizontalAndVerticalLines(maps, digraph, nextStay, nowStay, true)) {
            if (maps.getBlackFigure().get(nextStay) != null || maps.getWhiteFigure().get(nextStay) == null) {
                maps.moveFigure(figure, nextStay);
                System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
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

    private static boolean moveBlackKing(Figure figure, String[] moves, Digraph digraph, Maps maps) {
        if (maps.getCellWhiteFigure().get(figure) != null) {
            System.out.println("This is not your figure!");
            return false;
        }
        int nextStay = Constant.toEdge(moves[1]);
        int nowStay = maps.getCellBlackFigure().get(figure);
        if (checkDiagonalLines(maps, digraph, nextStay, nowStay, true) || checkHorizontalAndVerticalLines(maps, digraph, nextStay, nowStay, true)) {
            if (maps.getWhiteFigure().get(nextStay) != null || maps.getBlackFigure().get(nextStay) == null) {
                maps.moveFigure(figure, nextStay);
                System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
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

    private static boolean moveQueen(Figure figure, String[] moves, Digraph digraph, Maps maps, boolean isBlack) {
        if (isBlack) {
            return moveBlackQueen(figure, moves, digraph, maps);
        } else {
            return moveWhiteQueen(figure, moves, digraph, maps);
        }
    }

    private static boolean moveWhiteQueen(Figure figure, String[] moves, Digraph digraph, Maps maps) {
        if (maps.getCellBlackFigure().get(figure) != null) {
            System.out.println("This is not your figure!");
            return false;
        }
        int nextStay = Constant.toEdge(moves[1]);
        int nowStay = maps.getCellWhiteFigure().get(figure);
        if (checkDiagonalLines(maps, digraph, nextStay, nowStay) || checkHorizontalAndVerticalLines(maps, digraph, nextStay, nowStay)) {
            if (maps.getBlackFigure().get(nextStay) != null || maps.getWhiteFigure().get(nextStay) == null) {
                maps.moveFigure(figure, nextStay);
                System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
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

    private static boolean moveBlackQueen(Figure figure, String[] moves, Digraph digraph, Maps maps) {
        if (maps.getCellWhiteFigure().get(figure) != null) {
            System.out.println("This is not your figure!");
            return false;
        }
        int nextStay = Constant.toEdge(moves[1]);
        int nowStay = maps.getCellBlackFigure().get(figure);
        if (checkDiagonalLines(maps, digraph, nextStay, nowStay) || checkHorizontalAndVerticalLines(maps, digraph, nextStay, nowStay)) {
            if (maps.getWhiteFigure().get(nextStay) != null || maps.getBlackFigure().get(nextStay) == null) {
                maps.moveFigure(figure, nextStay);
                System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
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

    private static boolean moveBishop(Figure figure, String[] moves, Digraph digraph, Maps maps, boolean isBlack) {
        if (isBlack) {
            return moveBlackBishop(figure, moves, digraph, maps);
        } else {
            return moveWhiteBishop(figure, moves, digraph, maps);
        }
    }

    private static boolean moveWhiteBishop(Figure figure, String[] moves, Digraph digraph, Maps maps) {
        if (maps.getCellBlackFigure().get(figure) != null) {
            System.out.println("This is not your figure!");
            return false;
        }
        int nextStay = Constant.toEdge(moves[1]);
        int nowStay = maps.getCellWhiteFigure().get(figure);
        if (checkDiagonalLines(maps, digraph, nextStay, nowStay)) {
            if (maps.getBlackFigure().get(nextStay) != null || maps.getWhiteFigure().get(nextStay) == null) {
                maps.moveFigure(figure, nextStay);
                System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
                return true;
            } else {
                System.out.println("Your figure is standing here.");
                return false;
            }
        } else {
            System.out.println("The Bishop cannot go here.");
            return false;
        }
    }

    private static boolean moveBlackBishop(Figure figure, String[] moves, Digraph digraph, Maps maps) {
        if (maps.getCellWhiteFigure().get(figure) != null) {
            System.out.println("This is not your figure!");
            return false;
        }
        int nextStay = Constant.toEdge(moves[1]);
        int nowStay = maps.getCellBlackFigure().get(figure);
        if (checkDiagonalLines(maps, digraph, nextStay, nowStay)) {
            if (maps.getWhiteFigure().get(nextStay) != null || maps.getBlackFigure().get(nextStay) == null) {
                maps.moveFigure(figure, nextStay);
                System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
                return true;
            } else {
                System.out.println("Your figure is standing here.");
                return false;
            }
        } else {
            System.out.println("The Bishop cannot go here.");
            return false;
        }
    }

    /**
     * Checking diagonal lines for moveability. Simply by the brute force method,
     * we iterate over all the problems in which the figure can reach and if oa can reach the same coordinate,
     * then we return true. isKing is limited to the ability to walk more than 1 time. Needed for the king.
     **/

    private static boolean checkDiagonalLines(Maps maps, Digraph digraph, int nextStay, int nowStay) {
        return checkDiagonalLines(maps, digraph, nextStay, nowStay, false);
    }

    private static boolean checkDiagonalLines(Maps maps, Digraph digraph, int nextStay, int nowStay, boolean isKing) {
        int bufferNowStay;
        boolean limiter;
        for (int i = 0; i < 6; i++) {
            bufferNowStay = nowStay;
            limiter = true;
            do {
                switch (i) {
                    case 0:
                        bufferNowStay = digraph.getUpRight(digraph.getUp(bufferNowStay));
                        if (isKing) {
                            limiter = false;
                        }
                        break;
                    case 1:
                        bufferNowStay = digraph.getUpLeft(digraph.getUp(bufferNowStay));
                        if (isKing) {
                            limiter = false;
                        }
                        break;
                    case 2:
                        bufferNowStay = digraph.getDownRight(digraph.getUpRight(bufferNowStay));
                        if (isKing) {
                            limiter = false;
                        }
                        break;
                    case 3:
                        bufferNowStay = digraph.getDownLeft(digraph.getUpLeft(bufferNowStay));
                        if (isKing) {
                            limiter = false;
                        }
                        break;
                    case 4:
                        bufferNowStay = digraph.getDownLeft(digraph.getDown(bufferNowStay));
                        if (isKing) {
                            limiter = false;
                        }
                        break;
                    case 5:
                        bufferNowStay = digraph.getDownRight(digraph.getDown(bufferNowStay));
                        if (isKing) {
                            limiter = false;
                        }
                        break;
                }
                if (bufferNowStay == nextStay) {
                    return true;
                }
            } while (maps.getFigures().get(bufferNowStay) == null && bufferNowStay != -1 && limiter);
        }
        return false;
    }

    /**
     * The logic is no different from the officer. Only vertical and porousotal lines are checked
     **/

    private static boolean moveRook(Figure figure, String[] moves, Digraph digraph, Maps maps, boolean isBlack) {
        if (isBlack) {
            return moveBlackRook(figure, moves, digraph, maps);
        } else {
            return moveWhiteRook(figure, moves, digraph, maps);
        }
    }

    private static boolean moveWhiteRook(Figure figure, String[] moves, Digraph digraph, Maps maps) {
        if (maps.getCellBlackFigure().get(figure) != null) {
            System.out.println("This is not your figure!");
            return false;
        }
        int nextStay = Constant.toEdge(moves[1]);
        int nowStay = maps.getCellWhiteFigure().get(figure);
        if (checkHorizontalAndVerticalLines(maps, digraph, nextStay, nowStay)) {
            if (maps.getBlackFigure().get(nextStay) != null || maps.getWhiteFigure().get(nextStay) == null) {
                maps.moveFigure(figure, nextStay);
                System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
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

    private static boolean moveBlackRook(Figure figure, String[] moves, Digraph digraph, Maps maps) {
        if (maps.getCellWhiteFigure().get(figure) != null) {
            System.out.println("This is not your figure!");
            return false;
        }
        int nextStay = Constant.toEdge(moves[1]);
        int nowStay = maps.getCellBlackFigure().get(figure);
        if (checkHorizontalAndVerticalLines(maps, digraph, nextStay, nowStay)) {
            if (maps.getWhiteFigure().get(nextStay) != null || maps.getBlackFigure().get(nextStay) == null) {
                maps.moveFigure(figure, nextStay);
                System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
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
    private static boolean checkHorizontalAndVerticalLines(Maps maps, Digraph digraph, int nextStay, int nowStay) {
        return checkHorizontalAndVerticalLines(maps, digraph, nextStay, nowStay, false);
    }

    private static boolean checkHorizontalAndVerticalLines(Maps maps, Digraph digraph, int nextStay, int nowStay, boolean isKing) {
        int bufferNowStay;
        boolean limiter;
        for (int i = 0; i < 6; i++) {
            bufferNowStay = nowStay;
            limiter = true;
            do {
                switch (i) {
                    case 0:
                        bufferNowStay = digraph.getUp(bufferNowStay);
                        if (isKing) {
                            limiter = false;
                        }
                        break;
                    case 1:
                        bufferNowStay = digraph.getUpRight(bufferNowStay);
                        if (isKing) {
                            limiter = false;
                        }
                        break;
                    case 2:
                        bufferNowStay = digraph.getDownRight(bufferNowStay);
                        if (isKing) {
                            limiter = false;
                        }
                        break;
                    case 3:
                        bufferNowStay = digraph.getDown(bufferNowStay);
                        if (isKing) {
                            limiter = false;
                        }
                        break;
                    case 4:
                        bufferNowStay = digraph.getDownLeft(bufferNowStay);
                        if (isKing) {
                            limiter = false;
                        }
                        break;
                    case 5:
                        bufferNowStay = digraph.getUpLeft(bufferNowStay);
                        if (isKing) {
                            limiter = false;
                        }
                        break;
                }
                if (bufferNowStay == nextStay) {
                    return true;
                }
            } while (maps.getFigures().get(bufferNowStay) == null && bufferNowStay != -1 && limiter);
        }
        return false;
    }

    /**
     * The knight can only move to 12 defined points. So all these points are just checked here
     **/

    private static boolean moveKnight(Figure figure, String[] moves, Digraph digraph, Maps maps, boolean isBlack) {
        if (isBlack) {
            return moveBlackKnight(figure, moves, digraph, maps);
        } else {
            return moveWhiteKnight(figure, moves, digraph, maps);
        }
    }

    private static boolean moveWhiteKnight(Figure figure, String[] moves, Digraph digraph, Maps maps) {
        if (maps.getCellBlackFigure().get(figure) != null) {
            System.out.println("This is not your figure!");
            return false;
        }
        int nextStay = Constant.toEdge(moves[1]);
        int nowStay = maps.getCellWhiteFigure().get(figure);
        if (knightCanMove(digraph, nowStay, nextStay)) {
            if (maps.getBlackFigure().get(nextStay) != null || maps.getWhiteFigure().get(nextStay) == null) {
                maps.moveFigure(figure, nextStay);
                System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
                return true;
            } else {
                System.out.println("Your figure is standing here.");
                return false;
            }
        }
        System.out.println("The Knight cannot go here");
        return false;
    }

    private static boolean knightCanMove(Digraph digraph, int nowStay, int nextStay) {
        return digraph.getUpRight(digraph.getUp(digraph.getUp(nowStay))) == nextStay ||
                digraph.getUpLeft(digraph.getUp(digraph.getUp(nowStay))) == nextStay ||
                digraph.getUp(digraph.getUpRight(digraph.getUpRight(nowStay))) == nextStay ||
                digraph.getDownRight(digraph.getUpRight(digraph.getUpRight(nowStay))) == nextStay ||
                digraph.getUp(digraph.getUpLeft(digraph.getUpLeft(nowStay))) == nextStay ||
                digraph.getDownLeft(digraph.getUpLeft(digraph.getUpLeft(nowStay))) == nextStay ||
                digraph.getDownLeft(digraph.getDown(digraph.getDown(nowStay))) == nextStay ||
                digraph.getDownRight(digraph.getDown(digraph.getDown(nowStay))) == nextStay ||
                digraph.getUpLeft(digraph.getDownLeft(digraph.getDownLeft(nowStay))) == nextStay ||
                digraph.getDown(digraph.getDownLeft(digraph.getDownLeft(nowStay))) == nextStay ||
                digraph.getUp(digraph.getDownRight(digraph.getDownRight(nowStay))) == nextStay ||
                digraph.getDownLeft(digraph.getDownRight(digraph.getDownRight(nowStay))) == nextStay;
    }

    private static boolean moveBlackKnight(Figure figure, String[] moves, Digraph digraph, Maps maps) {
        if (maps.getCellWhiteFigure().get(figure) != null) {
            System.out.println("This is not your figure!");
            return false;
        }
        int nextStay = Constant.toEdge(moves[1]);
        int nowStay = maps.getCellBlackFigure().get(figure);
        if (knightCanMove(digraph, nowStay, nextStay)) {
            if (maps.getWhiteFigure().get(nextStay) != null || maps.getBlackFigure().get(nextStay) == null) {
                maps.moveFigure(figure, nextStay);
                System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
                return true;
            } else {
                System.out.println("Your figure is standing here.");
                return false;
            }
        }
        System.out.println("The Knight cannot go here");
        return false;
    }

    /**
     * The logic of the black and white pawns differs only in the direction of movement.
     * Also, a pawn can move two cells in certain places and turn into any piece if it reaches the end
     **/

    private static boolean movePawn(Figure figure, String[] moves, Digraph digraph, Maps maps, boolean isBlack) {
        if (isBlack) {
            return moveBlackPawn(figure, moves, digraph, maps);
        } else {
            return moveWhitePawn(figure, moves, digraph, maps);
        }
    }

    private static boolean moveBlackPawn(Figure figure, String[] moves, Digraph digraph, Maps maps) {
        int nowStay;
        int nextStay;
        if (maps.getCellWhiteFigure().get(figure) != null) {
            System.out.println("This is not your figure!");
            return false;
        }
        nowStay = maps.getCellBlackFigure().get(figure);
        int checkNextStay = Constant.toEdge(moves[1]);
        if (moves[0].charAt(0) == moves[1].charAt(0)) {
            nextStay = Constant.toEdge(moves[1]);
        } else if (checkNextStay == digraph.getDownRight(digraph.getDown(nowStay))) {
            nextStay = digraph.getDownRight(digraph.getDown(nowStay));
        } else if (checkNextStay == digraph.getDownLeft(digraph.getDown(nowStay))) {
            nextStay = digraph.getDownLeft(digraph.getDown(nowStay));
        } else {
            nextStay = -1;
        }
        if (nextStay != -1 && moves[0].charAt(0) == moves[1].charAt(0)) {
            if (maps.getWhiteFigure().get(nextStay) == null && maps.getBlackFigure().get(nextStay) == null) {
                if (digraph.getDown(nowStay) == nextStay) {
                    maps.moveFigure(figure, nextStay);
                    System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
                    if (digraph.getDown(nextStay) == -1) {
                        maps.setFigure(pawnConversion(false), nextStay);
                    }
                    return true;
                } else if (digraph.getDown(digraph.getDown(nowStay)) == nextStay && (nowStay == 82 || nowStay == 72 || nowStay == 62 || nowStay == 52 || nowStay == 64 || nowStay == 76 || nowStay == 88)) {
                    maps.moveFigure(figure, nextStay);
                    System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
                    return true;
                }
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

    private static boolean moveWhitePawn(Figure figure, String[] moves, Digraph digraph, Maps maps) {
        int nowStay;
        int nextStay;
        if (maps.getCellBlackFigure().get(figure) != null) {
            System.out.println("This is not your figure!");
            return false;
        }
        nowStay = maps.getCellWhiteFigure().get(figure);
        int checkNextStay = Constant.toEdge(moves[1]);
        if (moves[0].charAt(0) == moves[1].charAt(0)) {
            nextStay = Constant.toEdge(moves[1]);
        } else if (checkNextStay == digraph.getUpRight(digraph.getUp(nowStay))) {
            nextStay = digraph.getUpRight(digraph.getUp(nowStay));
        } else if (checkNextStay == digraph.getUpLeft(digraph.getUp(nowStay))) {
            nextStay = digraph.getUpLeft(digraph.getUp(nowStay));
        } else {
            nextStay = -1;
        }
        if (nextStay != -1 && moves[0].charAt(0) == moves[1].charAt(0)) {
            if (maps.getBlackFigure().get(nextStay) == null && maps.getWhiteFigure().get(nextStay) == null) {
                if (digraph.getUp(nowStay) == nextStay) {
                    maps.moveFigure(figure, nextStay);
                    System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
                    if (digraph.getUp(nextStay) == -1) {
                        maps.setFigure(pawnConversion(false), nextStay);
                    }
                    return true;
                } else if (digraph.getUp(digraph.getUp(nowStay)) == nextStay && nowStay >= 16 && nowStay <= 25) {
                    maps.moveFigure(figure, nextStay);
                    System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
                    return true;
                }
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

    private static List<Integer> getDirectionHorizontalAndVerticalLines(Maps maps, Digraph digraph, int nextStay, int nowStay) {
        int bufferNowStay;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            bufferNowStay = nowStay;
            do {
                switch (i) {
                    case 0:
                        list.add(bufferNowStay);
                        bufferNowStay = digraph.getUp(bufferNowStay);
                        break;
                    case 1:
                        list.add(bufferNowStay);
                        bufferNowStay = digraph.getUpRight(bufferNowStay);
                        break;
                    case 2:
                        list.add(bufferNowStay);
                        bufferNowStay = digraph.getDownRight(bufferNowStay);
                        break;
                    case 3:
                        list.add(bufferNowStay);
                        bufferNowStay = digraph.getDown(bufferNowStay);
                        break;
                    case 4:
                        list.add(bufferNowStay);
                        bufferNowStay = digraph.getDownLeft(bufferNowStay);
                        break;
                    case 5:
                        list.add(bufferNowStay);
                        bufferNowStay = digraph.getUpLeft(bufferNowStay);
                        break;
                }
                if (bufferNowStay == nextStay) {
                    return list;
                }else {
                    list.clear();
                }
            } while (maps.getFigures().get(bufferNowStay) == null && bufferNowStay != -1);
        }
        return null;
    }

    private static List<Integer> getDirectionDiagonalLines(Maps maps, Digraph digraph, int nextStay, int nowStay) {
        int bufferNowStay;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            bufferNowStay = nowStay;
            do {
                switch (i) {
                    case 0:
                        list.add(bufferNowStay);
                        bufferNowStay = digraph.getUpRight(digraph.getUp(bufferNowStay));
                        break;
                    case 1:
                        list.add(bufferNowStay);
                        bufferNowStay = digraph.getUpLeft(digraph.getUp(bufferNowStay));
                        break;
                    case 2:
                        list.add(bufferNowStay);
                        bufferNowStay = digraph.getDownRight(digraph.getUpRight(bufferNowStay));
                        break;
                    case 3:
                        list.add(bufferNowStay);
                        bufferNowStay = digraph.getDownLeft(digraph.getUpLeft(bufferNowStay));
                        break;
                    case 4:
                        list.add(bufferNowStay);
                        bufferNowStay = digraph.getDownLeft(digraph.getDown(bufferNowStay));
                        break;
                    case 5:
                        list.add(bufferNowStay);
                        bufferNowStay = digraph.getDownRight(digraph.getDown(bufferNowStay));
                        break;
                }
                if (bufferNowStay == nextStay) {
                    return list;
                }else {
                    list.clear();
                }
            } while (maps.getFigures().get(bufferNowStay) == null && bufferNowStay != -1);
        }
        return null;
    }
}
