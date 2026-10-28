package oop3;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oop4.ui.DrawingCanvas;
import oop4.ui.MyEditor;
import oop4.ui.ShapeToolBar;
import oop4.ui.ShapeType;
import oop4.ui.Utils;

// Lab4, Ж = 29 (непарний): глобальний статичний об'єкт класу MyEditor
// Кольори та стилі фігур - як у Lab3 (Ж = 30)
// "Гумовий" слід усіх фігур - пунктирна лінія

public class Main extends Application {
    private static final MyEditor editor = new MyEditor();

    private DrawingCanvas canvas;
    private ShapeToolBar toolBar;
    private Menu objectsMenu;
    private final ToggleGroup menuGroup = new ToggleGroup();
    private final RadioMenuItem[] items = new RadioMenuItem[ShapeType.values().length];

    @Override
    public void start(Stage stage) {
        MenuItem exitItem = new MenuItem("Вихід");
        exitItem.setOnAction(e -> Platform.exit());
        Menu fileMenu = new Menu("Файл", null, exitItem);

        objectsMenu = new Menu("Об'єкти");
        for (ShapeType t : ShapeType.values()) {
            items[t.ordinal()] = Utils.radioItem(objectsMenu, menuGroup, t.getTitle(), () -> select(t));
        }
        objectsMenu.setOnShowing(e -> items[editor.getType().ordinal()].setSelected(true));

        MenuItem aboutItem = new MenuItem("Про програму");
        aboutItem.setOnAction(e -> new Alert(Alert.AlertType.INFORMATION, "Lab4 - Graphic editor").showAndWait());
        Menu helpMenu = new Menu("Довідка", null, aboutItem);

        MenuBar menuBar = new MenuBar(fileMenu, objectsMenu, helpMenu);

        toolBar = new ShapeToolBar(this::select);
        canvas = new DrawingCanvas(600, 400, editor);

        BorderPane root = new BorderPane();
        root.setTop(new VBox(menuBar, toolBar));
        root.setCenter(new Pane(canvas));

        select(ShapeType.POINT);

        stage.setTitle("Lab4");
        stage.setScene(new Scene(root, 600, 460));
        stage.show();
    }

    private void select(ShapeType t) {
        editor.start(t);
        items[t.ordinal()].setSelected(true);
        toolBar.select(t);
    }

    public static void main(String[] args) {
        launch(args);
    }
}