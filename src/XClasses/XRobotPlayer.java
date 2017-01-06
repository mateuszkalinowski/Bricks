package XClasses;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Mateusz on 06.01.2017.
 * Project InferenceEngine
 */
public class XRobotPlayer {
    private final SimpleStringProperty type;
    private final SimpleStringProperty path;
    private final SimpleStringProperty name;
    public XRobotPlayer(String isOwn,String path) {
        this.type = new SimpleStringProperty(isOwn);
        this.path = new SimpleStringProperty(path);

        if (this.path.get().length() > 7 && this.type.get().equals("Wbudowany")) {
            int i = this.path.get().length() - 1;
            for (; i > 0; i--) {
                if (this.path.get().charAt(i) == '/' || this.path.get().charAt(i) == '\\')
                    break;
            }
            pathToPlayer = this.path.get().substring(0, i);
            name = new SimpleStringProperty(this.path.get().substring(i + 1, this.path.get().length()));
            if (this.path.get().substring(this.path.get().length() - 3).equals("out") || this.path.get().substring(this.path.get().length() - 3).equals("exe") || this.path.get().substring(this.path.get().length() - 3).equals("jar")) {
                name.set(name.get().substring(0, name.get().length() - 4));
            } else if (this.path.get().substring(this.path.get().length() - 5).equals("class")) {
                name.set(name.get().substring(0, name.get().length() - 6));
            }
        }
        else if(this.type.get().equals("Własny")){
            if(this.path.get().length()<=20) {
                name = new SimpleStringProperty(this.path.get());
            }
            else
                name = new SimpleStringProperty(this.path.get().substring(this.path.get().length()-21));
        }
        else {
            name = new SimpleStringProperty("null");
        }
    }
    private String pathToPlayer;

    public String getType(){
        return type.get();
    }
    public String getPath(){
        return path.get();
    }
    public String getName(){
        return name.get();
    }

    public String getPathToPlayer(){
        return pathToPlayer;
    }
}
