package Game;

import Game.Model.GameBoardModel;
import Game.Model.QuestionCardModel;
import Game.Views.GameBoardView;
import Game.Views.QuestionCardView;
import Game.Views.StartView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

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


    public static void main(String[] args) {
        launch(args);
    }
}
