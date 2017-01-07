package stages;

import XClasses.XResults;
import XClasses.XRobotPlayer;
import core.Bricks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by Mateusz on 03.01.2017.
 * Project Bricks
 */
class ResultsPane extends Pane {
    ResultsPane(double w, double h) {
        GridPane mainGridPane = new GridPane();
        mainGridPane.setPrefWidth(w);
        mainGridPane.setPrefHeight(h);
        RowConstraints row = new RowConstraints();
        row.setPercentHeight(9.09);
        for (int i = 0; i < 11; i++) {
            mainGridPane.getRowConstraints().add(row);
        }
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(14.28);
        for (int i = 0; i < 7; i++) {
            mainGridPane.getColumnConstraints().add(column);
        }
       /* boardsSizesListView = new ListView<>();
        boardsSizesListView.setMaxWidth(Double.MAX_VALUE);
        boardsSizesListView.setMaxHeight(Double.MAX_VALUE);
        mainGridPane.add(boardsSizesListView, 1, 1, 1, 5);*/

        Label playersListLabel = new Label("Gracze:");
        playersListLabel.setAlignment(Pos.CENTER);
        playersListLabel.setMaxWidth(Double.MAX_VALUE);
        mainGridPane.add(playersListLabel, 0, 0, 7, 1);

        //boardsSizesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        HBox clearLogsButtonHBox = new HBox();
        clearLogsButton = new Button("Wyczyść Wyniki");
        clearLogsButtonHBox.getChildren().add(clearLogsButton);
        clearLogsButtonHBox.setAlignment(Pos.CENTER);
        clearLogsButtonHBox.setPadding(new Insets(0, 20, 0, 0));
        clearLogsButtonHBox.setAlignment(Pos.CENTER_RIGHT);
        clearLogsButtonHBox.setSpacing(10);
        mainGridPane.add(clearLogsButtonHBox, 4, 9, 3, 2);
        backButton = new Button("Cofnij");
        clearLogsButtonHBox.getChildren().add(backButton);
        backButton.setOnAction(event -> {
            GamesPane gamesPane = new GamesPane(getWidth(), getHeight());
            Scene gamesScene = new Scene(gamesPane, getWidth(), getHeight());
            gamesScene.getStylesheets().add(Bricks.mainStage.selectedTheme);
            Bricks.mainStage.mainStage.setScene(gamesScene);
            Bricks.mainStage.mainStage.show();
        });
        resultsObservableList = FXCollections.observableArrayList();
        clearLogsButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
            alert.setTitle("Potwieredznie usuwania wyników");
            alert.setHeaderText("Chcesz usunąć zapisane wyniki?");
            alert.setTitle("Potwierdzenie");
            alert.setContentText("Ten operacji nie można cofnąć.");
            ButtonType buttonYes = new ButtonType("Tak");
            ButtonType buttonNo = new ButtonType("Anuluj");
            alert.getButtonTypes().setAll(buttonNo, buttonYes);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonYes) {
                resultsObservableList.clear();
                try {
                    String pathToFile = System.getProperty("user.home") + "/Documents/Bricks/logs.txt";
                    PrintWriter writer = new PrintWriter(pathToFile);
                    dataToWinSeries.getData().clear();
                    dataToLostSeries.getData().clear();
                    dataToWinToPlayedSeries.getData().clear();
                    writer.print("");
                    writer.close();
                } catch (Exception ignored) {
                }
            }
        });
        playersTableView = new TableView<>();

        TableColumn nameColumn = new TableColumn("Program");
        //noinspection unchecked
        nameColumn.setCellValueFactory(new PropertyValueFactory<XResults, String>("name"));
        nameColumn.prefWidthProperty().bind(playersTableView.widthProperty().divide(4));

        TableColumn winColumn = new TableColumn("Wygrane");
        //noinspection unchecked
        winColumn.setCellValueFactory(new PropertyValueFactory<XResults, String>("wins"));
        winColumn.prefWidthProperty().bind(playersTableView.widthProperty().divide(4));

        TableColumn lostColumn = new TableColumn("Przegrane");
        //noinspection unchecked
        lostColumn.setCellValueFactory(new PropertyValueFactory<XResults, String>("lost"));
        lostColumn.prefWidthProperty().bind(playersTableView.widthProperty().divide(4));

        TableColumn winToAllColumn = new TableColumn("Wygrane/Rozegrane");
        //noinspection unchecked
        winToAllColumn.setCellValueFactory(new PropertyValueFactory<XResults, String>("winsToAll"));
        winToAllColumn.prefWidthProperty().bind(playersTableView.widthProperty().divide(4));

        playersTableView.setItems(resultsObservableList);
        playersTableView.getColumns().addAll(nameColumn, winColumn, lostColumn,winToAllColumn);

        playersTableView.setColumnResizePolicy((param) -> false);
        //playersTableView.setSortPolicy((param) -> false);

        mainGridPane.add(playersTableView,1,1,5,8);

        widthProperty().addListener((observable, oldValue, newValue) -> {
            mainGridPane.setPrefWidth(newValue.doubleValue());
            mainTabPane.setPrefWidth(newValue.doubleValue());
        });
        heightProperty().addListener((observable, oldValue, newValue) -> {
            mainGridPane.setPrefHeight(newValue.doubleValue());
            playersListLabel.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 20));
            clearLogsButton.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 50));
            backButton.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 50));
            mainTabPane.setPrefHeight(newValue.doubleValue());
        });
        mainTabPane = new TabPane();
        Tab dataTab = new Tab("Dane");
        dataTab.setContent(mainGridPane);
        dataTab.setClosable(false);
        mainTabPane.getTabs().add(dataTab);
        BorderPane wintoplayedchartPane = new BorderPane();
        final CategoryAxis xWinToPlayedAxis = new CategoryAxis();
        final NumberAxis yWinToPlayedAxis = new NumberAxis();
        final BarChart<String, Number> wintoplayedChart = new BarChart<String, Number>(xWinToPlayedAxis, yWinToPlayedAxis);
        dataToWinToPlayedSeries = new XYChart.Series();
        dataToWinToPlayedSeries.setName("Wygranych/Rozegranych");
        wintoplayedChart.getData().addAll(dataToWinToPlayedSeries);
        wintoplayedchartPane.setCenter(wintoplayedChart);
        Tab wintoplayedchartTab = new Tab("Wykres - Wygrane/Rozegrane");
        wintoplayedchartTab.setContent(wintoplayedchartPane);
        wintoplayedchartTab.setClosable(false);

        BorderPane winandlostPane = new BorderPane();

        final CategoryAxis xWinAndLostAxis = new CategoryAxis();
        final NumberAxis yWinAndLostAxis = new NumberAxis();

        final BarChart<String, Number> winandlostChart = new BarChart<String, Number>(xWinAndLostAxis, yWinAndLostAxis);
        dataToWinSeries = new XYChart.Series();
        dataToWinSeries.setName("Wygrane");
        dataToLostSeries = new XYChart.Series();
        dataToLostSeries.setName("Przegrane");

        winandlostChart.getData().addAll(dataToWinSeries, dataToLostSeries);

        winandlostPane.setCenter(winandlostChart);

        Tab winandlosttab = new Tab("Wykres - Wygrane i Przegrane");
        winandlosttab.setContent(winandlostPane);
        winandlosttab.setClosable(false);

        generateTable();
        mainTabPane.getTabs().add(wintoplayedchartTab);
        mainTabPane.getTabs().add(winandlosttab);
        getChildren().add(mainTabPane);

        setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                alert.setTitle("Potwierdzenie Wyjścia");
                alert.setHeaderText("Chcesz wrócić do rozgrywki?");
                alert.setContentText("Żadne dane z tego okna nie zostaną utracone, mogą zostać jednak zmienione wraz z kolejnymi grami.");
                ButtonType buttonYes = new ButtonType("Tak");
                ButtonType buttonNo = new ButtonType("Anuluj");
                alert.getButtonTypes().setAll(buttonNo, buttonYes);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == buttonYes) {
                    GamesPane gamesPane = new GamesPane(getWidth(), getHeight());
                    Scene gamesScene = new Scene(gamesPane, getWidth(), getHeight());
                    gamesScene.getStylesheets().add(Bricks.mainStage.selectedTheme);
                    Bricks.mainStage.mainStage.setScene(gamesScene);
                    Bricks.mainStage.mainStage.show();
                }
            }
        });
       /* boardsSizesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                winsByFirstComputerLabel.setText(winsMap.get(newValue).toString());
                winsBySecondComputerLabel.setText(losesMap.get(newValue).toString());
                double value = winsMap.get(newValue).doubleValue() / (losesMap.get(newValue) + winsMap.get(newValue));
                value *= 100;
                value = Math.round(value);
                value /= 100;
                winsToAllResultLabel.setText(value + "");
            } catch (NullPointerException e) {
                winsByFirstComputerLabel.setText("0");
                winsBySecondComputerLabel.setText("0");
                winsToAllResultLabel.setText("0");
            }
        });*/
    }

    private void generateTable() {
        try {
            String path = System.getProperty("user.home") + "/Documents/Bricks";
            if (new File(path + "/logs.txt").exists()) {
                String pathToFile = System.getProperty("user.home") + "/Documents/Bricks/logs.txt";
                Scanner in = new Scanner(new File(pathToFile));
                ArrayList<String> wyniki = new ArrayList<>();
                while (in.hasNextLine()) {
                    String line = in.nextLine();
                    if (line.charAt(0) != '#') {
                        wyniki.add(line);
                    } else {
                        break;
                    }
                }
                in.close();

                winsMap = new HashMap<>();
                losesMap = new HashMap<>();
                Map<String, Double> winToAllMap = new HashMap<>();

                for (String s : wyniki) {
                    try {
                        String[] firstDivision = s.split("=");
                        String[] secondDivisionNames = firstDivision[0].split(",");
                        String[] secondDivisionValues = firstDivision[1].split(",");
                        if (secondDivisionNames.length != 2 || secondDivisionValues.length != 2)
                            continue;
                        winsMap.putIfAbsent(secondDivisionNames[0], 0);
                        winsMap.putIfAbsent(secondDivisionNames[1], 0);
                        winsMap.put(secondDivisionNames[0], winsMap.get(secondDivisionNames[0]) + Integer.parseInt(secondDivisionValues[0]));
                        winsMap.put(secondDivisionNames[1], winsMap.get(secondDivisionNames[1]) + Integer.parseInt(secondDivisionValues[1]));

                        losesMap.putIfAbsent(secondDivisionNames[0], 0);
                        losesMap.putIfAbsent(secondDivisionNames[1], 0);
                        losesMap.put(secondDivisionNames[0], losesMap.get(secondDivisionNames[0]) + Integer.parseInt(secondDivisionValues[1]));
                        losesMap.put(secondDivisionNames[1], losesMap.get(secondDivisionNames[1]) + Integer.parseInt(secondDivisionValues[0]));
                    } catch (IndexOutOfBoundsException | NumberFormatException ignored) {
                    }
                }

                for (String key : winsMap.keySet()) {
                    winToAllMap.put(key, losesMap.get(key).doubleValue() / (winsMap.get(key).doubleValue() + losesMap.get(key).doubleValue()));
                }

                while (!winToAllMap.isEmpty()) {
                    String maxKey = "";
                    for (String key : winToAllMap.keySet()) {
                        if (maxKey.equals(""))
                            maxKey = key;
                        else {
                            if (winToAllMap.get(key) < winToAllMap.get(maxKey))
                                maxKey = key;
                        }
                    }
                    winToAllMap.remove(maxKey);
                    //boardsSizesListView.getItems().add(maxKey);
                    resultsObservableList.add(new XResults(maxKey,winsMap.get(maxKey),losesMap.get(maxKey)));
                    double value = (winsMap.get(maxKey).doubleValue() / (winsMap.get(maxKey).doubleValue() + losesMap.get(maxKey).doubleValue()));
                    dataToWinToPlayedSeries.getData().add(new XYChart.Data(maxKey, value));
                    dataToWinSeries.getData().add(new XYChart.Data(maxKey, winsMap.get(maxKey)));
                    dataToLostSeries.getData().add(new XYChart.Data(maxKey, losesMap.get(maxKey)));
                }
            }

        } catch (IOException ignored) {
        }

    }

    TabPane mainTabPane;
    private Map<String, Integer> winsMap;
    private Map<String, Integer> losesMap;

    //private ListView<String> boardsSizesListView;
    private TableView<XResults> playersTableView;

    private final ObservableList<XResults> resultsObservableList;

    private Label winsByFirstComputerLabel;
    private Label winsBySecondComputerLabel;
    private Label winsToAllResultLabel;

    private Button clearLogsButton;

    private XYChart.Series dataToWinToPlayedSeries;

    private XYChart.Series dataToWinSeries;
    private XYChart.Series dataToLostSeries;

    private Button backButton;
}
