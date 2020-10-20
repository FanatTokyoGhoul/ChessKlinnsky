package graph;

/**
 * Интерфейс для описания ориентированного графа (орграфа)
 */
public interface Digraph extends Graph {
    /**
     * Интерфейс полностью совпадает с Graph
     */

    int getUp(int edge);
    int getDown(int edge);
    int getUpRight(int edge);
    int getUpLeft(int edge);
    int getDownRight(int edge);
    int getDownLeft(int edge);
}