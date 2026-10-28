package oop4.ui;

import javafx.scene.Node;
import oop3.ui.Icons;
import oop4.shape.*;

import java.util.function.Supplier;

public enum ShapeType {
    POINT("Крапка", Icons::pointIcon, PointShape::new),
    LINE("Лінія", Icons::lineIcon, LineShape::new),
    RECTANGLE("Прямокутник", Icons::rectIcon, RectShape::new),
    ELLIPSE("Еліпс", Icons::ellipseIcon, EllipseShape::new),
    LINE_O("Лінія з кружечками", Icons::lineOIcon, LineOShape::new),
    CUBE("Куб", Icons::cubeIcon, CubeShape::new);

    private final String title;
    private final Supplier<Node> icon;
    private final Supplier<Shape> factory;

    ShapeType(String title, Supplier<Node> icon, Supplier<Shape> factory) {
        this.title = title;
        this.icon = icon;
        this.factory = factory;
    }

    public String getTitle() {
        return title;
    }

    public Node createIcon() {
        return icon.get();
    }

    public Shape createShape() {
        return factory.get();
    }
}