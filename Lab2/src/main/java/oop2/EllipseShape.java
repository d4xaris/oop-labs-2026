import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class EllipseShape extends Shape {

    @Override
    public void show(GraphicsContext gc) {
        double left = Math.min(x1, x2);
        double top = Math.min(y1, y2);
        double w = Math.abs(x2 - x1);
        double h = Math.abs(y2 - y1);
        gc.setFill(Color.ORANGE);
        gc.fillOval(left, top, w, h);
        Utils.solidStroke(gc, Color.BLACK);
        gc.strokeOval(left, top, w, h);
    }

    @Override
    protected void outline(GraphicsContext gc) {
        gc.strokeOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
    }
}