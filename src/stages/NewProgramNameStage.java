package stages;

import XClasses.XRobotPlayer;
import core.Bricks;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Mateusz on 11.01.2017.
 * Project InferenceEngine
 */
public class NewProgramNameStage extends Application {
    public void start(Stage primaryStage) throws Exception {
        GridPane mainGridPane = new GridPane();
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(50);
        mainGridPane.getColumnConstraints().addAll(column, column);
        RowConstraints row = new RowConstraints();
        row.setPercentHeight(50);
        mainGridPane.getRowConstraints().addAll(row, row);

        TextField newNameTextField = new TextField();
        newNameTextField.setText(toEdit.getName());
        mainGridPane.add(newNameTextField, 1, 0);

        Label newProgramNameLabel = new Label("Nowa Nazwa:");
        newProgramNameLabel.setFont(Font.font("Comic Sans MS", 18));
        newProgramNameLabel.setAlignment(Pos.CENTER);
        newProgramNameLabel.setMaxWidth(Double.MAX_VALUE);
        mainGridPane.add(newProgramNameLabel, 0, 0);

        HBox saveButtonHBox = new HBox();
        saveButtonHBox.setAlignment(Pos.CENTER);
        Button saveButton = new Button("Ok");
        saveButton.setFont(Font.font("Comic Sans MS", 14));
        saveButtonHBox.getChildren().add(saveButton);
        saveButton.setOnAction(event -> {
            if(newNameTextField.getText().equals(toEdit.getName())) {
               mainStage.close();
            }
            else if (newNameTextField.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                alert.setTitle("Błąd zmiany nazwy");
                alert.setHeaderText("Nazwa programu nie może być pusta.");
                alert.setContentText("Wpisz poprawną nazwę, lub zamknij okno edycji aby anulować.");
                ButtonType buttonYes = new ButtonType("Ok");
                alert.getButtonTypes().setAll(buttonYes);
                alert.showAndWait();
            }
            else if (Bricks.mainStage.gameChooserPane.gamesPane.getProgramsNames().contains(newNameTextField.getText())|| resultsFileContainProgramName(newNameTextField.getText())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                alert.setTitle("Błąd zmiany nazwy");
                alert.setHeaderText("Program o takiej nazwie już instnieje.");
                alert.setContentText("Wpisz inną nazwę, lub zamknij okno edycji aby anulować. UWAGA: Gracz ten nie musi być widoczny na liście, może być również zapisany w wynikach.");
                ButtonType buttonYes = new ButtonType("Ok");
                alert.getButtonTypes().setAll(buttonYes);
                alert.showAndWait();
            } else {
                boolean found = false;
                if(newNameTextField.getText().contains("\\") || newNameTextField.getText().contains("/"))
                    found=true;
                if(!found) {
                    Bricks.mainStage.gameChooserPane.gamesPane.changeIndexName(newNameTextField.getText(), indexToEdit);
                    mainStage.close();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                    Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                    alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                    alert.setTitle("Błąd zmiany nazwy");
                    alert.setHeaderText("Nazwa zamiera niedozwolone znaki.");
                    alert.setContentText("Niedozwolone znaki to: '\\' i '/'");
                    ButtonType buttonYes = new ButtonType("Ok");
                    alert.getButtonTypes().setAll(buttonYes);
                    alert.showAndWait();
                }
            }
        });

        newNameTextField.setOnKeyReleased(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                if(newNameTextField.getText().equals(toEdit.getName())) {
                    mainStage.close();
                }
                else if (newNameTextField.getText().equals("")) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                    Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                    alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                    alert.setTitle("Błąd zmiany nazwy");
                    alert.setHeaderText("Nazwa programu nie może być pusta.");
                    alert.setContentText("Wpisz poprawną nazwę, lub zamknij okno edycji aby anulować.");
                    ButtonType buttonYes = new ButtonType("Ok");
                    alert.getButtonTypes().setAll(buttonYes);
                    alert.showAndWait();
                }
                else if (Bricks.mainStage.gameChooserPane.gamesPane.getProgramsNames().contains(newNameTextField.getText())|| resultsFileContainProgramName(newNameTextField.getText())) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                    Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                    alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                    alert.setTitle("Błąd zmiany nazwy");
                    alert.setHeaderText("Program o takiej nazwie już instnieje.");
                    alert.setContentText("Wpisz inną nazwę, lub zamknij okno edycji aby anulować. UWAGA: Gracz ten nie musi być widoczny na liście, może być również zapisany w wynikach.");
                    ButtonType buttonYes = new ButtonType("Ok");
                    alert.getButtonTypes().setAll(buttonYes);
                    alert.showAndWait();
                } else {
                    boolean found = false;
                    if(newNameTextField.getText().contains("\\"))
                        found=true;
                    if(!found) {
                        Bricks.mainStage.gameChooserPane.gamesPane.changeIndexName(newNameTextField.getText(), indexToEdit);
                        mainStage.close();
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                        alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                        alert.setTitle("Błąd zmiany nazwy");
                        alert.setHeaderText("Nazwa zamiera niedozwolone znaki.");
                        alert.setContentText("Niedozwolone znaki to: '\\'");
                        ButtonType buttonYes = new ButtonType("Ok");
                        alert.getButtonTypes().setAll(buttonYes);
                        alert.showAndWait();
                    }
                }
            }
        });

        HBox cancelButtonHBox = new HBox();
        cancelButtonHBox.setAlignment(Pos.CENTER);
        Button cancelButton = new Button("Anuluj");
        cancelButton.setFont(Font.font("Comic Sans MS", 14));
        cancelButtonHBox.getChildren().add(cancelButton);
        cancelButton.setOnAction(event -> mainStage.close());

        mainGridPane.add(saveButtonHBox, 0, 1);
        mainGridPane.add(cancelButtonHBox, 1, 1);
        Scene mainScene = new Scene(mainGridPane, 300, 100);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        mainStage.initModality(Modality.APPLICATION_MODAL);
        mainStage.setResizable(false);
        mainStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
        mainScene.getStylesheets().add(Bricks.mainStage.selectedTheme);
        mainStage.setTitle("Zmiana Nazwy Programu");
        mainStage.show();
    }

    NewProgramNameStage(XRobotPlayer toEdit, int indexToEdit) {
        this.toEdit = toEdit;
        this.indexToEdit = indexToEdit;
    }

    private Stage mainStage;

    private XRobotPlayer toEdit;

    private int indexToEdit;

    private boolean resultsFileContainProgramName(String toCheck) {
        try {
            String path = System.getProperty("user.home") + "/Documents/Bricks";
            if (!new File(path + "/logs.txt").exists()) {
                return false;
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
                if(winsMap.containsKey(toCheck)) {
                    return true;
                }
            }

        } catch (IOException ignored) {
        }

        return false;
    }
}
