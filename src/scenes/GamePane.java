package scenes;

import core.Bricks;
import javafx.application.Application;
import javafx.beans.NamedArg;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.Effect;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.BoardLogic;
import logic.MovesStorage;

import java.awt.*;

/**
 * Created by Mateusz on 18.12.2016.
 * Project InferenceEngine
 */
public class GamePane extends Pane {
    public GamePane(BoardLogic board, int gametype){
        this.board = board;
        this.gamemode = gametype;
        stop = new Button("Stop");
        canvas = new Canvas();

        mainGridPane = new GridPane();
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(100);
        mainGridPane.getColumnConstraints().add(column);
        RowConstraints rowWithGame = new RowConstraints();
        RowConstraints rowWithMenu = new RowConstraints();
        rowWithGame.setPercentHeight(95);
        rowWithMenu.setPercentHeight(5);
        mainGridPane.getRowConstraints().add(rowWithGame);
        mainGridPane.getRowConstraints().add(rowWithMenu);
        this.board = board;
        mainGridPane.add(canvas,0,0);

        getChildren().add(mainGridPane);
    }
    public void drawFrame(int w,int h){
        int width = w;
        int height = (int)mainGridPane.getRowConstraints().get(0).getPercentHeight()*h/100;
        //canvas = new Canvas();
        canvas.setHeight(height);
        canvas.setWidth(width);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0,0,width,height);
        oneFieldWidth = (width - margin * 2.0) / (board.width * 1.0);
        oneFieldHeight = (height - margin * 2.0) / (board.height * 1.0);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(margin, margin, width - margin * 2, height - margin * 2);
        int inFieldMargin = 0;

        java.awt.Color awtFirstColor = new java.awt.Color(100,100,100);
        int r = awtFirstColor.getRed();
        int g = awtFirstColor.getGreen();
        int b = awtFirstColor.getBlue();
        int a = awtFirstColor.getAlpha();
        double opacity = a / 255.0 ;
        javafx.scene.paint.Color firstPlayerColor = javafx.scene.paint.Color.rgb(r, g, b, opacity);

        java.awt.Color awtSecondColor = new java.awt.Color(200,200,200);
        r = awtSecondColor.getRed();
        g = awtSecondColor.getGreen();
        b = awtSecondColor.getBlue();
        a = awtSecondColor.getAlpha();
        opacity = a / 255.0 ;
        javafx.scene.paint.Color secondPlayerColor = javafx.scene.paint.Color.rgb(r, g, b, opacity);

        if (actualPlayer == 1) {
            if (isSelected) {
                gc.setStroke(firstPlayerColor);
                int j = selectedX;
                int i = selectedY;
                if (j != board.width - 1 && i != board.height - 1)
                    gc.fillRect(j * (int) Math.round(oneFieldWidth) + margin + inFieldMargin, i * (int) Math.round(oneFieldHeight) + margin + inFieldMargin, (int) Math.round(oneFieldWidth) - 2 * inFieldMargin, (int) Math.round(oneFieldHeight) - 2 * inFieldMargin);
                else if (j == board.width - 1 && i != board.height - 1)
                    gc.fillRect(j * (int) Math.round(oneFieldWidth) + margin + inFieldMargin, i * (int) Math.round(oneFieldHeight) + margin + inFieldMargin, width - margin * 2 - j * (int) Math.round(oneFieldWidth), (int) Math.round(oneFieldHeight) - 2 * inFieldMargin);
                else if (j != board.width - 1 && i == board.height - 1)
                    gc.fillRect(j * (int) Math.round(oneFieldWidth) + margin + inFieldMargin, i * (int) Math.round(oneFieldHeight) + margin + inFieldMargin, (int) Math.round(oneFieldWidth) - 2 * inFieldMargin, height - margin * 2 - i * (int) Math.round(oneFieldHeight));
                else
                    gc.fillRect(j * (int) Math.round(oneFieldWidth) + margin + inFieldMargin, i * (int) Math.round(oneFieldHeight) + margin + inFieldMargin, width - margin * 2 - j * (int) Math.round(oneFieldWidth), height - margin * 2 - i * (int) Math.round(oneFieldHeight));
            }
        }
        if (actualPlayer == 2) {
            if (isSelected) {
                gc.setStroke(secondPlayerColor);
                int j = selectedX;
                int i = selectedY;
                if (j != board.width - 1 && i != board.height - 1)
                    gc.fillRect(j * (int) Math.round(oneFieldWidth) + margin + inFieldMargin, i * (int) Math.round(oneFieldHeight) + margin + inFieldMargin, (int) Math.round(oneFieldWidth) - 2 * inFieldMargin, (int) Math.round(oneFieldHeight) - 2 * inFieldMargin);
                else if (j == board.width - 1 && i != board.height - 1)
                    gc.fillRect(j * (int) Math.round(oneFieldWidth) + margin + inFieldMargin, i * (int) Math.round(oneFieldHeight) + margin + inFieldMargin, width - margin * 2 - j * (int) Math.round(oneFieldWidth), (int) Math.round(oneFieldHeight) - 2 * inFieldMargin);
                else if (j != board.width - 1 && i == board.height - 1)
                    gc.fillRect(j * (int) Math.round(oneFieldWidth) + margin + inFieldMargin, i * (int) Math.round(oneFieldHeight) + margin + inFieldMargin, (int) Math.round(oneFieldWidth) - 2 * inFieldMargin, height - margin * 2 - i * (int) Math.round(oneFieldHeight));
                else
                    gc.fillRect(j * (int) Math.round(oneFieldWidth) + margin + inFieldMargin, i * (int) Math.round(oneFieldHeight) + margin + inFieldMargin, width - margin * 2 - j * (int) Math.round(oneFieldWidth), height - margin * 2 - i * (int) Math.round(oneFieldHeight));
            }
        }

