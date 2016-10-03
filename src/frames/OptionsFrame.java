package frames;

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
        setSize(300, 150);
        setLocationRelativeTo(null);
        setModal(true);
        setTitle("Opcje");
        JLabel title = new JLabel("Wybierz tryb gry:", SwingConstants.CENTER);
        JPanel optionsGridLayout = new JPanel(new GridLayout(2, 2));
        JLabel boardType = new JLabel("Typ gry: ", SwingConstants.CENTER);
        JLabel boardSize = new JLabel("Rozmiar planszy: ", SwingConstants.CENTER);
        optionsGridLayout.add(boardType);
        gameTypeComboBox = new JComboBox<>();
        gameTypeComboBox.addItem("Jednoosobowa");
        gameTypeComboBox.addItem("Dwuosobowa");
        gameTypeComboBox.setSelectedIndex(0);

        JButton execute = new JButton("Graj");
        execute.addActionListener(e -> dispose());
        execute.addActionListener(e -> {
            isPlayPressed = true;
            dispose();
        });
        optionsGridLayout.add(gameTypeComboBox);
        boardSizeComboBox = new JComboBox<>();
        boardSizeComboBox.addItem("5x5");
        boardSizeComboBox.addItem("7x7");
        boardSizeComboBox.addItem("9x9");
        boardSizeComboBox.addItem("11x11");
        optionsGridLayout.add(boardSize);
        optionsGridLayout.add(boardSizeComboBox);
        boardSizeComboBox.setEnabled(true);
        JPanel mainBorderLayout = new JPanel(new BorderLayout());
        mainBorderLayout.add(optionsGridLayout, BorderLayout.CENTER);
        mainBorderLayout.add(title, BorderLayout.NORTH);
        mainBorderLayout.add(execute, BorderLayout.SOUTH);
        add(mainBorderLayout);
    }

    int[] showDialog() {
        int[] values = new int[3];
        setVisible(true);
        values[0] = gameTypeComboBox.getSelectedIndex();
        values[1] = boardSizeComboBox.getSelectedIndex();
        values[2] = isPlayPressed ? 1 : 0;
        return values;
    }

    private JComboBox<String> gameTypeComboBox;
    private JComboBox<String> boardSizeComboBox;
    private boolean isPlayPressed = false;
}
