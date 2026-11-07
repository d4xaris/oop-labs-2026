package oop6;

import oop6.manager.ManagerWindow;
import oop6.manager.MessageServer;

import javax.swing.SwingUtilities;

// Ж = 29
// Ж mod 4 = 1: варіант 1
// Lab6 (менеджер): користувач вводить n, Min, Max у діалозі,
//   програма викликає Object2 та Object3 і обмінюється з ними повідомленнями
// Object2: створює матрицю n×n цілих чисел у діапазоні Min – Max,
//   показує її у власному вікні, записує в буфер обміну у текстовому форматі
// Object3: зчитує дані з буфера обміну,
//   відображає значення детермінанту матриці у власному вікні

public class Main {
    public static void main(String[] args) throws Exception {
        MessageServer server = new MessageServer();
        server.start();
        SwingUtilities.invokeLater(() -> new ManagerWindow(server).setVisible(true));
    }
}