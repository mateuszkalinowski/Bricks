package stages;

import com.apple.eawt.Application;
import core.Bricks;
import exceptions.InvalidMoveException;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.AutoGameThread;
import logic.BoardLogic;
import logic.MovesStorage;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

/**
 * Created by Mateusz on 25.12.2016.
 * Project Bricks
 */
public class GamesStage extends Application {
    public void start(Stage primaryStage){

        GridPane mainGridPane = new GridPane();

        RowConstraints row = new RowConstraints();
        row.setPercentHeight(8.3);
        for(int i = 0; i < 12;i++) {
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
        mainGridPane.add(boardsSizesListView,1,0,1,6);

        boardsSizesListView.getItems().add("");
        boardsSizesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        boardsSizesListView.getSelectionModel().selectFirst();

        boardsSizesListView.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                int selectedIndex = boardsSizesListView.getSelectionModel().getSelectedIndex();
                if (boardsSizesListView.getItems().size() > 0 && selectedIndex>=0) {
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
                            if (currentText.length() <= 2 && currentText.length()>0)
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
                                boardsSizesListView.scrollTo(selectedIndex+1);
                            }

                        }
                    }
                }
            }
        });


        Label wonGamesLabel = new Label("Wygranych:");
        wonGamesLabel.setMaxWidth(Double.MAX_VALUE);
        wonGamesLabel.setAlignment(Pos.CENTER);
        wonGamesLabel.setFont(new Font("Comic Sans MS",12));
        mainGridPane.add(wonGamesLabel,0,6,2,1);
        Label firstComputerwonGamesLabel = new Label("Komputer Pierwszy:");
        firstComputerwonGamesLabel.setMaxWidth(Double.MAX_VALUE);
        firstComputerwonGamesLabel.setAlignment(Pos.CENTER);
        firstComputerwonGamesLabel.setFont(new Font("Comic Sans MS",12));
        mainGridPane.add(firstComputerwonGamesLabel,0,7,2,1);
        Label secondComputerwonGamesLabel = new Label("Komputer Drugi:");
        secondComputerwonGamesLabel.setMaxWidth(Double.MAX_VALUE);
        secondComputerwonGamesLabel.setAlignment(Pos.CENTER);
        secondComputerwonGamesLabel.setFont(new Font("Comic Sans MS",12));
        mainGridPane.add(secondComputerwonGamesLabel,0,8,2,1);

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

        HBox runButtonHBox = new HBox();
        runButton = new Button("Uruchom");
        runButton.setPrefWidth(200);
        runButton.setPrefHeight(350);
        runButtonHBox.setAlignment(Pos.CENTER);
        runButtonHBox.getChildren().add(runButton);
        HBox.setMargin(runButton, new Insets(5,0,5,0));
        HBox.setHgrow(runButton, Priority.ALWAYS);
        mainGridPane.add(runButtonHBox,0,9,3,1);

        runButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (runButton.getText().equals("Uruchom")) {
                    gamesProgressBar.setProgress(0);
                ArrayList<Integer> boardsSizes = new ArrayList<Integer>();
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
                            firstPlayerWins = 0;
                            secondPlayerWins = 0;
                            int counter = 0;
                            for (Integer boardsSize : boardsSizes) {
                                if (running) {
                                    counter++;
                                    BoardLogic board = new BoardLogic(boardsSize);
                                    MovesStorage movesStorage = new MovesStorage();
                                    try {
                                        Bricks.firstRobotPlayer.reset(boardsSize);
                                        Bricks.secondRobotPlayer.reset(boardsSize);
                                    } catch (Exception ignored) {
                                        break;
                                    }
                                    int player = 1;
                                    while (true && running) {
                                        int[] move = new int[4];
                                        if (movesStorage.isEmpty()) {
                                            try {
                                                move = Bricks.firstRobotPlayer.makeMove("ZACZYNAJ");
                                            } catch (Exception badMove) {
                                                secondPlayerWins++;
                                                break;
                                            }
                                        } else {
                                            if (player == 1) {
                                                try {
                                                    move = Bricks.firstRobotPlayer.makeMove(movesStorage.getLastMoveAsString());
                                                } catch (Exception badMove) {
                                                    secondPlayerWins++;
                                                    break;
                                                }
                                            }
                                            if (player == 2) {
                                                try {
                                                    move = Bricks.secondRobotPlayer.makeMove(movesStorage.getLastMoveAsString());
                                                } catch (Exception badMove) {
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
                                                firstPlayerWins++;
                                                break;
                                            } else {
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
                                    if (running) {
                                    double progress = ((counter * 1.0) / (boardsSizes.size() * 1.0)) * 100;
                                    // Bricks.mainFrame.autoPlayFrame.setProgress((int) Math.round(progress));
                                    //gamesProgressBar.setProgress(progress);
                                        updateProgress(counter,boardsSizes.size());
                                    }
                                } else {
                                    break;
                                }
                            }
                            return null;
                        }

                    };
                    running=true;
                    Thread autoGameThread = new Thread(gamesTask);
                    gamesProgressBar.progressProperty().bind(gamesTask.progressProperty());
                    autoGameThread.start();
                    gamesTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                        @Override
                        public void handle(WorkerStateEvent event) {
                            runButton.setText("Uruchom");
                            winsByFirstComputerLabel.setText(firstPlayerWins+"");
                            winsBySecondComputerLabel.setText(secondPlayerWins+"");
                            gamesProgressBar.progressProperty().unbind();
                        }
                    });

                    runButton.setText("Przerwij");

                }
            } else {
                    running=false;
                }
            }
        });

        HBox backButtonHBox = new HBox();
        backButton = new Button("Powrót");
        backButton.setPrefWidth(140);
        backButton.setPrefHeight(350);
        backButtonHBox.setAlignment(Pos.CENTER);
        backButtonHBox.getChildren().add(backButton);
        HBox.setMargin(backButton, new Insets(5,0,5,0));
        HBox.setHgrow(backButton, Priority.ALWAYS);
        mainGridPane.add(backButtonHBox,0,11,3,1);
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Bricks.firstRobotPlayer.reset(Bricks.mainStage.BoardSize);
                    Bricks.secondRobotPlayer.reset(Bricks.mainStage.BoardSize);
                }
                catch (Exception e){

                }
                running=false;
                gamesProgressBar.progressProperty().unbind();
                gamesStage.close();
            }
        });

        gamesProgressBar = new ProgressBar();
        gamesProgressBar.setMaxWidth(Double.MAX_VALUE);

        gamesProgressBar.setProgress(0);

        mainGridPane.add(gamesProgressBar,0,10,3,1);

        gamesStage = new Stage();
        Scene gamesScene = new Scene(mainGridPane,270,350);
        gamesStage.setScene(gamesScene);
        gamesStage.setResizable(false);
        gamesStage.initModality(Modality.APPLICATION_MODAL);
        gamesStage.show();
        gamesStage.setResizable(true);
        gamesStage.setMinHeight(350);
        gamesStage.setMinWidth(270);


        gamesStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    running = false;
                    Bricks.firstRobotPlayer.reset(Bricks.mainStage.BoardSize);
                    Bricks.secondRobotPlayer.reset(Bricks.mainStage.BoardSize);
                } catch (Exception e) {

                }
            }
        });

    }
    ListView<String> boardsSizesListView;
    Button runButton;
    Button backButton;
    ProgressBar gamesProgressBar;
    Stage gamesStage;

    boolean running = false;

    Task<Void> gamesTask;

    Label winsByFirstComputerLabel;

    Label winsBySecondComputerLabel;

    int firstPlayerWins = 0;
    int secondPlayerWins = 0;
}