package oop6.manager;

import oop6.common.Protocol;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ManagerWindow extends JFrame {
    private static final String OBJ2_CLASS = "oop6.object2.Object2Main";
    private static final String OBJ3_CLASS = "oop6.object3.Object3Main";

    private final MessageServer server;
    private final JLabel status = new JLabel("Готово", SwingConstants.CENTER);
    private final JMenuItem runItem = new JMenuItem("Виконати...");

    public ManagerWindow(MessageServer server) {
        super("Lab6 - менеджер");
        this.server = server;

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                server.stopAll();
                System.exit(0);
            }
        });

        runItem.addActionListener(e -> onRun());
        JMenu menu = new JMenu("Робота");
        menu.add(runItem);
        JMenuBar bar = new JMenuBar();
        bar.add(menu);
        setJMenuBar(bar);

        add(status, BorderLayout.CENTER);
        setSize(400, 150);
        setLocation(50, 50);
    }

    private void onRun() {
        ParamsDialog dlg = new ParamsDialog(this);
        dlg.setVisible(true);
        if (!dlg.isOk()) return;

        int n = dlg.getN(), min = dlg.getMin(), max = dlg.getMax();
        status.setText("Виконується...");
        runItem.setEnabled(false);

        new Thread(() -> {
            try {
                String r2 = server.request(Protocol.OBJECT2, OBJ2_CLASS,
                        Protocol.GENERATE + " " + n + " " + min + " " + max);
                if (!Protocol.READY.equals(r2)) throw new Exception("Object2: " + r2);

                String r3 = server.request(Protocol.OBJECT3, OBJ3_CLASS, Protocol.READ);
                if (!Protocol.DONE.equals(r3)) throw new Exception("Object3: " + r3);

                SwingUtilities.invokeLater(() -> status.setText("Виконано"));
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    status.setText("Помилка");
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                });
            } finally {
                SwingUtilities.invokeLater(() -> runItem.setEnabled(true));
            }
        }).start();
    }
}