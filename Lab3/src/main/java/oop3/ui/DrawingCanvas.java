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

        setOnMousePressed(e -> {
            if (count >= N) {
                return;
            }
            current = type.createShape();
            current.setStart(e.getX(), e.getY());
            redraw();
        });
        setOnMouseDragged(e -> {
            if (current != null) {
                current.setEnd(e.getX(), e.getY());
                redraw();
            }
        });
        setOnMouseReleased(e -> {
            if (current != null) {
                current.setEnd(e.getX(), e.getY());
                pcshape[count++] = current;
                current = null;
                redraw();
            }
        });

        redraw();
    }

    public void setType(ShapeType type) {
        this.type = type;
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