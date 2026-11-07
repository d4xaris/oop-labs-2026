package calc.ui;

import calc.model.CalcEngine;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class CalcView extends BorderPane {
    private final CalcEngine engine;
    private final Label display = new Label("0");
    private final KeypadPanel keypad;

    public CalcView(CalcEngine engine) {
        this.engine = engine;

        display.getStyleClass().add("display");
        display.setMaxWidth(Double.MAX_VALUE);

        keypad = new KeypadPanel(engine, this::refresh);

        VBox top = new VBox(new BasePanel(engine, this::refresh), display);
        top.getStyleClass().add("top");

        setTop(top);
        setCenter(keypad);
        refresh();
    }

    private void refresh() {
        display.setText(engine.getDisplay());
        keypad.updateDigits();
    }
}