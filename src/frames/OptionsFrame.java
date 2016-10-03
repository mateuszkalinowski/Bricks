package frames;

import javax.swing.*;
import java.awt.*;

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
        JLabel title = new JLabel("Wybierz typ Planszy", SwingConstants.CENTER);
        JPanel optionsGridLayout = new JPanel(new GridLayout(2, 2));
        JLabel boardType = new JLabel("Kształt: ", SwingConstants.CENTER);
        JLabel boardSize = new JLabel("Rozmiar planszy: ", SwingConstants.CENTER);
        optionsGridLayout.add(boardType);
        boardTypeComboBox = new JComboBox<>();
        boardTypeComboBox.addItem("Klasyczna");
        boardTypeComboBox.addItem("Kwadratowa");
        boardTypeComboBox.addItem("Trójkątna");

        JButton execute = new JButton("Graj");
        execute.addActionListener(e -> dispose());

        boardTypeComboBox.addActionListener(e -> {
            if (boardTypeComboBox.getSelectedIndex() == 1 || boardTypeComboBox.getSelectedIndex() == 2)
                boardSizeComboBox.setEnabled(true);
            else {
                boardSizeComboBox.setEnabled(false);
                boardSizeComboBox.setSelectedIndex(1);
            }
        });
        optionsGridLayout.add(boardTypeComboBox);
        boardSizeComboBox = new JComboBox<>();
        boardSizeComboBox.addItem("5x5");
        boardSizeComboBox.addItem("7x7");
        boardSizeComboBox.addItem("9x9");
        boardSizeComboBox.addItem("11x11");
        optionsGridLayout.add(boardSize);
        optionsGridLayout.add(boardSizeComboBox);
        boardSizeComboBox.setEnabled(false);
        JPanel mainBorderLayout = new JPanel(new BorderLayout());
        mainBorderLayout.add(optionsGridLayout, BorderLayout.CENTER);
        mainBorderLayout.add(title, BorderLayout.NORTH);
        mainBorderLayout.add(execute, BorderLayout.SOUTH);
        add(mainBorderLayout);
    }

    int[] showDialog() {
        int[] values = new int[2];
        setVisible(true);
        values[0] = boardTypeComboBox.getSelectedIndex();
        values[1] = boardSizeComboBox.getSelectedIndex();

        return values;
    }

    private JComboBox<String> boardTypeComboBox;
    private JComboBox<String> boardSizeComboBox;
}
