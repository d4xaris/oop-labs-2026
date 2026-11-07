package calc.ui;

import calc.model.CalcEngine;
import calc.model.NumBase;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class BasePanel extends HBox {

    public BasePanel(CalcEngine engine, Runnable onChange) {
        getStyleClass().add("base-panel");

        ToggleGroup group = new ToggleGroup();
        group.selectedToggleProperty().addListener((obs, old, now) -> {
            if (now == null && old != null) old.setSelected(true);
        });

        for (NumBase b : NumBase.values()) {
            ToggleButton tb = new ToggleButton(b.name());
            tb.setToggleGroup(group);
            tb.setFocusTraversable(false);
            tb.setSelected(b == NumBase.DEC);
            tb.setOnAction(e -> {
                engine.setBase(b);
                onChange.run();
            });
            getChildren().add(tb);
        }
    }
}