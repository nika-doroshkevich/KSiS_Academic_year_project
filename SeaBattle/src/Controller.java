import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Controller implements MouseListener, KeyListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        if (ReceivingObject.isWin != -1)
            return;
        int x = e.getX();
        int y = e.getY();
        x -= ReceivingObject.widthLeft;
        y -= ReceivingObject.heightTop;
        x /= ReceivingObject.size / 10;
        y /= ReceivingObject.size / 10;
        if (x < 10 && ReceivingObject.stateController == 0) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                ReceivingObject.setShip(y, x);
            } else {
                int d = ReceivingObject.deleteShip(y, x);
                ReceivingObject.masNumShip[d - 1]++;
            }
            return;
        }
        if (ReceivingObject.stateController == 0)
            return;
        if (ReceivingObject.numPlayer != ReceivingObject.numStepPlayer)
            return;
        x -= 10;
        //click for second field
        ReceivingObject.send("step:" + x + ":" + y);
        System.out.println(x + " " + y);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (ReceivingObject.stateController != 0)
            return;
        System.out.println(e.getKeyCode());
        if (e.getKeyCode() == '1')
            ReceivingObject.ship.setSizeShip(1);
        if (e.getKeyCode() == '2')
            ReceivingObject.ship.setSizeShip(2);
        if (e.getKeyCode() == '3')
            ReceivingObject.ship.setSizeShip(3);
        if (e.getKeyCode() == '4')
            ReceivingObject.ship.setSizeShip(4);
        if (e.getKeyCode() == 81)
            ReceivingObject.ship.setDir((ReceivingObject.ship.getDir() + 1) % 2);
        if (e.getKeyCode() == 10)
            if (ReceivingObject.checkMap()) {
                ReceivingObject.stateController = 1;
                ReceivingObject.send("start:" + ReceivingObject.getPlayerMap());
            } else
                System.out.println("Error");
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
