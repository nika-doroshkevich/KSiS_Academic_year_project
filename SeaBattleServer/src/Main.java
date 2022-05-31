import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    static PlayerThreadController p1 = null;
    static PlayerThreadController p2 = null;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(5000)) {
            System.out.println("Server Socket Created");

            while (!server.isClosed()) {
                System.out.println("wait");
                Socket client = server.accept();
                System.out.println("connect");
                if (p1 == null)
                    p1 = new PlayerThreadController(client);
                else
                    p2 = new PlayerThreadController(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
