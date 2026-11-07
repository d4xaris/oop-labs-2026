package oop6.object3;

import oop6.common.Client;
import oop6.common.ClipboardHelper;
import oop6.common.Protocol;

import javax.swing.SwingUtilities;

// Ж = 29, варіант 1 (Ж mod 4 = 1)
// Object3: отримує READ, зчитує матрицю з буфера обміну,
//   показує її детермінант у вікні, відповідає DONE
public class Object3Main {
    public static void main(String[] args) throws Exception {
        DeterminantView view = new DeterminantView();
        SwingUtilities.invokeAndWait(() -> view.setVisible(true));

        Client.run(Protocol.OBJECT3, msg -> {
            if (!msg.equals(Protocol.READ)) return Protocol.ERROR;

            String text = ClipboardHelper.get();
            String result = Determinant.det(Determinant.parse(text)).toString();
            SwingUtilities.invokeAndWait(() -> view.setResult(result));
            return Protocol.DONE;
        });
    }
}