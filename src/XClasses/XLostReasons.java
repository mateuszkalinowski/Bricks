package XClasses;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Mateusz on 10.01.2017.
 * Project Bricks
 */
public class XLostReasons {
    private final SimpleStringProperty firstProgramName;
    private final SimpleStringProperty secondProgramName;
    private final SimpleStringProperty boardSize;
    private final SimpleStringProperty winProgramName;
    private final SimpleStringProperty reason;

    public XLostReasons(String firstProgramName,String secondProgramName,int boardSize, int win,String reason) {
        this.firstProgramName = new SimpleStringProperty(firstProgramName);
        this.secondProgramName = new SimpleStringProperty(secondProgramName);
        this.boardSize = new SimpleStringProperty(boardSize + "x"+boardSize);
        if(win==1) {
            winProgramName = new SimpleStringProperty(firstProgramName);
        }
        else {
            winProgramName = new SimpleStringProperty(secondProgramName);
        }
        this.reason = new SimpleStringProperty(reason);
    }
    public String getFirstProgramName(){
        return firstProgramName.get();
    }
    public String getSecondProgramName(){
        return secondProgramName.get();
    }
    public String getBoardSize() { return boardSize.get();}
    public String getWinProgramName(){
        return winProgramName.get();
    }
    public String getReason(){
        return reason.get();
    }

    public void setFirstProgramName(String newName){ firstProgramName.set(newName);}
    public void setSecondProgramName(String newName){secondProgramName.set(newName);}
    public void setWinProgramName(String newName) {winProgramName.set(newName);}


}

