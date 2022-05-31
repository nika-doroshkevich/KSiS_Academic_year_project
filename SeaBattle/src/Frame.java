import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame implements Runnable {
    private GameGraphic gameGraphic = new GameGraphic();
    private StatusPanel statusPanel = new StatusPanel();

    public static void main(String[] args) {
        Thread thread = new Thread(new Frame());
        thread.start();
        while (true) {
            ReceivingObject.getMessage();
        }
    }

    public Frame() {
        setSize(950, 670); //610, 670 //690, 670
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(statusPanel, BorderLayout.NORTH);
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
            statusPanel.refresh();
            try {
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
