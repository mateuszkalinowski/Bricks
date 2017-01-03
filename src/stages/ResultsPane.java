package stages;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import core.Bricks;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.BoardLogic;
import logic.MovesStorage;
import logic.RobotPlayer;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
/**
 * Created by Mateusz on 03.01.2017.
 * Project Bricks
 */
class ResultsPane extends Pane {
    ResultsPane(double w,double h){
        GridPane mainGridPane = new GridPane();
        mainGridPane.setPrefWidth(w);
        mainGridPane.setPrefHeight(h);
        RowConstraints row = new RowConstraints();
        row.setPercentHeight(9.09);
        for(int i = 0; i < 11;i++) {
            mainGridPane.getRowConstraints().add(row);
        }
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(33.3);
        for(int i =0;i<3;i++) {
            mainGridPane.getColumnConstraints().add(column);
        }
        boardsSizesListView = new ListView<>();
        boardsSizesListView.setMaxWidth(Double.MAX_VALUE);
        boardsSizesListView.setMaxHeight(Double.MAX_VALUE);
        mainGridPane.add(boardsSizesListView,1,1,1,5);

        Label playersListLabel = new Label("Gracze:");
        playersListLabel.setAlignment(Pos.CENTER);
        playersListLabel.setMaxWidth(Double.MAX_VALUE);
        mainGridPane.add(playersListLabel,0,0,3,1);

        boardsSizesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


        Label resultsLabel = new Label("Wyniki:");
        resultsLabel.setMaxWidth(Double.MAX_VALUE);
        resultsLabel.setAlignment(Pos.CENTER);
        resultsLabel.setFont(new Font("Comic Sans MS",12));
        mainGridPane.add(resultsLabel,0,6,2,1);
        Label wonGames = new Label("Wygranych:");
        wonGames.setMaxWidth(Double.MAX_VALUE);
        wonGames.setAlignment(Pos.CENTER);
        wonGames.setFont(new Font("Comic Sans MS",12));
        mainGridPane.add(wonGames,0,7,2,1);
        Label lostGames = new Label("Przegranych:");
        lostGames.setMaxWidth(Double.MAX_VALUE);
        lostGames.setAlignment(Pos.CENTER);
        lostGames.setFont(new Font("Comic Sans MS",12));
        mainGridPane.add(lostGames,0,8,2,1);
        Label winsToAllLabel = new Label("Wygranych/Rozegranych:");
        winsToAllLabel.setMaxWidth(Double.MAX_VALUE);
        winsToAllLabel.setAlignment(Pos.CENTER);
        winsToAllLabel.setFont(new Font("Comic Sans MS",12));
        mainGridPane.add(winsToAllLabel,0,9,2,1);

        winsByFirstComputerLabel = new Label("0");
        winsByFirstComputerLabel.setMaxWidth(Double.MAX_VALUE);
        winsByFirstComputerLabel.setAlignment(Pos.CENTER);
        winsByFirstComputerLabel.setFont(new Font("Comic Sans MS",12));
        mainGridPane.add(winsByFirstComputerLabel,2,7);
        winsBySecondComputerLabel = new Label("0");
        winsBySecondComputerLabel.setMaxWidth(Double.MAX_VALUE);
        winsBySecondComputerLabel.setAlignment(Pos.CENTER);
        winsBySecondComputerLabel.setFont(new Font("Comic Sans MS",12));
        mainGridPane.add(winsBySecondComputerLabel,2,8);
        winsToAllResultLabel = new Label("0");
        winsToAllResultLabel.setMaxWidth(Double.MAX_VALUE);
        winsToAllResultLabel.setAlignment(Pos.CENTER);
        winsToAllResultLabel.setFont(new Font("Comic Sans MS",12));
        mainGridPane.add(winsToAllResultLabel,2,9);

        HBox clearLogsButtonHBox = new HBox();
        clearLogsButton = new Button("Wyczyść Wyniki");
        clearLogsButtonHBox.getChildren().add(clearLogsButton);
        clearLogsButtonHBox.setAlignment(Pos.CENTER);
        clearLogsButtonHBox.setPadding(new Insets(0,20,0,0));
        clearLogsButtonHBox.setAlignment(Pos.TOP_RIGHT);
        mainGridPane.add(clearLogsButtonHBox,2,10);

        clearLogsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boardsSizesListView.getItems().clear();
                winsByFirstComputerLabel.setText("0");
                winsBySecondComputerLabel.setText("0");
                winsToAllResultLabel.setText("0");
                try {
                    String pathToFile = System.getProperty("user.home") + "/Documents/Bricks/logs.txt";
                    PrintWriter writer = new PrintWriter(pathToFile);
                    writer.close();
                }
                catch (Exception e) {

                }
            }
        });


        widthProperty().addListener((observable, oldValue, newValue) -> {
            mainGridPane.setPrefWidth(newValue.doubleValue());
        });
        heightProperty().addListener((observable, oldValue, newValue) -> {
            mainGridPane.setPrefHeight(newValue.doubleValue());
            playersListLabel.setFont(Font.font("Comic Sans MS",newValue.doubleValue()/20));
            resultsLabel.setFont(Font.font("Comic Sans MS",newValue.doubleValue()/25));
            winsByFirstComputerLabel.setFont(Font.font("Comic Sans MS",newValue.doubleValue()/25));
            winsBySecondComputerLabel.setFont(Font.font("Comic Sans MS",newValue.doubleValue()/25));
            wonGames.setFont(Font.font("Comic Sans MS",newValue.doubleValue()/25));
            lostGames.setFont(Font.font("Comic Sans MS",newValue.doubleValue()/25));
            winsToAllLabel.setFont(Font.font("Comic Sans MS",newValue.doubleValue()/25));
            winsToAllResultLabel.setFont(Font.font("Comic Sans MS",newValue.doubleValue()/25));
            clearLogsButton.setFont(Font.font("Comic Sans MS",newValue.doubleValue()/50));
        });

        getChildren().add(mainGridPane);
        setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                alert.setTitle("Potwierdznie Wyjścia");
                alert.setHeaderText("Chcesz wyjść z \"Wyników\"");
                alert.setContentText("");
                Optional<ButtonType> result=  alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK){
                    try {
                        Bricks.firstRobotPlayer.reset(Bricks.mainStage.BoardSize);
                        Bricks.secondRobotPlayer.reset(Bricks.mainStage.BoardSize);
                    }
                    catch (Exception ignored){}
                    double height = Bricks.mainStage.mainStage.getHeight();
                    double width = Bricks.mainStage.mainStage.getWidth();
                    Bricks.mainStage.mainStage.setScene(Bricks.mainStage.sceneOfTheGame);
                    Bricks.mainStage.mainStage.setWidth(width);
                    Bricks.mainStage.mainStage.setHeight(height);
                    Bricks.mainStage.mainStage.show();
                }
            }
        });
        generateTable();
        boardsSizesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    winsByFirstComputerLabel.setText(winsMap.get(newValue).toString());
                    winsBySecondComputerLabel.setText(losesMap.get(newValue).toString());
                    double value = winsMap.get(newValue).doubleValue() / (losesMap.get(newValue) + winsMap.get(newValue));
                    value *= 100;
                    value = Math.round(value);
                    value /= 100;
                    winsToAllResultLabel.setText(value + "");
                }
                catch (NullPointerException e) {
                    winsByFirstComputerLabel.setText("0");
                    winsBySecondComputerLabel.setText("0");
                    winsToAllResultLabel.setText("0");
                }
            }
        });
    }

    public void generateTable() {
        try {
            String path = System.getProperty("user.home") + "/Documents/Bricks";
            if (!new File(path + "/logs.txt").exists()) {
                //TODO KOMUNIKAT O BRAKU DANYCH, MOŻE TUTAJ, MOŻE NIEPOZWOLIC NA OTWARCIE TEGO OKNA
            }
            else {
                String pathToFile = System.getProperty("user.home") + "/Documents/Bricks/logs.txt";
                Scanner in = new Scanner(new File(pathToFile));
                ArrayList<String> wyniki = new ArrayList<>();
                while(in.hasNextLine()) {
                    String line = in.nextLine();
                    if(line.charAt(0) != '#') {
                        wyniki.add(line);
                    }
                    else {
                        break;
                    }
                }
                in.close();

                winsMap= new HashMap<>();
                losesMap= new HashMap<>();
                winToAllMap = new TreeMap<>();

                for(String s : wyniki) {
                    try {
                        String[] firstDivision = s.split("=");
                        String[] secondDivisionNames = firstDivision[0].split(",");
                        String[] secondDivisionValues = firstDivision[1].split(",");
                        if(secondDivisionNames.length!=2 || secondDivisionValues.length!=2)
                            continue;
                        winsMap.putIfAbsent(secondDivisionNames[0],0);
                        winsMap.putIfAbsent(secondDivisionNames[1],0);
                        winsMap.put(secondDivisionNames[0],winsMap.get(secondDivisionNames[0])+Integer.parseInt(secondDivisionValues[0]));
                        winsMap.put(secondDivisionNames[1],winsMap.get(secondDivisionNames[1])+Integer.parseInt(secondDivisionValues[1]));

                        losesMap.putIfAbsent(secondDivisionNames[0],0);
                        losesMap.putIfAbsent(secondDivisionNames[1],0);
                        losesMap.put(secondDivisionNames[0],losesMap.get(secondDivisionNames[0])+Integer.parseInt(secondDivisionValues[1]));
                        losesMap.put(secondDivisionNames[1],losesMap.get(secondDivisionNames[1])+Integer.parseInt(secondDivisionValues[0]));



                    }
                    catch (IndexOutOfBoundsException | NumberFormatException ignored){
                    }
                }

                for(String key : winsMap.keySet()) {
                    winToAllMap.put(losesMap.get(key).doubleValue()/(winsMap.get(key).doubleValue()+losesMap.get(key).doubleValue()),key);
                }
                for(Double key : winToAllMap.keySet()) {
                    boardsSizesListView.getItems().add(winToAllMap.get(key));
                }
            }

        } catch (IOException ignored){}

    }
    Map<String,Integer> winsMap;
    Map<String,Integer> losesMap;
    Map<Double,String> winToAllMap;

    private ListView<String> boardsSizesListView;

    private  Label winsByFirstComputerLabel;
    private Label winsBySecondComputerLabel;
    private Label winsToAllResultLabel;

    private Button clearLogsButton;

}
