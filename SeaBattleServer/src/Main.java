import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class Main {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(5000)) {
            System.out.println("Server Socket Created");

            while (!server.isClosed()) {
                System.out.println("wait");
                Socket client = server.accept();
                System.out.println("connect");
                new PlayerThreadController(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
