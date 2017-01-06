package XClasses;

/**
 * Created by Mateusz on 04.10.2016.
 * Project Bricks
 */
public class XSettings {
    public XSettings(int BoardSize, javafx.scene.paint.Color playerFirstColor, javafx.scene.paint.Color playerSecondColor, boolean isSound, int volume, boolean debugMode, String path1, String path2, int programTypeFirst, int programTypeSecond, String runCommandFirst, String runCommandSecond, int computerPlayerType, int theme) {
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
        this.theme = theme;
    }

    public String getFirstComputerPlayerPath() {
        return firstComputerPlayerPath;
    }

    public String getSecondComputerPlayerPath() {
        return secondComputerPlayerPath;
    }

    public int getBoardSize() {
        return BoardSize;
    }

    public javafx.scene.paint.Color getPlayerFirstColor() {
        return playerFirstColor;
    }

    public javafx.scene.paint.Color getPlayerSecondColor() {
        return playerSecondColor;
    }

    public boolean getIsSound() {
        return isSound;
    }

    public int getVolume() {
        return volume;
    }

    public boolean getDebugMode() {
        return debugMode;
    }

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

    public int getComputerPlayerType() {
        return computerPlayerType;
    }

    public int getTheme() {
        return theme;
    }

    private boolean isSound;
    private boolean debugMode;

    private int BoardSize;
    private int volume;
    private int firstPlayerProgramType;
    private int secondPlayerProgramType;
    private int computerPlayerType;

    private javafx.scene.paint.Color playerFirstColor;
    private javafx.scene.paint.Color playerSecondColor;

    private String firstComputerPlayerPath;
    private String secondComputerPlayerPath;
    private String firstPlayerRunCommand;
    private String secondPlayerRunCommand;

    private int theme;
}
