package stages;

import com.apple.eawt.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Mateusz on 25.12.2016.
 * Project Bricks
 */
public class GamesStage extends Application {
    public void start(Stage primaryStage){

        GridPane mainGridPane = new GridPane();






        Stage gamesStage = new Stage();
        Scene gamesScene = new Scene(mainGridPane,300,150);
        gamesStage.setScene(gamesScene);
        gamesStage.setResizable(false);
        gamesStage.initModality(Modality.APPLICATION_MODAL);
        gamesStage.show();
    }
}
