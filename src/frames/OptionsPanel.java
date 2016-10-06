package frames;

import core.Bricks;
import core.Settings;
import gfx.ColorPreview;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Mateusz on 21.05.2016.
 * Project Bricks
 */
class OptionsPanel extends JPanel {
    OptionsPanel(Settings firstSettings) {
        JLabel title = new JLabel("Dostosuj ustawienia:", SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
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
            isSound = true;
            soundIsCheckBox.setSelected(true);
            volume = 0;
            soundVolumeSlider.setValue(volume);
            for(int i = 0; i < boardSizeComboBox.getItemCount();i++) {
                if(Integer.parseInt(boardSizeComboBox.getItemAt(i).split("x")[0])==BoardSize ) {
                    boardSizeComboBox.setSelectedIndex(i);
                }
            }
            debugMode = false;
            debugModeCheckBox.setSelected(false);

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


        firstPlayerColor = new ColorPreview(playerFirstColor);
        secondPlayerColor = new ColorPreview(playerSecondColor);


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
        //mainBorderLayout.add(optionsGridLayout, BorderLayout.CENTER);
        mainBorderLayout.add(title, BorderLayout.NORTH);

        JButton saveChanges = new JButton("Zapisz zmiany");

        saveChanges.addActionListener(e -> {
            Bricks.mainFrame.setSettings(new Settings(BoardSize,playerFirstColor,playerSecondColor,isSound,volume,debugMode));
            Bricks.mainFrame.backToMenu();
        });

        JPanel southGridLayout = new JPanel(new GridLayout(2,1));
        southGridLayout.add(resetOptions);
        southGridLayout.add(saveChanges);
        mainBorderLayout.add(southGridLayout,BorderLayout.SOUTH);


        JPanel soundOptionsGridLayout = new JPanel(new GridLayout(2,2));

        JLabel soundIsLabel = new JLabel("Dźwięki:");
        soundIsLabel.setHorizontalAlignment(JLabel.CENTER);
        soundIsCheckBox = new JCheckBox();
        JLabel soundVolumeLabel = new JLabel("Głośność:");
        soundVolumeLabel.setHorizontalAlignment(JLabel.CENTER);
        soundVolumeSlider = new JSlider(JSlider.HORIZONTAL,-80,6,0);


        soundIsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(soundIsCheckBox.isSelected())
                    isSound = true;
                else
                    isSound = false;
            }
        });

        soundVolumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                volume = soundVolumeSlider.getValue();
            }
        });

        debugModeCheckBox = new JCheckBox();
        JLabel debugModeLabel = new JLabel("Tryb Debugowania: ");
        debugModeLabel.setHorizontalAlignment(JLabel.CENTER);

        debugModeCheckBox.addActionListener(e -> {
            if(debugModeCheckBox.isSelected())
                debugMode = true;
            else
                debugMode = false;
        });


        mainGridLayout = new JPanel(new GridLayout(15,2));
        JLabel generalSectionLabel = new JLabel("Ogólne:");
        generalSectionLabel.setFont(new Font("Comic Sans MS", Font.BOLD,20));
        mainGridLayout.add(new JLabel());
        mainGridLayout.add(new JLabel());
        mainGridLayout.add(generalSectionLabel);
        mainGridLayout.add(new JLabel());


        mainGridLayout.add(boardSize);
        mainGridLayout.add(boardSizeComboBox);
        mainGridLayout.add(firstPlayerColorLabel);
        firstPlayerOptionsGridLayout.add(chooseFirstPlayerColorButton);
        mainGridLayout.add(firstPlayerOptionsGridLayout);
        secondPlayerOptionsGridLayout.add(chooseSecondPlayerColorButton);
        mainGridLayout.add(secondPlayerColorLabel);
        mainGridLayout.add(secondPlayerOptionsGridLayout);
        mainGridLayout.add(new JLabel());
        mainGridLayout.add(new JLabel());
        mainGridLayout.add(new JSeparator());
        mainGridLayout.add(new JSeparator());
        JLabel soundSectionLabel = new JLabel("Dźwięk:");
        soundSectionLabel.setFont(new Font("Comic Sans MS", Font.BOLD,20));
        mainGridLayout.add(soundSectionLabel);
        mainGridLayout.add(new JLabel());
        mainGridLayout.add(soundIsLabel);
        mainGridLayout.add(soundIsCheckBox);
        mainGridLayout.add(soundVolumeLabel);
        mainGridLayout.add(soundVolumeSlider);
        mainGridLayout.add(new JLabel());
        mainGridLayout.add(new JLabel());
        mainGridLayout.add(new JSeparator());
        mainGridLayout.add(new JSeparator());
        JLabel advancedSectionLabel = new JLabel("Zaawansowane:");
        advancedSectionLabel.setFont(new Font("Comic Sans MS", Font.BOLD,20));
        mainGridLayout.add(advancedSectionLabel);
        mainGridLayout.add(new JLabel());
        mainGridLayout.add(debugModeLabel);
        mainGridLayout.add(debugModeCheckBox);

        mainGridLayout.add(new JLabel());
        mainGridLayout.add(new JLabel());

        firstPlayerOptionsGridLayout.add(firstPlayerColor);
        secondPlayerOptionsGridLayout.add(secondPlayerColor);

        mainBorderLayout.add(mainGridLayout,BorderLayout.CENTER);
        add(mainBorderLayout);

        wasSaveClicked = false;
        firstPlayerColor.setColor(firstSettings.getPlayerFirstColor());
        secondPlayerColor.setColor(firstSettings.getPlayerSecondColor());
        playerFirstColor = firstSettings.getPlayerFirstColor();
        playerSecondColor = firstSettings.getPlayerSecondColor();
        isSound = firstSettings.getIsSound();
        volume = firstSettings.getVolume();
        BoardSize = firstSettings.getBoardSize();
        debugMode = firstSettings.getDebugMode();
        for(int i = 0; i < boardSizeComboBox.getItemCount();i++) {
            if(Integer.parseInt(boardSizeComboBox.getItemAt(i).split("x")[0])==BoardSize ) {
                boardSizeComboBox.setSelectedIndex(i);
            }
        }
        soundIsCheckBox.setSelected(isSound);
        soundVolumeSlider.setValue(volume);

        debugModeCheckBox.setSelected(debugMode);
        repaint();
    }

    private JPanel mainGridLayout;
    private JSlider soundVolumeSlider;
    private JCheckBox soundIsCheckBox;
    private int BoardSize = 5;

    private JCheckBox debugModeCheckBox;

    private Color playerFirstColor;

    private Color playerSecondColor;

    private boolean isSound = true;

    private int volume = 0;

    private boolean debugMode;

    private JComboBox<String> boardSizeComboBox;
    private boolean isPlayPressed = false;


    boolean wasSaveClicked = false;
    private ColorPreview firstPlayerColor;
    private ColorPreview secondPlayerColor;
}
