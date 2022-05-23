import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame implements Runnable {
    private GameGraphic gameGraphic = new GameGraphic();
    private ScorePanel scorePanel = new ScorePanel();

    public static void main(String[] args) {
        Thread thread = new Thread(new Frame());
        thread.start();
    }

    public Frame() {
        setSize(690, 670); //610, 670
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(gameGraphic, BorderLayout.CENTER);
        Controller controller = new Controller();
        gameGraphic.addMouseListener(controller);
        addKeyListener(controller);
        setVisible(true);
    }

    @Override
    public void run() {
        while (true) {
            gameGraphic.repaint();
            try {
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
