package ChessObject;

public class Cell {
    private Figure figure;
    private int x, y, z;

    public Cell(Figure figure, int x, int y, int z) {
        this.figure = figure;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }
}
