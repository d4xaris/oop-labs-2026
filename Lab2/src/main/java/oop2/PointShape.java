import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PointShape extends Shape {

    @Override
    public void show(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillOval(x2 - 2, y2 - 2, 4, 4);
    }

    @Override
    protected void outline(GraphicsContext gc) {
        gc.strokeOval(x2 - 2, y2 - 2, 4, 4);
    }
}