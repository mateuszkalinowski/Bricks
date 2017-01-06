package stages;

import XClasses.XRobotPlayer;
import core.Bricks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.BoardLogic;
import logic.MovesStorage;
import logic.RobotPlayer;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

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
        boardsSizesListView = new ListView<>();
        boardsSizesListView.setMaxWidth(Double.MAX_VALUE);
        boardsSizesListView.setMaxHeight(Double.MAX_VALUE);
        mainGridPane.add(boardsSizesListView, 1, 1, 1, 6);

        Label boardsListLabel = new Label("Plansze:");
        boardsListLabel.setAlignment(Pos.CENTER);
        boardsListLabel.setMaxWidth(Double.MAX_VALUE);
        mainGridPane.add(boardsListLabel, 0, 0, 3, 1);

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
                }
            }
        });


        //Label wonGamesLabel = new Label("Wygranych:");
        //wonGamesLabel.setMaxWidth(Double.MAX_VALUE);
        //wonGamesLabel.setAlignment(Pos.CENTER);
        //wonGamesLabel.setFont(new Font("Comic Sans MS",12));
        //mainGridPane.add(wonGamesLabel,0,6,2,1);
        /*Label firstComputerwonGamesLabel = new Label("Komputer Pierwszy:");
        firstComputerwonGamesLabel.setMaxWidth(Double.MAX_VALUE);
        firstComputerwonGamesLabel.setAlignment(Pos.CENTER);
        firstComputerwonGamesLabel.setFont(new Font("Comic Sans MS",12));
        mainGridPane.add(firstComputerwonGamesLabel,0,7,2,1);
        Label secondComputerwonGamesLabel = new Label("Komputer Drugi:");
        secondComputerwonGamesLabel.setMaxWidth(Double.MAX_VALUE);
        secondComputerwonGamesLabel.setAlignment(Pos.CENTER);
        secondComputerwonGamesLabel.setFont(new Font("Comic Sans MS",12));
        mainGridPane.add(secondComputerwonGamesLabel,0,8,2,1);*/

        /*winsByFirstComputerLabel = new Label("0");
        winsByFirstComputerLabel.setMaxWidth(Double.MAX_VALUE);
        winsByFirstComputerLabel.setAlignment(Pos.CENTER);
        winsByFirstComputerLabel.setFont(new Font("Comic Sans MS",12));
        mainGridPane.add(winsByFirstComputerLabel,2,7);
        winsBySecondComputerLabel = new Label("0");
        winsBySecondComputerLabel.setMaxWidth(Double.MAX_VALUE);
        winsBySecondComputerLabel.setAlignment(Pos.CENTER);
        winsBySecondComputerLabel.setFont(new Font("Comic Sans MS",12));
        mainGridPane.add(winsBySecondComputerLabel,2,8);*/

        HBox runButtonHBox = new HBox();
        runButton = new Button("Uruchom");
        runButton.setPrefWidth(180);
        runButton.setPrefHeight(350);
        runButtonHBox.setAlignment(Pos.CENTER);
        runButtonHBox.getChildren().add(runButton);
        HBox.setMargin(runButton, new Insets(5, 0, 5, 0));
        HBox.setHgrow(runButton, Priority.ALWAYS);
        mainGridPane.add(runButtonHBox, 0, 8, 3, 1);

        runButton.setOnAction(event -> {
            if (runButton.getText().equals("Uruchom")) {
                gamesProgressBar.setProgress(0);
                resultsButton.setDisable(true);
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
                    gamesTask = new Task<Void>() {
                        @Override
                        protected Void call() {
                            int counter = 0;
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
                                                    } catch (Exception badMove) {
                                                        firstRobotPlayer.sendEndingMessages(false);
                                                        secondRobotPlayer.sendEndingMessages(true);
                                                        secondPlayerWins++;
                                                        break;
                                                    }
                                                } else {
                                                    if (player == 1) {
                                                        try {
                                                            move = firstRobotPlayer.makeMove(movesStorage.getLastMoveAsString());
                                                        } catch (Exception badMove) {
                                                            firstRobotPlayer.sendEndingMessages(true);
                                                            secondRobotPlayer.sendEndingMessages(false);
                                                            secondPlayerWins++;
                                                            break;
                                                        }
                                                    }
                                                    if (player == 2) {
                                                        try {
                                                            move = secondRobotPlayer.makeMove(movesStorage.getLastMoveAsString());
                                                        } catch (Exception badMove) {
                                                            firstRobotPlayer.sendEndingMessages(false);
                                                            secondRobotPlayer.sendEndingMessages(true);
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
                                                }
                                                if (!board.anyMoves()) {
                                                    if (player == 1) {
                                                        firstRobotPlayer.sendEndingMessages(true);
                                                        secondRobotPlayer.sendEndingMessages(false);
                                                        firstPlayerWins++;
                                                        break;
                                                    } else {
                                                        firstRobotPlayer.sendEndingMessages(false);
                                                        secondRobotPlayer.sendEndingMessages(true);
                                                        secondPlayerWins++;
                                                        break;
                                                    }
                                                } else {
                                                    if (player == 1) {
                                                        player = 2;
                                                    } else {
                                                        player = 1;
                                                    }
                                                }
                                            }
                                        } else {
                                            break;
                                        }
                                    }
                                    counter++;
                                    updateProgress(counter, gamesNumber);
                                    RobotPlayer.exportLogs(firstPlayerWins, secondPlayerWins, playerFirst.getName(), playerSecond.getName());
                                }
                            }
                            return null;
                        }

                    };
                    running = true;
                    Thread autoGameThread = new Thread(gamesTask);
                    gamesProgressBar.progressProperty().bind(gamesTask.progressProperty());
                    autoGameThread.start();
                    gamesTask.setOnSucceeded(event1 -> {
                        runButton.setText("Uruchom");
                        resultsButton.setDisable(false);
                        gamesProgressBar.progressProperty().unbind();
                    });

                    runButton.setText("Przerwij");

                }
            } else {
                running = false;
                resultsButton.setDisable(false);
            }
        });
        gamesProgressBar = new ProgressBar();
        gamesProgressBar.setMaxWidth(Double.MAX_VALUE);

        gamesProgressBar.setProgress(0);

        mainGridPane.add(gamesProgressBar, 0, 9, 3, 1);

        HBox resultsButtonHBox = new HBox();
        resultsButton = new Button("Wyniki");
        resultsButtonHBox.setAlignment(Pos.TOP_CENTER);
        resultsButtonHBox.getChildren().add(resultsButton);
        mainGridPane.add(resultsButtonHBox, 0, 10);

        resultsButton.setOnAction(event -> {
            ResultsPane resultsPane = new ResultsPane(getWidth(), getHeight());
            Scene resultsScene = new Scene(resultsPane, getWidth(), getHeight());
            resultsScene.getStylesheets().add(Bricks.mainStage.selectedTheme);
            Bricks.mainStage.mainStage.setScene(resultsScene);
            Bricks.mainStage.mainStage.show();
        });

        HBox backButtonHBox = new HBox();
        backButton = new Button("Cofnij");
        backButtonHBox.setAlignment(Pos.TOP_CENTER);
        backButtonHBox.getChildren().add(backButton);
        mainGridPane.add(backButtonHBox, 2, 10);

        backButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
            alert.setTitle("Potwierdznie Wyjścia");
            alert.setHeaderText("Chcesz wyjść z \"Rozgrywek\"");
            ButtonType buttonYes = new ButtonType("Tak");
            ButtonType buttonNo = new ButtonType("Anuluj");
            alert.getButtonTypes().setAll(buttonNo, buttonYes);
            alert.setContentText("Podane przez Ciebie rozmiary plansz zostaną zachowane, również po wyłączeniu programu.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonYes) {
                try {
                    Bricks.firstRobotPlayer.reset(Bricks.mainStage.BoardSize);
                    Bricks.secondRobotPlayer.reset(Bricks.mainStage.BoardSize);
                } catch (Exception ignored) {
                }
                running = false;
                gamesProgressBar.progressProperty().unbind();
                exportPoints();
                double height = Bricks.mainStage.mainStage.getHeight();
                double width = Bricks.mainStage.mainStage.getWidth();
                Bricks.mainStage.mainStage.setScene(Bricks.mainStage.sceneOfTheGame);
                Bricks.mainStage.mainStage.setWidth(width);
                Bricks.mainStage.mainStage.setHeight(height);
                Bricks.mainStage.mainStage.show();
            }
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

        playersTableView = new TableView<>();
        playersObservableList = FXCollections.observableArrayList();

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

        Label addPlayerLabel = new Label("Dodaj Gracza:");
        addPlayerLabel.setAlignment(Pos.CENTER);
        addPlayerLabel.setMaxWidth(Double.MAX_VALUE);

        Label pathToPlayerLabel = new Label("");
        pathToPlayerLabel.setAlignment(Pos.CENTER);
        pathToPlayerLabel.setMaxWidth(Double.MAX_VALUE);


        Label runCommandLabel = new Label("Komenda uruchomienia:");
        runCommandLabel.setAlignment(Pos.CENTER);
        runCommandLabel.setMaxWidth(Double.MAX_VALUE);

        TextArea runCommandTextArea = new TextArea();
        runCommandTextArea.setMaxHeight(30);
        runCommandTextArea.setMaxWidth(200);
        runCommandTextArea.setTooltip(new Tooltip("Jeśli tutaj coś wpiszesz, wybrana powyżej ścieżka zostanie zignorowana."));


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


        TextArea programNameTextArea = new TextArea();
        programNameTextArea.setMaxHeight(30);
        programNameTextArea.setMaxWidth(200);
        programNameTextArea.setTooltip(new Tooltip("Nazwa zostanie zastosowana do programu 'własnego'."));

        HBox addPlayerHBox = new HBox();
        Button addPlayerButton = new Button("Dodaj Gracza");
        addPlayerButton.setFont(Font.font("Comic Sans MS", 14));
        addPlayerHBox.getChildren().add(addPlayerButton);
        addPlayerHBox.setAlignment(Pos.CENTER);
        addPlayerButton.setOnAction(event -> {
            if (!running) {
                boolean found = false;
                if (runCommandTextArea.getText().equals("")) {
                    FileChooser chooseFile = new FileChooser();
                    chooseFile.setTitle("Wybierz gracza");
                    File openFile = chooseFile.showOpenDialog(Bricks.mainStage.mainStage);
                    if (openFile != null) {
                        try {
                            playerPath = openFile.getCanonicalPath();
                        } catch (IOException ignored) {
                            return;
                        }
                    }
                    XRobotPlayer toAdd = new XRobotPlayer("Wbudowany", playerPath);
                    found = false;
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
                }
                if (!found) {
                    exportPrograms();
                    playerPath = "";
                    runCommandTextArea.setText("");
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

        });

        Label programNameLabel = new Label("Nazwa (opcjonalne)");
        programNameLabel.setAlignment(Pos.CENTER);
        programNameLabel.setMaxWidth(Double.MAX_VALUE);

        playersGridPane.add(removePlayerHBox, 1, 9);
        playersGridPane.add(addPlayerHBox, 0, 9);
        playersGridPane.add(programNameLabel, 0, 8);
        playersGridPane.add(programNameTextArea, 1, 8);
        playersGridPane.add(runCommandTextArea, 1, 7);
        playersGridPane.add(runCommandLabel, 0, 7);
        playersGridPane.add(addPlayerLabel, 0, 6, 2, 1);
        playersGridPane.add(playersTableView, 0, 1, 2, 5);

        if (!Bricks.mainStage.getFirstPlayerProgramName().equals(Bricks.mainStage.getSecondPlayerProgramName())) {
            String programType;
            String programPath;
            if (Bricks.mainStage.firstPlayerProgramType == 0) {
                programType = "Wbudowany";
                programPath = Bricks.mainStage.playerFirstFullPath;
            } else {
                programType = "Własny";
                programPath = Bricks.mainStage.firstPlayerRunCommand;
            }
            playersObservableList.add(new XRobotPlayer(programType, programPath));

            if (Bricks.mainStage.secondPlayerProgramType == 0) {
                programType = "Wbudowany";
                programPath = Bricks.mainStage.playerSecondFullPath;
            } else {
                programType = "Własny";
                programPath = Bricks.mainStage.secondPlayerRunCommand;
            }
            playersObservableList.add(new XRobotPlayer(programType, programPath));
        }


        playersSelectTab.setContent(playersGridPane);
        playersSelectTab.setClosable(false);
        mainTabPane.getTabs().add(playersSelectTab);
        getChildren().add(mainTabPane);
        importPrograms();
        setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                alert.setTitle("Potwierdznie Wyjścia");
                alert.setHeaderText("Chcesz wyjść z \"Rozgrywek\"");
                ButtonType buttonYes = new ButtonType("Tak");
                ButtonType buttonNo = new ButtonType("Anuluj");
                alert.getButtonTypes().setAll(buttonNo, buttonYes);
                alert.setContentText("Podane przez Ciebie rozmiary plansz zostaną zachowane, również po wyłączeniu programu.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == buttonYes) {
                    try {
                        Bricks.firstRobotPlayer.reset(Bricks.mainStage.BoardSize);
                        Bricks.secondRobotPlayer.reset(Bricks.mainStage.BoardSize);
                    } catch (Exception ignored) {
                    }
                    running = false;
                    gamesProgressBar.progressProperty().unbind();
                    exportPoints();
                    double height = Bricks.mainStage.mainStage.getHeight();
                    double width = Bricks.mainStage.mainStage.getWidth();
                    Bricks.mainStage.mainStage.setScene(Bricks.mainStage.sceneOfTheGame);
                    Bricks.mainStage.mainStage.setWidth(width);
                    Bricks.mainStage.mainStage.setHeight(height);
                    Bricks.mainStage.mainStage.show();
                }
            }
        });
        widthProperty().addListener((observable, oldValue, newValue) -> {
            mainGridPane.setPrefWidth(newValue.doubleValue());
            mainTabPane.setPrefWidth(newValue.doubleValue());
        });
        heightProperty().addListener((observable, oldValue, newValue) -> {
            mainGridPane.setPrefHeight(newValue.doubleValue());
            boardsListLabel.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 20));
            playersListLabel.setFont(Font.font("Comic Sans MS", newValue.doubleValue() / 20));
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

    private void exportPoints() {
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

    private void importPrograms() {
        try {
            String path = System.getProperty("user.home") + "/Documents/Bricks/programs";
            Scanner in = new Scanner(new File(path));
            String line;
            String[] divided;
            while (in.hasNextLine()) {
                line = in.nextLine();
                divided = line.split("=");
                try {
                    if (divided[0].equals("Własny")) {
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
                        }
                    } else {
                        XRobotPlayer toAdd = new XRobotPlayer(divided[0], divided[1]);
                        RobotPlayer toCheck = toAdd.getRobotPlayer();
                        if (toCheck != null) {
                            boolean found = false;
                            for (XRobotPlayer aPlayersObservableList : playersObservableList) {
                                if (toAdd.getName().equals(aPlayersObservableList.getName()))
                                    found = true;
                            }
                            if (!found)
                                playersObservableList.add(toAdd);
                        }
                    }

                } catch (NumberFormatException ignored) {
                }
            }
            in.close();
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

    private String playerPath = "";

    private ListView<String> boardsSizesListView;
    private Button runButton;
    private Button backButton;
    private Button resultsButton;
    private ProgressBar gamesProgressBar;
    //private Stage gamesStage;

    private boolean running = false;

    private Task<Void> gamesTask;

    //  private  Label winsByFirstComputerLabel;

    //  private Label winsBySecondComputerLabel;

    private int firstPlayerWins = 0;
    private int secondPlayerWins = 0;

    private TabPane mainTabPane;

    private TableView<XRobotPlayer> playersTableView;
    private final ObservableList<XRobotPlayer> playersObservableList;
}
