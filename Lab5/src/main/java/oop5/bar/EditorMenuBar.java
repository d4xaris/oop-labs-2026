package oop5.ui.bar;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import oop5.ui.MyEditor;
import oop5.ui.ShapeType;
import oop5.ui.Utils;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public class EditorMenuBar extends MenuBar {
    private final RadioMenuItem[] items = new RadioMenuItem[ShapeType.values().length];

    public EditorMenuBar(MyEditor editor, Consumer<ShapeType> onSelect, Runnable onOpen,
                         Runnable onSave, Runnable onToggleTable, BooleanSupplier tableShowing) {
        MenuItem openItem = new MenuItem("Відкрити...");
        openItem.setOnAction(e -> onOpen.run());
        MenuItem saveItem = new MenuItem("Зберегти...");
        saveItem.setOnAction(e -> onSave.run());
        CheckMenuItem tableItem = new CheckMenuItem("Таблиця об'єктів");
        tableItem.setOnAction(e -> onToggleTable.run());
        MenuItem exitItem = new MenuItem("Вихід");
        exitItem.setOnAction(e -> Platform.exit());
        Menu fileMenu = new Menu("Файл", null, openItem, saveItem,
                new SeparatorMenuItem(), tableItem, new SeparatorMenuItem(), exitItem);
        fileMenu.setOnShowing(e -> tableItem.setSelected(tableShowing.getAsBoolean()));

        ToggleGroup group = new ToggleGroup();
        Menu objectsMenu = new Menu("Об'єкти");
        for (ShapeType t : ShapeType.values()) {
            items[t.ordinal()] = Utils.radioItem(objectsMenu, group, t.getTitle(), () -> onSelect.accept(t));
        }
        objectsMenu.setOnShowing(e -> select(editor.getType()));

        MenuItem aboutItem = new MenuItem("Про програму");
        aboutItem.setOnAction(e -> new Alert(Alert.AlertType.INFORMATION, "Lab5 - Graphic editor").showAndWait());
        Menu helpMenu = new Menu("Довідка", null, aboutItem);

        getMenus().addAll(fileMenu, objectsMenu, helpMenu);
    }

    public void select(ShapeType t) {
        items[t.ordinal()].setSelected(true);
    }
}