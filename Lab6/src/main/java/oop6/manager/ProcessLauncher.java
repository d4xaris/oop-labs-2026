package oop6.manager;

import java.io.IOException;
import java.nio.file.Paths;

public class ProcessLauncher {
    public static void launch(String mainClass) throws IOException {
        String java = Paths.get(System.getProperty("java.home"), "bin", "java").toString();
        String cp = System.getProperty("java.class.path");
        new ProcessBuilder(java, "-cp", cp, mainClass).inheritIO().start();
    }
}