public class Core {
    enum Mode {
        MAP,
        SHOT
    }

    static int size = 10;
    private static int[][][] playerMap = new int[2][size][size];
    private static int[][][] playerShot = new int[2][size][size];

    public static int numConnectedPlayer = 0;
    public static int numStepPlayer = 0;

    public static void setPlayerMap(int ID, String map) {
        String[] s = map.split(" ");
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                playerMap[ID][i][j] = Integer.parseInt(s[i * size + j]);
    }

    public static String getPlayerMap(int ID, Mode mode) {
        int arr[][];
        if (mode == Mode.MAP)
            arr = playerMap[ID];
        else
            arr = playerShot[ID];

        String map = "";
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                map += arr[i][j] + " ";
        return map.trim();
    }

    public static void shot(int ID, int x, int y) {
        int enemyID = (ID + 1) % 2;
        if (playerShot[ID][x][y] != 0)
            return;
        if (playerMap[enemyID][x][y] == 0) {
            playerShot[ID][x][y] = 2;
            playerMap[enemyID][x][y] = 2;
            numStepPlayer = enemyID;
            return;
        }
        if (playerMap[enemyID][x][y] == 1) {
            playerShot[ID][x][y] = 3;
            playerMap[enemyID][x][y] = 3;
        }
    }

    public static int checkWin() {
        if (checkLooseMatrix(0))
            return 1;
        if (checkLooseMatrix(1))
            return 0;
        return -1;
    }

    public static boolean checkLooseMatrix(int ID) {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (playerMap[ID][i][j] == 1)
                    return false;
        return true;
    }
}
