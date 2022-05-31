import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class ReceivingObject {
    public static volatile int heroMap[][] = new int[10][10];
    public static volatile int enemyMap[][] = new int[10][10];
    static int masNumShip[] = new int[]{4, 3, 2, 1};

    static int size = 1;
    static int heightTop = 1;
    static int widthLeft = 1;

    static Ship ship = new Ship();

    static Socket socket;
    static DataInputStream in;
    static DataOutputStream out;

    static int numPlayer = 0; //The number of the player himself
    static int numStepPlayer = 1; //The number of the player who should be shooting now

    static int stateController = 0; //0 - can change, 1 - not

    static int isWin = -1; //0 - lose, 1 - win

    static {
        try {
            socket = new Socket("localhost", 5000);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.exit(0);
        }
    }

    public static int[][] getHeroMap() {
        return heroMap;
    }

    public static int[][] getEnemyMap() {
        return enemyMap;
    }

    public static void setShip(int x1, int y1) {
        if (masNumShip[ship.getSizeShip() - 1] <= 0)
            return;
        int x2 = (ship.getDir() == 0) ? x1 + ship.getSizeShip() - 1 : x1;
        int y2 = (ship.getDir() == 0) ? y1 : y1 + ship.getSizeShip() - 1;
        if (x2 >= heroMap.length || y2 >= heroMap.length)
            return;
        int x1Check = Math.max(x1 - 1, 0);
        int y1Check = Math.max(y1 - 1, 0);
        int x2Check = Math.min(x2 + 1, heroMap.length - 1);
        int y2Check = Math.min(y2 + 1, heroMap.length - 1);

        boolean isOk = true;
        for (int i = x1Check; i <= x2Check; i++)
            for (int j = y1Check; j <= y2Check; j++)
                isOk = isOk && heroMap[i][j] == 0;
        if (!isOk)
            return;
        for (int i = x1; i <= x2; i++)
            for (int j = y1; j <= y2; j++)
                heroMap[i][j] = 1;

        masNumShip[ship.getSizeShip() - 1]--;
    }

    public static int deleteShip(int x, int y) {
        if (x < 0 || y < 0 || x >= heroMap.length || y >= heroMap.length)
            return 0;
        if (heroMap[x][y] == 0)
            return 0;
        int d = 1;
        heroMap[x][y] = 0;
        d += deleteShip(x - 1, y);
        d += deleteShip(x + 1, y);
        d += deleteShip(x, y - 1);
        d += deleteShip(x, y + 1);

        return d;
    }

    public static boolean checkMap() {
        boolean numShip = true;
        for (int i = 0; i < masNumShip.length; i++)
            numShip = numShip && (masNumShip[i] == 0);
        return numShip;
    }

    public static String getPlayerMap() {
        String map = "";
        for (int i = 0; i < heroMap.length; i++)
            for (int j = 0; j < heroMap.length; j++)
                map += heroMap[i][j] + " ";
        return map.trim();
    }

    public static void setPlayerMap(String mapHero, String mapShot) {
        String[] s1 = mapHero.split(" ");
        String[] s2 = mapShot.split(" ");
        int size = heroMap.length;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                heroMap[i][j] = Integer.parseInt(s1[i * size + j]);
                enemyMap[i][j] = Integer.parseInt(s2[i * size + j]);
            }
    }

    public static void send(String command) {
        try {
            System.out.println("send " + command);
            out.writeBytes(command + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getMessage() {
        try {
            String[] command = in.readLine().split(":");
            System.out.println("get message " + Arrays.toString(command));
            runCommand(command);
            if (numPlayer != numStepPlayer)
                getMessage();

        } catch (IOException e) {
            System.exit(0);
        }

    }

    public static void runCommand(String[] command) {
        switch (command[0]) {
            case "game":
                setPlayerMap(command[1], command[2]);
                numPlayer = Integer.parseInt(command[3]);
                numStepPlayer = Integer.parseInt((command[4]));
                break;
            case "win":
                setPlayerMap(command[3], command[4]);
                isWin = (command[1].equals(command[2])) ? 1 : 0;

        }
    }
}

class Ship {
    private int dir = 0; //0 - horizontal, //1 - vertical
    private int sizeShip = 1;

    public int getDir() {
        return dir;
    }

    public int getSizeShip() {
        return sizeShip;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public void setSizeShip(int sizeShip) {
        this.sizeShip = sizeShip;
    }
}
