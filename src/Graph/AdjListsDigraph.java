package Graph;

/**
 * Реализация ориентированного графа (орграфа) на основе матрицы смежности
 */
public class AdjListsDigraph extends AdjListsGraph implements Digraph {
    @Override
    public int getUp(int edge) {
        return vEdjLists.get(edge).get(0);
    }

    @Override
    public int getDown(int edge) {
        return vEdjLists.get(edge).get(3);
    }

    @Override
    public int getUpRight(int edge) {
        return vEdjLists.get(edge).get(1);
    }

    @Override
    public int getUpLeft(int edge) {
        return vEdjLists.get(edge).get(5);
    }

    @Override
    public int getDownRight(int edge) {
        return vEdjLists.get(edge).get(2);
    }

    @Override
    public int getDownLeft(int edge) {
        return vEdjLists.get(edge).get(4);
    }
}

