package oop4.ui;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;

import java.util.function.Consumer;

public class ShapeToolBar extends ToolBar {
    private final ToggleGroup group = new ToggleGroup();
    private final ToggleButton[] buttons = new ToggleButton[ShapeType.values().length];

    public ShapeToolBar(Consumer<ShapeType> onSelect) {
        for (ShapeType t : ShapeType.values()) {
            buttons[t.ordinal()] = Utils.iconButton(
                    this, group, t.createIcon(), t.getTitle(), () -> onSelect.accept(t));
        }
    }

    public void select(ShapeType t) {
        buttons[t.ordinal()].setSelected(true);
    }
}