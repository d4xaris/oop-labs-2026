import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.function.Consumer;

// B2 = 2
public class Module2 {

    public static void showDialogs(Stage owner, Consumer<String> callback) {
        showFirstDialog(owner, callback);
    }

    private static void showFirstDialog(Stage owner, Consumer<String> callback) {
        Stage stage = Utils.createDialog(owner, "Dialog 1");

        Label label = new Label("First dialog window");
        Button btnNext = new Button("Next >");
        Button btnCancel = new Button("Cancel");

        btnNext.setOnAction(e -> {
            stage.close();
            showSecondDialog(owner, callback);
        });
        btnCancel.setOnAction(e -> stage.close());

        Utils.showDialog(stage, Utils.dialogRoot(label, Utils.buttonRow(btnNext, btnCancel)), 280, 150);
    }

    private static void showSecondDialog(Stage owner, Consumer<String> callback) {
        Stage stage = Utils.createDialog(owner, "Dialog 2");

        Label label = new Label("Second dialog window");
        Button btnBack = new Button("< Back");
        Button btnYes = new Button("Yes");
        Button btnCancel = new Button("Cancel");

        btnBack.setOnAction(e -> {
            stage.close();
            showFirstDialog(owner, callback);
        });
        btnYes.setOnAction(e -> {
            callback.accept("Both dialog windows completed successfully!");
            stage.close();
        });
        btnCancel.setOnAction(e -> stage.close());

        Utils.showDialog(stage, Utils.dialogRoot(label, Utils.buttonRow(btnBack, btnYes, btnCancel)), 300, 150);
    }
}