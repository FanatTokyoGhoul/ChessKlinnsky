package constant;

import java.util.HashMap;
import java.util.Map;

public class Constant {
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

    public static Integer toEdge(String chessCoordinates){
        return toEdge.get(chessCoordinates);
    }
}
