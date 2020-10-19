package ChessObject;

import Graph.Digraph;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ChessUtils {

    private static final Map<String, Integer> toEdge = new HashMap<>();

    static {
        toEdge.put("a1", 25);
        toEdge.put("a2", 36);
        toEdge.put("a3", 47);
        toEdge.put("a4", 59);
        toEdge.put("a5", 69);
        toEdge.put("a6", 80);
        toEdge.put("b1", 16);
        toEdge.put("b2", 26);
        toEdge.put("b3", 37);
        toEdge.put("b4", 48);
        toEdge.put("b5", 59);
        toEdge.put("b6", 70);
        toEdge.put("b7", 81);
        toEdge.put("c1", 9);
        toEdge.put("c2", 17);
        toEdge.put("c3", 27);
        toEdge.put("c4", 38);
        toEdge.put("c5", 49);
        toEdge.put("c6", 60);
        toEdge.put("c7", 71);
        toEdge.put("c8", 82);
        toEdge.put("d1", 4);
        toEdge.put("d2", 10);
        toEdge.put("d3", 18);
        toEdge.put("d4", 28);
        toEdge.put("d5", 39);
        toEdge.put("d6", 50);
        toEdge.put("d7", 61);
        toEdge.put("d8", 72);
        toEdge.put("d9", 83);
        toEdge.put("e1", 1);
        toEdge.put("e2", 5);
        toEdge.put("e3", 11);
        toEdge.put("e4", 19);
        toEdge.put("e5", 29);
        toEdge.put("e6", 40);
        toEdge.put("e7", 51);
        toEdge.put("e8", 62);
        toEdge.put("e9", 73);
        toEdge.put("e10", 84);
        toEdge.put("f1", 0);
        toEdge.put("f2", 2);
        toEdge.put("f3", 6);
        toEdge.put("f4", 12);
        toEdge.put("f5", 20);
        toEdge.put("f6", 30);
        toEdge.put("f7", 41);
        toEdge.put("f8", 52);
        toEdge.put("f9", 63);
        toEdge.put("f10", 74);
        toEdge.put("f11", 85);
        toEdge.put("g1", 3);
        toEdge.put("g2", 7);
        toEdge.put("g3", 13);
        toEdge.put("g4", 21);
        toEdge.put("g5", 31);
        toEdge.put("g6", 42);
        toEdge.put("g7", 53);
        toEdge.put("g8", 64);
        toEdge.put("g9", 75);
        toEdge.put("g10", 86);
        toEdge.put("h1", 8);
        toEdge.put("h2", 13);
        toEdge.put("h3", 22);
        toEdge.put("h4", 32);
        toEdge.put("h5", 43);
        toEdge.put("h6", 54);
        toEdge.put("h7", 65);
        toEdge.put("h8", 76);
        toEdge.put("h9", 87);
        toEdge.put("i1", 15);
        toEdge.put("i2", 23);
        toEdge.put("i3", 32);
        toEdge.put("i4", 44);
        toEdge.put("i5", 55);
        toEdge.put("i6", 66);
        toEdge.put("i7", 77);
        toEdge.put("i8", 88);
        toEdge.put("k1", 24);
        toEdge.put("k2", 34);
        toEdge.put("k3", 45);
        toEdge.put("k4", 56);
        toEdge.put("k5", 67);
        toEdge.put("k6", 78);
        toEdge.put("k7", 89);
        toEdge.put("l1", 35);
        toEdge.put("l2", 46);
        toEdge.put("l3", 57);
        toEdge.put("l4", 68);
        toEdge.put("l5", 79);
        toEdge.put("l6", 90);
    }

    public static boolean moveFigure(String move, Digraph digraph, Maps maps, boolean isBlack) {
        move = move.toLowerCase();
        String[] moves = move.split(" ");

        if (toEdge.get(moves[1]) == null || toEdge.get(moves[0]) == null) {
            System.out.println("You have selected a non-existing coordinate.");
            return false;
        }

        Figure figure = maps.getFigures().get(toEdge.get(moves[0]));

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
        int nextStay = toEdge.get(moves[1]);
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
        int nextStay = toEdge.get(moves[1]);
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
        int nextStay = toEdge.get(moves[1]);
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
        int nextStay = toEdge.get(moves[1]);
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
        int nextStay = toEdge.get(moves[1]);
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
        int nextStay = toEdge.get(moves[1]);
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
        int nextStay = toEdge.get(moves[1]);
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
        int nextStay = toEdge.get(moves[1]);
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
        int nextStay = toEdge.get(moves[1]);
        int nowStay = maps.getCellWhiteFigure().get(figure);
        if (digraph.getUpRight(digraph.getUp(digraph.getUp(nowStay))) == nextStay ||
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
                digraph.getDownLeft(digraph.getDownRight(digraph.getDownRight(nowStay))) == nextStay) {
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

    private static boolean moveBlackKnight(Figure figure, String[] moves, Digraph digraph, Maps maps) {
        if (maps.getCellWhiteFigure().get(figure) != null) {
            System.out.println("This is not your figure!");
            return false;
        }
        int nextStay = toEdge.get(moves[1]);
        int nowStay = maps.getCellBlackFigure().get(figure);
        if (digraph.getUpRight(digraph.getUp(digraph.getUp(nowStay))) == nextStay ||
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
                digraph.getDownLeft(digraph.getDownRight(digraph.getDownRight(nowStay))) == nextStay) {
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
        int checkNextStay = toEdge.get(moves[1]);
        if (moves[0].charAt(0) == moves[1].charAt(0)) {
            nextStay = toEdge.get(moves[1]);
        } else if (checkNextStay == digraph.getDownRight(digraph.getDown(nowStay))) {
            nextStay = digraph.getDownRight(digraph.getDown(nowStay));
        } else if (checkNextStay == digraph.getDownLeft(digraph.getDown(nowStay))) {
            nextStay = digraph.getDownLeft(digraph.getDown(nowStay));
        } else {
            nextStay = -1;
        }
        if (nextStay != -1 && moves[0].charAt(0) == moves[1].charAt(0)) {
            if (maps.getWhiteFigure().get(nextStay) == null && maps.getBlackFigure().get(nextStay) == null) {
                if(digraph.getDown(nowStay) == nextStay) {
                    maps.moveFigure(figure, nextStay);
                    System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
                    if (digraph.getDown(nextStay) == -1) {
                        maps.setFigure(pawnConversion(nextStay, false), nextStay);
                    }
                    return true;
                }else if(digraph.getDown(digraph.getDown(nowStay)) == nextStay && (nowStay == 82 || nowStay == 72 || nowStay == 62 || nowStay == 52 || nowStay == 64 || nowStay == 76 || nowStay == 88)){
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
        int checkNextStay = toEdge.get(moves[1]);
        if (moves[0].charAt(0) == moves[1].charAt(0)) {
            nextStay = toEdge.get(moves[1]);
        } else if (checkNextStay == digraph.getUpRight(digraph.getUp(nowStay))) {
            nextStay = digraph.getUpRight(digraph.getUp(nowStay));
        } else if (checkNextStay == digraph.getUpLeft(digraph.getUp(nowStay))) {
            nextStay = digraph.getUpLeft(digraph.getUp(nowStay));
        } else {
            nextStay = -1;
        }
        if (nextStay != -1 && moves[0].charAt(0) == moves[1].charAt(0)) {
            if (maps.getBlackFigure().get(nextStay) == null && maps.getWhiteFigure().get(nextStay) == null) {
                if(digraph.getUp(nowStay) == nextStay) {
                    maps.moveFigure(figure, nextStay);
                    System.out.println("Way " + moves[0] + " " + moves[1] + " was successful.");
                    if (digraph.getUp(nextStay) == -1) {
                        maps.setFigure(pawnConversion(nextStay, false), nextStay);
                    }
                    return true;
                }else if(digraph.getUp(digraph.getUp(nowStay)) == nextStay && nowStay >= 16 && nowStay <= 25){
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

    private static Figure pawnConversion(int stayNow, boolean isBlack) {
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
}
