import javax.swing.*;

public class StatusPanel extends JPanel {
    JLabel heroStatusLabel = new JLabel("");

    public StatusPanel() {
        add(heroStatusLabel);
    }

    public void refresh() {
        String s = "";
        if (ReceivingObject.numPlayer == ReceivingObject.numStepPlayer)
            s = "Your turn";
        if (ReceivingObject.numPlayer != ReceivingObject.numStepPlayer)
            s = "Enemy's turn";
        if (ReceivingObject.isWin != -1) {
            if (ReceivingObject.isWin == 0)
                s = "You lose";
            else
                s = "You win";
        }
        if (ReceivingObject.stateController == 0)
            s = "Set ships: 1 - one-deck, 2 - two-deck, 3 - three-deck, 4 - four-deck, q - turn the ship";
        heroStatusLabel.setText(s);
    }
}
