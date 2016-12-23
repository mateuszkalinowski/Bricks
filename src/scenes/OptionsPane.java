package scenes;

import core.Bricks;
import core.Settings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

/**
 * Created by Mateusz on 23.12.2016.
 * Project Bricks
 */
public class OptionsPane extends Pane {
    public OptionsPane(double w,double h){
        GridPane mainGridPane = new GridPane();
        mainGridPane.setPrefHeight(h);
        mainGridPane.setPrefWidth(w);
        //mainGridPane.setGridLinesVisible(true);
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(50);
        mainGridPane.getColumnConstraints().add(column);
        mainGridPane.getColumnConstraints().add(column);
        RowConstraints row = new RowConstraints();
        row.setPercentHeight(6);
        for(int i = 0; i < 17;i++) {
            mainGridPane.getRowConstraints().add(row);
        }
        Label settingsLabel = new Label("Ustawienia:");
        settingsLabel.setFont(Font.font("Comic Sans MS",26));
        settingsLabel.setPrefWidth(Double.MAX_VALUE);
        settingsLabel.setAlignment(Pos.CENTER);

        Label generalSettingsLabel = new Label("Ogólne:");
        generalSettingsLabel.setFont(Font.font("Comic Sans MS",24));
        generalSettingsLabel.setPrefWidth(Double.MAX_VALUE);
        generalSettingsLabel.setAlignment(Pos.CENTER);

        Label boardSizeLabel = new Label("Rozmiar Planszy:");
        boardSizeLabel.setFont(Font.font("Comic Sans MS",18));
        boardSizeLabel.setPrefWidth(Double.MAX_VALUE);
        boardSizeLabel.setAlignment(Pos.BASELINE_RIGHT);

        Label firstPlayerColorLabel = new Label("Kolor pierwszego gracza:");
        firstPlayerColorLabel.setFont(Font.font("Comic Sans MS",18));
        firstPlayerColorLabel.setPrefWidth(Double.MAX_VALUE);
        firstPlayerColorLabel.setAlignment(Pos.BASELINE_RIGHT);

        Label secondPlayerColorLabel = new Label("Kolor drugiego gracza:");
        secondPlayerColorLabel.setFont(Font.font("Comic Sans MS",18));
        secondPlayerColorLabel.setPrefWidth(Double.MAX_VALUE);
        secondPlayerColorLabel.setAlignment(Pos.BASELINE_RIGHT);

        Label soundSettingsLabel = new Label("Dźwięk:");
        soundSettingsLabel.setFont(Font.font("Comic Sans MS",24));
        soundSettingsLabel.setPrefWidth(Double.MAX_VALUE);
        soundSettingsLabel.setAlignment(Pos.CENTER);

        Label robotWarsLabel = new Label("Wojny Robotów:");
        robotWarsLabel.setFont(Font.font("Comic Sans MS",24));
        robotWarsLabel.setPrefWidth(Double.MAX_VALUE);
        robotWarsLabel.setAlignment(Pos.CENTER);

        HBox chooseFirstComputerPlayerHBox = new HBox();
        Button chooseFirstComputerPlayerButton = new Button("Pierwszy Gracz");
        chooseFirstComputerPlayerButton.setFont(Font.font("Comic Sans MS",14));
        chooseFirstComputerPlayerButton.setPrefWidth(150);
        chooseFirstComputerPlayerButton.setPadding(new Insets(5,0,5,0));
        chooseFirstComputerPlayerHBox.getChildren().add(chooseFirstComputerPlayerButton);
        chooseFirstComputerPlayerHBox.setAlignment(Pos.BASELINE_RIGHT);

        Label runAsFirstPlayerLabel = new Label("Uruchom jako:");
        runAsFirstPlayerLabel.setFont(Font.font("Comic Sans MS",18));
        runAsFirstPlayerLabel.setPrefWidth(Double.MAX_VALUE);
        runAsFirstPlayerLabel.setAlignment(Pos.BASELINE_RIGHT);

        Label parametersFirstPlayerLabel = new Label("Parametry uruchomienia:");
        parametersFirstPlayerLabel.setFont(Font.font("Comic Sans MS",18));
        parametersFirstPlayerLabel.setPrefWidth(Double.MAX_VALUE);
        parametersFirstPlayerLabel.setAlignment(Pos.BASELINE_RIGHT);

        HBox chooseSecondComputerPlayerHBox = new HBox();
        Button chooseSecondComputerPlayerButton = new Button("Drugi Gracz");
        chooseSecondComputerPlayerButton.setFont(Font.font("Comic Sans MS",14));
        chooseSecondComputerPlayerButton.setPrefWidth(150);
        chooseSecondComputerPlayerButton.setPadding(new Insets(5,0,5,0));
        chooseSecondComputerPlayerHBox.getChildren().add(chooseSecondComputerPlayerButton);
        chooseSecondComputerPlayerHBox.setAlignment(Pos.BASELINE_RIGHT);

        Label runAsSecondPlayerLabel = new Label("Uruchom jako:");
        runAsSecondPlayerLabel.setFont(Font.font("Comic Sans MS",18));
        runAsSecondPlayerLabel.setPrefWidth(Double.MAX_VALUE);
        runAsSecondPlayerLabel.setAlignment(Pos.BASELINE_RIGHT);

        Label parametersSecondPlayerLabel = new Label("Parametry uruchomienia:");
        parametersSecondPlayerLabel.setFont(Font.font("Comic Sans MS",18));
        parametersSecondPlayerLabel.setPrefWidth(Double.MAX_VALUE);
        parametersSecondPlayerLabel.setAlignment(Pos.BASELINE_RIGHT);

        Label singPlayerComputerPlayerLabel = new Label("Gracz Komputerowy:");
        singPlayerComputerPlayerLabel.setFont(Font.font("Comic Sans MS",18));
        singPlayerComputerPlayerLabel.setPrefWidth(Double.MAX_VALUE);
        singPlayerComputerPlayerLabel.setAlignment(Pos.BASELINE_RIGHT);

        HBox defaultSettingsHBOx = new HBox();
        defaultSettingsButton = new Button("Ustawienia Domyślne");
        defaultSettingsButton.setPrefWidth(300);
        defaultSettingsButton.setPrefHeight(Double.MAX_VALUE);
        defaultSettingsHBOx.setAlignment(Pos.CENTER);
        defaultSettingsHBOx.getChildren().add(defaultSettingsButton);
        HBox.setMargin(defaultSettingsButton,new Insets(5,0,5,0));

        HBox saveAndExitHBox = new HBox();
        saveAndExitButton = new Button("Zapisz Zmiany");
        saveAndExitButton.setPrefWidth(300);
        saveAndExitButton.setPrefHeight(Double.MAX_VALUE);
        saveAndExitHBox.setAlignment(Pos.CENTER);
        saveAndExitHBox.getChildren().add(saveAndExitButton);
        HBox.setMargin(saveAndExitButton,new Insets(5,0,5,0));
        HBox.setHgrow(saveAndExitButton,Priority.ALWAYS);
        mainGridPane.add(singPlayerComputerPlayerLabel,0,14);
        mainGridPane.add(parametersSecondPlayerLabel,0,13);
        mainGridPane.add(runAsSecondPlayerLabel,0,12);
        mainGridPane.add(chooseSecondComputerPlayerHBox,0,11);
        mainGridPane.add(parametersFirstPlayerLabel,0,10);
        mainGridPane.add(runAsFirstPlayerLabel,0,9);
        mainGridPane.add(chooseFirstComputerPlayerHBox,0,8);
        mainGridPane.add(robotWarsLabel,0,7,2,1);
        mainGridPane.add(soundSettingsLabel,0,5,2,1);
        mainGridPane.add(boardSizeLabel,0,2);
        mainGridPane.add(firstPlayerColorLabel,0,3);
        mainGridPane.add(secondPlayerColorLabel,0,4);
        mainGridPane.add(settingsLabel,0,0,2,1);
        mainGridPane.add(generalSettingsLabel,0,1,2,1);
        mainGridPane.add(defaultSettingsHBOx,0,15,2,1);
        mainGridPane.add(saveAndExitHBox,0,16,2,1);
        getChildren().add(mainGridPane);

        saveAndExitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Bricks.mainStage.backToMenu();
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

    private Button defaultSettingsButton;
    private Button saveAndExitButton;

}
