package XClasses;

import javafx.beans.property.SimpleStringProperty;
import logic.RobotPlayer;

/**
 * Created by Mateusz on 06.01.2017.
 * Project InferenceEngine
 */
public class XRobotPlayer {
    private final SimpleStringProperty type;
    private final SimpleStringProperty path;
    private final SimpleStringProperty name;

    public XRobotPlayer(String isOwn, String path) {
        this.type = new SimpleStringProperty(isOwn);
        this.path = new SimpleStringProperty(path);

        if (this.path.get().length() > 7 && this.type.get().equals("Plik class/exe/jar/out/py")) {
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
            } else if (this.path.get().substring(this.path.get().length() - 2).equals("py")) {
                name.set(name.get().substring(0, name.get().length() - 3));
            }
        } else if (this.type.get().equals("Własny")) {
            if (this.path.get().length() <= 20) {
                name = new SimpleStringProperty(this.path.get());
            } else
                name = new SimpleStringProperty(this.path.get().substring(this.path.get().length() - 21));
        } else {
            name = new SimpleStringProperty("null");
        }
    }
    public XRobotPlayer(String isOwn, String path, String customName) {
        if(isOwn.equals("Własny")) {
            this.type = new SimpleStringProperty("Własny");
            this.path = new SimpleStringProperty(path);
            if (customName.equals("")) {
                if (this.path.get().length() <= 10) {
                    name = new SimpleStringProperty(this.path.get());
                } else
                    name = new SimpleStringProperty(this.path.get().substring(this.path.get().length() - 11));
            } else {
                name = new SimpleStringProperty(customName);
            }
        }
        else {
            this.type = new SimpleStringProperty("Plik class/exe/jar/out/py");
            this.path = new SimpleStringProperty(path);
            this.name = new SimpleStringProperty(customName);
        }
    }

    private String pathToPlayer;

    public String getType() {
        return type.get();
    }

    public String getPath() {
        return path.get();
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {this.name.set(name);}

    public RobotPlayer getRobotPlayer() {
            if (type.get().equals("Plik class/exe/jar/out/py")) {
                if (path.get().substring(path.get().length() - 3).equals("out") || path.get().substring(path.get().length() - 3).equals("exe")) {
                    try {
                        String cmd[] = new String[1];
                        cmd[0] = path.get();
                        return new RobotPlayer(cmd, 5);
                    } catch (Exception ignored) {}
                } else if (path.get().substring(path.get().length() - 3).equals("jar")) {
                    try {
                        String cmd[] = new String[3];
                        cmd[0] = "java";
                        cmd[1] = "-jar";
                        cmd[2] = path.get();
                        return new RobotPlayer(cmd, 5);
                    } catch (Exception ignored) {}
                } else if (path.get().substring(path.get().length() - 5).equals("class")) {
                    try {
                        String cmd[] = new String[4];
                        cmd[0] = "java";
                        cmd[1] = "-cp";
                        cmd[2] = pathToPlayer;
                        cmd[3] = name.get();
                        return new RobotPlayer(cmd, 5);
                    } catch (Exception ignored) {}
                } else if (path.get().substring(path.get().length() - 2).equals("py")) {
                    try {
                        String cmd[] = new String[2];
                        cmd[0] = "python3";
                        cmd[1] = path.get();
                        return new RobotPlayer(cmd, 5);
                    } catch (Exception ignored) {}
                }

            }
        if (type.get().equals("Własny")) {
            try {
                return new RobotPlayer(path.get(), 5);
            } catch (Exception ignored) {}
        }
        return null;
    }
}
