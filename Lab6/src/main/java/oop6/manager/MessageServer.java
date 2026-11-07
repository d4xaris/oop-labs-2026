package oop6.manager;

import oop6.common.Connection;
import oop6.common.Protocol;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageServer {
    private final Map<String, Connection> clients = new ConcurrentHashMap<>();
    private ServerSocket server;

    public void start() throws IOException {
        server = new ServerSocket(Protocol.PORT);
        Thread t = new Thread(this::acceptLoop);
        t.setDaemon(true);
        t.start();
    }

    private void acceptLoop() {
        while (!server.isClosed()) {
            try {
                Connection c = new Connection(server.accept());
                String hello = c.receive();
                String prefix = Protocol.HELLO + " ";
                if (hello != null && hello.startsWith(prefix)) {
                    clients.put(hello.substring(prefix.length()), c);
                } else {
                    c.close();
                }
            } catch (IOException e) {
                if (server.isClosed()) break;
            }
        }
    }

    // Надсилає повідомлення програмі, за потреби запускає її. Повертає відповідь.
    public String request(String name, String mainClass, String message) throws Exception {
        for (int attempt = 0; attempt < 2; attempt++) {
            Connection c = getOrLaunch(name, mainClass);
            try {
                c.send(message);
                String reply = c.receive();
                if (reply != null) return reply;
            } catch (IOException ignored) {
            }
            clients.remove(name);
            c.close();
        }
        throw new IOException("Немає зв'язку з " + name);
    }

    private Connection getOrLaunch(String name, String mainClass) throws Exception {
        Connection c = clients.get(name);
        if (c != null) return c;

        ProcessLauncher.launch(mainClass);
        for (int i = 0; i < 100; i++) {
            c = clients.get(name);
            if (c != null) return c;
            Thread.sleep(100);
        }
        throw new IOException("Програма " + name + " не запустилась");
    }

    public void stopAll() {
        for (Connection c : clients.values()) {
            c.send(Protocol.EXIT);
            c.close();
        }
        clients.clear();
        try {
            if (server != null) server.close();
        } catch (IOException ignored) {
        }
    }
}