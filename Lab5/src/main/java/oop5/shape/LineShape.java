package oop4.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import oop4.ui.Utils;

public class LineShape extends Shape {

    @Override
    public void show(GraphicsContext gc) {
        Utils.solidStroke(gc, Color.BLACK);
        gc.strokeLine(x1, y1, x2, y2);
    }

    @Override
    protected void outline(GraphicsContext gc) {
        gc.strokeLine(x1, y1, x2, y2);
    }
}