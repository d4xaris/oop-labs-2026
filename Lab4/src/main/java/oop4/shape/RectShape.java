package oop3.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import oop3.ui.Utils;

public class RectShape extends Shape {

    @Override
    public void show(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
        Utils.solidStroke(gc, Color.BLACK);
        outline(gc);
    }

    @Override
    protected void outline(GraphicsContext gc) {
        gc.strokeRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
    }
}