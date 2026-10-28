package oop4.shape;

import javafx.scene.canvas.GraphicsContext;

public class LineOShape extends Shape {
    private static final double R = 6;

    private final LineShape line = new LineShape();
    private final EllipseShape startCircle = new EllipseShape();
    private final EllipseShape endCircle = new EllipseShape();

    private void sync() {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double len = Math.hypot(dx, dy);

        if (len > 2 * R) {
            double ux = dx / len;
            double uy = dy / len;
            line.setStart(x1 + ux * R, y1 + uy * R);
            line.setEnd(x2 - ux * R, y2 - uy * R);
        } else {
            line.setStart(x1, y1);
            line.setEnd(x1, y1);
        }

        startCircle.setStart(x1, y1);
        startCircle.setEnd(x1 + R, y1 + R);
        endCircle.setStart(x2, y2);
        endCircle.setEnd(x2 + R, y2 + R);
    }

    @Override
    public void show(GraphicsContext gc) {
        sync();
        line.show(gc);
        startCircle.show(gc);
        endCircle.show(gc);
    }

    @Override
    protected void outline(GraphicsContext gc) {
        sync();
        line.outline(gc);
        startCircle.outline(gc);
        endCircle.outline(gc);
    }
}