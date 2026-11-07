package oop6.common;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Connection implements Closeable {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
    }

    public static Connection connect(String host, int port) throws IOException {
        return new Connection(new Socket(host, port));
    }

    public void send(String message) {
        out.println(message);
    }

    public String receive() throws IOException {
        return in.readLine();
    }

    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException ignored) {
        }
    }
}