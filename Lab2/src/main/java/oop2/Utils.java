import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

public class Utils {

    public static void solidStroke(GraphicsContext gc, Color color) {
        gc.setStroke(color);
        gc.setLineWidth(1);
    }

    public static void clear(GraphicsContext gc, double width, double height) {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);
    }

    public static RadioMenuItem radioItem(Menu menu, ToggleGroup group, String title, Runnable onSelect) {
        RadioMenuItem item = new RadioMenuItem(title);
        item.setToggleGroup(group);
        item.setOnAction(e -> onSelect.run());
        menu.getItems().add(item);
        return item;
    }
}