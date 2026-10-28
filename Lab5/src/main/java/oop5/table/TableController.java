package oop5.ui.table;

import javafx.stage.Stage;
import oop5.shape.Shape;
import oop5.ui.DrawingCanvas;
import oop5.ui.MyEditor;

import java.util.ArrayList;
import java.util.List;

public class TableController {
    private final MyEditor editor;
    private final DrawingCanvas canvas;
    private final MyTable table;

    public TableController(Stage owner, MyEditor editor, DrawingCanvas canvas) {
        this.editor = editor;
        this.canvas = canvas;
        this.table = new MyTable(owner, this::onRowSelected, this::onRowDelete);
        editor.addListener(this::onEditorChanged);   // Observer
    }

    public void toggle() {
        if (table.isShowing()) {
            hide();
        } else {
            refresh();
            table.show();
        }
    }

    public boolean isShowing() {
        return table.isShowing();
    }

    public void hide() {
        table.hide();
        editor.selectShape(-1);
        canvas.redraw();
    }

    private void refresh() {
        List<MyTable.Row> rows = new ArrayList<>();
        for (int i = 0; i < editor.getCount(); i++) {
            Shape s = editor.getShapeAt(i);
            rows.add(new MyTable.Row(editor.getTypeAt(i).getTitle(),
                    (int) Math.round(s.getX1()), (int) Math.round(s.getY1()),
                    (int) Math.round(s.getX2()), (int) Math.round(s.getY2())));
        }
        table.setRows(rows);
    }

    private void onEditorChanged() {
        refresh();
        canvas.redraw();
    }

    private void onRowSelected(int index) {
        editor.selectShape(index);
        canvas.redraw();
    }

    private void onRowDelete(int index) {
        editor.remove(index);
    }
}