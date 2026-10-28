package oop4.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import oop4.ui.Utils;

public class RectShape extends Shape {
    private boolean filled = true;

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    @Override
    public void show(GraphicsContext gc) {
        if (filled) {
            gc.setFill(Color.WHITE);
            gc.fillRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
        }
        Utils.solidStroke(gc, Color.BLACK);
        outline(gc);
    }

    @Override
    protected void outline(GraphicsContext gc) {
        gc.strokeRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1), Math.abs(y2 - y1));
    }
}