        for (int i = 0; i < board.height; i++) {
            for (int j = 0; j < board.width; j++) {
                if (board.board[j][i] == 1) {
                    gc.setStroke(firstPlayerColor);
                    if (j != board.width - 1 && i != board.height - 1)
                        gc.fillRect(j * (int) Math.round(oneFieldWidth) + margin + inFieldMargin, i * (int) Math.round(oneFieldHeight) + margin + inFieldMargin, (int) Math.round(oneFieldWidth) - 2 * inFieldMargin, (int) Math.round(oneFieldHeight) - 2 * inFieldMargin);
                    else if (j == board.width - 1 && i != board.height - 1)
                        gc.fillRect(j * (int) Math.round(oneFieldWidth) + margin + inFieldMargin, i * (int) Math.round(oneFieldHeight) + margin + inFieldMargin, width - margin * 2 - j * (int) Math.round(oneFieldWidth), (int) Math.round(oneFieldHeight) - 2 * inFieldMargin);
                    else if (j != board.width - 1 && i == board.height - 1)
                        gc.fillRect(j * (int) Math.round(oneFieldWidth) + margin + inFieldMargin, i * (int) Math.round(oneFieldHeight) + margin + inFieldMargin, (int) Math.round(oneFieldWidth) - 2 * inFieldMargin, height - margin * 2 - i * (int) Math.round(oneFieldHeight));
                    else
                        gc.fillRect(j * (int) Math.round(oneFieldWidth) + margin + inFieldMargin, i * (int) Math.round(oneFieldHeight) + margin + inFieldMargin, width - margin * 2 - j * (int) Math.round(oneFieldWidth), height - margin * 2 - i * (int) Math.round(oneFieldHeight));
                }
                if (board.board[j][i] == 2) {
                    gc.setStroke(secondPlayerColor);
                    if (j != board.width - 1 && i != board.height - 1)
                        gc.fillRect(j * (int) Math.round(oneFieldWidth) + margin + inFieldMargin, i * (int) Math.round(oneFieldHeight) + margin + inFieldMargin, (int) Math.round(oneFieldWidth) - 2 * inFieldMargin, (int) Math.round(oneFieldHeight) - 2 * inFieldMargin);
                    else if (j == board.width - 1 && i != board.height - 1)
                        gc.fillRect(j * (int) Math.round(oneFieldWidth) + margin + inFieldMargin, i * (int) Math.round(oneFieldHeight) + margin + inFieldMargin, width - margin * 2 - j * (int) Math.round(oneFieldWidth), (int) Math.round(oneFieldHeight) - 2 * inFieldMargin);
                    else if (j != board.width - 1 && i == board.height - 1)
                        gc.fillRect(j * (int) Math.round(oneFieldWidth) + margin + inFieldMargin, i * (int) Math.round(oneFieldHeight) + margin + inFieldMargin, (int) Math.round(oneFieldWidth) - 2 * inFieldMargin, height - margin * 2 - i * (int) Math.round(oneFieldHeight));
                    else
                        gc.fillRect(j * (int) Math.round(oneFieldWidth) + margin + inFieldMargin, i * (int) Math.round(oneFieldHeight) + margin + inFieldMargin, width - margin * 2 - j * (int) Math.round(oneFieldWidth), height - margin * 2 - i * (int) Math.round(oneFieldHeight));
                }
            }
        }
        for (int i = 0; i < board.height; i++) {
            if (i > 0) {
                gc.setStroke(javafx.scene.paint.Color.BLACK);
                gc.strokeLine(margin, margin + i * oneFieldHeight, width - margin, margin + i * oneFieldHeight);
            }

            for (int j = 0; j < board.width; j++) {
                if (j > 0) {
                    gc.setStroke(javafx.scene.paint.Color.BLACK);
                    gc.strokeLine(margin + j * oneFieldWidth, margin, margin + j * oneFieldWidth, height - margin);
                }
            }
        }


    }
    private BorderPane mainBorderPane = new BorderPane();
    Button stop;
    Canvas canvas = new Canvas();
    private VBox gameAreaVBox = new VBox();
    GridPane mainGridPane;


    private BoardLogic board;

    private int actualPlayer = 1;

    private int margin = 20;

    private double oneFieldWidth;
    private double oneFieldHeight;

    private boolean[] directions;

    private boolean isSelected = false;
    private int selectedX;
    private int selectedY;


    public MovesStorage movesStorage;
    private int gamemode;

}
