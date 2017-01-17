package stages;

import XClasses.XLostReasons;
import XClasses.XResults;
import XClasses.XRobotPlayer;
import core.Bricks;
import exceptions.InvalidMoveException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
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
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import logic.BoardLogic;
import logic.MovesStorage;
import logic.RobotPlayer;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.TimeoutException;

/**
 * Created by Mateusz on 25.12.2016.
 * Project Bricks
 */
class GamesPane extends Pane {
    GamesPane(double w, double h) {

        GridPane mainGridPane = new GridPane();
        mainGridPane.setPrefWidth(w);
        mainGridPane.setPrefHeight(h);
        RowConstraints row = new RowConstraints();
        row.setPercentHeight(9.09);
        for (int i = 0; i < 11; i++) {
            mainGridPane.getRowConstraints().add(row);
        }
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(33.3);
        for (int i = 0; i < 3; i++) {
            mainGridPane.getColumnConstraints().add(column);
        }
        HBox boardsSizesHbox = new HBox();
        boardsSizesHbox.setPadding(new Insets(0,10,10,10));
        boardsSizesListView = new ListView<>();
        boardsSizesListView.setMaxWidth(Double.MAX_VALUE);
        boardsSizesListView.setMaxHeight(Double.MAX_VALUE);
        boardsSizesHbox.getChildren().add(boardsSizesListView);
        mainGridPane.add(boardsSizesHbox, 0, 1, 1, 7);

        Label boardsListLabel = new Label("Plansze:");
        boardsListLabel.setAlignment(Pos.CENTER);
        boardsListLabel.setMaxWidth(Double.MAX_VALUE);
        mainGridPane.add(boardsListLabel, 0, 0, 1, 1);

        boardsSizesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        importPoints();


        boardsSizesListView.setOnKeyReleased(e -> {
            int selectedIndex = boardsSizesListView.getSelectionModel().getSelectedIndex();
            if (boardsSizesListView.getItems().size() > 0 && selectedIndex >= 0) {
                String currentText = boardsSizesListView.getSelectionModel().getSelectedItem();
                if (selectedIndex >= 0) {
                    if (e.getCode() == KeyCode.DIGIT1) {
                        if (currentText.length() <= 2)
                            boardsSizesListView.getItems().set(selectedIndex, currentText + "1");
                        boardsSizesListView.getSelectionModel().select(selectedIndex);
                    }
                    if (e.getCode() == KeyCode.DIGIT2) {
                        if (currentText.length() <= 2)
                            boardsSizesListView.getItems().set(selectedIndex, currentText + "2");
                        boardsSizesListView.getSelectionModel().select(selectedIndex);
                    }
                    if (e.getCode() == KeyCode.DIGIT3) {
                        if (currentText.length() <= 2)
                            boardsSizesListView.getItems().set(selectedIndex, currentText + "3");
                        boardsSizesListView.getSelectionModel().select(selectedIndex);
                    }
                    if (e.getCode() == KeyCode.DIGIT4) {
                        if (currentText.length() <= 2)
                            boardsSizesListView.getItems().set(selectedIndex, currentText + "4");
                        boardsSizesListView.getSelectionModel().select(selectedIndex);
                    }
                    if (e.getCode() == KeyCode.DIGIT5) {
                        if (currentText.length() <= 2)
                            boardsSizesListView.getItems().set(selectedIndex, currentText + "5");
                        boardsSizesListView.getSelectionModel().select(selectedIndex);
                    }
                    if (e.getCode() == KeyCode.DIGIT6) {
                        if (currentText.length() <= 2)
                            boardsSizesListView.getItems().set(selectedIndex, currentText + "6");
                        boardsSizesListView.getSelectionModel().select(selectedIndex);
                    }
                    if (e.getCode() == KeyCode.DIGIT7) {
                        if (currentText.length() <= 2)
                            boardsSizesListView.getItems().set(selectedIndex, currentText + "7");
                        boardsSizesListView.getSelectionModel().select(selectedIndex);
                    }
                    if (e.getCode() == KeyCode.DIGIT8) {
                        if (currentText.length() <= 2)
                            boardsSizesListView.getItems().set(selectedIndex, currentText + "8");
                        boardsSizesListView.getSelectionModel().select(selectedIndex);
                    }
                    if (e.getCode() == KeyCode.DIGIT9) {
                        if (currentText.length() <= 2)
                            boardsSizesListView.getItems().set(selectedIndex, currentText + "9");
                        boardsSizesListView.getSelectionModel().select(selectedIndex);
                    }
                    if (e.getCode() == KeyCode.DIGIT0) {
                        if (currentText.length() <= 2 && currentText.length() > 0)
                            boardsSizesListView.getItems().set(selectedIndex, currentText + "0");
                        boardsSizesListView.getSelectionModel().select(selectedIndex);
                    }
                    if (e.getCode() == KeyCode.BACK_SPACE) {
                        if (currentText.length() >= 2) {
                            boardsSizesListView.getItems().set(selectedIndex, currentText.substring(0, currentText.length() - 1));
                        } else if (currentText.length() == 1 && boardsSizesListView.getItems().size() > 1) {
                            boardsSizesListView.getItems().remove(selectedIndex);
                            boardsSizesListView.getSelectionModel().select(selectedIndex - 1);
                        } else if (currentText.length() == 1 && boardsSizesListView.getItems().size() == 1) {
                            boardsSizesListView.getItems().set(selectedIndex, "");
                        } else if (currentText.length() == 0 && selectedIndex > 0) {
                            boardsSizesListView.getItems().remove(selectedIndex);
                            boardsSizesListView.getSelectionModel().select(selectedIndex - 1);
                        }
                    }
                    if (e.getCode() == KeyCode.ENTER) {
                        if (selectedIndex < boardsSizesListView.getItems().size() - 1 && boardsSizesListView.getItems().get(selectedIndex).length() > 0) {
                            boardsSizesListView.getSelectionModel().select(selectedIndex + 1);
                        } else if (boardsSizesListView.getItems().get(selectedIndex).length() > 0) {
                            boardsSizesListView.getItems().add("");
                            boardsSizesListView.getSelectionModel().select(selectedIndex + 1);
                            boardsSizesListView.scrollTo(selectedIndex + 1);
                        }
                    }
                    if(e.getCode() == KeyCode.DELETE) {
                        if(selectedIndex >=0) {
                            boardsSizesListView.getItems().remove(selectedIndex);
                            if(boardsSizesListView.getItems().isEmpty())
                                boardsSizesListView.getItems().add("");
                        }
                    }
                }
            }
        });
        HBox runButtonHBox = new HBox();
        runButton = new Button("Uruchom");
        runButton.setPrefWidth(180);
        runButton.setPrefHeight(350);
        runButtonHBox.setAlignment(Pos.BOTTOM_CENTER);
        runButtonHBox.getChildren().add(runButton);
        HBox.setMargin(runButton, new Insets(5, 0, 5, 0));
        HBox.setHgrow(runButton, Priority.ALWAYS);
        mainGridPane.add(runButtonHBox, 0, 8, 3, 1);

        playersObservableList = FXCollections.observableArrayList();
        reasonsObservableList = FXCollections.observableArrayList();
        resultsObservableList = FXCollections.observableArrayList();

        runButton.setOnAction(event -> {
            if (runButton.getText().equals("Uruchom")) {
                if (playersObservableList.size() >= 2) {
                    gamesProgressBar.setProgress(0);
                    resultsButton.setDisable(true);
                    finish = true;
                    ArrayList<Integer> boardsSizes = new ArrayList<>();
                    for (int i = boardsSizesListView.getItems().size() - 1; i >= 0; i--) {
                        try {
                            int toAdd = Integer.parseInt(boardsSizesListView.getItems().get(i));
                            if (toAdd >= 5 && toAdd <= 255 && toAdd % 2 == 1) {
                                boardsSizes.add(toAdd);
                            } else {
                                boardsSizesListView.getItems().remove(i);
                            }
                        } catch (Exception ignored) {
                        }
                    }
                    if (boardsSizesListView.getItems().size() == 0) {
                        boardsSizesListView.getItems().add("");
                        gamesProgressBar.setProgress(100.0);
                    } else if (boardsSizes.size() > 0) {
                        reasonsObservableList.clear();
                        running = true;
                        gamesTask = new Task<Void>() {
                            @Override
                            protected Void call() {
                                int counter = 0;
                                lostReasons.clear();
                                int gamesNumber = playersObservableList.size() * (playersObservableList.size() - 1);

                                RobotPlayer firstRobotPlayer;
                                RobotPlayer secondRobotPlayer;

                                for (XRobotPlayer playerFirst : playersObservableList) {
                                    firstRobotPlayer = playerFirst.getRobotPlayer();
                                    for (XRobotPlayer playerSecond : playersObservableList) {
                                        if (playerFirst.getName().equals(playerSecond.getName())) {
                                            continue;
                                        }
                                        secondRobotPlayer = playerSecond.getRobotPlayer();
                                        firstPlayerWins = 0;
                                        secondPlayerWins = 0;
                                        for (Integer boardsSize : boardsSizes) {
                                            if (running) {
                                                BoardLogic board = new BoardLogic(boardsSize);
                                                MovesStorage movesStorage = new MovesStorage();
                                                try {
                                                    firstRobotPlayer.reset(boardsSize);
                                                    secondRobotPlayer.reset(boardsSize);
                                                } catch (Exception ignored) {
                                                    break;
                                                }
                                                int player = 1;
                                                while (running) {
                                                    int[] move = new int[4];
                                                    if (movesStorage.isEmpty()) {
                                                        try {
                                                            move = firstRobotPlayer.makeMove("ZACZYNAJ");
                                                        } catch (InvalidMoveException badMove) {
                                                            firstRobotPlayer.sendEndingMessages(false);
                                                            secondRobotPlayer.sendEndingMessages(true);
                                                            reasonsObservableList.add(new XLostReasons(playerFirst.getName(),playerSecond.getName(),boardsSize,2,"Zły Ruch"));
                                                            secondPlayerWins++;
                                                            break;
                                                        } catch (TimeoutException badMove) {
                                                            firstRobotPlayer.sendEndingMessages(false);
                                                            secondRobotPlayer.sendEndingMessages(true);
                                                            reasonsObservableList.add(new XLostReasons(playerFirst.getName(),playerSecond.getName(),boardsSize,2,"Przekroczenie Czasu"));
                                                            secondPlayerWins++;
                                                            break;
                                                        }
                                                    } else {
                                                        if (player == 1) {
                                                            try {
                                                                move = firstRobotPlayer.makeMove(movesStorage.getLastMoveAsString());
                                                            } catch (InvalidMoveException badMove) {
                                                                firstRobotPlayer.sendEndingMessages(true);
                                                                secondRobotPlayer.sendEndingMessages(false);
                                                                reasonsObservableList.add(new XLostReasons(playerFirst.getName(),playerSecond.getName(),boardsSize,2,"Zły Ruch"));
                                                                secondPlayerWins++;
                                                                break;
                                                            } catch (TimeoutException timeoutMove) {
                                                                firstRobotPlayer.sendEndingMessages(true);
                                                                secondRobotPlayer.sendEndingMessages(false);
                                                                reasonsObservableList.add(new XLostReasons(playerFirst.getName(),playerSecond.getName(),boardsSize,2,"Przekroczenie Czasu"));
                                                                secondPlayerWins++;
                                                                break;
                                                            }
                                                        }
                                                        if (player == 2) {
                                                            try {
                                                                move = secondRobotPlayer.makeMove(movesStorage.getLastMoveAsString());
                                                            } catch (InvalidMoveException badMove) {
                                                                firstRobotPlayer.sendEndingMessages(false);
                                                                secondRobotPlayer.sendEndingMessages(true);
                                                                firstPlayerWins++;
                                                                reasonsObservableList.add(new XLostReasons(playerFirst.getName(),playerSecond.getName(),boardsSize,1,"Zły Ruch"));
                                                                break;
                                                            } catch (TimeoutException timeoutMove) {
                                                                firstRobotPlayer.sendEndingMessages(false);
                                                                secondRobotPlayer.sendEndingMessages(true);
                                                                reasonsObservableList.add(new XLostReasons(playerFirst.getName(),playerSecond.getName(),boardsSize,1,"Przekroczenie Czasu"));
                                                                firstPlayerWins++;
                                                                break;
                                                            }
                                                        }
                                                    }

                                                    int x1 = move[0];
                                                    int y1 = move[1];
                                                    int x2 = move[2];
                                                    int y2 = move[3];
                                                    if (Bricks.mainStage.possibleMove(x1, y1, x2, y2, board.board)) {
                                                        board.board[x1][y1] = player;
                                                        board.board[x2][y2] = player;
                                                        movesStorage.addMove(x1,y1,x2,y2);
                                                    }
                                                    else {
                                                        if(player == 1) {
                                                            firstRobotPlayer.sendEndingMessages(false);
                                                            secondRobotPlayer.sendEndingMessages(true);
                                                            reasonsObservableList.add(new XLostReasons(playerFirst.getName(),playerSecond.getName(),boardsSize,2,"Zły Ruch"));
                                                            secondPlayerWins++;
                                                            break;
                                                        } else {
                                                            firstRobotPlayer.sendEndingMessages(true);
                                                            secondRobotPlayer.sendEndingMessages(false);
                                                            reasonsObservableList.add(new XLostReasons(playerFirst.getName(),playerSecond.getName(),boardsSize,1,"Zły Ruch"));
                                                            firstPlayerWins++;
                                                            break;
                                                        }
                                                    }
                                                    if (!board.anyMoves()) {
                                                        if (player == 1) {
                                                            firstRobotPlayer.sendEndingMessages(true);
                                                            secondRobotPlayer.sendEndingMessages(false);
                                                            reasonsObservableList.add(new XLostReasons(playerFirst.getName(),playerSecond.getName(),boardsSize,1,"Koniec Ruchów"));
                                                            firstPlayerWins++;
                                                            break;
                                                        } else {
                                                            firstRobotPlayer.sendEndingMessages(false);
                                                            secondRobotPlayer.sendEndingMessages(true);
                                                            reasonsObservableList.add(new XLostReasons(playerFirst.getName(),playerSecond.getName(),boardsSize,2,"Koniec Ruchów"));
                                                            secondPlayerWins++;
                                                            break;
                                                        }
                                                    }
                                                    if (player == 1) {
                                                        player = 2;
                                                    } else  {
                                                        player = 1;
                                                    }
                                                }
                                            } else {
                                                break;
                                            }
                                        }
                                        counter++;
                                        updateProgress(counter, gamesNumber);
                                        RobotPlayer.exportLogs(firstPlayerWins, secondPlayerWins, playerFirst.getName(), playerSecond.getName());
                                        secondRobotPlayer.killRobot();
                                        if(!running)
                                            break;
                                    }
                                    firstRobotPlayer.killRobot();
                                    if(!running)
                                        break;
                                }
                                return null;
                            }

                        };
                        Thread autoGameThread = new Thread(gamesTask);
                        gamesProgressBar.progressProperty().bind(gamesTask.progressProperty());
                        autoGameThread.start();
                        gamesTask.setOnSucceeded(event1 -> {
                            runButton.setText("Uruchom");
                            resultsButton.setDisable(false);
                            gamesProgressBar.progressProperty().unbind();
                            gamesProgressBar.setProgress(100);
                            running = false;
                            resultsObservableList.clear();
                            Map<String, Integer> winsMap = new HashMap<>();
                            Map<String, Integer> losesMap = new HashMap<>();
                            Map<String, Double> winToAllMap = new HashMap<>();
                            if (this.finish ) {
                                for (XLostReasons reason : reasonsObservableList) {
                                    {
                                        String firstProgram = reason.getFirstProgramName();
                                        String secondProgram = reason.getSecondProgramName();
                                        if (reason.getWinProgramName().equals(firstProgram)) {
                                            if (winsMap.containsKey(firstProgram)) {
                                                winsMap.put(firstProgram, winsMap.get(firstProgram) + 1);
                                            } else {
                                                winsMap.put(firstProgram, 1);
                                            }
                                            if (losesMap.containsKey(secondProgram)) {
                                                losesMap.put(secondProgram, losesMap.get(secondProgram) + 1);
                                            } else {
                                                losesMap.put(secondProgram, 1);
                                            }
                                        } else {
                                            if (winsMap.containsKey(secondProgram)) {
                                                winsMap.put(secondProgram, winsMap.get(secondProgram) + 1);
                                            } else {
                                                winsMap.put(secondProgram, 1);
                                            }
                                            if (losesMap.containsKey(firstProgram)) {
                                                losesMap.put(firstProgram, losesMap.get(firstProgram) + 1);
                                            } else {
                                                losesMap.put(firstProgram, 1);
                                            }
                                        }
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
                            }
                        }

                        });

                        runButton.setText("Przerwij");

                    } else {
                        resultsButton.setDisable(false);
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                    Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                    alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                    alert.setTitle("Błąd rozpoczynania rozgrywek");
                    alert.setHeaderText("Nie można było rozpocząć rozgrywek");
                    alert.setContentText("Podaj przynajmniej dwa programy komputerowe.");
                    ButtonType buttonOk = new ButtonType("Ok");
                    alert.getButtonTypes().setAll(buttonOk);
                    alert.showAndWait();
                }
            } else {
                running = false;
                finish  =false;
                resultsButton.setDisable(false);
            }
        });
        gamesProgressBar = new ProgressBar();
        gamesProgressBar.setMaxWidth(Double.MAX_VALUE);

        gamesProgressBar.setProgress(0);
        mainGridPane.add(gamesProgressBar, 0, 9, 3, 1);
        //gamesProgressBar.setEffect();

        HBox resultsButtonHBox = new HBox();
        resultsButton = new Button("Wyniki Sumaryczne");
        resultsButtonHBox.setAlignment(Pos.TOP_LEFT);
        resultsButtonHBox.getChildren().add(resultsButton);
        resultsButtonHBox.setPadding(new Insets(0,0,0,30));
        mainGridPane.add(resultsButtonHBox, 0, 10,2,1);

        resultsButton.setOnAction(event -> {
            ResultsPane resultsPane = new ResultsPane(getWidth(), getHeight());
            Scene resultsScene = new Scene(resultsPane, getWidth(), getHeight());
            resultsScene.getStylesheets().add(Bricks.mainStage.selectedTheme);
            Bricks.mainStage.mainStage.setScene(resultsScene);
            Bricks.mainStage.mainStage.show();
            exportPoints();
        });

        HBox backButtonHBox = new HBox();
        backButton = new Button("Powrót");
        backButtonHBox.setAlignment(Pos.TOP_RIGHT);
        backButtonHBox.getChildren().add(backButton);
        backButtonHBox.setPadding(new Insets(0,30,0,0));
        mainGridPane.add(backButtonHBox, 2, 10);

        backButton.setOnAction(event -> {
                running = false;
                gamesProgressBar.progressProperty().unbind();
                exportPoints();
                Bricks.mainStage.backToMenu();
        });


        mainTabPane = new TabPane();
        Tab boardsSelectTab = new Tab("Plansze");
        mainTabPane.getTabs().add(boardsSelectTab);
        boardsSelectTab.setContent(mainGridPane);
        boardsSelectTab.setClosable(false);


        Tab playersSelectTab = new Tab("Gracze");
        GridPane playersGridPane = new GridPane();
        playersGridPane.setPrefWidth(w);
        playersGridPane.setPrefHeight(h);
        row = new RowConstraints();
        row.setPercentHeight(9.09);
        for (int i = 0; i < 11; i++) {
            playersGridPane.getRowConstraints().add(row);
        }
        column = new ColumnConstraints();
        column.setPercentWidth(50);
        for (int i = 0; i < 2; i++) {
            playersGridPane.getColumnConstraints().add(column);
        }

        Label playersListLabel = new Label("Zarejestrowani Gracze:");
        playersListLabel.setAlignment(Pos.CENTER);
        playersListLabel.setMaxWidth(Double.MAX_VALUE);
        playersGridPane.add(playersListLabel, 0, 0, 3, 1);

        VBox playersTableViewVBox = new VBox();
        playersTableView = new TableView<>();
        playersTableViewVBox.getChildren().add(playersTableView);
        playersTableViewVBox.setVgrow(playersTableView,Priority.ALWAYS);
        playersTableViewVBox.setPadding(new Insets(0,5,0,5));
        TableColumn typeColumn = new TableColumn("Typ Programu");
        //noinspection unchecked
        typeColumn.setCellValueFactory(new PropertyValueFactory<XRobotPlayer, String>("type"));
        typeColumn.prefWidthProperty().bind(playersTableView.widthProperty().divide(4));

        TableColumn pathColumn = new TableColumn("Scieżka/Komenda uruchomienia");
        //noinspection unchecked
        pathColumn.setCellValueFactory(new PropertyValueFactory<XRobotPlayer, String>("path"));
        pathColumn.prefWidthProperty().bind(playersTableView.widthProperty().divide(2));

        TableColumn nameColumn = new TableColumn("Nazwa Programu");
        //noinspection unchecked
        nameColumn.setCellValueFactory(new PropertyValueFactory<XRobotPlayer, String>("name"));
        nameColumn.prefWidthProperty().bind(playersTableView.widthProperty().divide(4));

        playersTableView.setItems(playersObservableList);
        playersTableView.getColumns().addAll(typeColumn, pathColumn, nameColumn);

        playersTableView.setColumnResizePolicy((param) -> false);
        playersTableView.setSortPolicy((param) -> false);

        playersTableView.setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.DELETE) {
                XRobotPlayer toDelete = playersTableView.getSelectionModel().getSelectedItem();
                if(toDelete!=null) {
                    playersObservableList.remove(toDelete);
                    exportPrograms();
                }
            }
        });

