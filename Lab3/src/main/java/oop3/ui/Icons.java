package oop3.ui;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Icons {

    private Icons() {
    }

    public static Node pointIcon() {
        return new Circle(3, Color.BLACK);
    }

    public static Node lineIcon() {
        Line line = new Line(0, 12, 14, 0);
        line.setStroke(Color.BLACK);
        return line;
    }

    public static Node rectIcon() {
        Rectangle r = new Rectangle(14, 10);
        r.setFill(Color.WHITE);
        r.setStroke(Color.BLACK);
        return r;
    }

    public static Node ellipseIcon() {
        Ellipse e = new Ellipse(8, 5);
        e.setFill(Color.WHITE);
        e.setStroke(Color.BLACK);
        return e;
    }
}