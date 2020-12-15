package chess.services;

import chess.board.Direction;
import chess.board.Maps;
import chess.figure.*;
import constant.Constant;
import graph.AdjListChessDigraph;
import player.BotPlayer;
import player.Player;

import java.util.*;

/**
 * Service class in which the movement of chess pieces is implemented
 **/

public class ChessService {
    public  void createChess(Player player, Player defendPlayer, Maps maps) {
        addFigure(new Pawn(), Constant.toChessCoord(16), player, maps);
        addFigure(new Pawn(), Constant.toChessCoord(17), player, maps);
        addFigure(new Pawn(), Constant.toChessCoord(18), player, maps);
        addFigure(new Pawn(), Constant.toChessCoord(19), player, maps);
        addFigure(new Pawn(), Constant.toChessCoord(20), player, maps);
        addFigure(new Pawn(), Constant.toChessCoord(21), player, maps);
        addFigure(new Pawn(), Constant.toChessCoord(22), player, maps);
        addFigure(new Pawn(), Constant.toChessCoord(23), player, maps);
        addFigure(new Pawn(), Constant.toChessCoord(24), player, maps);
        addFigure(new Rook(), Constant.toChessCoord(15), player, maps);
        addFigure(new Rook(), Constant.toChessCoord(9), player, maps);
        addFigure(new Knight(), Constant.toChessCoord(8), player, maps);
        addFigure(new Knight(), Constant.toChessCoord(4), player, maps);
        addFigure(new Queen(), Constant.toChessCoord(1), player, maps);
        addFigure(new King(), Constant.toChessCoord(3), player, maps);
        addFigure(new Bishop(), Constant.toChessCoord(0), player, maps);
        addFigure(new Bishop(), Constant.toChessCoord(2), player, maps);
        addFigure(new Bishop(), Constant.toChessCoord(6), player, maps);
        addFigure(new Pawn(), Constant.toChessCoord(81), defendPlayer, maps);
        addFigure(new Pawn(), Constant.toChessCoord(71), defendPlayer, maps);
        addFigure(new Pawn(), Constant.toChessCoord(61), defendPlayer, maps);
        addFigure(new Pawn(), Constant.toChessCoord(51), defendPlayer, maps);
        addFigure(new Pawn(), Constant.toChessCoord(41), defendPlayer, maps);
        addFigure(new Pawn(), Constant.toChessCoord(53), defendPlayer, maps);
        addFigure(new Pawn(), Constant.toChessCoord(65), defendPlayer, maps);
        addFigure(new Pawn(), Constant.toChessCoord(77), defendPlayer, maps);
        addFigure(new Pawn(), Constant.toChessCoord(89), defendPlayer, maps);
        addFigure(new Rook(), Constant.toChessCoord(88), defendPlayer, maps);
        addFigure(new Rook(), Constant.toChessCoord(82), defendPlayer, maps);
        addFigure(new Knight(), Constant.toChessCoord(83), defendPlayer, maps);
        addFigure(new Knight(), Constant.toChessCoord(87), defendPlayer, maps);
        addFigure(new Queen(), Constant.toChessCoord(84), defendPlayer, maps);
        addFigure(new King(), Constant.toChessCoord(86), defendPlayer, maps);
        addFigure(new Bishop(), Constant.toChessCoord(85), defendPlayer, maps);
        addFigure(new Bishop(), Constant.toChessCoord(74), defendPlayer, maps);
        addFigure(new Bishop(), Constant.toChessCoord(63), defendPlayer, maps);
    }

