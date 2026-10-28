package oop5.ui.table;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.List;
import java.util.function.Function;
import java.util.function.IntConsumer;

public class MyTable {
    public record Row(String name, int x1, int y1, int x2, int y2) {
    }

    private final Stage stage = new Stage();
    private final TableView<Row> table = new TableView<>();

    public MyTable(Stage owner, IntConsumer onSelect, IntConsumer onDelete) {
        stage.initOwner(owner);   // вікно закривається разом з головним
        stage.setTitle("Таблиця графічних об'єктів");

        TableColumn<Row, String> nameCol = new TableColumn<>("Назва");
        nameCol.setCellValueFactory(d -> new SimpleObjectProperty<>(d.getValue().name()));
        nameCol.setPrefWidth(160);

        table.getColumns().add(nameCol);
        table.getColumns().add(intCol("x1", Row::x1));
        table.getColumns().add(intCol("y1", Row::y1));
        table.getColumns().add(intCol("x2", Row::x2));
        table.getColumns().add(intCol("y2", Row::y2));

        table.getSelectionModel().selectedIndexProperty()
                .addListener((o, oldV, newV) -> onSelect.accept(newV.intValue()));

        Button deleteButton = new Button("Видалити");
        deleteButton.setOnAction(e -> deleteSelected(onDelete));
        table.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DELETE) {
                deleteSelected(onDelete);
            }
        });

        BorderPane root = new BorderPane(table);
        BorderPane.setMargin(deleteButton, new Insets(6));
        root.setBottom(deleteButton);

        stage.setScene(new Scene(root, 460, 320));
    }

    private TableColumn<Row, Integer> intCol(String title, Function<Row, Integer> getter) {
        TableColumn<Row, Integer> col = new TableColumn<>(title);
        col.setCellValueFactory(d -> new SimpleObjectProperty<>(getter.apply(d.getValue())));
        col.setPrefWidth(70);
        return col;
    }

    private void deleteSelected(IntConsumer onDelete) {
        int index = table.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            onDelete.accept(index);
        }
    }

    public void setRows(List<Row> rows) {
        table.setItems(FXCollections.observableArrayList(rows));
    }

    public void show() {
        stage.show();
    }

    public void hide() {
        stage.hide();
    }

    public boolean isShowing() {
        return stage.isShowing();
    }
}