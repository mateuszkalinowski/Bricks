package scenes;

import javafx.application.Application;
import javafx.beans.NamedArg;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Created by Mateusz on 18.12.2016.
 * Project InferenceEngine
 */
public class GameScene extends Canvas {
    public GameScene(){
        super(500,500);
    }
    public void drawFrame(int widht,int height){
        System.out.println(widht + " " + height);
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,getWidth(),getHeight());
    }
}
