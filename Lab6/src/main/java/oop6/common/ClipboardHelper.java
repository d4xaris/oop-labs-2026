package oop6.common;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;

public class ClipboardHelper {

    public static void put(String text) throws Exception {
        for (int i = 0; ; i++) {
            try {
                Toolkit.getDefaultToolkit().getSystemClipboard()
                        .setContents(new StringSelection(text), null);
                return;
            } catch (IllegalStateException e) {
                if (i == 4) throw e;
                Thread.sleep(100);
            }
        }
    }

    public static String get() throws Exception {
        for (int i = 0; ; i++) {
            try {
                return (String) Toolkit.getDefaultToolkit().getSystemClipboard()
                        .getData(DataFlavor.stringFlavor);
            } catch (IllegalStateException e) {
                if (i == 4) throw e;
                Thread.sleep(100);
            }
        }
    }
}