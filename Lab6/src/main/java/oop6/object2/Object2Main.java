package oop6.object2;

import oop6.common.Client;
import oop6.common.ClipboardHelper;
import oop6.common.Protocol;

import javax.swing.SwingUtilities;

// Ж = 29, варіант 1 (Ж mod 4 = 1)
// Object2: отримує GENERATE n min max, створює матрицю n×n цілих чисел,
//   показує її у вікні, записує в буфер обміну, відповідає READY
public class Object2Main {
    public static void main(String[] args) throws Exception {
        MatrixView view = new MatrixView();
        SwingUtilities.invokeAndWait(() -> view.setVisible(true));

        Client.run(Protocol.OBJECT2, msg -> {
            String[] p = msg.split(" ");
            if (!p[0].equals(Protocol.GENERATE)) return Protocol.ERROR;

            int n = Integer.parseInt(p[1]);
            int min = Integer.parseInt(p[2]);
            int max = Integer.parseInt(p[3]);

            String text = MatrixGenerator.toText(MatrixGenerator.generate(n, min, max));
            SwingUtilities.invokeAndWait(() -> view.setMatrix(text));
            ClipboardHelper.put(text);
            return Protocol.READY;
        });
    }
}