package chess_object.utils;

import chess_object.board.Board;
import chess_object.board.Maps;
import chess_object.figure.*;
import constant.Constant;
import graph.AdjListChessDigraph;
import player.BotPlayer;
import player.Player;
import player.RealPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GameUtils {

    public static String movePlayer(Player player, Player notMovePlayer, Maps maps, AdjListChessDigraph digraph) {
        System.out.print("Ход игрока " + player.getName() + ": ");
        if (player instanceof RealPlayer) {
            return moveRealPlayer();
        } else if (player instanceof BotPlayer) {
            String move = moveBotPlayer(player, notMovePlayer, maps, digraph);
            System.out.println(move);
            return move;
        }
        return "";
    }

    private static String moveBotPlayer(Player player, Player notMovePlayer, Maps maps, AdjListChessDigraph digraph) {

        List<MoveAndScore> moves = new ArrayList<>();


        for (Map.Entry<String, Figure> entry : player.getFigures().entrySet()) {
            moves.add(moveFigure(entry.getValue(), player, notMovePlayer, maps, digraph));
        }

        MoveAndScore bestMove = new MoveAndScore();
        bestMove.score = Integer.MIN_VALUE;

        for (MoveAndScore move : moves) {
            if (move.score > bestMove.score) {
                bestMove = move;
            }
        }

        return bestMove.move;
    }

    private static MoveAndScore moveFigure(Figure figure, Player player, Player notMovePlayer, Maps maps, AdjListChessDigraph digraph) {
        List<MoveAndScore> movesAndScores = new ArrayList<>();
        List<String> moves = ChessUtils.getMovesFigure(figure, player, maps, digraph);

            for (String move : moves) {
                MoveAndScore buffer = new MoveAndScore();
                buffer.move = player.getCells().get(figure) + " " + move;
                buffer.score = getScore(figure, move, player, notMovePlayer, maps, digraph);
                movesAndScores.add(buffer);
            }

        MoveAndScore bestMove = new MoveAndScore();
        bestMove.score = Integer.MIN_VALUE;

        for (MoveAndScore move : movesAndScores) {
            if (move.score > bestMove.score) {
                bestMove = move;
            }
        }

        return bestMove;
    }

    private static int getScore(Figure figure, String move, Player player, Player notMovePlayer, Maps maps, AdjListChessDigraph digraph) {

        int score = 0;
        Figure enemy = null;
        String stayNow = player.getCells().get(figure);

        if (notMovePlayer.getFigures().get(move) != null && !(notMovePlayer.getFigures().get(move) instanceof King)) {
            score += getScoreKill(notMovePlayer.getFigures().get(move));
            enemy = player.getFigures().get(move);
        }

        Player copyPlayer = player.copy();
        Player copyNotMovedPlayer = notMovePlayer.copy();
        Maps copyMaps = maps.copy();
        if(!(notMovePlayer.getFigures().get(move) instanceof King)) {
            ChessUtils.moveFigure(figure, move, copyPlayer, copyNotMovedPlayer, copyMaps);

            if (ChessUtils.isMate(copyNotMovedPlayer.getKing(), copyMaps, digraph, copyPlayer, copyNotMovedPlayer)) {
                ChessUtils.moveFigure(figure, stayNow, copyPlayer, copyNotMovedPlayer, copyMaps);
                if (enemy != null) {
                    ChessUtils.addFigure(enemy, move, copyNotMovedPlayer, copyMaps);
                }
                return 1000;
            }
        }



        int enemyKillScore = Integer.MAX_VALUE;

        for (Map.Entry<String, Figure> entry : notMovePlayer.getFigures().entrySet()) {
            if (ChessUtils.cellIsAttacked(entry.getValue(), entry.getKey(), digraph, maps, move, notMovePlayer, player) ||
                    ChessUtils.cellIsAttacked(entry.getValue(), entry.getKey(), digraph, maps, entry.getKey(), notMovePlayer, player)) {
                if (enemyKillScore == Integer.MAX_VALUE) {
                    score -= 110;
                }
                if (enemyKillScore > getScoreKill(entry.getValue())) {
                    enemyKillScore = getScoreKill(entry.getValue());
                }
            }
        }

        if(enemyKillScore != Integer.MAX_VALUE && !(figure instanceof King)) {
            for (Map.Entry<String, Figure> entry : player.getFigures().entrySet()) {
                if (ChessUtils.cellIsAttacked(entry.getValue(), entry.getKey(), digraph, maps, move, player, notMovePlayer)) {
                    score += 95;
                    score += enemyKillScore;
                    break;
                }
            }
        }

        List<String> moves = ChessUtils.getMovesFigure(figure, player, maps, digraph);

        enemyKillScore = 0;

        for (Map.Entry<String, Figure> entry : notMovePlayer.getFigures().entrySet()) {
            if (ChessUtils.cellIsAttacked(entry.getValue(), entry.getKey(), digraph, maps, move, notMovePlayer, player)) {
                if (enemyKillScore < getScoreKill(entry.getValue())) {
                    enemyKillScore = getScoreKill(entry.getValue());
                }
            }
        }

        score += enemyKillScore;

        return score;
    }


    private static int getScoreKill(Figure figure) {
        if (figure instanceof Pawn) {
            return 4;
        } else if (figure instanceof Knight) {
            return 9;
        } else if (figure instanceof Rook) {
            return 7;
        } else if (figure instanceof Bishop) {
            return 7;
        } else if (figure instanceof Queen) {
            return 10;
        }

        return 0;
    }

    private static String moveRealPlayer() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static void test(Player player, Player defendPlayer, Maps maps) {
        ChessUtils.addFigure(new King(true), "f5", player, maps);
        ChessUtils.addFigure(new Pawn(true), "f6", player, maps);
        ChessUtils.addFigure(new Pawn(true), "f4", player, maps);
        ChessUtils.addFigure(new Pawn(true), "e5", player, maps);
        ChessUtils.addFigure(new Pawn(true), "e4", player, maps);
        ChessUtils.addFigure(new Pawn(true), "g4", player, maps);
        ChessUtils.addFigure(new Pawn(true), "g5", player, maps);
        ChessUtils.addFigure(new Pawn(true), "e3", player, maps);
        ChessUtils.addFigure(new Pawn(true), "g6", player, maps);
        ChessUtils.addFigure(new Pawn(true), "e6", player, maps);
        ChessUtils.addFigure(new Pawn(true), "d4", player, maps);
        ChessUtils.addFigure(new Pawn(true), "h4", player, maps);
        ChessUtils.addFigure(new Bishop(false), "h1", defendPlayer, maps);
        ChessUtils.addFigure(new Rook(true), "f1", player, maps);
    }

    public static void createChess(Player player, Player defendPlayer, Maps maps) {
        ChessUtils.addFigure(new Pawn(true), Constant.toChessCoord(16), player, maps);
        ChessUtils.addFigure(new Pawn(true), Constant.toChessCoord(17), player, maps);
        ChessUtils.addFigure(new Pawn(true), Constant.toChessCoord(18), player, maps);
        ChessUtils.addFigure(new Pawn(true), Constant.toChessCoord(19), player, maps);
        ChessUtils.addFigure(new Pawn(true), Constant.toChessCoord(20), player, maps);
        ChessUtils.addFigure(new Pawn(true), Constant.toChessCoord(21), player, maps);
        ChessUtils.addFigure(new Pawn(true), Constant.toChessCoord(22), player, maps);
        ChessUtils.addFigure(new Pawn(true), Constant.toChessCoord(23), player, maps);
        ChessUtils.addFigure(new Pawn(true), Constant.toChessCoord(24), player, maps);
        ChessUtils.addFigure(new Rook(true), Constant.toChessCoord(15), player, maps);
        ChessUtils.addFigure(new Rook(true), Constant.toChessCoord(9), player, maps);
        ChessUtils.addFigure(new Knight(true), Constant.toChessCoord(8), player, maps);
        ChessUtils.addFigure(new Knight(true), Constant.toChessCoord(4), player, maps);
        ChessUtils.addFigure(new Queen(true), Constant.toChessCoord(1), player, maps);
        ChessUtils.addFigure(new King(true), Constant.toChessCoord(3), player, maps);
        ChessUtils.addFigure(new Bishop(true), Constant.toChessCoord(0), player, maps);
        ChessUtils.addFigure(new Bishop(true), Constant.toChessCoord(2), player, maps);
        ChessUtils.addFigure(new Bishop(true), Constant.toChessCoord(6), player, maps);
        ChessUtils.addFigure(new Pawn(false), Constant.toChessCoord(81), defendPlayer, maps);
        ChessUtils.addFigure(new Pawn(false), Constant.toChessCoord(71), defendPlayer, maps);
        ChessUtils.addFigure(new Pawn(false), Constant.toChessCoord(61), defendPlayer, maps);
        ChessUtils.addFigure(new Pawn(false), Constant.toChessCoord(51), defendPlayer, maps);
        ChessUtils.addFigure(new Pawn(false), Constant.toChessCoord(41), defendPlayer, maps);
        ChessUtils.addFigure(new Pawn(false), Constant.toChessCoord(53), defendPlayer, maps);
        ChessUtils.addFigure(new Pawn(false), Constant.toChessCoord(65), defendPlayer, maps);
        ChessUtils.addFigure(new Pawn(false), Constant.toChessCoord(77), defendPlayer, maps);
        ChessUtils.addFigure(new Pawn(false), Constant.toChessCoord(89), defendPlayer, maps);
        ChessUtils.addFigure(new Rook(false), Constant.toChessCoord(88), defendPlayer, maps);
        ChessUtils.addFigure(new Rook(false), Constant.toChessCoord(82), defendPlayer, maps);
        ChessUtils.addFigure(new Knight(false), Constant.toChessCoord(83), defendPlayer, maps);
        ChessUtils.addFigure(new Knight(false), Constant.toChessCoord(87), defendPlayer, maps);
        ChessUtils.addFigure(new Queen(false), Constant.toChessCoord(84), defendPlayer, maps);
        ChessUtils.addFigure(new King(false), Constant.toChessCoord(86), defendPlayer, maps);
        ChessUtils.addFigure(new Bishop(false), Constant.toChessCoord(85), defendPlayer, maps);
        ChessUtils.addFigure(new Bishop(false), Constant.toChessCoord(74), defendPlayer, maps);
        ChessUtils.addFigure(new Bishop(false), Constant.toChessCoord(63), defendPlayer, maps);
    }


    public static void restart(Player player, Player defendPlayer, Maps maps) {
        player.getCells().clear();
        player.getFigures().clear();
        defendPlayer.getCells().clear();
        defendPlayer.getFigures().clear();
        maps.getCells().clear();
        maps.getFigures().clear();
        createChess(player, defendPlayer, maps);
    }

    private static class MoveAndScore {
        int score;
        String move;
    }

    public static void game(Board board) {
        while (true) {
            System.out.println(board.toString());
            String move = GameUtils.movePlayer(board.getPlayerWhoNowWalking(), board.getPlayerWhoNowNotWalking(), board.getMaps(), board.getDigraph());
            ChessUtils.move(move, board.getDigraph(), board.getMaps(), board.getPlayerWhoNowWalking(), board.getPlayerWhoNowNotWalking());
            if (ChessUtils.isMate(board.getPlayerWhoNowWalking().getKing(), board.getMaps(), board.getDigraph(), board.getPlayerWhoNowNotWalking(), board.getPlayerWhoNowWalking())) {
                break;
            }
            board.changePlayer();
        }
    }

}
