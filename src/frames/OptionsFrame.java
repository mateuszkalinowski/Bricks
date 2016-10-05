package frames;

import core.Settings;
import gfx.ColorPreview;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;

/**
 * Created by Mateusz on 21.05.2016.
 * Project Bricks
 */
class OptionsFrame extends JDialog {
    OptionsFrame() {
        setSize(350, 210);
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
                    firstPlayerColor.setColor(playerFirstColor);
                    repaint();
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
                    secondPlayerColor.setColor(playerSecondColor);
                    repaint();
                }
            }
        });

        JButton resetOptions = new JButton("Ustawienia Domyślne");
        resetOptions.addActionListener(e -> {
            BoardSize = 5;
            playerFirstColor = new Color(69,136,58);
            playerSecondColor = new Color(238,44,44);
            firstPlayerColor.setColor(playerFirstColor);
            secondPlayerColor.setColor(playerSecondColor);
            for(int i = 0; i < boardSizeComboBox.getItemCount();i++) {
                if(Integer.parseInt(boardSizeComboBox.getItemAt(i).split("x")[0])==BoardSize ) {
                    boardSizeComboBox.setSelectedIndex(i);
                }
            }

            repaint();
        });

        JPanel firstPlayerOptionsGridLayout = new JPanel(new GridLayout(1,2));
        JPanel secondPlayerOptionsGridLayout = new JPanel(new GridLayout(1,2));

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
        firstPlayerOptionsGridLayout.add(chooseFirstPlayerColorButton);
        optionsGridLayout.add(firstPlayerOptionsGridLayout);
        secondPlayerOptionsGridLayout.add(chooseSecondPlayerColorButton);
        optionsGridLayout.add(secondPlayerColorLabel);
        optionsGridLayout.add(secondPlayerOptionsGridLayout);


        firstPlayerColor = new ColorPreview(playerFirstColor);
        firstPlayerOptionsGridLayout.add(firstPlayerColor);
        secondPlayerColor = new ColorPreview(playerSecondColor);
        secondPlayerOptionsGridLayout.add(secondPlayerColor);


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

        JButton saveChanges = new JButton("Zapisz zmiany");

        saveChanges.addActionListener(e -> {
            wasSaveClicked = true;
            dispose();
        });

        JPanel southGridLayout = new JPanel(new GridLayout(2,1));
        southGridLayout.add(resetOptions);
        southGridLayout.add(saveChanges);
        mainBorderLayout.add(southGridLayout,BorderLayout.SOUTH);

        add(mainBorderLayout);
    }

    public Settings showDialog(Settings firstSettings) {
        wasSaveClicked = false;
        firstPlayerColor.setColor(firstSettings.getPlayerFirstColor());
        secondPlayerColor.setColor(firstSettings.getPlayerSecondColor());
        playerFirstColor = firstSettings.getPlayerFirstColor();
        playerSecondColor = firstSettings.getPlayerSecondColor();
        BoardSize = firstSettings.getBoardSize();

        for(int i = 0; i < boardSizeComboBox.getItemCount();i++) {
            if(Integer.parseInt(boardSizeComboBox.getItemAt(i).split("x")[0])==BoardSize ) {
                boardSizeComboBox.setSelectedIndex(i);
            }
        }
        repaint();
        setVisible(true);
        return new Settings(BoardSize,playerFirstColor,playerSecondColor);
    }

    private int BoardSize = 5;

    private Color playerFirstColor;

    private Color playerSecondColor;

    private JComboBox<String> boardSizeComboBox;
    private boolean isPlayPressed = false;

    boolean wasSaveClicked = false;
    private ColorPreview firstPlayerColor;
    private ColorPreview secondPlayerColor;
}
