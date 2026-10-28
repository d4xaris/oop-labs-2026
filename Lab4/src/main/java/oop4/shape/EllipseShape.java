package oop4.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import oop4.ui.Utils;

public class EllipseShape extends Shape {

    @Override
    public void show(GraphicsContext gc) {
        Utils.solidStroke(gc, Color.BLACK);
        outline(gc);
    }

    @Override
    protected void outline(GraphicsContext gc) {
        double dx = Math.abs(x2 - x1);
        double dy = Math.abs(y2 - y1);
        gc.strokeOval(x1 - dx, y1 - dy, 2 * dx, 2 * dy);
    }
}