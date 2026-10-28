import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.function.Consumer;

// B2 = 2

public class Module2 {

    public static void showDialog(Stage owner, Runnable onNext) {
        Stage stage = Utils.createDialog(owner, "Dialog 1");

        Label label = new Label("First dialog window");
        Button btnNext = new Button("Next >");
        Button btnCancel = new Button("Cancel");

        btnNext.setOnAction(e -> {
            stage.close();
            onNext.run();
        });
        btnCancel.setOnAction(e -> stage.close());

        Utils.showDialog(stage, Utils.dialogRoot(label, Utils.buttonRow(btnNext, btnCancel)), 280, 150);
    }
}