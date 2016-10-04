package frames;

import core.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Mateusz on 21.05.2016.
 * Project Bricks
 */
class OptionsFrame extends JDialog {
    OptionsFrame() {
        setSize(350, 190);
        setLocationRelativeTo(null);
        setResizable(false);
        setModal(true);
        setTitle("Opcje");

        JLabel title = new JLabel("Wybierz opcje:", SwingConstants.CENTER);
        JPanel optionsGridLayout = new JPanel(new GridLayout(3, 2));
        JLabel boardSize = new JLabel("Rozmiar planszy: ", SwingConstants.CENTER);
        JLabel firstPlayerColorLabel = new JLabel("Kolor pierwszego gracza: ", SwingConstants.CENTER);
        JLabel secondPlayerColorLabel = new JLabel("Kolor drugiego gracza: ", SwingConstants.CENTER);

        JButton chooseFirstPlayerColorButton = new JButton("Wybierz");
        chooseFirstPlayerColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(null,"Wybierz kolor pierwszego gracza",playerFirstColor);
                if(newColor!=null) {
                    playerFirstColor = newColor;
                }
            }
        });
        JButton chooseSecondPlayerColorButton = new JButton("Wybierz");
        chooseSecondPlayerColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(null,"Wybierz kolor drugiego gracza",playerSecondColor);
                if(newColor!=null) {
                    playerSecondColor = newColor;
                }
            }
        });

        boardSizeComboBox = new JComboBox<>();
        boardSizeComboBox.addItem("3x3");
        boardSizeComboBox.addItem("5x5");
        boardSizeComboBox.addItem("7x7");
        boardSizeComboBox.addItem("9x9");
        boardSizeComboBox.addItem("11x11");
        boardSizeComboBox.addItem("13x13");
        optionsGridLayout.add(boardSize);
        optionsGridLayout.add(boardSizeComboBox);
        optionsGridLayout.add(firstPlayerColorLabel);
        optionsGridLayout.add(chooseFirstPlayerColorButton);
        optionsGridLayout.add(secondPlayerColorLabel);
        optionsGridLayout.add(chooseSecondPlayerColorButton);
        boardSizeComboBox.setEnabled(true);

        boardSizeComboBox.addActionListener(e -> {
            try {
                BoardSize = Integer.parseInt(boardSizeComboBox.getSelectedItem().toString().split("x")[0]);
            }
            catch (Exception excIntegerParse) {
                BoardSize = 5;
            }
        });

        JPanel mainBorderLayout = new JPanel(new BorderLayout());
        mainBorderLayout.add(optionsGridLayout, BorderLayout.CENTER);
        mainBorderLayout.add(title, BorderLayout.NORTH);

        JButton saveChanges = new JButton("Zapisz Zmiany");

        saveChanges.addActionListener(e -> dispose());

        mainBorderLayout.add(saveChanges,BorderLayout.SOUTH);

        add(mainBorderLayout);
    }

    public Settings showDialog() {
        int[] values = new int[3];
        setVisible(true);
        return new Settings(BoardSize,playerFirstColor,playerSecondColor);
    }

    private int BoardSize = 5;

    private Color playerFirstColor = new Color(69, 136, 58);

    private Color playerSecondColor = new Color(238, 44, 44);

    private JComboBox<String> boardSizeComboBox;
    private boolean isPlayPressed = false;
}
