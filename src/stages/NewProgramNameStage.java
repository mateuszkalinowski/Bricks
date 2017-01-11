package stages;

import XClasses.XRobotPlayer;
import core.Bricks;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
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
                else if (Bricks.mainStage.gameChooserPane.gamesPane.getProgramsNames().contains(newNameTextField.getText())) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                    Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                    alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                    alert.setTitle("Błąd zmiany nazwy");
                    alert.setHeaderText("Program o takiej nazwie już instnieje.");
                    alert.setContentText("Wpisz inną nazwę, lub zamknij okno edycji aby anulować.");
                    ButtonType buttonYes = new ButtonType("Ok");
                    alert.getButtonTypes().setAll(buttonYes);
                    alert.showAndWait();
                } else {
                    Bricks.mainStage.gameChooserPane.gamesPane.changeIndexName(newNameTextField.getText(),indexToEdit);
                    mainStage.close();
                }
            }
        });

        newNameTextField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
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
                    else if (Bricks.mainStage.gameChooserPane.gamesPane.getProgramsNames().contains(newNameTextField.getText())) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.getDialogPane().getStylesheets().add(Bricks.mainStage.selectedTheme);
                        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                        alertStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
                        alert.setTitle("Błąd zmiany nazwy");
                        alert.setHeaderText("Program o takiej nazwie już instnieje.");
                        alert.setContentText("Wpisz inną nazwę, lub zamknij okno edycji aby anulować.");
                        ButtonType buttonYes = new ButtonType("Ok");
                        alert.getButtonTypes().setAll(buttonYes);
                        alert.showAndWait();
                    } else {
                        Bricks.mainStage.gameChooserPane.gamesPane.changeIndexName(newNameTextField.getText(),indexToEdit);
                        mainStage.close();
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
        mainScene = new Scene(mainGridPane, 300, 100);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        mainStage.initModality(Modality.APPLICATION_MODAL);
        mainStage.setResizable(false);
        mainStage.getIcons().add(new Image(MainStage.class.getResourceAsStream("resources/brick_red.png")));
        mainScene.getStylesheets().add(Bricks.mainStage.selectedTheme);
        mainStage.setTitle("Zmiana Nazwy Programu");
        mainStage.show();
    }

    public NewProgramNameStage(XRobotPlayer toEdit, int indexToEdit) {
        this.toEdit = toEdit;
        this.indexToEdit = indexToEdit;
    }

    private Scene mainScene;
    private Stage mainStage;

    private XRobotPlayer toEdit;

    private int indexToEdit;
}
