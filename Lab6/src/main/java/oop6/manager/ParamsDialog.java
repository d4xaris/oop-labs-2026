package oop6.manager;

import javax.swing.*;
import java.awt.*;

public class ParamsDialog extends JDialog {
    private final JTextField nField = new JTextField("4", 8);
    private final JTextField minField = new JTextField("-10", 8);
    private final JTextField maxField = new JTextField("10", 8);

    private boolean ok = false;
    private int n, min, max;

    public ParamsDialog(JFrame owner) {
        super(owner, "Параметри", true);
        setLayout(new BorderLayout(5, 5));

        JPanel form = new JPanel(new GridLayout(3, 2, 5, 5));
        form.add(new JLabel("n:"));
        form.add(nField);
        form.add(new JLabel("Min:"));
        form.add(minField);
        form.add(new JLabel("Max:"));
        form.add(maxField);

        JButton yes = new JButton("Так");
        JButton cancel = new JButton("Відміна");
        yes.addActionListener(e -> onYes());
        cancel.addActionListener(e -> dispose());

        JPanel buttons = new JPanel();
        buttons.add(yes);
        buttons.add(cancel);

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(yes);
        pack();
        setLocationRelativeTo(owner);
    }

    private void onYes() {
        try {
            n = Integer.parseInt(nField.getText().trim());
            min = Integer.parseInt(minField.getText().trim());
            max = Integer.parseInt(maxField.getText().trim());
            if (n < 1 || n > 12 || min > max) throw new NumberFormatException();
            ok = true;
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Некоректні дані (n від 1 до 12, Min ≤ Max)");
        }
    }

    public boolean isOk() { return ok; }
    public int getN() { return n; }
    public int getMin() { return min; }
    public int getMax() { return max; }
}