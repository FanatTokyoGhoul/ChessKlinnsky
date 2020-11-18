package graph;

import chess_object.Direction;

import java.util.*;

public class AdjListChessDigraph {

    private Map<String, Map<Direction, String>> verticalAndHorizontal = new HashMap<>();
    private Map<String, Map<Direction, String>> diagonal = new HashMap<>();

    int vCountVerticalAndHorizontal;
    int eCountVerticalAndHorizontal;
    int vCountDiagonal;
    int eCountDiagonal;


    public Map<Direction, String> getCellHorizontalAndVertical(String v1){
        return verticalAndHorizontal.get(v1);
    }

    public Map<Direction, String> getCellDiagonal(String v1){
        return diagonal.get(v1);
    }

    public void createDiagonal() {
        String isNow;
        for (Map.Entry<String, Map<Direction, String>> entryMapFirst : verticalAndHorizontal.entrySet()) {
            for (Map.Entry<Direction, String> entryMapSecond : entryMapFirst.getValue().entrySet()) {
                if (entryMapSecond.getKey().getDirection() == Direction.UP) {
                    isNow = entryMapSecond.getValue();
                    String upRight = verticalAndHorizontal.get(isNow).get(new Direction(Direction.UP_RIGHT));
                    String upLeft = verticalAndHorizontal.get(isNow).get(new Direction(Direction.UP_LEFT));
                    addEdge(entryMapFirst.getKey(), upRight, new Direction(1), true);
                    addEdge(entryMapFirst.getKey(), upLeft, new Direction(0), true);
                } else if (entryMapSecond.getKey().getDirection() == Direction.DOWN) {
                    isNow = entryMapSecond.getValue();
                    String downRight = verticalAndHorizontal.get(isNow).get(new Direction(Direction.DOWN_RIGHT));
                    String downLeft = verticalAndHorizontal.get(isNow).get(new Direction(Direction.DOWN_LEFT));
                    addEdge(entryMapFirst.getKey(), downRight, new Direction(3), true);
                    addEdge(entryMapFirst.getKey(), downLeft, new Direction(4), true);
                }else if (entryMapSecond.getKey().getDirection() == Direction.UP_RIGHT) {
                    isNow = entryMapSecond.getValue();
                    String downRight = verticalAndHorizontal.get(isNow).get(new Direction(Direction.DOWN_RIGHT));
                    addEdge(entryMapFirst.getKey(), downRight, new Direction(2), true);
                }else if (entryMapSecond.getKey().getDirection() == Direction.UP_LEFT) {
                    isNow = entryMapSecond.getValue();
                    String downLeft = verticalAndHorizontal.get(isNow).get(new Direction(Direction.DOWN_LEFT));
                    addEdge(entryMapFirst.getKey(), downLeft, new Direction(5), true);
                }
            }
        }
    }

    public int getVCountVerticalAndHorizontal() {
        return vCountVerticalAndHorizontal;
    }

    public int getECountVerticalAndHorizontal() {
        return eCountVerticalAndHorizontal;
    }

    public int getVCountDiagonal() {
        return vCountDiagonal;
    }

    public int getECountDiagonal() {
        return eCountDiagonal;
    }

    public void addEdge(String v1, String v2, Direction d, boolean itsDiagonal) {
        Map<String, Map<Direction, String>> addMap = itsDiagonal ? diagonal : verticalAndHorizontal;

        //if (!isAdj(v1, v2)) {

            if (addMap.get(v1) == null) {

                addMap.put(v1, new HashMap<>());
                if (itsDiagonal) {
                    vCountDiagonal++;
                } else {
                    vCountVerticalAndHorizontal++;
                }

            }

            addMap.get(v1).put(d, v2);
            if (itsDiagonal) {
                eCountDiagonal++;
            } else {
                eCountVerticalAndHorizontal++;
            }
        //}
    }


    public boolean isAdj(String v1, String v2) {
        return isAdjVerticalAndHorizontal(v1, v2) || isAdjDiagonals(v1, v2);
    }

    public boolean isAdjVerticalAndHorizontal(String v1, String v2) {
        for (Map.Entry<String, Map<Direction, String>> entryMapFirst : verticalAndHorizontal.entrySet()) {
            for (Map.Entry<Direction, String> entryMapSecond : entryMapFirst.getValue().entrySet()) {
                if (entryMapSecond.getValue().equals(v1)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isAdjDiagonals(String v1, String v2) {
        for (Map.Entry<String, Map<Direction, String>> entryMapFirst : diagonal.entrySet()) {
            for (Map.Entry<Direction, String> entryMapSecond : entryMapFirst.getValue().entrySet()) {
                if (entryMapSecond.getValue().equals(v1)) {
                    return true;
                }
            }
        }
        return false;
    }

}
