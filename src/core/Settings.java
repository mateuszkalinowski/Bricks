package core;

import java.awt.*;

/**
 * Created by Mateusz on 04.10.2016.
 * Project Bricks
 */
public class Settings {
    public Settings(int BoardSize,Color playerFirstColor,Color playerSecondColor,boolean isSound,int volume,boolean debugMode,String path1,String path2,int programTypeFirst,int programTypeSecond,String runCommandFirst, String runCommandSecond,int computerPlayerType) {
        this.BoardSize = BoardSize;
        this.playerFirstColor = playerFirstColor;
        this.playerSecondColor = playerSecondColor;
        this.isSound = isSound;
        this.volume = volume;
        this.debugMode = debugMode;
        firstComputerPlayerPath = path1;
        secondComputerPlayerPath = path2;
        firstPlayerProgramType = programTypeFirst;
        secondPlayerProgramType = programTypeSecond;
        firstPlayerRunCommand = runCommandFirst;
        secondPlayerRunCommand = runCommandSecond;
        this.computerPlayerType = computerPlayerType;
    }
    public String getFirstComputerPlayerPath(){return firstComputerPlayerPath;}
    public String getSecondComputerPlayerPath(){return secondComputerPlayerPath;}
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
    public boolean getDebugMode() {return debugMode;}
    private int BoardSize;
    private Color playerFirstColor;
    private Color playerSecondColor;
    private boolean isSound;
    private int volume;
    private boolean debugMode;
    private String firstComputerPlayerPath;
    private String secondComputerPlayerPath;

    private int firstPlayerProgramType;
    private int secondPlayerProgramType;

    private String firstPlayerRunCommand;
    private String secondPlayerRunCommand;

    private int computerPlayerType;

    public int getComputerPlayerType(){return computerPlayerType;}

    public String getSecondPlayerRunCommand() {
        return secondPlayerRunCommand;
    }

    public String getFirstPlayerRunCommand() {
        return firstPlayerRunCommand;
    }

    public int getSecondPlayerProgramType() {
        return secondPlayerProgramType;
    }

    public int getFirstPlayerProgramType() {
        return firstPlayerProgramType;
    }
}
