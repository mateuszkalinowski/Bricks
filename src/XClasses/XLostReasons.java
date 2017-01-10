package XClasses;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Mateusz on 10.01.2017.
 * Project Bricks
 */
public class XLostReasons {
    private final SimpleStringProperty firstProgramName;
    private final SimpleStringProperty secondProgramName;
    private final SimpleStringProperty winProgramName;
    private final SimpleStringProperty reason;

    public XLostReasons(String firstProgramName,String secondProgramName,int win,String reason) {
        this.firstProgramName = new SimpleStringProperty(firstProgramName);
        this.secondProgramName = new SimpleStringProperty(secondProgramName);
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
    public String getWinProgramName(){
        return winProgramName.get();
    }
    public String getReason(){
        return reason.get();
    }


}

