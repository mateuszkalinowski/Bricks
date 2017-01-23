package XClasses;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Mateusz on 06.01.2017.
 * Project Bricks
 */
public class XDetailedResults {
    private final SimpleStringProperty name;
    private final SimpleStringProperty wins;
    private final SimpleStringProperty lost;

    public XDetailedResults(String name,int wins,int lost) {
        this.name = new SimpleStringProperty(name);
        this.wins = new SimpleStringProperty(wins+"");
        this.lost = new SimpleStringProperty(lost+"");
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
}