        playersTableView.setOnMouseClicked(event -> {
            if(event.getClickCount()==2 && playersTableView.getSelectionModel().getSelectedItem()!=null) {
                if(!running) {
                    if (!playersTableView.getSelectionModel().getSelectedItem().getType().equals("Własny")) {
                        NewProgramNameStage programNameStage = new NewProgramNameStage(playersTableView.getSelectionModel().getSelectedItem(), playersTableView.getSelectionModel().getSelectedIndex());
                        try {
                            programNameStage.start(Bricks.mainStage.mainStage);
                        } catch (Exception ignored) {
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                        alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                        alert.setTitle("Błąd zmiany nazwy");
                        alert.setHeaderText("W obecnej wersji programu nie można zmieniać nazwy programów 'Własnych'.");
                        alert.setContentText("Funkcjonalność ta zostanie dodana w jednej z kolejnych wesji");
                        ButtonType buttonYes = new ButtonType("Ok");
                        alert.getButtonTypes().setAll(buttonYes);
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                    Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                    alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                    alert.setTitle("Błąd zmiany nazwy");
                    alert.setHeaderText("Nie można zmienić nazwy programu podczas trwania rozgrywek");
                    alert.setContentText("Poczekaj na ich zakończenie, lub je przerwij");
                    ButtonType buttonYes = new ButtonType("Ok");
                    alert.getButtonTypes().setAll(buttonYes);
                    alert.showAndWait();
                }

            }
        });

        Label addPlayerLabel = new Label("Dodaj Gracza:");
        addPlayerLabel.setAlignment(Pos.CENTER);
        addPlayerLabel.setMaxWidth(Double.MAX_VALUE);

        Label pathToPlayerLabel = new Label("");
        pathToPlayerLabel.setAlignment(Pos.CENTER);
        pathToPlayerLabel.setMaxWidth(Double.MAX_VALUE);

        Label descriptionTitle = new Label("Wyniki:");
        descriptionTitle.setMaxWidth(Double.MAX_VALUE);
        descriptionTitle.setAlignment(Pos.CENTER);
        descriptionTitle.setFont(Font.font("Comic Sans MS",30));

        mainGridPane.add(descriptionTitle,1,0,2,1);

        TableView<XResults> playersResultsTableView = new TableView<>();
        playersResultsTableView.editableProperty().set(false);

        TableColumn nameProgramColumn = new TableColumn("Program");
        //noinspection unchecked
        nameProgramColumn.setCellValueFactory(new PropertyValueFactory<XResults, String>("name"));
        nameProgramColumn.prefWidthProperty().bind(playersResultsTableView.widthProperty().divide(4));

        TableColumn winsColumn = new TableColumn("Wygrane");
        //noinspection unchecked
        winsColumn.setCellValueFactory(new PropertyValueFactory<XResults, String>("wins"));
        winsColumn.prefWidthProperty().bind(playersResultsTableView.widthProperty().divide(4));

        TableColumn lostColumn = new TableColumn("Przegrane");
        //noinspection unchecked
        lostColumn.setCellValueFactory(new PropertyValueFactory<XResults, String>("lost"));
        lostColumn.prefWidthProperty().bind(playersResultsTableView.widthProperty().divide(4));

        TableColumn winToAllColumn = new TableColumn("Wygrane/Rozegrane");
        //noinspection unchecked
        winToAllColumn.setCellValueFactory(new PropertyValueFactory<XResults, String>("winsToAll"));
        winToAllColumn.prefWidthProperty().bind(playersResultsTableView.widthProperty().divide(4));

        playersResultsTableView.setItems(resultsObservableList);
        playersResultsTableView.getColumns().addAll(nameProgramColumn, winsColumn, lostColumn,winToAllColumn);

        playersResultsTableView.setColumnResizePolicy((param) -> false);
        //playersTableView.setSortPolicy((param) -> false);

        HBox palyersResultsTableViewHBox = new HBox();
        palyersResultsTableViewHBox.getChildren().addAll(playersResultsTableView);
        palyersResultsTableViewHBox.setHgrow(playersResultsTableView,Priority.ALWAYS);
        palyersResultsTableViewHBox.setPadding(new Insets(0,10,10,10));
        mainGridPane.add(palyersResultsTableViewHBox,1, 1, 2, 7);

        Label runCommandLabel = new Label("Komenda uruchomienia:");
        runCommandLabel.setAlignment(Pos.CENTER);
        runCommandLabel.setMaxWidth(Double.MAX_VALUE);

        TextField runCommandTextArea = new TextField();
        runCommandTextArea.setMaxHeight(30);
        runCommandTextArea.setMaxWidth(200);
        runCommandTextArea.setTooltip(new Tooltip("Napisz tutaj komendę, jaką wprowadziłbyś w konsoli aby uruchomić swój program."));


        HBox removePlayerHBox = new HBox();
        Button removePlayerButton = new Button("Usuń Gracza");
        removePlayerButton.setTooltip(new Tooltip("Usuwa zaznaczonego na liście powyżej gracza."));
        removePlayerButton.setFont(Font.font("Comic Sans MS", 14));
        removePlayerHBox.getChildren().add(removePlayerButton);
        removePlayerHBox.setAlignment(Pos.CENTER);
        removePlayerButton.setOnAction(event -> {
            if (!running) {
                XRobotPlayer index = playersTableView.getSelectionModel().getSelectedItem();
                playersObservableList.remove(index);
                exportPrograms();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                alert.setTitle("Błąd usuwania gracza");
                alert.setHeaderText("Gracz nie został usunięty");
                alert.setContentText("Nie można usuwać graczy podczas trwania gier w tle.");
                ButtonType buttonOk = new ButtonType("Ok");
                alert.getButtonTypes().setAll(buttonOk);
                alert.showAndWait();
            }
        });


        TextField programNameTextArea = new TextField();
        programNameTextArea.setMaxHeight(30);
        programNameTextArea.setMaxWidth(200);
        programNameTextArea.setTooltip(new Tooltip("Nazwa zostanie zastosowana tylko do programu 'własnego'."));

        HBox addPlayerHBox = new HBox();
        Button addPlayerButton = new Button("Dodaj Gracza/y");
        addPlayerButton.setTooltip(new Tooltip("Jeśli nie wpiszesz nic w polu na własną komendę uruchomienia \nspowoduje wyświetlenie okna wyboru plików."));
        addPlayerButton.setFont(Font.font("Comic Sans MS", 14));
        addPlayerHBox.getChildren().add(addPlayerButton);
        addPlayerHBox.setAlignment(Pos.CENTER);
        addPlayerButton.setOnAction(event -> {
            if (!running) {
                boolean found = false;
                if (runCommandTextArea.getText().equals("")) {
                    FileChooser chooseFile = new FileChooser();
                    chooseFile.setTitle("Wybierz gracza");
                    boolean error = false;
                    List<File> programsToOpen = chooseFile.showOpenMultipleDialog(Bricks.mainStage.mainStage);
                    if(programsToOpen!=null) {
                        for (File openFile : programsToOpen) {
                            if (openFile != null) {
                                try {
                                    playerPath = openFile.getCanonicalPath();
                                } catch (IOException ignored) {
                                    return;
                                }

                                XRobotPlayer toAdd = new XRobotPlayer("Plik class/exe/jar/out/py", playerPath);
                                found = false;
                                for (XRobotPlayer aPlayersObservableList : playersObservableList) {
                                    if (toAdd.getName().equals(aPlayersObservableList.getName())) {
                                        error = true;
                                        found = true;
                                    }
                                }
                                if (!found) {
                                    RobotPlayer test = toAdd.getRobotPlayer();
                                    if (test != null) {
                                        test.sendEndingMessages(true);
                                        test.killRobot();
                                        playersObservableList.add(toAdd);
                                    } else {
                                        error = true;
                                    }
                                }
                            }
                        }
                    }
                    if(error) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                        alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                        alert.setTitle("Komunikat Dodawania Programów");
                        alert.setHeaderText("Jeden lub więcej programów nie zostało dodanych.");
                        alert.setContentText("Pominięte zostały programy o nazwie takiej samej jak już zaimportowane, jak również " +
                                "te niedziałające.");
                        ButtonType buttonOk = new ButtonType("Ok");
                        alert.getButtonTypes().setAll(buttonOk);
                        alert.showAndWait();
                    }
                } else {
                    if (!programNameTextArea.getText().equals("")) {

                        if(getProgramsNames().contains(programNameTextArea.getText())) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                            alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                            alert.setTitle("Błąd dodawania gracza");
                            alert.setHeaderText("Nowy gracz nie został dodany");
                            alert.setContentText("Gracz o tej nazwie już istnieje.");
                            ButtonType buttonOk = new ButtonType("Ok");
                            alert.getButtonTypes().setAll(buttonOk);
                            alert.showAndWait();
                            return;
                        }
                        if(programNameTextArea.getText().contains("\\") || programNameTextArea.getText().contains("/")) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                            alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                            alert.setTitle("Błąd dodawania gracza");
                            alert.setHeaderText("Nowy gracz nie został dodany");
                            alert.setContentText("Nazwa gracza zawiera niedozwolone znaki. Niedozwolone znaki to: '\\' i '/'");
                            ButtonType buttonOk = new ButtonType("Ok");
                            alert.getButtonTypes().setAll(buttonOk);
                            alert.showAndWait();
                            return;
                        }

                        XRobotPlayer toAdd = new XRobotPlayer("Własny", runCommandTextArea.getText(), programNameTextArea.getText());
                        for (XRobotPlayer aPlayersObservableList : playersObservableList) {
                            if (toAdd.getName().equals(aPlayersObservableList.getName()))
                                found = true;
                        }
                        if (!found) {
                            RobotPlayer test = toAdd.getRobotPlayer();
                            if (test != null) {
                                playersObservableList.add(toAdd);
                            } else {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                                alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                                alert.setTitle("Błąd dodawania gracza");
                                alert.setHeaderText("Nowy gracz nie został dodany");
                                alert.setContentText("Podany gracz nie działa");
                                ButtonType buttonOk = new ButtonType("Ok");
                                alert.getButtonTypes().setAll(buttonOk);
                                alert.showAndWait();
                            }
                        } else {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                            alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                            alert.setTitle("Błąd dodawania gracza");
                            alert.setHeaderText("Nowy gracz nie został dodany");
                            alert.setContentText("Gracz o tej nazwie jest już dodany.");
                            ButtonType buttonOk = new ButtonType("Ok");
                            alert.getButtonTypes().setAll(buttonOk);
                            alert.showAndWait();
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                        alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                        alert.setTitle("Błąd dodawania gracza");
                        alert.setHeaderText("Nowy gracz nie został dodany");
                        alert.setContentText("Podaj nazwę swojego gracza");
                        ButtonType buttonOk = new ButtonType("Ok");
                        alert.getButtonTypes().setAll(buttonOk);
                        alert.showAndWait();
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                alert.setTitle("Błąd dodawania gracza");
                alert.setHeaderText("Nowy gracz nie został dodany");
                alert.setContentText("Nie można dodawać graczy podczas trwania gier w tle.");
                ButtonType buttonOk = new ButtonType("Ok");
                alert.getButtonTypes().setAll(buttonOk);
                alert.showAndWait();
            }
            exportPrograms();
            playerPath = "";
            runCommandTextArea.setText("");

        });

        Label programNameLabel = new Label("Nazwa");
        programNameLabel.setAlignment(Pos.CENTER);
        programNameLabel.setMaxWidth(Double.MAX_VALUE);

        playersGridPane.add(removePlayerHBox, 1, 9);
        playersGridPane.add(addPlayerHBox, 0, 9);
        playersGridPane.add(programNameLabel, 0, 8);
        playersGridPane.add(programNameTextArea, 1, 8);
        playersGridPane.add(runCommandTextArea, 1, 7);
        playersGridPane.add(runCommandLabel, 0, 7);
        playersGridPane.add(addPlayerLabel, 0, 6, 2, 1);
        playersGridPane.add(playersTableViewVBox, 0, 1, 2, 5);

        Task<Void> importProgramsTask = new Task<Void>() {
            @Override
            protected Void call() {
                try {
                    String path = System.getProperty("user.home") + "/Documents/Bricks/programs";
                    Scanner in = new Scanner(new File(path));
                    String line;
                    String[] divided;
                    while (in.hasNextLine()) {
                        line = in.nextLine();
                        divided = line.split("=");
                        try {
                            XRobotPlayer toAdd = new XRobotPlayer(divided[0], divided[1], divided[2]);
                            RobotPlayer toCheck = toAdd.getRobotPlayer();
                            if (toCheck != null) {
                                boolean found = false;
                                for (XRobotPlayer aPlayersObservableList : playersObservableList) {
                                    if (toAdd.getName().equals(aPlayersObservableList.getName()))
                                        found = true;
                                }
                                if (!found)
                                    playersObservableList.add(toAdd);
                                toCheck.sendEndingMessages(true);
                                toCheck.killRobot();
                            }


                        } catch (NumberFormatException ignored) {
                        }
                    }
                    in.close();
                } catch (Exception ignored) {
                }
                return null;
            }
        };
        Thread importProgramsThread = new Thread(importProgramsTask);
        importProgramsTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                playersTableView.refresh();
            }
        });
        importProgramsThread.start();

        playersSelectTab.setContent(playersGridPane);
        playersSelectTab.setClosable(false);
        mainTabPane.getTabs().add(playersSelectTab);
        getChildren().add(mainTabPane);
        setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                alert.setTitle("Potwierdzenie Wyjścia");
                alert.setHeaderText("Chcesz wrócić do menu głównego?");
                alert.setContentText("Jeśli w tle trwa gra, to zostanie ona przerwana.");
                ButtonType buttonYes = new ButtonType("Tak");
                ButtonType buttonNo = new ButtonType("Anuluj");
                alert.getButtonTypes().setAll(buttonNo, buttonYes);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == buttonYes) {
                    running = false;
                    gamesProgressBar.progressProperty().unbind();
                    exportPoints();
                    Bricks.mainStage.backToMenu();
                }
            }
        });

        Tab reasonsTab = new Tab("Historia");
        reasonsTab.setClosable(false);

        GridPane reasonsGridPane = new GridPane();
        reasonsGridPane.setPrefWidth(w);
        reasonsGridPane.setPrefHeight(h);
        row = new RowConstraints();
        row.setPercentHeight(9.09);
        for (int i = 0; i < 11; i++) {
            reasonsGridPane.getRowConstraints().add(row);
        }
        column = new ColumnConstraints();
        column.setPercentWidth(50);
        for (int i = 0; i < 2; i++) {
            reasonsGridPane.getColumnConstraints().add(column);
        }
        reasonsTab.setContent(reasonsGridPane);

        Label reasonsListLabel = new Label("Historia:");
        reasonsListLabel.setAlignment(Pos.CENTER);
        reasonsListLabel.setMaxWidth(Double.MAX_VALUE);
        reasonsGridPane.add(reasonsListLabel, 0, 0, 3, 1);

        VBox reasonsTableViewVBox = new VBox();
        reasonsTableView = new TableView<>();
        reasonsTableViewVBox.setVgrow(reasonsTableView,Priority.ALWAYS);
        reasonsTableViewVBox.setPadding(new Insets(0,5,5,5));
        reasonsTableViewVBox.getChildren().add(reasonsTableView);
        TableColumn firstNameColumn = new TableColumn("I");
        //noinspection unchecked
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<XRobotPlayer, String>("firstProgramName"));
        firstNameColumn.prefWidthProperty().bind(reasonsTableView.widthProperty().divide(5));


        TableColumn secondNameColumn = new TableColumn("II");
        //noinspection unchecked
        secondNameColumn.setCellValueFactory(new PropertyValueFactory<XRobotPlayer, String>("secondProgramName"));
        secondNameColumn.prefWidthProperty().bind(reasonsTableView.widthProperty().divide(5));

        TableColumn programsColumn = new TableColumn("Programy Grające");
        programsColumn.getColumns().addAll(firstNameColumn,secondNameColumn);

        TableColumn boardSizeColumn = new TableColumn("Plansza");
        //noinspection unchecked
        boardSizeColumn.setCellValueFactory(new PropertyValueFactory<XRobotPlayer, String>("boardSize"));
        boardSizeColumn.prefWidthProperty().bind(reasonsTableView.widthProperty().divide(5));

        TableColumn winColumn = new TableColumn("Zwycięzca");
        //noinspection unchecked
        winColumn.setCellValueFactory(new PropertyValueFactory<XRobotPlayer, String>("winProgramName"));
        winColumn.prefWidthProperty().bind(reasonsTableView.widthProperty().divide(5));

        TableColumn reasonsColumn = new TableColumn("Powód");
        //noinspection unchecked
        reasonsColumn.setCellValueFactory(new PropertyValueFactory<XRobotPlayer, String>("reason"));
        reasonsColumn.prefWidthProperty().bind(reasonsTableView.widthProperty().divide(5));

        reasonsTableView.setItems(reasonsObservableList);
        reasonsTableView.getColumns().addAll(programsColumn, boardSizeColumn, winColumn, reasonsColumn);

        reasonsTableView.setColumnResizePolicy((param) -> false);
        reasonsTableView.setSortPolicy((param) -> false);

        reasonsGridPane.add(reasonsTableViewVBox,0,1,3,10);
        mainTabPane.getTabs().add(reasonsTab);

        setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles() && mainTabPane.getSelectionModel().getSelectedIndex()==1) {
                    event.acceptTransferModes(TransferMode.LINK);
                } else {
                    event.consume();
                }
            }
        });

        setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean found = false;
                boolean error = false;
                if(db.hasFiles() && mainTabPane.getSelectionModel().getSelectedIndex()==1) {
                    for (File openFile : db.getFiles()) {
                        if (openFile != null) {
                            try {
                                playerPath = openFile.getCanonicalPath();
                            } catch (IOException ignored) {
                                return;
                            }

                            XRobotPlayer toAdd = new XRobotPlayer("Plik class/exe/jar/out/py", playerPath);
                            found = false;
                            for (XRobotPlayer aPlayersObservableList : playersObservableList) {
                                if (toAdd.getName().equals(aPlayersObservableList.getName())) {
                                    error = true;
                                    found = true;
                                }
                            }
                            if (!found) {
                                RobotPlayer test = toAdd.getRobotPlayer();
                                if (test != null) {
                                    test.sendEndingMessages(true);
                                    test.killRobot();
                                    playersObservableList.add(toAdd);
                                } else {
                                    error = true;
                                }
                            }
                        }
                    }
                    exportPrograms();
                    if(error) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                        alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                        alert.setTitle("Komunikat Dodawania Programów");
                        alert.setHeaderText("Jeden lub więcej programów nie zostało dodanych.");
                        alert.setContentText("Pominięte zostały programy o nazwie takiej samej jak już zaimportowane, jak również " +
                                "te niedziałające.");
                        ButtonType buttonOk = new ButtonType("Ok");
                        alert.getButtonTypes().setAll(buttonOk);
                        alert.showAndWait();
                    }
                }
                event.setDropCompleted(true);
                event.consume();
            }
        });
        widthProperty().addListener((observable, oldValue, newValue) -> {
            mainTabPane.setPrefWidth(newValue.doubleValue());
            if(newValue.doubleValue()>Bricks.mainStage.mainStage.getHeight()) {
                mainGridPane.setPadding(new Insets(0,(newValue.doubleValue()-Bricks.mainStage.mainStage.getHeight())/2.0,0,(newValue.doubleValue()-Bricks.mainStage.mainStage.getHeight())/2.0));
            }
            else {
                mainGridPane.setPadding(new Insets(0,0,0,0));
            }

        });
        heightProperty().addListener((observable, oldValue, newValue) -> {
            mainGridPane.setPrefHeight(newValue.doubleValue());
            //reasonsTableViewVBox.setMinHeight(newValue.doubleValue());
            boardsListLabel.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 20));
            descriptionTitle.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 20));
            playersListLabel.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 20));
            reasonsListLabel.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 20));
            addPlayerLabel.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 25));
            removePlayerButton.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 42));
            addPlayerButton.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 42));
            runCommandLabel.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 50));
            pathToPlayerLabel.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 50));
            programNameLabel.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 50));
            runButton.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 30));
            resultsButton.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 40));
            backButton.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 40));
            if (getWidth() * (2.0 / 3.0) <= 300) {
                runButton.setMaxWidth(getWidth() * (2.0 / 3.0));
            } else {
                runButton.setMaxWidth(300);
            }
            mainTabPane.setPrefHeight(newValue.doubleValue());
        });
    }

    private void importPoints() {
        try {
            String path = System.getProperty("user.home") + "/Documents/Bricks/boardsSizes";
            Scanner in = new Scanner(new File(path));
            boardsSizesListView.getItems().clear();
            String line;
            while (in.hasNextLine()) {
                line = in.nextLine();
                int i = Integer.parseInt(line);
                if (i >= 5 && i <= 255 && i % 2 == 1) {
                    boardsSizesListView.getItems().add(i + "");
                }
            }
            if (boardsSizesListView.getItems().isEmpty())
                boardsSizesListView.getItems().add("");
            in.close();
        } catch (Exception ignored) {
            if (boardsSizesListView.getItems().isEmpty())
                boardsSizesListView.getItems().add("");
        }
    }

    void exportPoints() {
        if(boardsSizesListView==null)
            return;
        try {
            String path = System.getProperty("user.home") + "/Documents/Bricks";
            if (!new File(path + "/boardsSizes").exists()) {
                //noinspection ResultOfMethodCallIgnored
                new File(path + "/boardsSizes").createNewFile();
            }
            String filename = path + "/boardsSizes";
            PrintWriter writer;
            writer = new PrintWriter(filename, "UTF-8");
            for (int i = 0; i < boardsSizesListView.getItems().size(); i++)
                writer.println(boardsSizesListView.getItems().get(i));
            writer.close();

        } catch (Exception ignored) {
        }

    }

    private void exportPrograms() {
        try {
            String path = System.getProperty("user.home") + "/Documents/Bricks";
            if (!new File(path + "/programs").exists()) {
                //noinspection ResultOfMethodCallIgnored
                new File(path + "/programs").createNewFile();
            }
            String filename = path + "/programs";
            PrintWriter writer;
            writer = new PrintWriter(filename, "UTF-8");
            for (XRobotPlayer e : playersObservableList)
                writer.println(e.getType() + "=" + e.getPath() + "=" + e.getName());
            writer.close();

        } catch (Exception ignored) {
        }

    }

    public void changeIndexName(String newName,int index) {
        String oldName = playersObservableList.get(index).getName();
        playersObservableList.get(index).setName(newName);
        playersTableView.refresh();
        exportPrograms();
        if (newName.equals(oldName)) {
            return;
        }
        try {
            String path = System.getProperty("user.home") + "/Documents/Bricks";
            if (!new File(path + "/logs.txt").exists()) {
                return;
            } else {
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

                Map<String, Integer> winsMap = new HashMap<>();
                Map<String, Integer> losesMap = new HashMap<>();

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
                PrintWriter writer = new PrintWriter(pathToFile);
                //wyniki.forEach(writer::println);

                for (String s : wyniki) {
                    if(s.contains(oldName)) {
                        String changed = s.replaceAll(oldName,newName);
                        writer.println(changed);
                    }
                    else {
                        writer.println(s);
                    }
                }
                writer.close();

            }

        } catch (IOException ignored) {
        }

        for(XLostReasons e : reasonsObservableList) {
            if(e.getFirstProgramName().equals(oldName)) {
                e.setFirstProgramName(newName);
            }
            if(e.getSecondProgramName().equals(oldName)) {
                e.setSecondProgramName(newName);
            }
            if(e.getWinProgramName().equals(oldName)) {
                e.setWinProgramName(newName);
            }
        }
        reasonsTableView.refresh();
    }

    private String playerPath = "";

    private final ObservableList<XResults> resultsObservableList;

    private boolean finish = true;

    private ListView<String> boardsSizesListView;
    private Button runButton;
    private Button backButton;
    private Button resultsButton;
    private ProgressBar gamesProgressBar;
    //private Stage gamesStage;

    private boolean running = false;

    private Task<Void> gamesTask;

    private int firstPlayerWins = 0;
    private int secondPlayerWins = 0;

    private TabPane mainTabPane;

    private TableView<XRobotPlayer> playersTableView;
    private final ObservableList<XRobotPlayer> playersObservableList;

    private TableView<XLostReasons> reasonsTableView;
    private final ObservableList<XLostReasons> reasonsObservableList;

    private ArrayList<String> lostReasons = new ArrayList<>();

    public ArrayList<String> getProgramsNames(){
        ArrayList<String> programsNames = new ArrayList<>();

        for(XRobotPlayer name : playersObservableList)
            programsNames.add(name.getName());

        return programsNames;
    }
}
