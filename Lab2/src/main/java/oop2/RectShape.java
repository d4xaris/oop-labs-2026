import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RectShape extends Shape {

    @Override
    public void show(GraphicsContext gc) {
        Utils.solidStroke(gc, Color.BLACK);
        outline(gc);
    }

    @Override
    protected void outline(GraphicsContext gc) {
        double dx = Math.abs(x2 - x1);
        double dy = Math.abs(y2 - y1);
        gc.strokeRect(x1 - dx, y1 - dy, 2 * dx, 2 * dy);
    }
}