    public boolean isMate(Figure king, Maps maps, AdjListChessDigraph digraph, Player attackPlayer, Player defendPlayer) {


        Figure attackedKnight = null;
        List<Figure> attackFigure = new ArrayList<>();

        for (Map.Entry<Figure, String> keyAndValue : attackPlayer.getCells().entrySet()) {
            if (cellIsAttacked(keyAndValue.getKey(), attackPlayer.getCells().get(keyAndValue.getKey()), digraph, maps, defendPlayer.getCells().get(king), attackPlayer, defendPlayer)) {
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

        boolean attackedKnightNotDie = true;

        if (attackedKnight != null) {
            for (Map.Entry<Figure, String> keyAndValue : defendPlayer.getCells().entrySet()) {
                 if(cellIsAttacked(keyAndValue.getKey(), defendPlayer.getCells().get(keyAndValue.getKey()), digraph, maps, attackPlayer.getCells().get(attackedKnight), attackPlayer, defendPlayer)){
                     attackedKnightNotDie = false;
                 }
            }

            if(attackedKnightNotDie){
                return true;
            }
        }



        List<String> edgeWhereTheKingCanGo = checkEdgeWhereTheKingCanGo(king, defendPlayer, digraph);

        for (Map.Entry<Figure, String> keyAndValue : attackPlayer.getCells().entrySet()) {
            edgeWhereTheKingCanGo.removeIf(edge -> cellIsAttacked(keyAndValue.getKey(), attackPlayer.getCells().get(keyAndValue.getKey()), digraph, maps, edge, attackPlayer, defendPlayer));
        }

        if (edgeWhereTheKingCanGo.size() > 0) {
            return false;
        }

        List<String> directionAttackKing = checkDirection(attackFigure.get(0), attackPlayer.getCells().get(attackFigure.get(0)), digraph, maps, defendPlayer.getCells().get(king), attackPlayer, defendPlayer);

        if (directionAttackKing.size() == 0) {
            return false;
        }

        for (Map.Entry<Figure, String> keyAndValue : defendPlayer.getCells().entrySet()) {
            for (String edge : directionAttackKing) {
                if (cellIsAttacked(keyAndValue.getKey(), defendPlayer.getCells().get(keyAndValue.getKey()), digraph, maps, edge, attackPlayer, defendPlayer)) {
                    return false;
                }
            }
        }

        return true;
    }

    private List<String> checkDirection(Figure figure, String nowStay, AdjListChessDigraph digraph, Maps maps, String nextStay, Player attackPlayer, Player defendPlayer) {
        if (figure instanceof Rook) {
            return getDirectionHorizontalAndVerticalLines(maps, digraph, nextStay, nowStay);
        } else if (figure instanceof Bishop) {
            return getDirectionDiagonalLines(maps, digraph, nextStay, nowStay);
        } else if (figure instanceof Queen) {
            List<String> bufferedList = getDirectionDiagonalLines(maps, digraph, nextStay, nowStay);
            if (bufferedList.size() > 0) {
                return bufferedList;
            } else {
                return getDirectionHorizontalAndVerticalLines(maps, digraph, nextStay, nowStay);
            }
        }
        return new ArrayList<>();
    }

    public boolean cellIsAttacked(Figure figure, String nowStay, AdjListChessDigraph digraph, Maps maps, String nextStay, Player attackPlayer, Player defendPlayer) {
        if (figure instanceof Pawn) {
            return nextStay.equals(digraph.getCellDiagonal(nowStay).get(new Direction(0)))
                    || nextStay.equals(digraph.getCellDiagonal(nowStay).get(new Direction(0))) ||
                    nextStay.equals(digraph.getCellDiagonal(nowStay).get(new Direction(3))) ||
                    nextStay.equals(digraph.getCellDiagonal(nowStay).get(new Direction(4)));
        } else if (figure instanceof Knight) {
            return knightCanMove(nowStay, nextStay, digraph, maps, attackPlayer, defendPlayer);
        } else if (figure instanceof Rook) {
            return checkHorizontalAndVerticalLines(maps, digraph, nextStay, nowStay);
        } else if (figure instanceof Bishop) {
            return checkDiagonalLines(maps, digraph, nextStay, nowStay);
        } else if (figure instanceof Queen) {
            return checkHorizontalAndVerticalLines(maps, digraph, nextStay, nowStay) || checkDiagonalLines(maps, digraph, nextStay, nowStay);
        }
        return false;
    }



    /**
     * In this method, we find out which instance of the class we got and, depending on this,
     * select the logic of the figure's move
     **/
    public boolean move(String move, AdjListChessDigraph digraph, Maps maps, Player attackPlayer, Player defendPlayer) {
        move = move.toLowerCase();
        String[] moves = move.split(" ");

        if (Constant.toEdge(moves[1]) == null || Constant.toEdge(moves[0]) == null) { //Check in case we are given a
            System.out.println("You have selected a non-existing coordinate.");       //coordinate which does not exist
            return false;                                                             //whose coordinate does not exist
        }

        Figure figure = maps.getFigures().get(moves[0]);

        if (figure instanceof Pawn) {
            return movePawn(figure, moves, digraph, maps, attackPlayer, defendPlayer);
        } else if (figure instanceof Knight) {
            return moveKnight(figure, moves, digraph, maps, attackPlayer, defendPlayer);
        } else if (figure instanceof Rook) {
            return moveRook(figure, moves, digraph, maps, attackPlayer, defendPlayer);
        } else if (figure instanceof Bishop) {
            return moveBishop(figure, moves, digraph, maps, attackPlayer, defendPlayer);
        } else if (figure instanceof Queen) {
            return moveQueen(figure, moves, digraph, maps, attackPlayer, defendPlayer);
        } else if (figure instanceof King) {
            return moveKing(figure, moves, digraph, maps, attackPlayer, defendPlayer);
        }

        System.out.println("You have chosen an empty cell!!");
        return false;
    }

    /**
     * Logic for the king. For black and white, the logic is not very different.
     * The bottom line is that we check all possible directions in which the king
     * can go with the condition that the length of the step e is greater than 1
     **/

    private boolean moveKing(Figure figure, String[] moves, AdjListChessDigraph digraph, Maps maps, Player attackPlayer, Player defendPlayer) {
        if (checkDiagonalLines(maps, digraph, moves[1], moves[0], true) ||
                checkHorizontalAndVerticalLines(maps, digraph, moves[1], moves[0], true)) {
            if (attackPlayer.getFigures().get(moves[1]) == null) {
                moveFigure(figure, moves[1], attackPlayer, defendPlayer, maps);
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

    private boolean moveQueen(Figure figure, String[] moves, AdjListChessDigraph digraph, Maps maps, Player attackPlayer, Player defendPlayer) {
        if (checkDiagonalLines(maps, digraph, moves[1], moves[0]) || checkHorizontalAndVerticalLines(maps, digraph, moves[1], moves[0])) {
            if (attackPlayer.getFigures().get(moves[1]) == null) {
                moveFigure(figure, moves[1], attackPlayer, defendPlayer, maps);
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

    private boolean moveBishop(Figure figure, String[] moves, AdjListChessDigraph digraph, Maps maps, Player attackPlayer, Player defendPlayer) {
        if (checkDiagonalLines(maps, digraph, moves[1], moves[0])) {
            if (attackPlayer.getFigures().get(moves[1]) == null) {
                moveFigure(figure, moves[1], attackPlayer, defendPlayer, maps);
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

    private boolean checkDiagonalLines(Maps maps, AdjListChessDigraph digraph, String nextStay, String nowStay) {
        return checkDiagonalLines(maps, digraph, nextStay, nowStay, false);
    }

    private boolean checkDiagonalLines(Maps maps, AdjListChessDigraph digraph, String nextStay, String nowStay, boolean isKing) {
        Map<String, Figure> figures = maps.getFigures();

        boolean limiter;

        String bufferedNowStay;

        for (Map.Entry<Direction, String> keyAndValue : digraph.getCellDiagonal(nowStay).entrySet()) {
            int direction = keyAndValue.getKey().getDirection();
            bufferedNowStay = nowStay;
            do {
                bufferedNowStay = digraph.getCellDiagonal(bufferedNowStay).get(new Direction(direction));
                limiter = !isKing;
                if (bufferedNowStay == null) {
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

    private boolean moveRook(Figure figure, String[] moves, AdjListChessDigraph digraph, Maps maps, Player attackPlayer, Player defendPlayer) {

        if (checkHorizontalAndVerticalLines(maps, digraph, moves[1], moves[0])) {
            if (attackPlayer.getFigures().get(moves[1]) == null) {
                moveFigure(figure, moves[1], attackPlayer, defendPlayer, maps);
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
    private boolean checkHorizontalAndVerticalLines(Maps maps, AdjListChessDigraph digraph, String nextStay, String nowStay) {
        return checkHorizontalAndVerticalLines(maps, digraph, nextStay, nowStay, false);
    }

    private boolean checkHorizontalAndVerticalLines(Maps maps, AdjListChessDigraph digraph, String nextStay, String nowStay, boolean isKing) {
        Map<String, Figure> figures = maps.getFigures();

        boolean limiter;
        String bufferedNowStay;

        for (Map.Entry<Direction, String> keyAndValue : digraph.getCellHorizontalAndVertical(nowStay).entrySet()) {
            int direction = keyAndValue.getKey().getDirection();
            bufferedNowStay = nowStay;
            do {
                bufferedNowStay = digraph.getCellHorizontalAndVertical(bufferedNowStay).get(new Direction(direction));
                limiter = !isKing;
                if (bufferedNowStay == null) {
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

    private boolean moveKnight(Figure figure, String[] moves, AdjListChessDigraph digraph, Maps maps, Player attackPlayer, Player defendPlayer) {
        if (knightCanMove(moves[0], moves[1], digraph, maps, attackPlayer, defendPlayer)) {
            moveFigure(figure, moves[1], attackPlayer, defendPlayer, maps);
            System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
        }
        return false;
    }

    private boolean knightCanMove(String start, String end, AdjListChessDigraph digraph, Maps maps, Player attackPlayer, Player defendPlayer) {


        Map<Direction, String> isNow = digraph.getCellHorizontalAndVertical(start);
        String next;
        int counterFirst = 0;
        int counterSecond = 1;

        for (int i = 0; i < 6; i++) {
            next = isNow.get(new Direction(i));

            if(next == null){
                continue;
            }

            String firstMove = digraph.getCellDiagonal(next).get(new Direction(counterFirst));
            Figure firstFigure = null;

            if(firstMove != null){
                firstFigure = attackPlayer.getFigures().get(firstMove);
            }

            String secondMove = digraph.getCellDiagonal(next).get(new Direction(counterSecond));
            Figure secondFigure = null;

            if(secondMove != null){
                secondFigure = attackPlayer.getFigures().get(firstMove);
            }

            if(firstMove != null) {
                if (firstFigure == null && end.equals(firstMove)) {
                    return true;
                }
            }
            if(secondMove != null) {
                if (secondFigure == null && end.equals(secondMove)) {
                    return true;
                }
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

    private boolean movePawn(Figure figure, String[] moves, AdjListChessDigraph digraph, Maps maps, Player attackPlayer, Player defendPlayer) {
        boolean isDoubleMove;
        Direction direction = attackPlayer.getMoveDirection();
        Direction directionLeft = direction.getDirection() == Direction.UP ? new Direction(Direction.UP_RIGHT) : new Direction(Direction.DOWN_LEFT);


        if (direction.getDirection() == Direction.UP) {
            isDoubleMove = Constant.toEdge(moves[0]) >= 16 && Constant.toEdge(moves[0]) <= 25;
        } else {
            isDoubleMove = Constant.toEdge(moves[0]) == 81 || Constant.toEdge(moves[0]) == 71 || Constant.toEdge(moves[0]) == 61
                    || Constant.toEdge(moves[0]) == 51 || Constant.toEdge(moves[0]) == 63 || Constant.toEdge(moves[0]) == 75 || Constant.toEdge(moves[0]) == 87;
        }

        Map<Direction, String> isNow = digraph.getCellHorizontalAndVertical(moves[0]);
        String next = isNow.get(direction);

        if (next.equals(moves[1]) && attackPlayer.getFigures().get(next) == null) {
            moveFigure(figure, next, attackPlayer, defendPlayer, maps);
            System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
            if (digraph.getCellHorizontalAndVertical(next).get(new Direction(direction.getDirection())) == null) {
                setFigure(pawnConversion(direction.getDirection() == Direction.DOWN, attackPlayer instanceof BotPlayer), next, attackPlayer, maps);
            }
            return true;
        }

        isNow = digraph.getCellHorizontalAndVertical(next);
        next = isNow.get(direction);
        if(next != null) {
            if (next.equals(moves[1]) && maps.getFigures().get(next) == null && isDoubleMove) {
                moveFigure(figure, next, attackPlayer, defendPlayer, maps);
                System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
                return true;
            }
        }

        isNow = digraph.getCellDiagonal(moves[0]);
        next = isNow.get(direction);

        if (next.equals(moves[1]) && attackPlayer.getFigures().get(next) == null) {
            moveFigure(figure, next, attackPlayer, defendPlayer, maps);
            System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
            return true;
        }

        next = isNow.get(directionLeft);

        if (next.equals(moves[1]) && attackPlayer.getFigures().get(next) == null) {
            moveFigure(figure, next, attackPlayer, defendPlayer, maps);
            System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
            return true;
        }

        return false;
    }

    /**
     * Here is the choice of the piece that the pawn turns into
     **/

    private Figure pawnConversion(boolean isBlack, boolean isBot) {
        Scanner scanner;

        if(!isBot) {
            System.out.print("Choose a shape from the list:\n" +
                    "    Queen\n    Bishop\n    Rook\n    Knight\nYour Choose: ");
            scanner = new Scanner(System.in);
        }else {
            if (isBlack) {
                return new Queen();
            } else {
                return new Queen();
            }
        }

        while (true) {
            switch (scanner.nextLine()) {
                case "Rook":
                    if (isBlack) {
                        return new Rook();
                    } else {
                        return new Rook();
                    }
                case "Bishop":
                    if (isBlack) {
                        return new Bishop();
                    } else {
                        return new Bishop();
                    }
                case "Queen":
                    if (isBlack) {
                        return new Queen();
                    } else {
                        return new Queen();
                    }
                case "Knight":
                    if (isBlack) {
                        return new Knight();
                    } else {
                        return new Knight();
                    }
            }
        }
    }

    private List<String> getDirectionHorizontalAndVerticalLines(Maps maps, AdjListChessDigraph digraph, String nextStay, String nowStay) {
        Map<String, Figure> figures = maps.getFigures();

        List<String> list = new ArrayList<>();
        String bufferedNowStay;

        for (Map.Entry<Direction, String> keyAndValue : digraph.getCellHorizontalAndVertical(nowStay).entrySet()) {
            int direction = keyAndValue.getKey().getDirection();
            bufferedNowStay = nowStay;
            do {
                list.add(bufferedNowStay);
                bufferedNowStay = digraph.getCellHorizontalAndVertical(bufferedNowStay).get(new Direction(direction));
                if (bufferedNowStay == null) {
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
                if (bufferedNowStay == null) {
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

    /**
     * Устанавливает фигуру в определённом месте на доске
     * figure - фигура которую надо установить
     * edge - клетка в которую устанавливаем (h3)
     * player - игрок которому принадлежит фигура
     * maps - карта со всеми фигурами
     **/

    public  void setFigure(Figure figure, String edge, Player player, Maps maps) {
        if (player.getCells().get(player.getFigures().get(edge)) != null) {
            deleteFigure(player.getFigures().get(edge), edge, player, maps);
            addFigure(figure, edge, player, maps);
        }
    }

    /**
     * Передвигает фигуру из одного места в другое
     * figure - фигура которую надо установить
     * edge - клетка в которую перемещаем (h3)
     * player - игрок которому принадлежит фигура
     * defendPlayer - противник
     * maps - карта со всеми фигурами
     **/

    public void moveFigure(Figure figure, String edge, Player player, Player defendPlayer, Maps maps) {
        if (player.getCells().get(figure) != null) {
            String nowStay = player.getCells().get(figure);
            Figure enemy = defendPlayer.getFigures().get(edge);
            if (enemy != null) {
                deleteFigure(enemy, edge, defendPlayer, maps);
            }
            deleteFigure(figure, nowStay, player, maps);
            addFigure(figure, edge, player, maps);
        }
    }

    /**
     * Удаляет фигуру
     * figure - фигура которую надо удалить
     * edge - клетка из которой удаляем (h3)
     * player - игрок которому принадлежит фигура
     * maps - карта со всеми фигурами
     **/

    public void deleteFigure(Figure figure, String edge, Player player, Maps maps) {
        if (figure != null && edge != null) {
            player.getFigures().remove(edge);
            player.getCells().remove(figure);
            maps.getFigures().remove(edge);
            maps.getCells().remove(figure);
        }
    }

    /**
     * Добовляет фигуру
     * figure - фигура которую надо добавить
     * edge - клетка которую добавляем (h3)
     * player - игрок которому принадлежит фигура
     * maps - карта со всеми фигурами
     **/

    public void addFigure(Figure figure, String edge, Player player, Maps maps) {
        player.getFigures().put(edge, figure);
        player.getCells().put(figure, edge);
        maps.getFigures().put(edge, figure);
        maps.getCells().put(figure, edge);
    }

    /**
     * Дальше все методы будут возвращать все возможные ходы для фигур
     **/

    public List<String> getPawnMove(Figure figure, Player player, Maps maps, AdjListChessDigraph digraph) {
        boolean isDoubleMove;
        Direction direction = player.getMoveDirection();
        Direction directionLeft = direction.getDirection() == Direction.UP ? new Direction(Direction.UP_RIGHT) : new Direction(Direction.DOWN_LEFT);

        List<String> moves = new ArrayList<>();
        String stayNow = player.getCells().get(figure);

        if (direction.getDirection() == Direction.UP) {
            isDoubleMove = Constant.toEdge(stayNow) >= 16 && Constant.toEdge(stayNow) <= 25;
        } else {
            isDoubleMove = Constant.toEdge(stayNow) == 81 || Constant.toEdge(stayNow) == 71 || Constant.toEdge(stayNow) == 61
                    || Constant.toEdge(stayNow) == 51 || Constant.toEdge(stayNow) == 63 || Constant.toEdge(stayNow) == 75 || Constant.toEdge(stayNow) == 87;
        }

        Map<Direction, String> isNow = digraph.getCellHorizontalAndVertical(stayNow);
        String next = isNow.get(direction);

        if (player.getFigures().get(next) == null) {
            moves.add(next);
        }

        isNow = digraph.getCellHorizontalAndVertical(next);
        next = isNow.get(direction);

        if (maps.getFigures().get(next) == null && isDoubleMove) {
            moves.add(next);
        }

        isNow = digraph.getCellDiagonal(stayNow);
        next = isNow.get(direction);

        if (next != null) {
            if (player.getFigures().get(next) == null && maps.getFigures().get(next) != null) {
                moves.add(next);
            }
        }

        next = isNow.get(directionLeft);

        if(next != null) {
            if (player.getFigures().get(next) == null && maps.getFigures().get(next) != null) {
                moves.add(next);
            }
        }

        return moves;
    }

    private List<String> getKnightMove(Figure figure, Player player, AdjListChessDigraph digraph) {

        List<String> moves = new ArrayList<>();
        String stayNow = player.getCells().get(figure);

        Map<Direction, String> isNow = digraph.getCellHorizontalAndVertical(stayNow);
        String next;
        int counterFirst = 0;
        int counterSecond = 1;

        for (int i = 0; i < 6; i++) {
            next = isNow.get(new Direction(i));

            if(next == null){
                continue;
            }

            String firstMove = digraph.getCellDiagonal(next).get(new Direction(counterFirst));
            Figure firstFigure = null;

            if(firstMove != null){
                firstFigure = player.getFigures().get(firstMove);
            }

            String secondMove = digraph.getCellDiagonal(next).get(new Direction(counterSecond));
            Figure secondFigure = null;

            if(secondMove != null){
                secondFigure = player.getFigures().get(firstMove);
            }

            if (firstFigure == null && firstMove != null) {
                moves.add(firstMove);
            } else if (secondFigure == null && secondMove != null) {
                moves.add(secondMove);
            }
            counterFirst++;
            counterSecond++;
            counterSecond = counterSecond > 5 ? 0 : counterSecond;
        }

        return moves;
    }

    private List<String> getHorizontalMoves(Figure figure, Player player, Maps maps, AdjListChessDigraph digraph){
        Map<String, Figure> figures = maps.getFigures();

        List<String> list = new ArrayList<>();
        String nowStay = maps.getCells().get(figure);
        String bufferedNowStay;

        for (Map.Entry<Direction, String> keyAndValue : digraph.getCellHorizontalAndVertical(nowStay).entrySet()) {
            int direction = keyAndValue.getKey().getDirection();
            bufferedNowStay = nowStay;
            do {
                bufferedNowStay = digraph.getCellHorizontalAndVertical(bufferedNowStay).get(new Direction(direction));
                if (bufferedNowStay == null || player.getFigures().get(bufferedNowStay) != null) {
                    break;
                }
                list.add(bufferedNowStay);
            } while (figures.get(bufferedNowStay) == null);
        }

        return list;
    }

    private List<String> getDiagonalLinesMoves(Figure figure, Player player, Maps maps, AdjListChessDigraph digraph) {
        Map<String, Figure> figures = maps.getFigures();

        List<String> list = new ArrayList<>();
        String bufferedNowStay;
        String nowStay = maps.getCells().get(figure);

        for (Map.Entry<Direction, String> keyAndValue : digraph.getCellDiagonal(nowStay).entrySet()) {
            int direction = keyAndValue.getKey().getDirection();

            bufferedNowStay = nowStay;
            do {
                bufferedNowStay = digraph.getCellDiagonal(bufferedNowStay).get(new Direction(direction));
                if (bufferedNowStay == null || player.getFigures().get(bufferedNowStay) != null) {
                    break;
                }
                list.add(bufferedNowStay);
            } while (figures.get(bufferedNowStay) == null);
        }

        return list;
    }

    public List<String> checkEdgeWhereTheKingCanGo(Figure figure, Player player, AdjListChessDigraph digraph) {
        List<String> edgeWhereTheKingCanGo = new ArrayList<>();
        String nowStay = player.getCells().get(figure);
        for (Map.Entry<Direction, String> keyAndValue : digraph.getCellHorizontalAndVertical(nowStay).entrySet()) {
            if (player.getFigures().get(keyAndValue.getValue()) == null) {
                edgeWhereTheKingCanGo.add(keyAndValue.getValue());
            }
        }

        for (Map.Entry<Direction, String> keyAndValue : digraph.getCellDiagonal(nowStay).entrySet()) {
            if (player.getFigures().get(keyAndValue.getValue()) == null) {
                edgeWhereTheKingCanGo.add(keyAndValue.getValue());
            }
        }

        return edgeWhereTheKingCanGo;
    }

    public List<String> getMovesFigure(Figure figure, Player player, Maps maps, AdjListChessDigraph digraph) {
        if(figure instanceof  Pawn){
            return getPawnMove(figure, player, maps, digraph);
        }else if (figure instanceof Rook) {
            return getHorizontalMoves(figure,player, maps, digraph);
        } else if (figure instanceof Bishop) {
            return getDiagonalLinesMoves(figure,player, maps, digraph);
        }else if(figure instanceof Knight){
            return getKnightMove(figure, player,digraph);
        } else if (figure instanceof Queen) {
            List<String> bufferedList = getDiagonalLinesMoves(figure,player, maps, digraph);
            bufferedList.addAll(getHorizontalMoves(figure,player, maps, digraph));
            return bufferedList;
        }else if(figure instanceof  King){
            return checkEdgeWhereTheKingCanGo(figure, player, digraph);
        }
        return new ArrayList<>();
    }
}
