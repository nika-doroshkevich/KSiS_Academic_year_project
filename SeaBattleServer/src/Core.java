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
            fillEmptyCell(enemyID, x, y);
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

    public static void fillEmptyCell(int ID, int x, int y) {
        int x1 = x, y1 = y;
        int x2 = x, y2 = y;
        //top
        while (true) {
            if (x1 == 0)
                break;
            if (playerMap[ID][x1 - 1][y1] == 1)
                return;
            if (playerMap[ID][x1 - 1][y1] == 2 || playerMap[ID][x1 - 1][y1] == 0)
                break;
            x1--;
        }
        //left
        while (true) {
            if (y1 == 0)
                break;
            if (playerMap[ID][x1][y1 - 1] == 1)
                return;
            if (playerMap[ID][x1][y1 - 1] == 2 || playerMap[ID][x1][y1 - 1] == 0)
                break;
            y1--;
        }
        //bottom
        while (true) {
            if (x2 == size - 1)
                break;
            if (playerMap[ID][x2 + 1][y2] == 1)
                return;
            if (playerMap[ID][x2 + 1][y2] == 2 || playerMap[ID][x2 + 1][y2] == 0)
                break;
            x2++;
        }
        //right
        while (true) {
            if (y2 == size - 1)
                break;
            if (playerMap[ID][x2][y2 + 1] == 1)
                return;
            if (playerMap[ID][x2][y2 + 1] == 2 || playerMap[ID][x2][y2 + 1] == 0)
                break;
            y2++;
        }

        x1--;
        x2++;
        y1--;
        y2++;
        for (int i = x1; i <= x2; i++)
            if (i >= 0 && i < size) {
                if (y1 >= 0 && y1 < size) {
                    playerMap[ID][i][y1] = 2;
                    playerShot[(ID + 1) % 2][i][y1] = 2;
                }

                if (y2 >= 0 && y2 < size) {
                    playerMap[ID][i][y2] = 2;
                    playerShot[(ID + 1) % 2][i][y2] = 2;
                }
            }

        for (int j = y1; j <= y2; j++)
            if (j >= 0 && j < size) {
                if (x1 >= 0 && x1 < size) {
                    playerMap[ID][x1][j] = 2;
                    playerShot[(ID + 1) % 2][x1][j] = 2;
                }

                if (x2 >= 0 && x2 < size) {
                    playerMap[ID][x2][j] = 2;
                    playerShot[(ID + 1) % 2][x2][j] = 2;
                }
            }
    }
}
