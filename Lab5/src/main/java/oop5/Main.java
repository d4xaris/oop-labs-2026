package oop5;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oop5.ui.DrawingCanvas;
import oop5.bar.EditorMenuBar;
import oop5.file.FileController;
import oop5.ui.MyEditor;
import oop5.bar.ShapeToolBar;
import oop5.ui.ShapeType;
import oop5.table.TableController;

// Lab5, Ж = 29 (непарний): MyEditor - Singleton Меєрса
// Кольори та стилі фігур - як у Lab4

public class Main extends Application {
    private static final MyEditor editor = MyEditor.getInstance();

    private EditorMenuBar menuBar;
    private ShapeToolBar toolBar;

    @Override
    public void start(Stage stage) {
        DrawingCanvas canvas = new DrawingCanvas(600, 400, editor);
        TableController tables = new TableController(stage, editor, canvas);
        FileController files = new FileController(stage, editor);

        toolBar = new ShapeToolBar(this::select);
        menuBar = new EditorMenuBar(editor, this::select, files::open, files::save,
                tables::toggle, tables::isShowing);

        BorderPane root = new BorderPane();
        root.setTop(new VBox(menuBar, toolBar));
        root.setCenter(new Pane(canvas));

        select(ShapeType.POINT);

        stage.setTitle("Lab5");
        stage.setScene(new Scene(root, 600, 460));
        stage.setOnHidden(e -> tables.hide());   // таблиця закривається разом із програмою
        stage.show();
    }

    private void select(ShapeType t) {
        editor.start(t);
        menuBar.select(t);
        toolBar.select(t);
    }

    public static void main(String[] args) {
        launch(args);
    }
}