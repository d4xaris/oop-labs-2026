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
import oop3.ui.DrawingCanvas;
import oop3.ui.ShapeToolBar;
import oop3.ui.ShapeType;
import oop3.ui.Utils;

// Z = 30 (Z_lab2 + 1)
// Z mod 3 = 0: dynamic array
// Z mod 4 = 2: blue rubber trace
// Z mod 2 = 0: rectangle by two corners, ellipse from center, type shown in menu
// Z mod 5 = 0: rectangle white fill, ellipse no fill

public class Main extends Application {
    private ShapeType type = ShapeType.POINT;

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
        objectsMenu.setOnShowing(e -> items[type.ordinal()].setSelected(true));

        MenuItem aboutItem = new MenuItem("Про програму");
        aboutItem.setOnAction(e -> new Alert(Alert.AlertType.INFORMATION, "Lab3 - Graphic editor").showAndWait());
        Menu helpMenu = new Menu("Довідка", null, aboutItem);

        MenuBar menuBar = new MenuBar(fileMenu, objectsMenu, helpMenu);

        toolBar = new ShapeToolBar(this::select);
        canvas = new DrawingCanvas(600, 400);

        BorderPane root = new BorderPane();
        root.setTop(new VBox(menuBar, toolBar));
        root.setCenter(new Pane(canvas));

        select(ShapeType.POINT);

        stage.setTitle("Lab3");
        stage.setScene(new Scene(root, 600, 460));
        stage.show();
    }
    
    private void select(ShapeType t) {
        type = t;
        items[t.ordinal()].setSelected(true);
        toolBar.select(t);
        canvas.setType(t);
    }

    public static void main(String[] args) {
        launch(args);
    }
}