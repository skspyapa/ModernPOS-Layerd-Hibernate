package lk.ijse.dep.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AppInitializer extends Application {
    public static void main(String[] args) throws IOException {
        FileHandler fileHandler = new FileHandler("error.log",true);
        fileHandler.setFormatter(new SimpleFormatter());
        //Logger.getLogger(" ").addHandler(fileHandler);
        Logger logger = Logger.getLogger("lk.ijse.dep");
        logger.addHandler(fileHandler);
        //logger.setUseParentHandlers(false);
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/view/DashBoard.fxml"));
        Scene mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("DashBoard");
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
