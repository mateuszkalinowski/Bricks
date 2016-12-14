package frames;

import javax.swing.*;

/**
 * Created by Mateusz on 14.12.2016.
 * Project Bricks
 */
public class AutoGamesFrame extends JDialog {
    public AutoGamesFrame(JFrame owner){
        super(owner,true);
        setSize(400,600);
        JButton exitButton = new JButton("Powrót");
        exitButton.addActionListener(e -> setVisible(false));
    }
}
