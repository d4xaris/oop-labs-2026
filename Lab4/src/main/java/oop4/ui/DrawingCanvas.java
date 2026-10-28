package oop4.ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class DrawingCanvas extends Canvas {
    private final MyEditor editor;

    public DrawingCanvas(double width, double height, MyEditor editor) {
        super(width, height);
        this.editor = editor;

        setOnMousePressed(e -> {
            editor.onLBDown(e.getX(), e.getY());
            redraw();
        });
        setOnMouseDragged(e -> {
            editor.onMouseMove(e.getX(), e.getY());
            redraw();
        });
        setOnMouseReleased(e -> {
            editor.onLBUp(e.getX(), e.getY());
            redraw();
        });

        redraw();
    }

    public void redraw() {
        GraphicsContext gc = getGraphicsContext2D();
        Utils.clear(gc, getWidth(), getHeight());
        editor.onPaint(gc);
    }
}