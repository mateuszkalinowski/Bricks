package frames;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * Created by Mateusz on 18.12.2016.
 * Project InferenceEngine
 */
public class MainStage extends Application {
    public void start(Stage primaryStage) {
        BorderPane mainBorderPane = new BorderPane();
        GridPane mainGridPane = new GridPane();
        RowConstraints rowInMainMenu = new RowConstraints();
        rowInMainMenu.setPercentHeight(14);
        //mainGridPane.setGridLinesVisible(true);
        for(int i = 0; i < 7; i++)
            mainGridPane.getRowConstraints().add(rowInMainMenu);
        ColumnConstraints columnInMainMenu = new ColumnConstraints();
        columnInMainMenu.setPercentWidth(100);
        mainGridPane.getColumnConstraints().add(columnInMainMenu);

        Label bricksTitleLabel = new Label();
        bricksTitleLabel.setText("Bricks");
        bricksTitleLabel.setFont(new Font("Comic Sans MS",102));
        bricksTitleLabel.setMaxWidth(Double.MAX_VALUE);
        bricksTitleLabel.setAlignment(Pos.CENTER);

        HBox singlePlayerGameButtonHBox = new HBox();
        singlePlayerGameButtonHBox.setAlignment(Pos.CENTER);
        Button singlePlayerGameButton = new Button("Gra Jednoosobowa");
        singlePlayerGameButton.setMaxWidth(420);
        singlePlayerGameButton.setMaxHeight(Double.MAX_VALUE);
        singlePlayerGameButton.setFont(new Font("Comic Sans MS",28));
        singlePlayerGameButtonHBox.getChildren().add(singlePlayerGameButton);
        HBox.setMargin(singlePlayerGameButton, new Insets(10,0,10,0));
        HBox.setHgrow(singlePlayerGameButton,Priority.ALWAYS);

        HBox twoPlayersGameButtonHBox = new HBox();
        twoPlayersGameButtonHBox.setAlignment(Pos.CENTER);
        Button twoPlayersGameButton = new Button("Gra Dwuosobowa");
        twoPlayersGameButton.setMaxWidth(420);
        twoPlayersGameButton.setMaxHeight(Double.MAX_VALUE);
        twoPlayersGameButton.setFont(new Font("Comic Sans MS",28));
        twoPlayersGameButtonHBox.getChildren().add(twoPlayersGameButton);
        HBox.setMargin(twoPlayersGameButton, new Insets(10,0,10,0));
        HBox.setHgrow(twoPlayersGameButton,Priority.ALWAYS);

        HBox robotWarsButtonHBox = new HBox();
        robotWarsButtonHBox.setAlignment(Pos.CENTER);
        Button robotWarsButton = new Button("Wojna Robotów");
        robotWarsButton.setMaxWidth(420);
        robotWarsButton.setMaxHeight(Double.MAX_VALUE);
        robotWarsButton.setFont(new Font("Comic Sans MS",28));
        robotWarsButtonHBox.getChildren().add(robotWarsButton);
        HBox.setMargin(robotWarsButton, new Insets(10,0,10,0));
        HBox.setHgrow(robotWarsButton,Priority.ALWAYS);

        HBox optionsButtonHBox = new HBox();
        optionsButtonHBox.setAlignment(Pos.CENTER);
        Button optionsButton = new Button("Opcje");
        optionsButton.setMaxWidth(420);
        optionsButton.setMaxHeight(Double.MAX_VALUE);
        optionsButton.setFont(new Font("Comic Sans MS",28));
        optionsButtonHBox.getChildren().add(optionsButton);
        HBox.setMargin(optionsButton, new Insets(10,0,10,0));
        HBox.setHgrow(optionsButton,Priority.ALWAYS);

        HBox exitButtonHBox = new HBox();
        exitButtonHBox.setAlignment(Pos.CENTER);
        Button exitButton = new Button("Wyjście");
        exitButton.setMaxWidth(420);
        exitButton.setMaxHeight(Double.MAX_VALUE);
        exitButton.setFont(new Font("Comic Sans MS",28));
        exitButtonHBox.getChildren().add(exitButton);
        HBox.setMargin(exitButton, new Insets(10,0,10,0));
        HBox.setHgrow(exitButton,Priority.ALWAYS);

        mainGridPane.add(bricksTitleLabel,0,0,1,2);
        mainGridPane.add(singlePlayerGameButtonHBox,0,2);
        mainGridPane.add(twoPlayersGameButtonHBox,0,3);
        mainGridPane.add(robotWarsButtonHBox,0,4);
        mainGridPane.add(optionsButtonHBox,0,5);
        mainGridPane.add(exitButtonHBox,0,6);
        mainBorderPane.setCenter(mainGridPane);

        Label programInfoLabel = new Label();
        programInfoLabel.setMaxWidth(Double.MAX_VALUE);
        programInfoLabel.setAlignment(Pos.CENTER);
        programInfoLabel.setText("Autorzy: Mateusz Kalinowski, Michał Romaszko \nWersja 1.3.0");
        programInfoLabel.setTextAlignment(TextAlignment.CENTER);

        mainBorderPane.setBottom(programInfoLabel);
        Scene mainScene = new Scene(mainBorderPane, 500, 650);
        mainStage = primaryStage;
        mainStage.setTitle("Bricks");
        mainStage.setScene(mainScene);
        mainStage.show();
    }
    private Stage mainStage;
}
