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
}
