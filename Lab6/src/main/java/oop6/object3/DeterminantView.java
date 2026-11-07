package oop6.object3;

import javax.swing.*;

public class DeterminantView extends JFrame {
    private final JLabel label = new JLabel("Детермінант: -", SwingConstants.CENTER);

    public DeterminantView() {
        super("Object3 - детермінант");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(label);
        setSize(450, 150);
        setLocation(520, 250);
    }

    public void setResult(String value) {
        label.setText("Детермінант: " + value);
    }
}