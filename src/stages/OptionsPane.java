package stages;

import core.Bricks;
import core.Settings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by Mateusz on 23.12.2016.
 * Project Bricks
 */
public class OptionsPane extends Pane {
    public OptionsPane(double w,double h,Settings settings){

        firstPlayerProgramType = settings.getFirstPlayerProgramType();
        secondPlayerProgramType = settings.getSecondPlayerProgramType();
        volume = settings.getVolume();
        computerPlayerType = settings.getComputerPlayerType();
        BoardSize = settings.getBoardSize();

        isSound = settings.getIsSound();
        debugMode = settings.getDebugMode();

        firstPlayerColor = settings.getPlayerFirstColor();
        secondPlayerColor = settings.getPlayerSecondColor();

        playerFirstFullPath = settings.getFirstComputerPlayerPath();
        playerSecondFullPath = settings.getSecondComputerPlayerPath();
        firstPlayerRunCommand = settings.getFirstPlayerRunCommand();
        secondPlayerRunCommand = settings.getSecondPlayerRunCommand();


        GridPane mainGridPane = new GridPane();
        mainGridPane.setPrefHeight(h);
        mainGridPane.setPrefWidth(w);
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(50);
        mainGridPane.getColumnConstraints().add(column);
        mainGridPane.getColumnConstraints().add(column);
        RowConstraints row = new RowConstraints();
        row.setPercentHeight(6.25);
        for(int i = 0; i < 16;i++) {
            mainGridPane.getRowConstraints().add(row);
        }

        Label generalSettingsLabel = new Label("Ogólne:");
        generalSettingsLabel.setFont(Font.font("Comic Sans MS",20));
        generalSettingsLabel.setPrefWidth(Double.MAX_VALUE);
        generalSettingsLabel.setAlignment(Pos.CENTER);

        Label boardSizeLabel = new Label("Rozmiar Planszy:  ");
        boardSizeLabel.setFont(Font.font("Comic Sans MS",16));
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
        for(int i = 0; i < boardSizeComboBox.getItems().size();i++) {
            if(Integer.parseInt(boardSizeComboBox.getItems().get(i).split("x")[0]) == BoardSize) {
                boardSizeComboBox.getSelectionModel().select(i);
                break;
            }
        }
        boardSizeComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    BoardSize = Integer.parseInt(newValue.split("x")[0]);
                }
                catch (NumberFormatException ignored){

                }
            }
        });
        Label firstPlayerColorLabel = new Label("Kolor pierwszego gracza:  ");
        firstPlayerColorLabel.setFont(Font.font("Comic Sans MS",16));
        firstPlayerColorLabel.setPrefWidth(Double.MAX_VALUE);
        firstPlayerColorLabel.setAlignment(Pos.BASELINE_RIGHT);
        ColorPicker firstPlayerColorPicker = new ColorPicker();
        firstPlayerColorPicker.setValue(firstPlayerColor);
        firstPlayerColorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setColor(firstPlayerColorPicker.getValue(),1);
            }
        });

        Label secondPlayerColorLabel = new Label("Kolor drugiego gracza:  ");
        secondPlayerColorLabel.setFont(Font.font("Comic Sans MS",16));
        secondPlayerColorLabel.setPrefWidth(Double.MAX_VALUE);
        secondPlayerColorLabel.setAlignment(Pos.BASELINE_RIGHT);
        ColorPicker secondPlayerColorPicker = new ColorPicker();
        secondPlayerColorPicker.setValue(secondPlayerColor);
        secondPlayerColorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setColor(secondPlayerColorPicker.getValue(),2);
            }
        });

        Label soundSettingsLabel = new Label("Dźwięk:");
        soundSettingsLabel.setFont(Font.font("Comic Sans MS",20));
        soundSettingsLabel.setPrefWidth(Double.MAX_VALUE);
        soundSettingsLabel.setAlignment(Pos.CENTER);

        Label volumeSettingLabel = new Label("Głośność:  ");
        volumeSettingLabel.setFont(Font.font("Comic Sans MS",16));
        volumeSettingLabel.setPrefWidth(Double.MAX_VALUE);
        volumeSettingLabel.setAlignment(Pos.CENTER_RIGHT);

        Slider volumeSlider = new Slider();
        volumeSlider.setMaxWidth(150);
        volumeSlider.setMin(-80.0);
        volumeSlider.setMax(6.0);
        volumeSlider.setValue(volume);
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                volume = newValue.intValue();
                if(volume==0)
                    isSound = false;
                else
                    isSound = true;

            }
        });

        firstProgramRunCommandTextArea = new TextArea();
        firstProgramRunCommandTextArea.setText(firstPlayerRunCommand);
        firstProgramRunCommandTextArea.setMaxWidth(200);
        firstProgramRunCommandTextArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                firstPlayerRunCommand = newValue;
            }
        });
        if(firstPlayerProgramType==2) {
            firstProgramRunCommandTextArea.setEditable(true);
        }
        else {
            firstProgramRunCommandTextArea.setEditable(false);
        }
        secondProgramRunCommandTextArea = new TextArea();
        secondProgramRunCommandTextArea.setText(secondPlayerRunCommand);
        secondProgramRunCommandTextArea.setMaxWidth(200);
        secondProgramRunCommandTextArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                secondPlayerRunCommand = newValue;
            }
        });
        if(secondPlayerProgramType==2) {
            secondProgramRunCommandTextArea.setEditable(true);
        }
        else {
            secondProgramRunCommandTextArea.setEditable(false);
        }
        firstProgramNameLabel = new Label();
        firstProgramNameLabel.setAlignment(Pos.CENTER);
        firstProgramNameLabel.setPrefWidth(Double.MAX_VALUE);
        if (playerFirstFullPath.length() <= 30) {
            firstProgramNameLabel.setText(playerFirstFullPath);
        } else {
            firstProgramNameLabel.setText("..." + playerFirstFullPath.substring(playerFirstFullPath.length() - 30, playerFirstFullPath.length()));
        }
        secondProgramNameLabel = new Label();
        secondProgramNameLabel.setAlignment(Pos.CENTER);
        secondProgramNameLabel.setPrefWidth(Double.MAX_VALUE);
        secondProgramNameLabel.setText(playerSecondFullPath);
        if (playerSecondFullPath.length() <= 30) {
            secondProgramNameLabel.setText(playerSecondFullPath);
        } else {
            secondProgramNameLabel.setText("..." + playerSecondFullPath.substring(playerSecondFullPath.length() - 30, playerSecondFullPath.length()));
        }

        Label robotWarsLabel = new Label("Wojny Robotów:");
        robotWarsLabel.setFont(Font.font("Comic Sans MS",20));
        robotWarsLabel.setPrefWidth(Double.MAX_VALUE);
        robotWarsLabel.setAlignment(Pos.CENTER);

        HBox chooseFirstComputerPlayerHBox = new HBox();
        Button chooseFirstComputerPlayerButton = new Button("Pierwszy Gracz");
        chooseFirstComputerPlayerButton.setFont(Font.font("Comic Sans MS",14));
        chooseFirstComputerPlayerButton.setPrefWidth(150);
        //chooseFirstComputerPlayerButton.setPadding(new Insets(5,0,5,0));
        chooseFirstComputerPlayerHBox.getChildren().add(chooseFirstComputerPlayerButton);
        chooseFirstComputerPlayerHBox.setAlignment(Pos.BASELINE_RIGHT);

        chooseFirstComputerPlayerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
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
                    } catch (IOException ignored){

                    }
                }
            }
        });


        Label runAsFirstPlayerLabel = new Label("Uruchom jako:  ");
        runAsFirstPlayerLabel.setFont(Font.font("Comic Sans MS",16));
        runAsFirstPlayerLabel.setPrefWidth(Double.MAX_VALUE);
        runAsFirstPlayerLabel.setAlignment(Pos.BASELINE_RIGHT);

        Label parametersFirstPlayerLabel = new Label("Parametry uruchomienia:  ");
        parametersFirstPlayerLabel.setFont(Font.font("Comic Sans MS",16));
        parametersFirstPlayerLabel.setPrefWidth(Double.MAX_VALUE);
        parametersFirstPlayerLabel.setAlignment(Pos.BASELINE_RIGHT);

        HBox chooseSecondComputerPlayerHBox = new HBox();
        Button chooseSecondComputerPlayerButton = new Button("Drugi Gracz");
        chooseSecondComputerPlayerButton.setFont(Font.font("Comic Sans MS",14));
        chooseSecondComputerPlayerButton.setPrefWidth(150);
        //chooseSecondComputerPlayerButton.setPadding(new Insets(5,0,5,0));
        chooseSecondComputerPlayerHBox.getChildren().add(chooseSecondComputerPlayerButton);
        chooseSecondComputerPlayerHBox.setAlignment(Pos.BASELINE_RIGHT);
        chooseSecondComputerPlayerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser chooseFile = new FileChooser();
                chooseFile.setTitle("Wybierz komputer drugi");
                File openFile = chooseFile.showOpenDialog(Bricks.mainStage.mainStage);
                if(openFile != null) {
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
            }
        });

        Label runAsSecondPlayerLabel = new Label("Uruchom jako:  ");
        runAsSecondPlayerLabel.setFont(Font.font("Comic Sans MS",16));
        runAsSecondPlayerLabel.setPrefWidth(Double.MAX_VALUE);
        runAsSecondPlayerLabel.setAlignment(Pos.BASELINE_RIGHT);

        Label parametersSecondPlayerLabel = new Label("Parametry uruchomienia:  ");
        parametersSecondPlayerLabel.setFont(Font.font("Comic Sans MS",16));
        parametersSecondPlayerLabel.setPrefWidth(Double.MAX_VALUE);
        parametersSecondPlayerLabel.setAlignment(Pos.BASELINE_RIGHT);

        Label singPlayerComputerPlayerLabel = new Label("Gracz Komputerowy:  ");
        singPlayerComputerPlayerLabel.setFont(Font.font("Comic Sans MS",16));
        singPlayerComputerPlayerLabel.setPrefWidth(Double.MAX_VALUE);
        singPlayerComputerPlayerLabel.setAlignment(Pos.BASELINE_RIGHT);

        firstProgramTypeComboBox = new ComboBox<>();
        firstProgramTypeComboBox.getItems().add("Plik exe/out");
        firstProgramTypeComboBox.getItems().add("Plik class(java)");
        firstProgramTypeComboBox.getItems().add("Własne");

        firstProgramTypeComboBox.getSelectionModel().select(firstPlayerProgramType);
        if(firstPlayerProgramType==2) {
            chooseFirstComputerPlayerButton.setDisable(true);
            firstProgramRunCommandTextArea.setEditable(true);
        }
        else {
            chooseFirstComputerPlayerButton.setDisable(false);
            firstProgramRunCommandTextArea.setEditable(false);
        }
        firstProgramTypeComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                firstPlayerProgramType = firstProgramTypeComboBox.getSelectionModel().getSelectedIndex();
                if(firstPlayerProgramType==2) {
                    chooseFirstComputerPlayerButton.setDisable(true);
                    firstProgramRunCommandTextArea.setEditable(true);
                }
                else {
                    chooseFirstComputerPlayerButton.setDisable(false);
                    firstProgramRunCommandTextArea.setEditable(false);
                }
            }
        });


        secondProgramTypeComboBox = new ComboBox<>();
        secondProgramTypeComboBox.getItems().add("Plik exe/out");
        secondProgramTypeComboBox.getItems().add("Plik class(java)");
        secondProgramTypeComboBox.getItems().add("Własne");

        secondProgramTypeComboBox.getSelectionModel().select(secondPlayerProgramType);
        if(secondPlayerProgramType==2) {
            chooseSecondComputerPlayerButton.setDisable(true);
            secondProgramRunCommandTextArea.setEditable(true);
        }
        else {
            chooseSecondComputerPlayerButton.setDisable(false);
            secondProgramRunCommandTextArea.setEditable(false);
        }
        secondProgramTypeComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                secondPlayerProgramType = secondProgramTypeComboBox.getSelectionModel().getSelectedIndex();
                if(secondPlayerProgramType==2) {
                    chooseSecondComputerPlayerButton.setDisable(true);
                    secondProgramRunCommandTextArea.setEditable(true);
                }
                else {
                    chooseSecondComputerPlayerButton.setDisable(false);
                    secondProgramRunCommandTextArea.setEditable(false);
                }
            }
        });

        ComboBox<String> chooseCompterTypeComboBox = new ComboBox<>();

        chooseCompterTypeComboBox.getItems().add("Wbudowany");
        chooseCompterTypeComboBox.getItems().add("Komputer Pierwszy");
        chooseCompterTypeComboBox.getItems().add("Komputer Drugi");

        chooseCompterTypeComboBox.getSelectionModel().select(computerPlayerType);

        chooseCompterTypeComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                computerPlayerType = chooseCompterTypeComboBox.getSelectionModel().getSelectedIndex();
            }
        });

       /* HBox defaultSettingsHBOx = new HBox();
        defaultSettingsButton = new Button("Ustawienia Domyślne");
        defaultSettingsButton.setPrefWidth(300);
        defaultSettingsButton.setPrefHeight(Double.MAX_VALUE);
        defaultSettingsHBOx.setAlignment(Pos.CENTER);
        defaultSettingsHBOx.getChildren().add(defaultSettingsButton);
        HBox.setMargin(defaultSettingsButton,new Insets(5,0,5,0));*/

        HBox saveAndExitHBox = new HBox();
        saveAndExitButton = new Button("Zapisz Zmiany");
        saveAndExitButton.setPrefWidth(300);
        saveAndExitButton.setPrefHeight(Double.MAX_VALUE);
        saveAndExitHBox.setAlignment(Pos.CENTER);
        saveAndExitHBox.getChildren().add(saveAndExitButton);
        HBox.setMargin(saveAndExitButton,new Insets(5,0,5,0));
        HBox.setHgrow(saveAndExitButton,Priority.ALWAYS);
        mainGridPane.add(chooseCompterTypeComboBox,1,13);
        mainGridPane.add(singPlayerComputerPlayerLabel,0,13);
        mainGridPane.add(secondProgramRunCommandTextArea,1,12);
        mainGridPane.add(parametersSecondPlayerLabel,0,12);
        mainGridPane.add(secondProgramTypeComboBox,1,11);
        mainGridPane.add(runAsSecondPlayerLabel,0,11);
        mainGridPane.add(secondProgramNameLabel,1,10);
        mainGridPane.add(chooseSecondComputerPlayerHBox,0,10);
        mainGridPane.add(firstProgramRunCommandTextArea,1,9);
        mainGridPane.add(parametersFirstPlayerLabel,0,9);
        mainGridPane.add(firstProgramTypeComboBox,1,8);
        mainGridPane.add(runAsFirstPlayerLabel,0,8);
        mainGridPane.add(firstProgramNameLabel,1,7);
        mainGridPane.add(chooseFirstComputerPlayerHBox,0,7);
        mainGridPane.add(robotWarsLabel,0,6,2,1);
        mainGridPane.add(volumeSlider,1,5);
        mainGridPane.add(volumeSettingLabel,0,5);
        mainGridPane.add(soundSettingsLabel,0,4,2,1);
        mainGridPane.add(boardSizeLabel,0,1);
        mainGridPane.add(boardSizeComboBoxHBox,1,1);
        mainGridPane.add(firstPlayerColorLabel,0,2);
        mainGridPane.add(firstPlayerColorPicker,1,2);
        mainGridPane.add(secondPlayerColorLabel,0,3);
        mainGridPane.add(secondPlayerColorPicker,1,3);
        mainGridPane.add(generalSettingsLabel,0,0,2,1);
        //mainGridPane.add(defaultSettingsHBOx,0,15,2,1);
        mainGridPane.add(saveAndExitHBox,0,14,2,1);
        getChildren().add(mainGridPane);

        saveAndExitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Bricks.mainStage.setSettings(BoardSize,firstPlayerColor,secondPlayerColor,isSound,volume,debugMode,playerFirstFullPath,playerSecondFullPath,firstPlayerProgramType,secondPlayerProgramType,firstPlayerRunCommand,secondPlayerRunCommand,computerPlayerType);
                Bricks.mainStage.backToMenu();
            }
        });
        setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode()== KeyCode.ESCAPE) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Potwierdznie Wyjścia");
                    alert.setHeaderText("Chcesz wyjść z ustawień?");
                    alert.setContentText("Zmiany nie zostaną zapisane.");
                    Optional<ButtonType> result=  alert.showAndWait();
                    if(result.get() == ButtonType.OK){
                        Bricks.mainStage.backToMenu();
                    }
                }
            }
        });
        widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mainGridPane.setPrefWidth(newValue.doubleValue());
            }
        });
        heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mainGridPane.setPrefHeight(newValue.doubleValue());
            }
        });
    }
    private void setColor(Color newColor,int player) {
        if(player==1)
            firstPlayerColor = newColor;
        if(player==2)
            secondPlayerColor = newColor;
    }

    //private Button defaultSettingsButton;
    private Button saveAndExitButton;

    private int firstPlayerProgramType;
    private int secondPlayerProgramType;
    public int volume;
    public int computerPlayer;
    private int BoardSize;
    private int computerPlayerType;

    public boolean isSound;
    private boolean debugMode;

    public javafx.scene.paint.Color firstPlayerColor;
    public javafx.scene.paint.Color secondPlayerColor;

    private String playerFirstFullPath = "";
    private String playerSecondFullPath = "";
    private String firstPlayerRunCommand;
    private String secondPlayerRunCommand;

    //KOMPONENTY
    Label firstProgramNameLabel;
    Label secondProgramNameLabel;

    ComboBox<String> firstProgramTypeComboBox;
    ComboBox<String> secondProgramTypeComboBox;

    TextArea firstProgramRunCommandTextArea;
    TextArea secondProgramRunCommandTextArea;

}
