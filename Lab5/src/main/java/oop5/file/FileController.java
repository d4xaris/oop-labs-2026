package oop5.ui.file;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import oop5.ui.MyEditor;

import java.io.File;
import java.io.IOException;

public class FileController {
    private final Stage stage;
    private final MyEditor editor;

    public FileController(Stage stage, MyEditor editor) {
        this.stage = stage;
        this.editor = editor;
    }

    private FileChooser chooser(String title) {
        FileChooser fc = new FileChooser();
        fc.setTitle(title);
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        return fc;
    }

    public void save() {
        File file = chooser("Зберегти об'єкти").showSaveDialog(stage);
        if (file == null) {
            return;
        }
        try {
            editor.save(file);
        } catch (IOException ex) {
            error("Не вдалося зберегти: " + ex.getMessage());
        }
    }

    public void open() {
        File file = chooser("Відкрити об'єкти").showOpenDialog(stage);
        if (file == null) {
            return;
        }
        try {
            editor.load(file);
        } catch (IOException ex) {
            error("Не вдалося відкрити: " + ex.getMessage());
        }
    }

    private void error(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }
}