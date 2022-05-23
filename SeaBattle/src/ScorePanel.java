import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    JLabel heroScoreLabel = new JLabel("Score:");

    public ScorePanel() {
        add(heroScoreLabel);
    }
}
