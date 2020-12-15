package chess.services;

import chess.board.Board;
import chess.board.Maps;
import chess.figure.*;
import graph.AdjListChessDigraph;
import gson.services.GsonService;
import player.BotPlayer;
import player.Player;
import player.RealPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GameService {

    private final ChessService cs = new ChessService();
    private final GsonService gs = new GsonService();

    private String movePlayer(Player player, Player notMovePlayer, Maps maps, AdjListChessDigraph digraph) {
        System.out.print("Ход игрока " + player.getName() + ": ");
        if (player instanceof RealPlayer) {
            return readString();
        } else if (player instanceof BotPlayer) {
            String move = moveBotPlayer(player, notMovePlayer, maps, digraph);
            System.out.println(move);
            return move;
        }
        return "";
    }

    private String moveBotPlayer(Player player, Player notMovePlayer, Maps maps, AdjListChessDigraph digraph) {

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

    private MoveAndScore moveFigure(Figure figure, Player player, Player notMovePlayer, Maps maps, AdjListChessDigraph digraph) {
        List<MoveAndScore> movesAndScores = new ArrayList<>();
        List<String> moves = cs.getMovesFigure(figure, player, maps, digraph);

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

    private int getScore(Figure figure, String move, Player player, Player notMovePlayer, Maps maps, AdjListChessDigraph digraph) {

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
            cs.moveFigure(figure, move, copyPlayer, copyNotMovedPlayer, copyMaps);

            if (cs.isMate(copyNotMovedPlayer.getKing(), copyMaps, digraph, copyPlayer, copyNotMovedPlayer)) {
                cs.moveFigure(figure, stayNow, copyPlayer, copyNotMovedPlayer, copyMaps);
                if (enemy != null) {
                    cs.addFigure(enemy, move, copyNotMovedPlayer, copyMaps);
                }
                return 1000;
            }
        }



        int enemyKillScore = Integer.MAX_VALUE;

        for (Map.Entry<String, Figure> entry : notMovePlayer.getFigures().entrySet()) {
            if (cs.cellIsAttacked(entry.getValue(), entry.getKey(), digraph, maps, move, notMovePlayer, player) ||
                    cs.cellIsAttacked(entry.getValue(), entry.getKey(), digraph, maps, entry.getKey(), notMovePlayer, player)) {
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
                if (cs.cellIsAttacked(entry.getValue(), entry.getKey(), digraph, maps, move, player, notMovePlayer)) {
                    score += 95;
                    score += enemyKillScore;
                    break;
                }
            }
        }

        List<String> moves = cs.getMovesFigure(figure, player, maps, digraph);

        enemyKillScore = 0;

        for (Map.Entry<String, Figure> entry : notMovePlayer.getFigures().entrySet()) {
            if (cs.cellIsAttacked(entry.getValue(), entry.getKey(), digraph, maps, move, notMovePlayer, player)) {
                if (enemyKillScore < getScoreKill(entry.getValue())) {
                    enemyKillScore = getScoreKill(entry.getValue());
                }
            }
        }

        score += enemyKillScore;

        return score;
    }


    private int getScoreKill(Figure figure) {
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

    private String readString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private int readInt() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }


    public void restart(Player player, Player defendPlayer, Maps maps) {
        player.getCells().clear();
        player.getFigures().clear();
        defendPlayer.getCells().clear();
        defendPlayer.getFigures().clear();
        maps.getCells().clear();
        maps.getFigures().clear();
        cs.createChess(player, defendPlayer, maps);
    }

    private static class MoveAndScore {
        int score;
        String move;
    }

    public void startGame() throws InterruptedException {
        System.out.println("Введите 1 если хотите начать новую игру,\n а если хотите загрузить введите 2: ");
        int n = readInt();
        if(n == 1){
            System.out.println("Введите имя 1 игрока: ");
            String firstName = readString();
            System.out.println("Введите имя 2 игрока: ");
            String secondName = readString();
            game(new Board(firstName, secondName));
        }else {
            System.out.println("Введите путь к файлу: ");
            game(gs.toBoard(readString()));
        }
    }

    private void game(Board board) throws InterruptedException {
        while (true) {
            Thread.sleep(2000);
            gs.toJson(board);
            System.out.println(board.toString());
            String move = movePlayer(board.getPlayerWhoNowWalking(), board.getPlayerWhoNowNotWalking(), board.getMaps(), board.getDigraph());
            cs.move(move, board.getDigraph(), board.getMaps(), board.getPlayerWhoNowWalking(), board.getPlayerWhoNowNotWalking());
            if (cs.isMate(board.getPlayerWhoNowWalking().getKing(), board.getMaps(), board.getDigraph(), board.getPlayerWhoNowNotWalking(), board.getPlayerWhoNowWalking())) {
                break;
            }
            board.changePlayer();
        }
    }

}
