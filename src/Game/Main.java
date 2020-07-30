package Game;

import Game.Model.GameBoardModel;
import Game.Model.QuestionCardModel;
import Game.Model.WordDataSource;
import Game.Views.GameBoardView;
import Game.Views.StartView;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.Scanner;

public class Main extends Application {

    private static Stage stage = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception{

        StartView startView = new StartView();
        GameBoardModel gameBoardModel = new GameBoardModel();
        GameBoardView gameBoardView = new GameBoardView();
        QuestionCardModel questionCardModel = new QuestionCardModel();

        Controller controller = new Controller(startView,
                gameBoardView, gameBoardModel, questionCardModel,
                stage);

        controller.init();

        Scene scene = startView.getScene();
        stage.setScene(scene);
        stage.showAndWait();
    }

    @Override
    public void stop() throws Exception {
        WordDataSource.getInstance().close();
    }

    public static void main(String[] args) {
        launch(args);

    }

}
