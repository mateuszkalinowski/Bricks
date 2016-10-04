package core;

import java.awt.*;

/**
 * Created by Mateusz on 04.10.2016.
 * Project Bricks
 */
public class Settings {
    public Settings(int BoardSize,Color playerFirstColor,Color playerSecondColor) {
        this.BoardSize = BoardSize;
        this.playerFirstColor = playerFirstColor;
        this.playerSecondColor = playerSecondColor;
    }

    public int getBoardSize(){
        return  BoardSize;
    }
    public Color getPlayerFirstColor(){
        return playerFirstColor;
    }
    public Color getPlayerSecondColor(){
        return playerSecondColor;
    }

    private int BoardSize;
    private Color playerFirstColor;
    private Color playerSecondColor;
}
