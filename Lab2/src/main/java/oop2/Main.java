import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

// Z = 29
// Z mod 3 = 2: static array
// Z mod 4 = 1: red rubber trace
// Z mod 2 = 1: rectangle from center, ellipse by corners, type in title
// Z mod 5 = 4: rectangle no fill, ellipse filled
// Z mod 6 = 5: orange

import java.util.function.Supplier;

public class Main extends Application {
    private static final int N = 129;

    private final Shape[] pcshape = new Shape[N];
    private int count = 0;
    private Shape current;
    private Supplier<Shape> factory;
    private Stage stage;
    private Canvas canvas;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> Platform.exit());
        Menu fileMenu = new Menu("File", null, exitItem);

        ToggleGroup group = new ToggleGroup();
        Menu objectsMenu = new Menu("Objects");
        RadioMenuItem pointItem = Utils.radioItem(objectsMenu, group, "Point", () -> select("Point", PointShape::new));
        Utils.radioItem(objectsMenu, group, "Line", () -> select("Line", LineShape::new));
        Utils.radioItem(objectsMenu, group, "Rectangle", () -> select("Rectangle", RectShape::new));
        Utils.radioItem(objectsMenu, group, "Ellipse", () -> select("Ellipse", EllipseShape::new));

        MenuItem aboutItem = new MenuItem("About");
        aboutItem.setOnAction(e -> new Alert(Alert.AlertType.INFORMATION, "Lab2 - Graphic editor").showAndWait());
        Menu helpMenu = new Menu("Help", null, aboutItem);

        MenuBar menuBar = new MenuBar(fileMenu, objectsMenu, helpMenu);

        canvas = new Canvas(600, 400);
        Pane pane = new Pane(canvas);

        canvas.setOnMousePressed(e -> {
            if (count >= N) {
                return;
            }
            current = factory.get();
            current.setStart(e.getX(), e.getY());
            redraw();
        });
        canvas.setOnMouseDragged(e -> {
            if (current != null) {
                current.setEnd(e.getX(), e.getY());
                redraw();
            }
        });
        canvas.setOnMouseReleased(e -> {
            if (current != null) {
                current.setEnd(e.getX(), e.getY());
                pcshape[count++] = current;
                current = null;
                redraw();
            }
        });

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(pane);

        pointItem.setSelected(true);
        select("Point", PointShape::new);

        stage.setScene(new Scene(root, 600, 430));
        stage.show();
        redraw();
    }

    private void select(String title, Supplier<Shape> shapeFactory) {
        factory = shapeFactory;
        stage.setTitle("Lab2 - " + title);
    }

    private void redraw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Utils.clear(gc, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < count; i++) {
            pcshape[i].show(gc);
        }
        if (current != null) {
            current.showRubber(gc);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}