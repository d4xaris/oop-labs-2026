package oop6.manager;

import oop6.common.Protocol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    private static final String OBJ2_CLASS = "oop6.object2.Object2Main";
    private static final String OBJ3_CLASS = "oop6.object3.Object3Main";

    private static final MessageServer server = new MessageServer();
    private static JLabel status;

    public static void main(String[] args) throws Exception {
        server.start();
        SwingUtilities.invokeLater(Main::createWindow);
    }

    private static void createWindow() {
        JFrame frame = new JFrame("Lab6 - менеджер");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                server.stopAll();
                System.exit(0);
            }
        });

        status = new JLabel("Готово", SwingConstants.CENTER);

        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("Робота");
        JMenuItem run = new JMenuItem("Виконати...");
        run.addActionListener(e -> onRun(frame, run));
        menu.add(run);
        bar.add(menu);
        frame.setJMenuBar(bar);

        frame.add(status, BorderLayout.CENTER);
        frame.setSize(400, 150);
        frame.setLocation(50, 50);
        frame.setVisible(true);
    }

    private static void onRun(JFrame frame, JMenuItem runItem) {
        ParamsDialog dlg = new ParamsDialog(frame);
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
                    JOptionPane.showMessageDialog(frame, ex.getMessage());
                });
            } finally {
                SwingUtilities.invokeLater(() -> runItem.setEnabled(true));
            }
        }).start();
    }
}