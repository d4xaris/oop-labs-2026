import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class Module3 {

    public static void showDialog(Stage owner, Runnable onBack, Consumer<String> callback) {
        Stage stage = Utils.createDialog(owner, "Dialog 2");

        Label label = new Label("Second dialog window");
        Button btnBack = new Button("< Back");
        Button btnYes = new Button("Yes");
        Button btnCancel = new Button("Cancel");

        btnBack.setOnAction(e -> {
            stage.close();
            onBack.run();
        });
        btnYes.setOnAction(e -> {
            callback.accept("Both dialog windows completed successfully!");
            stage.close();
        });
        btnCancel.setOnAction(e -> stage.close());

        Utils.showDialog(stage, Utils.dialogRoot(label, Utils.buttonRow(btnBack, btnYes, btnCancel)), 300, 150);
    }
}