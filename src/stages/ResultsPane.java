package stages;

import XClasses.XDetailedResults;
import XClasses.XResults;
import core.Bricks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
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
        Label playersListLabel = new Label("Gracze:");
        playersListLabel.setAlignment(Pos.CENTER);
        playersListLabel.setMaxWidth(Double.MAX_VALUE);
        mainGridPane.add(playersListLabel, 0, 0, 7, 1);
        HBox clearLogsButtonHBox = new HBox();
        exportLogsButton = new Button("Eksportuj Wyniki");
        clearLogsButton = new Button("Wyczyść Wyniki");
        clearLogsButtonHBox.getChildren().add(exportLogsButton);
        clearLogsButtonHBox.getChildren().add(clearLogsButton);
        clearLogsButtonHBox.setAlignment(Pos.CENTER);
        clearLogsButtonHBox.setPadding(new Insets(0, 20, 0, 0));
        clearLogsButtonHBox.setAlignment(Pos.CENTER_RIGHT);
        clearLogsButtonHBox.setSpacing(10);
        mainGridPane.add(clearLogsButtonHBox, 1, 9, 6, 2);
        backButton = new Button("Powrót");
        clearLogsButtonHBox.getChildren().add(backButton);
        backButton.setOnAction(event -> {
            double width = Bricks.mainStage.mainStage.getWidth();
            double height = Bricks.mainStage.mainStage.getHeight();
            Bricks.mainStage.mainStage.setScene(Bricks.mainStage.gameChooserPane.gamesScene);
            Bricks.mainStage.mainStage.setWidth(width);
            Bricks.mainStage.mainStage.setHeight(height);
            Bricks.mainStage.mainStage.show();
        });
        resultsObservableList = FXCollections.observableArrayList();
       // detailedResultsObservableList = FXCollections.observableArrayList();
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

        exportLogsButton.setOnAction(event -> {
            FileChooser chooseFile = new FileChooser();
            chooseFile.setTitle("Wybierz lokalizacje zapisu");
            chooseFile.setInitialFileName("wyniki.txt");
            File saveFile = chooseFile.showSaveDialog(Bricks.mainStage.mainStage);
            if (saveFile != null) {
                try {
                    PrintWriter writer = new PrintWriter(saveFile);

                    for(XResults e : resultsObservableList) {
                        writer.println("Gracz: " + e.getName());
                        writer.println("wygrane: " + e.getWins());
                        writer.println("przegrane: " + e.getLost());
                        writer.println("wygrane/rozegrane: " + e.getWinsToAll());
                        writer.println("");
                    }

                    writer.close();
                } catch (FileNotFoundException ignored) {

                }
            }
        });

        TableView<XResults> playersTableView = new TableView<>();
        playersTableView.editableProperty().set(false);

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

            for(Label l : detailsLabelArrayList) {
                l.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 20));
            }

            clearLogsButton.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 50));
            backButton.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 50));
            exportLogsButton.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 50));
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
        final BarChart<String, Number> wintoplayedChart = new BarChart<>(xWinToPlayedAxis, yWinToPlayedAxis);
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

        final BarChart<String, Number> winandlostChart = new BarChart<>(xWinAndLostAxis, yWinAndLostAxis);
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
                double width = Bricks.mainStage.mainStage.getWidth();
                double height = Bricks.mainStage.mainStage.getHeight();
                Bricks.mainStage.mainStage.setScene(Bricks.mainStage.gameChooserPane.gamesScene);
                Bricks.mainStage.mainStage.setWidth(width);
                Bricks.mainStage.mainStage.setHeight(height);
                Bricks.mainStage.mainStage.show();
            }
        });

        playersTableView.setOnMouseClicked(event -> {
            if(event.getClickCount()==2 && playersTableView.getSelectionModel().getSelectedItem()!=null) {

                for(int i = 3; i < mainTabPane.getTabs().size();i++) {
                    if(mainTabPane.getTabs().get(i).getText().equals(playersTableView.getSelectionModel().getSelectedItem().getName() + "-szczegóły")) {
                        mainTabPane.getSelectionModel().select(i);
                        return;
                    }
                }

                Tab detailedResults = new Tab(playersTableView.getSelectionModel().getSelectedItem().getName() + "-szczegóły");

                GridPane detailedResultsGridPane = new GridPane();

                RowConstraints rowInDetails = new RowConstraints();
                rowInDetails.setPercentHeight(9.09);
                for (int i = 0; i < 11; i++) {
                    detailedResultsGridPane.getRowConstraints().add(rowInDetails);
                }
                ColumnConstraints columnInDetails = new ColumnConstraints();
                columnInDetails.setPercentWidth(14.28);
                for (int i = 0; i < 7; i++) {
                    detailedResultsGridPane.getColumnConstraints().add(columnInDetails);
                }
                detailedResults.setContent(detailedResultsGridPane);

                Label detailsLabel = new Label("Wyniki szczegółowe:");
                detailsLabel.setAlignment(Pos.CENTER);
                detailsLabel.setMaxWidth(Double.MAX_VALUE);
                detailedResultsGridPane.add(detailsLabel, 0, 0, 7, 1);
                detailsLabel.setFont(Font.font("Comic Sans MS", getHeight() / 20));
                detailsLabelArrayList.add(detailsLabel);

                ObservableList<XDetailedResults> detailedResultsObservableList = FXCollections.observableArrayList();

                TableView<XDetailedResults> playersTableViewInDetailed = new TableView<>();
                playersTableViewInDetailed.editableProperty().set(false);

                TableColumn nameColumnInDetailed = new TableColumn("Z Programem");
                //noinspection unchecked
                nameColumnInDetailed.setCellValueFactory(new PropertyValueFactory<XResults, String>("name"));
                nameColumnInDetailed.prefWidthProperty().bind(playersTableViewInDetailed.widthProperty().divide(3));

                TableColumn winColumnInDetailed = new TableColumn("Wygranych");
                //noinspection unchecked
                winColumnInDetailed.setCellValueFactory(new PropertyValueFactory<XResults, String>("wins"));
                winColumnInDetailed.prefWidthProperty().bind(playersTableViewInDetailed.widthProperty().divide(3));

                TableColumn lostColumnInDetailed = new TableColumn("Przegranych");
                //noinspection unchecked
                lostColumnInDetailed.setCellValueFactory(new PropertyValueFactory<XResults, String>("lost"));
                lostColumnInDetailed.prefWidthProperty().bind(playersTableViewInDetailed.widthProperty().divide(3));


                playersTableViewInDetailed.setItems(detailedResultsObservableList);
                playersTableViewInDetailed.getColumns().addAll(nameColumnInDetailed, winColumnInDetailed, lostColumnInDetailed);

                playersTableViewInDetailed.setColumnResizePolicy((param) -> false);
                //playersTableView.setSortPolicy((param) -> false);

                detailedResultsGridPane.add(playersTableViewInDetailed,1,1,5,7);

                Label wonAsFirstLabel = new Label();
                wonAsFirstLabel.setAlignment(Pos.CENTER);
                wonAsFirstLabel.setMaxWidth(Double.MAX_VALUE);
                detailedResultsGridPane.add(wonAsFirstLabel, 0, 8, 7, 1);
                wonAsFirstLabel.setFont(Font.font("Comic Sans MS", getHeight() / 40));

                Label wonAsSecondLabel = new Label();
                wonAsSecondLabel.setAlignment(Pos.CENTER);
                wonAsSecondLabel.setMaxWidth(Double.MAX_VALUE);
                detailedResultsGridPane.add(wonAsSecondLabel, 0, 9, 7, 1);
                wonAsSecondLabel.setFont(Font.font("Comic Sans MS", getHeight() / 40));

                Button backToMainCardButton = new Button("Powrót do głównej karty");
                detailedResultsGridPane.add(backToMainCardButton,2,10,3,1);
                backToMainCardButton.setMaxWidth(Double.MAX_VALUE);
                backToMainCardButton.setFont(Font.font("Comic Sans MS", getHeight() / 50));
                backToMainCardButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        mainTabPane.getSelectionModel().selectFirst();
                    }
                });


                tableViewsArrayList.add(playersTableViewInDetailed);
                detailedObservableListResults.add(detailedResultsObservableList);

                Map<String, Integer> winsMap = new HashMap<>();
                Map<String, Integer> losesMap = new HashMap<>();

                String programName = playersTableView.getSelectionModel().getSelectedItem().getName();

                int firstWins = 0;
                int secondWins = 0;

                String path = System.getProperty("user.home") + "/Documents/Bricks";
                try {
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

                        for(String s : wyniki) {
                            String[] firstDivision = s.split("=");
                            String[] secondDivisionNames = firstDivision[0].split(",");
                            String[] secondDivisionValues = firstDivision[1].split(",");
                            if (secondDivisionNames.length != 2 || secondDivisionValues.length != 2)
                                continue;
                            if(secondDivisionNames[0].equals(programName)) {
                                winsMap.putIfAbsent(secondDivisionNames[1],0);
                                losesMap.putIfAbsent(secondDivisionNames[1],0);
                                winsMap.put(secondDivisionNames[1],winsMap.get(secondDivisionNames[1]) + Integer.parseInt(secondDivisionValues[0]));
                                losesMap.put(secondDivisionNames[1],losesMap.get(secondDivisionNames[1]) + Integer.parseInt(secondDivisionValues[1]));
                                firstWins += Integer.parseInt(secondDivisionValues[0]);
                            }
                            if(secondDivisionNames[1].equals(programName)) {
                                winsMap.putIfAbsent(secondDivisionNames[0],0);
                                losesMap.putIfAbsent(secondDivisionNames[0],0);
                                winsMap.put(secondDivisionNames[0],winsMap.get(secondDivisionNames[0]) + Integer.parseInt(secondDivisionValues[1]));
                                losesMap.put(secondDivisionNames[0],losesMap.get(secondDivisionNames[0]) + Integer.parseInt(secondDivisionValues[0]));
                                secondWins +=Integer.parseInt(secondDivisionValues[1]);
                            }
                        }

                        for(String s : winsMap.keySet()) {
                            detailedResultsObservableList.add(new XDetailedResults(s,winsMap.get(s),losesMap.get(s)));
                        }
                        playersTableViewInDetailed.refresh();
                        wonAsFirstLabel.setText("Wygranych jako rozpoczynający: " + firstWins);
                        wonAsSecondLabel.setText("Wygranych jako drugi: " + secondWins);


                    }
                }
                catch (Exception ignored){}


                detailedResults.setOnClosed(new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        detailsLabelArrayList.remove(detailsLabel);
                        tableViewsArrayList.remove(playersTableViewInDetailed);
                        detailedObservableListResults.remove(detailedResultsObservableList);
                        mainTabPane.getSelectionModel().selectFirst();
                    }
                });
                mainTabPane.getTabs().add(detailedResults);
                mainTabPane.getSelectionModel().selectLast();

            }
        });

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
                    if(line.equals(""))
                        continue;
                    if (line.charAt(0) != '#') {
                        wyniki.add(line);
                    } else {
                        break;
                    }
                }
                in.close();

                Map<String, Integer> winsMap = new HashMap<>();
                Map<String, Integer> losesMap = new HashMap<>();
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
                    resultsObservableList.add(new XResults(maxKey, winsMap.get(maxKey), losesMap.get(maxKey)));
                    double value = (winsMap.get(maxKey).doubleValue() / (winsMap.get(maxKey).doubleValue() + losesMap.get(maxKey).doubleValue()));
                    dataToWinToPlayedSeries.getData().add(new XYChart.Data(maxKey, value));
                    dataToWinSeries.getData().add(new XYChart.Data(maxKey, winsMap.get(maxKey)));
                    dataToLostSeries.getData().add(new XYChart.Data(maxKey, losesMap.get(maxKey)));
                }
            }

        } catch (IOException ignored) {
        }

    }

    private TabPane mainTabPane;

    private final ObservableList<XResults> resultsObservableList;

    private Button clearLogsButton;

    private XYChart.Series dataToWinToPlayedSeries;

    private XYChart.Series dataToWinSeries;
    private XYChart.Series dataToLostSeries;

    private Button backButton;
    private Button exportLogsButton;

    ArrayList<Label> detailsLabelArrayList = new ArrayList<>();
    ArrayList<TableView<XDetailedResults>> tableViewsArrayList = new ArrayList<>();
    ArrayList<ObservableList<XDetailedResults>> detailedObservableListResults = new ArrayList<>();
}
