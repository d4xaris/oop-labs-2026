import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

import java.util.function.Consumer;

// B1 = 1
public class Module1 {

    public static void showDialog(Stage owner, Consumer<String> callback) {
        Stage stage = Utils.createDialog(owner, "Choose a number (1-100)");

        Slider slider = new Slider(1, 100, 50);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(25);

        Label valueLabel = new Label("Value: 50");
        slider.valueProperty().addListener((obs, oldVal, newVal) ->
                valueLabel.setText("Value: " + newVal.intValue()));

        Button btnYes = new Button("Yes");
        Button btnCancel = new Button("Cancel");

        btnYes.setOnAction(e -> {
            callback.accept("Selected number: " + (int) slider.getValue());
            stage.close();
        });
        btnCancel.setOnAction(e -> stage.close());

        Utils.showDialog(stage, Utils.dialogRoot(valueLabel, slider, Utils.buttonRow(btnYes, btnCancel)), 300, 180);
    }
}