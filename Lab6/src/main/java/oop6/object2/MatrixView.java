package oop6.object2;

import javax.swing.*;
import java.awt.*;

public class MatrixView extends JFrame {
    private final JTextArea area = new JTextArea();

    public MatrixView() {
        super("Object2 - матриця");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        add(new JScrollPane(area));
        setSize(450, 400);
        setLocation(50, 250);
    }

    public void setMatrix(String text) {
        area.setText(text);
    }
}