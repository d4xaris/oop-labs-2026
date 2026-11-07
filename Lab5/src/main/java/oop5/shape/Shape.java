package oop5.shape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import oop5.ui.Utils;

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

    public double getX1() { return x1; }
    public double getY1() { return y1; }
    public double getX2() { return x2; }
    public double getY2() { return y2; }

    public void showRubber(GraphicsContext gc) {
        Utils.solidStroke(gc, Color.BLUE);
        outline(gc);
    }

    public void showSelected(GraphicsContext gc) {
        gc.setStroke(Color.RED);
        gc.setLineWidth(3);
        outline(gc);
        gc.setLineWidth(1);
    }

    public abstract void show(GraphicsContext gc);

    protected abstract void outline(GraphicsContext gc);
}