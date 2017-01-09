package stages;

import core.Bricks;
import XClasses.XSettings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by Mateusz on 23.12.2016.
 * Project Bricks
 */
class OptionsPane extends Pane {
    OptionsPane(double w, double h, XSettings settings) {

        firstPlayerProgramType = settings.getFirstPlayerProgramType();
        secondPlayerProgramType = settings.getSecondPlayerProgramType();
        volume = settings.getVolume();
        computerPlayerType = settings.getComputerPlayerType();
        BoardSize = settings.getBoardSize();

        isSound = settings.getIsSound();

        firstPlayerColor = settings.getPlayerFirstColor();
        secondPlayerColor = settings.getPlayerSecondColor();

        playerFirstFullPath = settings.getFirstComputerPlayerPath();
        playerSecondFullPath = settings.getSecondComputerPlayerPath();
        firstPlayerRunCommand = settings.getFirstPlayerRunCommand();
        secondPlayerRunCommand = settings.getSecondPlayerRunCommand();

        theme = settings.getTheme();

        GridPane mainGridPane = new GridPane();
        mainGridPane.setPrefHeight(h);
        mainGridPane.setPrefWidth(w);
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(50);
        mainGridPane.getColumnConstraints().add(column);
        mainGridPane.getColumnConstraints().add(column);
        RowConstraints row = new RowConstraints();
        row.setPercentHeight(6.66);
        for (int i = 0; i < 15; i++) {
            mainGridPane.getRowConstraints().add(row);
        }

        Label generalSettingsLabel = new Label("Ogólne:");
        generalSettingsLabel.setFont(Font.font("Comic Sans MS", 28.4));
        generalSettingsLabel.setPrefWidth(Double.MAX_VALUE);
        generalSettingsLabel.setAlignment(Pos.CENTER);

        Label boardSizeLabel = new Label("Rozmiar Planszy:  ");
        boardSizeLabel.setFont(Font.font("Comic Sans MS", 16));
        boardSizeLabel.setPrefWidth(Double.MAX_VALUE);
        boardSizeLabel.setAlignment(Pos.BASELINE_RIGHT);

        HBox boardSizeComboBoxHBox = new HBox();
        ComboBox<String> boardSizeComboBox = new ComboBox<>();
        boardSizeComboBox.getItems().add("5x5");
        boardSizeComboBox.getItems().add("9x9");
        boardSizeComboBox.getItems().add("13x13");
        boardSizeComboBox.getItems().add("17x17");
        boardSizeComboBox.getItems().add("21x21");
        boardSizeComboBox.getItems().add("25x25");
        boardSizeComboBox.getItems().add("29x29");
        boardSizeComboBox.getItems().add("33x33");
        boardSizeComboBox.getItems().add("37x37");
        boardSizeComboBox.getItems().add("41x41");
        boardSizeComboBox.getItems().add("45x45");
        boardSizeComboBox.getItems().add("49x49");
        boardSizeComboBox.getItems().add("53x53");
        boardSizeComboBox.getItems().add("57x57");
        boardSizeComboBox.getItems().add("61x61");
        boardSizeComboBox.getItems().add("65x65");
        boardSizeComboBoxHBox.getChildren().add(boardSizeComboBox);
        boardSizeComboBoxHBox.setAlignment(Pos.CENTER_LEFT);
        for (int i = 0; i < boardSizeComboBox.getItems().size(); i++) {
            if (Integer.parseInt(boardSizeComboBox.getItems().get(i).split("x")[0]) == BoardSize) {
                boardSizeComboBox.getSelectionModel().select(i);
                break;
            }
        }
        boardSizeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            try {
                BoardSize = Integer.parseInt(newValue.split("x")[0]);
            } catch (NumberFormatException ignored) {

            }
        });
        Label firstPlayerColorLabel = new Label("Kolor pierwszego gracza:  ");
        firstPlayerColorLabel.setFont(Font.font("Comic Sans MS", 16));
        firstPlayerColorLabel.setPrefWidth(Double.MAX_VALUE);
        firstPlayerColorLabel.setAlignment(Pos.BASELINE_RIGHT);
        ColorPicker firstPlayerColorPicker = new ColorPicker();
        firstPlayerColorPicker.setValue(firstPlayerColor);
        firstPlayerColorPicker.setOnAction(event -> setColor(firstPlayerColorPicker.getValue(), 1));

        Label secondPlayerColorLabel = new Label("Kolor drugiego gracza:  ");
        secondPlayerColorLabel.setFont(Font.font("Comic Sans MS", 16));
        secondPlayerColorLabel.setPrefWidth(Double.MAX_VALUE);
        secondPlayerColorLabel.setAlignment(Pos.BASELINE_RIGHT);
        ColorPicker secondPlayerColorPicker = new ColorPicker();
        secondPlayerColorPicker.setValue(secondPlayerColor);
        secondPlayerColorPicker.setOnAction(event -> setColor(secondPlayerColorPicker.getValue(), 2));

        Label soundSettingsLabel = new Label("Motyw Graficzny:  ");
        soundSettingsLabel.setFont(Font.font("Comic Sans MS", 16));
        soundSettingsLabel.setPrefWidth(Double.MAX_VALUE);
        soundSettingsLabel.setAlignment(Pos.CENTER_RIGHT);

        ComboBox<String> chooseTheme = new ComboBox<>();

        chooseTheme.getItems().add("Standardowy");
        chooseTheme.getItems().add("\"Minecraft\"");

        chooseTheme.getSelectionModel().select(theme);

        chooseTheme.valueProperty().addListener((observable, oldValue, newValue) -> theme = chooseTheme.getSelectionModel().getSelectedIndex());

        Label volumeSettingLabel = new Label("Głośność Dźwięków Gry:  ");
        volumeSettingLabel.setFont(Font.font("Comic Sans MS", 16));
        volumeSettingLabel.setPrefWidth(Double.MAX_VALUE);
        volumeSettingLabel.setAlignment(Pos.CENTER_RIGHT);

        Slider volumeSlider = new Slider();
        volumeSlider.setMaxWidth(150);
        volumeSlider.setMin(-80.0);
        volumeSlider.setMax(6.0);
        volumeSlider.setValue(volume);
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            volume = newValue.intValue();
            isSound = volume != 0;

        });

        firstProgramRunCommandTextArea = new TextArea();
        firstProgramRunCommandTextArea.setText(firstPlayerRunCommand);
        firstProgramRunCommandTextArea.setMaxWidth(200);
        firstProgramRunCommandTextArea.textProperty().addListener((observable, oldValue, newValue) -> firstPlayerRunCommand = newValue);
        if (firstPlayerProgramType == 1) {
            firstProgramRunCommandTextArea.setEditable(true);
        } else {
            firstProgramRunCommandTextArea.setEditable(false);
        }
        secondProgramRunCommandTextArea = new TextArea();
        secondProgramRunCommandTextArea.setText(secondPlayerRunCommand);
        secondProgramRunCommandTextArea.setMaxWidth(200);
        secondProgramRunCommandTextArea.textProperty().addListener((observable, oldValue, newValue) -> secondPlayerRunCommand = newValue);
        if (secondPlayerProgramType == 1) {
            secondProgramRunCommandTextArea.setEditable(true);
        } else {
            secondProgramRunCommandTextArea.setEditable(false);
        }
        firstProgramNameLabel = new Label();
        firstProgramNameLabel.setAlignment(Pos.CENTER_LEFT);
        firstProgramNameLabel.setPrefWidth(Double.MAX_VALUE);
        firstProgramNameLabel.setPadding(new Insets(0, 0, 0, 20));
        if (playerFirstFullPath.length() <= 30) {
            firstProgramNameLabel.setText(playerFirstFullPath);
        } else {
            firstProgramNameLabel.setText("..." + playerFirstFullPath.substring(playerFirstFullPath.length() - 30, playerFirstFullPath.length()));
        }
        secondProgramNameLabel = new Label();
        secondProgramNameLabel.setAlignment(Pos.CENTER_LEFT);
        secondProgramNameLabel.setPrefWidth(Double.MAX_VALUE);
        secondProgramNameLabel.setText(playerSecondFullPath);
        secondProgramNameLabel.setPadding(new Insets(0, 0, 0, 20));
        if (playerSecondFullPath.length() <= 30) {
            secondProgramNameLabel.setText(playerSecondFullPath);
        } else {
            secondProgramNameLabel.setText("..." + playerSecondFullPath.substring(playerSecondFullPath.length() - 30, playerSecondFullPath.length()));
        }

        Label robotWarsLabel = new Label("Wojny Robotów:");
        robotWarsLabel.setFont(Font.font("Comic Sans MS", 28.4));
        robotWarsLabel.setPrefWidth(Double.MAX_VALUE);
        robotWarsLabel.setAlignment(Pos.CENTER);

        HBox chooseFirstComputerPlayerHBox = new HBox();
        Button chooseFirstComputerPlayerButton = new Button("Pierwszy Gracz");
        chooseFirstComputerPlayerButton.setFont(Font.font("Comic Sans MS", 14));
        chooseFirstComputerPlayerButton.setPrefWidth(150);
        chooseFirstComputerPlayerHBox.getChildren().add(chooseFirstComputerPlayerButton);
        chooseFirstComputerPlayerHBox.setAlignment(Pos.CENTER_RIGHT);

        chooseFirstComputerPlayerButton.setOnAction(event -> {
            FileChooser chooseFile = new FileChooser();
            chooseFile.setTitle("Wybierz komputer pierwszy");
            File openFile = chooseFile.showOpenDialog(Bricks.mainStage.mainStage);
            if (openFile != null) {
                try {
                    playerFirstFullPath = openFile.getCanonicalPath();
                    if (playerFirstFullPath.length() <= 30) {
                        firstProgramNameLabel.setText(playerFirstFullPath);
                    } else {
                        firstProgramNameLabel.setText("..." + playerFirstFullPath.substring(playerFirstFullPath.length() - 30, playerFirstFullPath.length()));
                    }
                } catch (IOException ignored) {

                }
            }
        });


        Label runAsFirstPlayerLabel = new Label("Uruchom jako:  ");
        runAsFirstPlayerLabel.setFont(Font.font("Comic Sans MS", 16));
        runAsFirstPlayerLabel.setPrefWidth(Double.MAX_VALUE);
        runAsFirstPlayerLabel.setAlignment(Pos.BASELINE_RIGHT);

        Label parametersFirstPlayerLabel = new Label("Parametry uruchomienia:  ");
        parametersFirstPlayerLabel.setFont(Font.font("Comic Sans MS", 16));
        parametersFirstPlayerLabel.setPrefWidth(Double.MAX_VALUE);
        parametersFirstPlayerLabel.setAlignment(Pos.BASELINE_RIGHT);

        HBox chooseSecondComputerPlayerHBox = new HBox();
        Button chooseSecondComputerPlayerButton = new Button("Drugi Gracz");
        chooseSecondComputerPlayerButton.setFont(Font.font("Comic Sans MS", 14));
        chooseSecondComputerPlayerButton.setPrefWidth(150);
        chooseSecondComputerPlayerHBox.getChildren().add(chooseSecondComputerPlayerButton);
        chooseSecondComputerPlayerHBox.setAlignment(Pos.CENTER_RIGHT);
        chooseSecondComputerPlayerButton.setOnAction(event -> {
            FileChooser chooseFile = new FileChooser();
            chooseFile.setTitle("Wybierz komputer drugi");
            File openFile = chooseFile.showOpenDialog(Bricks.mainStage.mainStage);
            if (openFile != null) {
                try {
                    playerSecondFullPath = openFile.getCanonicalPath();
                    if (playerSecondFullPath.length() <= 30) {
                        secondProgramNameLabel.setText(playerSecondFullPath);
                    } else {
                        secondProgramNameLabel.setText("..." + playerSecondFullPath.substring(playerSecondFullPath.length() - 30, playerSecondFullPath.length()));
                    }
                } catch (IOException ignored) {

                }
            }
        });

        Label runAsSecondPlayerLabel = new Label("Uruchom jako:  ");
        runAsSecondPlayerLabel.setFont(Font.font("Comic Sans MS", 16));
        runAsSecondPlayerLabel.setPrefWidth(Double.MAX_VALUE);
        runAsSecondPlayerLabel.setAlignment(Pos.BASELINE_RIGHT);

        Label parametersSecondPlayerLabel = new Label("Parametry uruchomienia:  ");
        parametersSecondPlayerLabel.setFont(Font.font("Comic Sans MS", 16));
        parametersSecondPlayerLabel.setPrefWidth(Double.MAX_VALUE);
        parametersSecondPlayerLabel.setAlignment(Pos.BASELINE_RIGHT);

        Label singPlayerComputerPlayerLabel = new Label("Gracz Komputerowy:  ");
        singPlayerComputerPlayerLabel.setFont(Font.font("Comic Sans MS", 16));
        singPlayerComputerPlayerLabel.setPrefWidth(Double.MAX_VALUE);
        singPlayerComputerPlayerLabel.setAlignment(Pos.BASELINE_RIGHT);

        firstProgramTypeComboBox = new ComboBox<>();
        firstProgramTypeComboBox.getItems().add("Plik exe/out/jar/class");
        firstProgramTypeComboBox.getItems().add("Własne");

        firstProgramTypeComboBox.getSelectionModel().select(firstPlayerProgramType);
        if (firstPlayerProgramType == 1) {
            chooseFirstComputerPlayerButton.setDisable(true);
            firstProgramRunCommandTextArea.setEditable(true);
        } else {
            chooseFirstComputerPlayerButton.setDisable(false);
            firstProgramRunCommandTextArea.setEditable(false);
        }
        firstProgramTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            firstPlayerProgramType = firstProgramTypeComboBox.getSelectionModel().getSelectedIndex();
            if (firstPlayerProgramType == 1) {
                chooseFirstComputerPlayerButton.setDisable(true);
                firstProgramRunCommandTextArea.setEditable(true);
            } else {
                chooseFirstComputerPlayerButton.setDisable(false);
                firstProgramRunCommandTextArea.setEditable(false);
            }
        });


        secondProgramTypeComboBox = new ComboBox<>();
        secondProgramTypeComboBox.getItems().add("Plik exe/out/jar/class");
        secondProgramTypeComboBox.getItems().add("Własne");

        secondProgramTypeComboBox.getSelectionModel().select(secondPlayerProgramType);
        if (secondPlayerProgramType == 1) {
            chooseSecondComputerPlayerButton.setDisable(true);
            secondProgramRunCommandTextArea.setEditable(true);
        } else {
            chooseSecondComputerPlayerButton.setDisable(false);
            secondProgramRunCommandTextArea.setEditable(false);
        }
        secondProgramTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            secondPlayerProgramType = secondProgramTypeComboBox.getSelectionModel().getSelectedIndex();
            if (secondPlayerProgramType == 1) {
                chooseSecondComputerPlayerButton.setDisable(true);
                secondProgramRunCommandTextArea.setEditable(true);
            } else {
                chooseSecondComputerPlayerButton.setDisable(false);
                secondProgramRunCommandTextArea.setEditable(false);
            }
        });

        ComboBox<String> chooseCompterTypeComboBox = new ComboBox<>();

        chooseCompterTypeComboBox.getItems().add("Wbudowany");
        chooseCompterTypeComboBox.getItems().add("Komputer Pierwszy");
        chooseCompterTypeComboBox.getItems().add("Komputer Drugi");

        chooseCompterTypeComboBox.getSelectionModel().select(computerPlayerType);

        chooseCompterTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> computerPlayerType = chooseCompterTypeComboBox.getSelectionModel().getSelectedIndex());

        HBox saveAndExitHBox = new HBox();
        saveAndExitButton = new Button("Zapisz Zmiany");
        saveAndExitButton.setFont(Font.font("Comic Sans MS", 12.9));
        saveAndExitButton.setPrefWidth(300);
        saveAndExitButton.setPrefHeight(Double.MAX_VALUE);
        saveAndExitHBox.setAlignment(Pos.CENTER);
        saveAndExitHBox.getChildren().add(saveAndExitButton);
        HBox.setMargin(saveAndExitButton, new Insets(5, 0, 5, 0));
        HBox.setHgrow(saveAndExitButton, Priority.ALWAYS);
        mainGridPane.add(chooseCompterTypeComboBox, 1, 13);
        mainGridPane.add(singPlayerComputerPlayerLabel, 0, 13);
        mainGridPane.add(secondProgramRunCommandTextArea, 1, 12);
        mainGridPane.add(parametersSecondPlayerLabel, 0, 12);
        mainGridPane.add(secondProgramTypeComboBox, 1, 11);
        mainGridPane.add(runAsSecondPlayerLabel, 0, 11);
        mainGridPane.add(secondProgramNameLabel, 1, 10);
        mainGridPane.add(chooseSecondComputerPlayerHBox, 0, 10);
        mainGridPane.add(firstProgramRunCommandTextArea, 1, 9);
        mainGridPane.add(parametersFirstPlayerLabel, 0, 9);
        mainGridPane.add(firstProgramTypeComboBox, 1, 8);
        mainGridPane.add(runAsFirstPlayerLabel, 0, 8);
        mainGridPane.add(firstProgramNameLabel, 1, 7);
        mainGridPane.add(chooseFirstComputerPlayerHBox, 0, 7);
        mainGridPane.add(robotWarsLabel, 0, 6, 2, 1);
        mainGridPane.add(volumeSlider, 1, 5);
        mainGridPane.add(volumeSettingLabel, 0, 5);
        mainGridPane.add(chooseTheme, 1, 4);
        mainGridPane.add(soundSettingsLabel, 0, 4);
        mainGridPane.add(boardSizeLabel, 0, 1);
        mainGridPane.add(boardSizeComboBoxHBox, 1, 1);
        mainGridPane.add(firstPlayerColorLabel, 0, 2);
        mainGridPane.add(firstPlayerColorPicker, 1, 2);
        mainGridPane.add(secondPlayerColorLabel, 0, 3);
        mainGridPane.add(secondPlayerColorPicker, 1, 3);
        mainGridPane.add(generalSettingsLabel, 0, 0, 2, 1);
        mainGridPane.add(saveAndExitHBox, 0, 14, 2, 1);
        getChildren().add(mainGridPane);

        generalSettingsLabel.setFont(Font.font("Comic Sans MS", Bricks.mainStage.mainStage.getHeight() / 25));
        robotWarsLabel.setFont(Font.font("Comic Sans MS", Bricks.mainStage.mainStage.getHeight() / 25));
        saveAndExitButton.setFont(Font.font("Comic Sans MS", Bricks.mainStage.mainStage.getHeight() / 55));

        boardSizeLabel.setFont(Font.font("Comic Sans MS", Bricks.mainStage.mainStage.getHeight() / 40));
        firstPlayerColorLabel.setFont(Font.font("Comic Sans MS", Bricks.mainStage.mainStage.getHeight() / 40));
        secondPlayerColorLabel.setFont(Font.font("Comic Sans MS", Bricks.mainStage.mainStage.getHeight() / 40));
        soundSettingsLabel.setFont(Font.font("Comic Sans MS", Bricks.mainStage.mainStage.getHeight() / 40));
        volumeSettingLabel.setFont(Font.font("Comic Sans MS", Bricks.mainStage.mainStage.getHeight() / 40));
        runAsFirstPlayerLabel.setFont(Font.font("Comic Sans MS", Bricks.mainStage.mainStage.getHeight() / 40));
        runAsSecondPlayerLabel.setFont(Font.font("Comic Sans MS", Bricks.mainStage.mainStage.getHeight() / 40));
        singPlayerComputerPlayerLabel.setFont(Font.font("Comic Sans MS", Bricks.mainStage.mainStage.getHeight() / 40));
        parametersFirstPlayerLabel.setFont(Font.font("Comic Sans MS", Bricks.mainStage.mainStage.getHeight() / 40));
        parametersSecondPlayerLabel.setFont(Font.font("Comic Sans MS", Bricks.mainStage.mainStage.getHeight() / 40));
        firstProgramNameLabel.setFont(Font.font("Comic Sans MS", Bricks.mainStage.mainStage.getHeight() / 55));
        secondProgramNameLabel.setFont(Font.font("Comic Sans MS", Bricks.mainStage.mainStage.getHeight() / 55));

        boardSizeComboBoxHBox.setMaxHeight(Bricks.mainStage.mainStage.getHeight() / 40);
        boardSizeComboBox.setMaxHeight(Bricks.mainStage.mainStage.getHeight() / 40);

        chooseFirstComputerPlayerHBox.setMaxHeight(Bricks.mainStage.mainStage.getHeight() / 40);
        firstProgramTypeComboBox.setMaxHeight(Bricks.mainStage.mainStage.getHeight() / 40);

        chooseSecondComputerPlayerHBox.setMaxHeight(Bricks.mainStage.mainStage.getHeight() / 40);
        secondProgramTypeComboBox.setMaxHeight(Bricks.mainStage.mainStage.getHeight() / 40);

        if (Bricks.mainStage.mainStage.getHeight() / 40 > 25) {
            firstPlayerColorPicker.setMaxHeight(Bricks.mainStage.mainStage.getHeight() / 40);
            secondPlayerColorPicker.setMaxHeight(Bricks.mainStage.mainStage.getHeight() / 40);
        } else {
            firstPlayerColorPicker.setMaxHeight(25);
            secondPlayerColorPicker.setMaxHeight(25);
        }

        chooseTheme.setMaxHeight(Bricks.mainStage.mainStage.getHeight() / 40);

        saveAndExitButton.setOnAction(event -> {
                Bricks.mainStage.setSettings(BoardSize, firstPlayerColor, secondPlayerColor, isSound, volume, playerFirstFullPath, playerSecondFullPath, firstPlayerProgramType, secondPlayerProgramType, firstPlayerRunCommand, secondPlayerRunCommand, computerPlayerType, theme);
                Bricks.mainStage.backToMenu();
        });

        Bricks.mainStage.mainStage.heightProperty().addListener((observable, oldValue, newValue) -> {
            generalSettingsLabel.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 25));
            robotWarsLabel.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 25));
            saveAndExitButton.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 55));

            boardSizeLabel.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 40));
            firstPlayerColorLabel.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 40));
            secondPlayerColorLabel.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 40));
            soundSettingsLabel.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 40));
            volumeSettingLabel.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 40));
            runAsFirstPlayerLabel.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 40));
            runAsSecondPlayerLabel.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 40));
            singPlayerComputerPlayerLabel.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 40));
            parametersFirstPlayerLabel.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 40));
            parametersSecondPlayerLabel.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 40));
            firstProgramNameLabel.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 55));
            secondProgramNameLabel.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 55));

            boardSizeComboBoxHBox.setMaxHeight(newValue.doubleValue() / 40);
            boardSizeComboBox.setMaxHeight(newValue.doubleValue() / 40);

            chooseFirstComputerPlayerHBox.setMaxHeight(newValue.doubleValue() / 40);
            firstProgramTypeComboBox.setMaxHeight(newValue.doubleValue() / 40);

            chooseSecondComputerPlayerHBox.setMaxHeight(newValue.doubleValue() / 40);
            secondProgramTypeComboBox.setMaxHeight(newValue.doubleValue() / 40);

            if (newValue.doubleValue() / 40 > 25) {
                firstPlayerColorPicker.setMaxHeight(newValue.doubleValue() / 40);
                secondPlayerColorPicker.setMaxHeight(newValue.doubleValue() / 40);
            } else {
                firstPlayerColorPicker.setMaxHeight(25);
                secondPlayerColorPicker.setMaxHeight(25);
            }

            chooseTheme.setMaxHeight(newValue.doubleValue() / 40);
        });
        setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                alert.setTitle("Potwierdznie Wyjścia");
                alert.setHeaderText("Chcesz wyjść z ustawień?");
                alert.setContentText("Zmiany nie zostaną zapisane.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Bricks.mainStage.backToMenu();
                }
            }
        });
        widthProperty().addListener((observable, oldValue, newValue) -> mainGridPane.setPrefWidth(newValue.doubleValue()));
        heightProperty().addListener((observable, oldValue, newValue) -> mainGridPane.setPrefHeight(newValue.doubleValue()));
    }

    private void setColor(Color newColor, int player) {
        if (player == 1)
            firstPlayerColor = newColor;
        if (player == 2)
            secondPlayerColor = newColor;
    }

    private Button saveAndExitButton;

    private int firstPlayerProgramType;
    private int secondPlayerProgramType;
    private int volume;
    private int BoardSize;
    private int computerPlayerType;

    private boolean isSound;

    private javafx.scene.paint.Color firstPlayerColor;
    private javafx.scene.paint.Color secondPlayerColor;

    private String playerFirstFullPath = "";
    private String playerSecondFullPath = "";
    private String firstPlayerRunCommand;
    private String secondPlayerRunCommand;

    //KOMPONENTY
    private Label firstProgramNameLabel;
    private Label secondProgramNameLabel;

    private ComboBox<String> firstProgramTypeComboBox;
    private ComboBox<String> secondProgramTypeComboBox;

    private TextArea firstProgramRunCommandTextArea;
    private TextArea secondProgramRunCommandTextArea;

    private int theme;
}
