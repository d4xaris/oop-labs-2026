import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {
    protected double x1, y1, x2, y2;

    public void setStart(double x, double y) {
        x1 = x;
        y1 = y;
        x2 = x;
        y2 = y;
    }

    public void setEnd(double x, double y) {
        x2 = x;
        y2 = y;
    }

    public void showRubber(GraphicsContext gc) {
        Utils.solidStroke(gc, Color.RED);
        outline(gc);
    }

    public abstract void show(GraphicsContext gc);

    protected abstract void outline(GraphicsContext gc);
}