package oop4.ui;

import javafx.scene.Group;
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

    public static Node lineOIcon() {
        Line line = new Line(3, 12, 17, 2);
        line.setStroke(Color.BLACK);
        Circle c1 = new Circle(3, 12, 3, Color.WHITE);
        c1.setStroke(Color.BLACK);
        Circle c2 = new Circle(17, 2, 3, Color.WHITE);
        c2.setStroke(Color.BLACK);
        return new Group(line, c1, c2);
    }

    public static Node cubeIcon() {
        Rectangle back = new Rectangle(5, 0, 11, 9);
        back.setFill(Color.WHITE);
        back.setStroke(Color.BLACK);
        Rectangle front = new Rectangle(0, 5, 11, 9);
        front.setFill(Color.WHITE);
        front.setStroke(Color.BLACK);
        Line l1 = new Line(0, 5, 5, 0);
        Line l2 = new Line(11, 5, 16, 0);
        Line l3 = new Line(11, 14, 16, 9);
        for (Line l : new Line[]{l1, l2, l3}) {
            l.setStroke(Color.BLACK);
        }
        return new Group(back, l1, l2, l3, front);
    }
}