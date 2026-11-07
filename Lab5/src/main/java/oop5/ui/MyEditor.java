package oop5.ui;

import javafx.scene.canvas.GraphicsContext;
import oop5.shape.Shape;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

// Редактор об'єктів - Singleton Меєрса (Ж = 29, непарний).
// У Java аналог статичної локальної змінної C++ - holder-ідіома:
// екземпляр створюється ліниво і потокобезпечно при першому getInstance().
public class MyEditor {
    private static final int N = 130;

    private static class Holder {
        private static final MyEditor INSTANCE = new MyEditor();
    }

    public static MyEditor getInstance() {
        return Holder.INSTANCE;
    }

    private final Shape[] pcshape = new Shape[N];
    private final ShapeType[] types = new ShapeType[N];
    private int count = 0;
    private int selected = -1;
    private Shape current;
    private ShapeType type = ShapeType.POINT;
    private final List<Runnable> listeners = new ArrayList<>();

    private MyEditor() {
    }

    public void addListener(Runnable listener) {
        listeners.add(listener);
    }

    private void fireChanged() {
        for (Runnable l : listeners) {
            l.run();
        }
    }

    public void start(ShapeType type) {
        this.type = type;
    }

    public ShapeType getType() {
        return type;
    }

    public int getCount() {
        return count;
    }

    public Shape getShapeAt(int i) {
        return pcshape[i];
    }

    public ShapeType getTypeAt(int i) {
        return types[i];
    }

    public void selectShape(int index) {
        selected = (index >= 0 && index < count) ? index : -1;
    }

    public void remove(int index) {
        if (index < 0 || index >= count) {
            return;
        }
        for (int i = index; i < count - 1; i++) {
            pcshape[i] = pcshape[i + 1];
            types[i] = types[i + 1];
        }
        count--;
        pcshape[count] = null;
        types[count] = null;
        selected = -1;
        fireChanged();
    }

    private void add(ShapeType t, Shape s) {
        pcshape[count] = s;
        types[count] = t;
        count++;
    }

    public void onLBDown(double x, double y) {
        if (count >= N) {
            return;
        }
        current = type.createShape();
        current.setStart(x, y);
    }

    public void onMouseMove(double x, double y) {
        if (current != null) {
            current.setEnd(x, y);
        }
    }

    public void onLBUp(double x, double y) {
        if (current != null) {
            current.setEnd(x, y);
            add(type, current);
            current = null;
            fireChanged();
        }
    }

    public void onPaint(GraphicsContext gc) {
        for (int i = 0; i < count; i++) {
            pcshape[i].show(gc);
        }
        if (selected >= 0 && selected < count) {
            pcshape[selected].showSelected(gc);
        }
        if (current != null) {
            gc.setLineDashes(6, 4);
            current.showRubber(gc);
            gc.setLineDashes((double[]) null);
        }
    }

    public void save(File file) throws IOException {
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Shape s = pcshape[i];
            lines.add(String.format(Locale.ROOT, "%s,%d,%d,%d,%d", types[i].name(),
                    Math.round(s.getX1()), Math.round(s.getY1()),
                    Math.round(s.getX2()), Math.round(s.getY2())));
        }
        Files.write(file.toPath(), lines, StandardCharsets.UTF_8);
    }

    public void load(File file) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        ShapeType[] newTypes = new ShapeType[N];
        Shape[] newShapes = new Shape[N];
        int n = 0;
        try {
            for (String line : lines) {
                if (line.isBlank()) {
                    continue;
                }
                String[] p = line.split(",");
                if (p.length != 5 || n >= N) {
                    throw new IllegalArgumentException(line);
                }
                ShapeType t = ShapeType.valueOf(p[0].trim());
                Shape s = t.createShape();
                s.setStart(Double.parseDouble(p[1].trim()), Double.parseDouble(p[2].trim()));
                s.setEnd(Double.parseDouble(p[3].trim()), Double.parseDouble(p[4].trim()));
                newTypes[n] = t;
                newShapes[n] = s;
                n++;
            }
        } catch (IllegalArgumentException e) {
            throw new IOException("Невірний формат файлу: " + e.getMessage());
        }
        Arrays.fill(pcshape, null);
        Arrays.fill(types, null);
        System.arraycopy(newShapes, 0, pcshape, 0, n);
        System.arraycopy(newTypes, 0, types, 0, n);
        count = n;
        selected = -1;
        fireChanged();
    }
}