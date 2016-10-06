package core;

import java.awt.*;

/**
 * Created by Mateusz on 04.10.2016.
 * Project Bricks
 */
public class Settings {
    public Settings(int BoardSize,Color playerFirstColor,Color playerSecondColor,boolean isSound,int volume) {
        this.BoardSize = BoardSize;
        this.playerFirstColor = playerFirstColor;
        this.playerSecondColor = playerSecondColor;
        this.isSound = isSound;
        this.volume = volume;
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
    public boolean getIsSound() {return isSound;}
    public int getVolume() {return volume;}
    private int BoardSize;
    private Color playerFirstColor;
    private Color playerSecondColor;
    private boolean isSound;
    private int volume;
}
