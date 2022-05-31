import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
        boolean isActive = true;
        while (isActive) {
            try {
                System.out.println("Start thread " + ID);
                String[] command = in.readLine().split(":");
                System.out.println("read " + Arrays.toString(command));
                control(command);
                while (Core.numConnectedPlayer < 2) {
                    Thread.sleep(2);
                }
                System.out.println(ID + "end wait 2-nd player");
                int win = Core.checkWin();
                out.flush();
                if (win == -1)
                    out.writeBytes("game:" +
                            Core.getPlayerMap(ID, Core.Mode.MAP) + ":" +
                            Core.getPlayerMap(ID, Core.Mode.SHOT) +
                            ":" + ID + ":" + Core.numStepPlayer + "\n");
                else
                    out.writeBytes("win:" + ID + ":" + win + ":" +
                            Core.getPlayerMap(ID, Core.Mode.MAP) + ":" +
                            Core.getPlayerMap(ID, Core.Mode.SHOT) + "\n");


            } catch (IOException e) {
                isActive = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {

            }
        }
    }

    public void control(String[] command) throws IOException {
        switch (command[0]) {
            case "start":
                Core.setPlayerMap(ID, command[1]);
                Core.numConnectedPlayer++;
                break;
            case "step":
                Core.shot(ID, Integer.parseInt(command[2]), Integer.parseInt(command[1]));
                PlayerThreadController p = (Main.p1 == this) ? Main.p2 : Main.p1;
                p.out.flush();
                int win = Core.checkWin();
                if (win == -1)
                    p.out.writeBytes("game:" +
                            Core.getPlayerMap(p.ID, Core.Mode.MAP) + ":" +
                            Core.getPlayerMap(p.ID, Core.Mode.SHOT) +
                            ":" + p.ID + ":" + Core.numStepPlayer + "\n");
                else
                    p.out.writeBytes("win:" + p.ID + ":" + win + ":" +
                            Core.getPlayerMap(p.ID, Core.Mode.MAP) + ":" +
                            Core.getPlayerMap(p.ID, Core.Mode.SHOT) + "\n");

                break;
        }
    }
}
