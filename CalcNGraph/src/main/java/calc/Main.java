package calc;

import calc.model.CalcEngine;
import calc.ui.CalcView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new CalcView(new CalcEngine()), 340, 520);
        scene.getStylesheets().add(getClass().getResource("/calc.css").toExternalForm());
        stage.setTitle("Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}