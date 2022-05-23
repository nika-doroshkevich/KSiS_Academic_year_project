import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.Arrays;

public class PlayerThreadController implements Runnable {
    Socket clientDialog;
    DataInputStream in;
    DataOutputStream out;
    static int nextID = 0;
    int ID = nextID++;

    public PlayerThreadController(Socket client) {
        clientDialog = client;
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            in = new DataInputStream(clientDialog.getInputStream());
            out = new DataOutputStream(clientDialog.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                System.out.println("Start thread " + ID);
                String[] command = in.readLine().split(":");
                System.out.println("read " + Arrays.toString(command));
                control(command);
                while (Core.numConnectedPlayer < 2) {
                    Thread.sleep(2);
                    //  System.out.println(ID+" "+Core.numConnectedPlayer);
                }
                System.out.println(ID + "end wait 2-nd player");
                out.writeBytes("game:" +
                        Core.getPlayerMap(ID, Core.Mode.MAP) + ":" +
                        Core.getPlayerMap((ID + 1) % 2, Core.Mode.SHOT) +
                        ":" + ID + ":" + Core.numStepPlayer + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void control(String[] command) {
        switch (command[0]) {
            case "start":
                Core.setPlayerMap(ID, command[1]);
                Core.numConnectedPlayer++;
                break;
        }
    }
}
