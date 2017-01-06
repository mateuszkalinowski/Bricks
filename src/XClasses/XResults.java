package XClasses;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Mateusz on 06.01.2017.
 * Project Bricks
 */
public class XResults {
    private final SimpleStringProperty name;
    private final SimpleStringProperty wins;
    private final SimpleStringProperty lost;
    private final SimpleStringProperty winsToAll;

    public XResults(String name,int wins,int lost) {
        this.name = new SimpleStringProperty(name);
        this.wins = new SimpleStringProperty(wins+"");
        this.lost = new SimpleStringProperty(lost+"");

        double value = wins*1.0 / (lost*1.0 + wins*1.0);
        value *= 100;
        value = Math.round(value);
        value /= 100;
        winsToAll = new SimpleStringProperty(value+"");
    }

    public String getName() {
        return name.get();
    }

    public String getWins() {
        return wins.get();
    }
    public String getLost(){
        return lost.get();
    }
    public String getWinsToAll(){
        return winsToAll.get();
    }

}
