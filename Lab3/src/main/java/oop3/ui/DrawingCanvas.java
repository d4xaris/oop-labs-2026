package oop3.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import oop3.shape.Shape;

public class DrawingCanvas extends Canvas {
    private static final int N = 130;

    private final Shape[] pcshape = new Shape[N];
    private int count = 0;
    private Shape current;
    private ShapeType type = ShapeType.POINT;

    public DrawingCanvas(double width, double height) {
        super(width, height);

        setOnMousePressed(e -> onLBDown(e.getX(), e.getY()));
        setOnMouseDragged(e -> onMouseMove(e.getX(), e.getY()));
        setOnMouseReleased(e -> onLBUp(e.getX(), e.getY()));
        redraw();
    }

    public void setType(ShapeType type) {
        this.type = type;
    }

    private void onLBDown(double x, double y) {
        if (count >= N) {
            return;
        }
        current = type.createShape();
        current.setStart(x, y);
        redraw();
    }

    private void onMouseMove(double x, double y) {
        if (current != null) {
            current.setEnd(x, y);
            redraw();
        }
    }

    private void onLBUp(double x, double y) {
        if (current != null) {
            current.setEnd(x, y);
            pcshape[count++] = current;
            current = null;
            redraw();
        }
    }

    public void redraw() {
        GraphicsContext gc = getGraphicsContext2D();
        Utils.clear(gc, getWidth(), getHeight());
        for (int i = 0; i < count; i++) {
            pcshape[i].show(gc);
        }
        if (current != null) {
            current.showRubber(gc);
        }
    }
}