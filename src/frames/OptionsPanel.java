package frames;

import core.Bricks;
import core.Settings;
import gfx.ColorPreview;

import javax.swing.*;
import java.awt.*;

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

        playerFirstFullPath = firstSettings.getFirstComputerPlayerPath();
        playerSecondFullPath = firstSettings.getSecondComputerPlayerPath();

        firstPlayerProgramType = firstSettings.getFirstPlayerProgramType();
        secondPlayerProgramType = firstSettings.getSecondPlayerProgramType();

        firstPlayerRunCommand = firstSettings.getFirstPlayerRunCommand();
        secondPlayerRunCommand = firstSettings.getSecondPlayerRunCommand();

        computerPlayerType = firstSettings.getComputerPlayerType();

        JLabel setFirstPathLabel = new JLabel();
        JLabel setSecondPathLabel = new JLabel();

        JButton chooseFirstPlayerColorButton = new JButton("Wybierz");
        chooseFirstPlayerColorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, "Wybierz kolor pierwszego gracza", playerFirstColor);
            if (newColor != null) {
                playerFirstColor = newColor;
                firstPlayerColor.setColor(playerFirstColor);
                repaint();
            }
        });
        JButton chooseSecondPlayerColorButton = new JButton("Wybierz");
        chooseSecondPlayerColorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, "Wybierz kolor drugiego gracza", playerSecondColor);
            if (newColor != null) {
                playerSecondColor = newColor;
                secondPlayerColor.setColor(playerSecondColor);
                repaint();
            }
        });

        JButton resetOptions = new JButton("Ustawienia Domyślne");
        resetOptions.addActionListener(e -> {
            BoardSize = 5;
            playerFirstColor = new Color(69, 136, 58);
            playerSecondColor = new Color(238, 44, 44);
            firstPlayerColor.setColor(playerFirstColor);
            secondPlayerColor.setColor(playerSecondColor);
            isSound = true;
            soundIsCheckBox.setSelected(true);
            volume = 0;
            soundVolumeSlider.setValue(volume);
            for (int i = 0; i < boardSizeComboBox.getItemCount(); i++) {
                if (Integer.parseInt(boardSizeComboBox.getItemAt(i).split("x")[0]) == BoardSize) {
                    boardSizeComboBox.setSelectedIndex(i);
                }
            }
            debugMode = false;
            debugModeCheckBox.setSelected(false);

            firstPlayerProgramType = 1;
            secondPlayerProgramType = 1;

            firstPlayerProgramTypeCombo.setSelectedIndex(firstPlayerProgramType);
            secondPlayerProgramTypeCombo.setSelectedIndex(secondPlayerProgramType);

            inputOwnRunTextPlayerFirstTextField.setText("");
            inputOwnRunTextPlayerSecondTextField.setText("");

            firstPlayerRunCommand = "";
            secondPlayerRunCommand = "";

            playerFirstFullPath = "";
            playerSecondFullPath = "";

            setFirstPathLabel.setText("");
            setSecondPathLabel.setText("");

            repaint();
        });

        JPanel firstPlayerOptionsGridLayout = new JPanel(new GridLayout(1, 2));
        JPanel secondPlayerOptionsGridLayout = new JPanel(new GridLayout(1, 2));

        boardSizeComboBox = new JComboBox<>();
        boardSizeComboBox.addItem("5x5");
        boardSizeComboBox.addItem("9x9");
        boardSizeComboBox.addItem("13x13");
        boardSizeComboBox.addItem("17x17");
        boardSizeComboBox.addItem("21x21");
        boardSizeComboBox.addItem("25x25");
        boardSizeComboBox.addItem("29x29");
        boardSizeComboBox.addItem("33x33");
        boardSizeComboBox.addItem("37x37");
        boardSizeComboBox.addItem("41x41");
        boardSizeComboBox.addItem("45x45");


        firstPlayerColor = new ColorPreview(playerFirstColor);
        secondPlayerColor = new ColorPreview(playerSecondColor);


        boardSizeComboBox.setEnabled(true);

        boardSizeComboBox.addActionListener(e -> {
            try {
                BoardSize = Integer.parseInt(boardSizeComboBox.getSelectedItem().toString().split("x")[0]);
            } catch (Exception excIntegerParse) {
                BoardSize = 5;
            }
        });

        JPanel mainBorderLayout = new JPanel(new BorderLayout());
        mainBorderLayout.add(title, BorderLayout.NORTH);

        JButton saveChanges = new JButton("Zapisz zmiany");

        saveChanges.addActionListener(e -> {
            Bricks.mainFrame.setSettings(new Settings(BoardSize, playerFirstColor, playerSecondColor, isSound, volume,
                    debugMode, playerFirstFullPath, playerSecondFullPath, firstPlayerProgramType, secondPlayerProgramType,
                    inputOwnRunTextPlayerFirstTextField.getText(), inputOwnRunTextPlayerSecondTextField.getText(), computerPlayerType));
            Bricks.mainFrame.backToMenu();
        });

        JPanel southGridLayout = new JPanel(new GridLayout(2, 1));
        southGridLayout.add(resetOptions);
        southGridLayout.add(saveChanges);
        mainBorderLayout.add(southGridLayout, BorderLayout.SOUTH);

        JLabel soundIsLabel = new JLabel("Dźwięki:");
        soundIsLabel.setHorizontalAlignment(JLabel.CENTER);
        soundIsCheckBox = new JCheckBox();
        JLabel soundVolumeLabel = new JLabel("Głośność:");
        soundVolumeLabel.setHorizontalAlignment(JLabel.CENTER);
        soundVolumeSlider = new JSlider(JSlider.HORIZONTAL, -80, 6, 0);


        soundIsCheckBox.addActionListener(e -> isSound = soundIsCheckBox.isSelected());

        soundVolumeSlider.addChangeListener(e -> volume = soundVolumeSlider.getValue());

        debugModeCheckBox = new JCheckBox();
        JLabel debugModeLabel = new JLabel("Tryb Debugowania: ");
        debugModeLabel.setHorizontalAlignment(JLabel.CENTER);

        debugModeCheckBox.addActionListener(e -> debugMode = debugModeCheckBox.isSelected());

        JButton setFirstPath = new JButton("Ustal Komputer Pierwszy");
        JButton setSecondPath = new JButton("Ustal Komputer Drugi");

        if (playerFirstFullPath.length() <= 30) {
            setFirstPathLabel.setText(playerFirstFullPath);
        } else {
            setFirstPathLabel.setText("..." + playerFirstFullPath.substring(playerFirstFullPath.length() - 30, playerFirstFullPath.length()));
        }
        if (playerSecondFullPath.length() <= 30) {
            setSecondPathLabel.setText(playerSecondFullPath);
        } else {
            setSecondPathLabel.setText("..." + playerSecondFullPath.substring(playerSecondFullPath.length() - 30, playerSecondFullPath.length()));
        }

        JPanel mainGridLayout = new JPanel(new GridLayout(15, 2));
        JLabel generalSectionLabel = new JLabel("Ogólne:");
        generalSectionLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
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
        soundSectionLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
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
        advancedSectionLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        mainGridLayout.add(advancedSectionLabel);
        mainGridLayout.add(new JLabel());
        mainGridLayout.add(debugModeLabel);
        mainGridLayout.add(debugModeCheckBox);

        setFirstPath.addActionListener(e -> {
            JFileChooser chooseFile = new JFileChooser();
            int save = chooseFile.showOpenDialog(null);
            if (save == JFileChooser.APPROVE_OPTION) {
                playerFirstFullPath = chooseFile.getSelectedFile().getPath();
                if (playerFirstFullPath.length() <= 30) {
                    setFirstPathLabel.setText(playerFirstFullPath);
                } else {
                    setFirstPathLabel.setText("..." + playerFirstFullPath.substring(playerFirstFullPath.length() - 30, playerFirstFullPath.length()));
                }
            }
        });
        setSecondPath.addActionListener(e -> {
            JFileChooser chooseFile = new JFileChooser();
            int save = chooseFile.showOpenDialog(null);
            if (save == JFileChooser.APPROVE_OPTION) {
                playerSecondFullPath = chooseFile.getSelectedFile().getPath();
                if (playerSecondFullPath.length() <= 30) {
                    setSecondPathLabel.setText(playerSecondFullPath);
                } else {
                    setSecondPathLabel.setText("..." + playerSecondFullPath.substring(playerSecondFullPath.length() - 30, playerSecondFullPath.length()));
                }
            }
        });


        mainGridLayout.add(new JLabel());
        mainGridLayout.add(new JLabel());

        firstPlayerOptionsGridLayout.add(firstPlayerColor);
        secondPlayerOptionsGridLayout.add(secondPlayerColor);

        JTabbedPane mainTabPane = new JTabbedPane();
        mainTabPane.add("Podstawowe", mainGridLayout);
        mainBorderLayout.add(mainTabPane, BorderLayout.CENTER);

        JPanel advancedGridLayout = new JPanel(new GridLayout(12, 2));
        JLabel robotsWarsSectionLabel = new JLabel("Wojna Robotów:");
        robotsWarsSectionLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        advancedGridLayout.add(robotsWarsSectionLabel);
        advancedGridLayout.add(new JLabel());
        advancedGridLayout.add(new JSeparator());
        advancedGridLayout.add(new JSeparator());
        advancedGridLayout.add(setFirstPath);
        advancedGridLayout.add(setFirstPathLabel);
        advancedGridLayout.add(new JLabel("Uruchom jako:"));

        firstPlayerProgramTypeCombo = new JComboBox<>();
        firstPlayerProgramTypeCombo.addItem("Plik exe/out");
        firstPlayerProgramTypeCombo.addItem("Plik class (java)");
        firstPlayerProgramTypeCombo.addItem("Własne");
        firstPlayerProgramTypeCombo.setSelectedIndex(firstPlayerProgramType);


        inputOwnRunTextPlayerFirstTextField = new JTextArea();
        inputOwnRunTextPlayerFirstTextField.setEnabled(false);
        inputOwnRunTextPlayerFirstTextField.setLineWrap(true);
        inputOwnRunTextPlayerFirstTextField.setFont(new Font("Courier",Font.PLAIN,10));
        JScrollPane inputOwnTextScrollPane = new JScrollPane(inputOwnRunTextPlayerFirstTextField);
        inputOwnRunTextPlayerFirstTextField.setToolTipText("Aktywne tylko, jeśli 'Uruchom Jako' jest wybrane na 'Własne'");
        inputOwnRunTextPlayerSecondTextField = new JTextArea();
        inputOwnRunTextPlayerSecondTextField.setEnabled(false);
        inputOwnRunTextPlayerSecondTextField.setLineWrap(true);
        inputOwnRunTextPlayerSecondTextField.setFont(new Font("Courier",Font.PLAIN,10));
        JScrollPane inputOwnTextSecondScrollPane = new JScrollPane(inputOwnRunTextPlayerSecondTextField);
        inputOwnRunTextPlayerSecondTextField.setToolTipText("Aktywne tylko, jeśli 'Uruchom Jako' jest wybrane na 'Własne'");

        firstPlayerProgramTypeCombo.addActionListener(e -> {
            firstPlayerProgramType = firstPlayerProgramTypeCombo.getSelectedIndex();
            if (firstPlayerProgramTypeCombo.getSelectedIndex() == 2) {
                inputOwnRunTextPlayerFirstTextField.setEnabled(true);
                setFirstPath.setEnabled(false);
            } else {
                inputOwnRunTextPlayerFirstTextField.setEnabled(false);
                setFirstPath.setEnabled(true);
            }
        });

        advancedGridLayout.add(firstPlayerProgramTypeCombo);
        advancedGridLayout.add(new JLabel("Parametry uruchomienia programu:"));
        advancedGridLayout.add(inputOwnTextScrollPane);

        advancedGridLayout.add(new JSeparator());
        advancedGridLayout.add(new JSeparator());

        advancedGridLayout.add(setSecondPath);
        advancedGridLayout.add(setSecondPathLabel);

        advancedGridLayout.add(new JLabel("Uruchom jako:"));

        secondPlayerProgramTypeCombo = new JComboBox<>();
        secondPlayerProgramTypeCombo.addItem("Plik exe/out");
        secondPlayerProgramTypeCombo.addItem("Plik class (java)");
        secondPlayerProgramTypeCombo.addItem("Własne");
        secondPlayerProgramTypeCombo.setSelectedIndex(secondPlayerProgramType);

        secondPlayerProgramTypeCombo.addActionListener(e -> {
            secondPlayerProgramType = secondPlayerProgramTypeCombo.getSelectedIndex();
            if (secondPlayerProgramTypeCombo.getSelectedIndex() == 2) {
                inputOwnRunTextPlayerSecondTextField.setEnabled(true);
                setSecondPath.setEnabled(false);
            } else {
                inputOwnRunTextPlayerSecondTextField.setEnabled(false);
                setSecondPath.setEnabled(true);
            }
        });
        advancedGridLayout.add(secondPlayerProgramTypeCombo);
        advancedGridLayout.add(new JLabel("Parametry uruchomienia programu:"));
        advancedGridLayout.add(inputOwnTextSecondScrollPane);

        JLabel singlePlayerComputerSelectionLabel = new JLabel("Gra jednoosobowa:");
        JComboBox<String> singlePlayerComputerSelectionComboBox = new JComboBox<>();
        advancedGridLayout.add(new JSeparator());
        advancedGridLayout.add(new JSeparator());
        singlePlayerComputerSelectionComboBox.addItem("Wbudowany");
        singlePlayerComputerSelectionComboBox.addItem("Komputer Pierwszy");
        singlePlayerComputerSelectionComboBox.addItem("Komputer Drugi");
        advancedGridLayout.add(singlePlayerComputerSelectionLabel);
        advancedGridLayout.add(singlePlayerComputerSelectionComboBox);
        singlePlayerComputerSelectionComboBox.setSelectedIndex(firstSettings.getComputerPlayerType());

        singlePlayerComputerSelectionComboBox.addActionListener(e -> computerPlayerType = singlePlayerComputerSelectionComboBox.getSelectedIndex());

        mainTabPane.add("Zaawansowane", advancedGridLayout);

        add(mainBorderLayout);

        firstPlayerColor.setColor(firstSettings.getPlayerFirstColor());
        secondPlayerColor.setColor(firstSettings.getPlayerSecondColor());
        playerFirstColor = firstSettings.getPlayerFirstColor();
        playerSecondColor = firstSettings.getPlayerSecondColor();
        isSound = firstSettings.getIsSound();
        volume = firstSettings.getVolume();
        BoardSize = firstSettings.getBoardSize();
        debugMode = firstSettings.getDebugMode();
        for (int i = 0; i < boardSizeComboBox.getItemCount(); i++) {
            if (Integer.parseInt(boardSizeComboBox.getItemAt(i).split("x")[0]) == BoardSize) {
                boardSizeComboBox.setSelectedIndex(i);
            }
        }
        soundIsCheckBox.setSelected(isSound);
        soundVolumeSlider.setValue(volume);

        debugModeCheckBox.setSelected(debugMode);


        setFirstPath.setEnabled(false);
        setSecondPath.setEnabled(false);
        inputOwnRunTextPlayerFirstTextField.setEnabled(false);
        inputOwnRunTextPlayerSecondTextField.setEnabled(false);

        if (firstPlayerProgramType == 2) {
            inputOwnRunTextPlayerFirstTextField.setEnabled(true);
            setFirstPath.setEnabled(false);
        } else {
            inputOwnRunTextPlayerFirstTextField.setEnabled(false);
            setFirstPath.setEnabled(true);
            setSecondPath.setEnabled(false);
        }

        if (secondPlayerProgramType == 2) {
            inputOwnRunTextPlayerSecondTextField.setEnabled(true);
        } else {
            inputOwnRunTextPlayerSecondTextField.setEnabled(false);
            setSecondPath.setEnabled(true);
        }

        inputOwnRunTextPlayerFirstTextField.setText(firstPlayerRunCommand);
        inputOwnRunTextPlayerSecondTextField.setText(secondPlayerRunCommand);

        repaint();
    }

    private JComboBox<String> secondPlayerProgramTypeCombo;
    private JComboBox<String> firstPlayerProgramTypeCombo;
    private JComboBox<String> boardSizeComboBox;

    private JSlider soundVolumeSlider;

    private JCheckBox soundIsCheckBox;
    private JCheckBox debugModeCheckBox;

    private int BoardSize = 5;
    private int firstPlayerProgramType;
    private int secondPlayerProgramType;
    private int computerPlayerType;
    private int volume = 0;

    private String firstPlayerRunCommand;
    private String secondPlayerRunCommand;
    private String playerFirstFullPath;
    private String playerSecondFullPath;

    private JTextArea inputOwnRunTextPlayerFirstTextField;
    private JTextArea inputOwnRunTextPlayerSecondTextField;

    private Color playerFirstColor;
    private Color playerSecondColor;

    private boolean isSound = true;
    private boolean debugMode;

    private ColorPreview firstPlayerColor;
    private ColorPreview secondPlayerColor;
}
