package oop6.common;

import java.io.IOException;

public class Client {

    public interface Handler {
        String handle(String message) throws Exception;
    }

    public static void run(String name, Handler handler) throws IOException {
        Connection conn = Connection.connect(Protocol.HOST, Protocol.PORT);
        conn.send(Protocol.HELLO + " " + name);

        String msg;
        while ((msg = conn.receive()) != null) {
            if (msg.equals(Protocol.EXIT)) break;

            String reply;
            try {
                reply = handler.handle(msg);
            } catch (Exception e) {
                reply = Protocol.ERROR;
            }
            conn.send(reply);
        }
        conn.close();
        System.exit(0);
    }
}