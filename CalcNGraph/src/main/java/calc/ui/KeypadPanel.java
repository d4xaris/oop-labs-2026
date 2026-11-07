package calc.ui;

import calc.model.CalcEngine;
import calc.model.Operation;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class KeypadPanel extends VBox {
    private final CalcEngine engine;
    private final Runnable onChange;
    private final List<Button> digitButtons = new ArrayList<>();

    public KeypadPanel(CalcEngine engine, Runnable onChange) {
        this.engine = engine;
        this.onChange = onChange;
        getStyleClass().add("keypad");
        setSpacing(1);

        GridPane grid = createGrid();
        VBox.setVgrow(grid, Priority.ALWAYS);
        getChildren().addAll(createHexRow(), grid);
    }

    private HBox createHexRow() {
        HBox row = new HBox(1);
        for (char c : "ABCDEF".toCharArray()) {
            Button b = digitButton(c, "hex");
            HBox.setHgrow(b, Priority.ALWAYS);
            row.getChildren().add(b);
        }
        return row;
    }

    private GridPane createGrid() {
        GridPane g = new GridPane();
        g.setHgap(1);
        g.setVgap(1);
        for (int i = 0; i < 4; i++) {
            ColumnConstraints c = new ColumnConstraints();
            c.setPercentWidth(25);
            g.getColumnConstraints().add(c);
        }
        for (int i = 0; i < 5; i++) {
            RowConstraints r = new RowConstraints();
            r.setPercentHeight(20);
            g.getRowConstraints().add(r);
        }

        g.addRow(0,
                button("C", "func", () -> engine.clear()),
                button("<-", "func", () -> engine.backspace()),
                button("1/X", "func", () -> engine.reciprocal()),
                button("/", "op", () -> engine.setOperation(Operation.DIV)));
        g.addRow(1,
                digitButton('7', "digit"), digitButton('8', "digit"), digitButton('9', "digit"),
                button("\u00D7", "op", () -> engine.setOperation(Operation.MUL)));
        g.addRow(2,
                digitButton('4', "digit"), digitButton('5', "digit"), digitButton('6', "digit"),
                button("-", "op", () -> engine.setOperation(Operation.SUB)));
        g.addRow(3,
                digitButton('1', "digit"), digitButton('2', "digit"), digitButton('3', "digit"),
                button("+", "op", () -> engine.setOperation(Operation.ADD)));
        g.addRow(4,
                button("\u00B1", "func", () -> engine.negate()),
                digitButton('0', "digit"));
        g.add(button("=", "op", () -> engine.calculate()), 2, 4, 2, 1); // займає 2 колонки
        return g;
    }

    private Button digitButton(char c, String style) {
        Button b = button(String.valueOf(c), style, () -> engine.inputDigit(c));
        digitButtons.add(b);
        return b;
    }

    private Button button(String text, String style, Runnable action) {
        Button b = new Button(text);
        b.getStyleClass().add(style);
        b.setFocusTraversable(false);
        b.setMaxWidth(Double.MAX_VALUE);
        b.setMaxHeight(Double.MAX_VALUE);
        b.setOnAction(e -> {
            action.run();
            onChange.run();
        });
        return b;
    }

    public void updateDigits() {
        for (Button b : digitButtons) {
            b.setDisable(!engine.getBase().isValidDigit(b.getText().charAt(0)));
        }
    }
}