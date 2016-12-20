package scenes;

import javafx.application.Application;
import javafx.beans.NamedArg;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by Mateusz on 18.12.2016.
 * Project InferenceEngine
 */
public class GameScene extends Pane {
    public GameScene(){
        stop = new Button("Stop");
        canvas = new Canvas();
        gameAreaVBox.getChildren().add(canvas);
        mainBorderPane.setCenter(gameAreaVBox);
        mainBorderPane.setBottom(stop);
        mainBorderPane.setMaxWidth(Double.MAX_VALUE);
        mainBorderPane.setMaxHeight(Double.MAX_VALUE);
        getChildren().add(mainBorderPane);
    }
    public void drawFrame(int widht,int height){
        canvas.setHeight(height-stop.getHeight());
        canvas.setWidth(widht);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,widht,height-stop.getHeight());
    }
    private BorderPane mainBorderPane = new BorderPane();
    Button stop;
    Canvas canvas = new Canvas();
    private VBox gameAreaVBox = new VBox();
}
