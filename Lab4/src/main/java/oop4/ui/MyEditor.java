package oop4.ui;

import javafx.scene.canvas.GraphicsContext;
import oop4.shape.Shape;

public class MyEditor {
    private static final int N = 130;

    private final Shape[] pcshape;
    private int count = 0;
    private Shape current;
    private ShapeType type = ShapeType.POINT;

    public MyEditor() {
        pcshape = new Shape[N];
    }

    public void start(ShapeType type) {
        this.type = type;
    }

    public ShapeType getType() {
        return type;
    }

    public void onLBDown(double x, double y) {
        if (count >= N) {
            return;
        }
        current = type.createShape();
        current.setStart(x, y);
    }

    public void onMouseMove(double x, double y) {
        if (current != null) {
            current.setEnd(x, y);
        }
    }

    public void onLBUp(double x, double y) {
        if (current != null) {
            current.setEnd(x, y);
            pcshape[count++] = current;
            current = null;
        }
    }

    public void onPaint(GraphicsContext gc) {
        for (int i = 0; i < count; i++) {
            pcshape[i].show(gc);
        }
        if (current != null) {
            gc.setLineDashes(6, 4);
            current.showRubber(gc);
            gc.setLineDashes((double[]) null);
        }
    }
}