import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameGraphic extends JPanel {
    Image black, enemyShot, miss, ship, shot, water;
    {
        try {
            black = ImageIO.read(GameGraphic.class.getResourceAsStream("black.png"));
            enemyShot = ImageIO.read(GameGraphic.class.getResourceAsStream("enemyShot.png"));
            miss = ImageIO.read(GameGraphic.class.getResourceAsStream("miss.png"));
            ship = ImageIO.read(GameGraphic.class.getResourceAsStream("ship.png"));
            shot = ImageIO.read(GameGraphic.class.getResourceAsStream("shot.png"));
            water = ImageIO.read(GameGraphic.class.getResourceAsStream("water.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        int size = Math.min(getWidth() / 2, getHeight());
        BufferedImage img = new BufferedImage(2 * size, size, BufferedImage.TYPE_4BYTE_ABGR);
        int[][] map = ReceivingObject.getHeroMap();
        if (map == null)
            return;
        double cellSize = size / map.length;
        Graphics imgG = img.createGraphics();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                switch (map[i][j]) {
                    case 0:
                        drawImage(imgG, j * cellSize, i * cellSize, cellSize, water);
                        break;
                    case 1:
                        drawImage(imgG, j * cellSize, i * cellSize, cellSize, ship);
                        break;
                    case 2:
                        drawImage(imgG, j * cellSize, i * cellSize, cellSize, miss);
                        break;
                    case 3:
                        drawImage(imgG, j * cellSize, i * cellSize, cellSize, shot);
                        break;
                    default:
                        drawImage(imgG, j * cellSize, i * cellSize, cellSize, black);
                }
            }
        }
        map = ReceivingObject.getEnemyMap();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                switch (map[i][j]) {
                    case 0:
                        drawImage(imgG, j * cellSize + size, i * cellSize, cellSize, water);
                        break;
                    case 2:
                        drawImage(imgG, j * cellSize + size, i * cellSize, cellSize, miss);
                        break;
                    case 3:
                        drawImage(imgG, j * cellSize, i * cellSize, cellSize, enemyShot);
                        break;
                    default:
                        drawImage(imgG, j * cellSize + size, i * cellSize, cellSize, black);
                }
            }
        }
        ReceivingObject.size = size;
        ReceivingObject.heightTop = (getHeight() - size) / 2;
        ReceivingObject.widthLeft = (getWidth() - 2 * size) / 2;
        g.drawImage(img, (getWidth() - 2 * size) / 2, (getHeight() - size) / 2, null);
    }

    private void drawImage(Graphics imgG, double x, double y, double cellSize, Image img) {
        imgG.drawImage(img,
                (int) x, (int) y, (int) (x + cellSize), (int) (y + cellSize),
                0, 0, img.getWidth(null), img.getHeight(null),
                null);
    }
}
