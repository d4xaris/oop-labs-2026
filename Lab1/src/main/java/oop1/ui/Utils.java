import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Utils {

    public static Stage createDialog(Stage owner, String title) {
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(owner);
        stage.setTitle(title);
        return stage;
    }

    public static HBox buttonRow(Node... buttons) {
        HBox row = new HBox(10, buttons);
        row.setAlignment(Pos.CENTER);
        return row;
    }

    public static VBox dialogRoot(Node... children) {
        VBox root = new VBox(15, children);
        root.setAlignment(Pos.CENTER);
        return root;
    }

    public static void showDialog(Stage stage, Node root, double width, double height) {
        stage.setScene(new Scene((VBox) root, width, height));
        stage.show();
    }
}