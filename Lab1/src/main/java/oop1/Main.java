import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private Label outputLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lab1 - Variant 29");

        MenuBar menuBar = new MenuBar();
        Menu menuWork = new Menu("Work");

        MenuItem itemWork1 = new MenuItem("Work 1");
        MenuItem itemWork2 = new MenuItem("Work 2");

        menuWork.getItems().addAll(itemWork1, itemWork2);
        menuBar.getMenus().add(menuWork);

        outputLabel = new Label("Result will appear here");
        outputLabel.setStyle("-fx-font-size: 16px; -fx-padding: 20px;");

        itemWork1.setOnAction(e -> Module1.showDialog(primaryStage, this::updateText));
        itemWork2.setOnAction(e -> showFirst(primaryStage));

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(new VBox(outputLabel));

        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }

    private void showFirst(Stage owner) {
        Module2.showDialog(owner, () -> showSecond(owner));
    }

    private void showSecond(Stage owner) {
        Module3.showDialog(owner, () -> showFirst(owner), this::updateText);
    }

    public void updateText(String text) {
        outputLabel.setText(text);
    }

    public static void main(String[] args) {
        launch(args);
    }
}