package oop4.shape;

import javafx.scene.canvas.GraphicsContext;

public class CubeShape extends Shape {
    private final RectShape front = new RectShape();
    private final RectShape back = new RectShape();
    private final LineShape[] edges = {
            new LineShape(), new LineShape(), new LineShape(), new LineShape()
    };

    public CubeShape() {
        front.setFilled(false);
        back.setFilled(false);
    }

    private void sync() {
        double l = Math.min(x1, x2), r = Math.max(x1, x2);
        double t = Math.min(y1, y2), b = Math.max(y1, y2);
        double d = Math.min(r - l, b - t) / 3;

        front.setStart(l, t + d);
        front.setEnd(r - d, b);
        back.setStart(l + d, t);
        back.setEnd(r, b - d);

        link(edges[0], l, t + d, l + d, t);
        link(edges[1], r - d, t + d, r, t);
        link(edges[2], l, b, l + d, b - d);
        link(edges[3], r - d, b, r, b - d);
    }

    private static void link(LineShape s, double ax, double ay, double bx, double by) {
        s.setStart(ax, ay);
        s.setEnd(bx, by);
    }

    @Override
    public void show(GraphicsContext gc) {
        sync();
        back.show(gc);
        for (LineShape e : edges) {
            e.show(gc);
        }
        front.show(gc);
    }

    @Override
    protected void outline(GraphicsContext gc) {
        sync();
        back.outline(gc);
        front.outline(gc);
        for (LineShape e : edges) {
            e.outline(gc);
        }
    }
}