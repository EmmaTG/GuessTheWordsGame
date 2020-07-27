package Game;

import Game.Model.GameBoardModel;
import Game.Model.Player;
import Game.Model.QuestionCardModel;
import Game.Views.GameBoardView;
import Game.Views.QuestionCardView;
import Game.Views.StartView;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private StartView startView;
    private GameBoardModel gameBoardModel;
    private GameBoardView gameBoardView;
    private QuestionCardModel questionCardModel;
    private Stage stage;
    private static int count =2;

    public Controller(StartView startView,
                      GameBoardView gameBoardView, GameBoardModel gameBoardModel,
                      QuestionCardModel questionCardModel,
                      Stage stage) {
        this.startView = startView;
        this.gameBoardModel = gameBoardModel;
        this.gameBoardView = gameBoardView;
        this.questionCardModel = questionCardModel;
        this.stage = stage;
    }

    public void init(){
        setEventHandlers();
    }

    private void setEventHandlers(){
                addPlayer();
                setExitButton();
                setDoneButton();
    }

    private void addPlayer(){
        startView.getAddPlayer().setOnAction(addPlayerEvent ->{
            if (count<6) {
                count++;
                GridPane startSceneGridPane = startView.getGridPane();
                HBox buttonsHbox = startView.getButtons();
                startSceneGridPane.add(startView.addPlayerVBox(), 0, count);
                startSceneGridPane.getChildren().remove(buttonsHbox);
                startSceneGridPane.add(buttonsHbox, 0, count + 1);
                stage.setHeight(stage.getHeight() + 26);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Sorry, Max of four players");
                alert.show();
            }
        });
    }

    private void setExitButton(){
        startView.getExit().setOnAction(exitEvent -> stage.close());
    }

    private void setDoneButton(){
        List<Player> listOfPlayers = new ArrayList<>();
        startView.getDone().setOnAction(doneEvent -> {
            startView.getPlayerNames().forEach(el -> listOfPlayers.add(new Player(el)));
            gameBoardModel.setListOfPlayers(listOfPlayers);
            gameBoardModel.setCurrentPlayer(listOfPlayers.get(0));
            showBoard();
        });
    }

    private void setPlayButton(List<Player> listOfPlayers){
        gameBoardView.getPlayButton().setOnAction((e) -> {
            if (gameBoardModel.getGameFinished()){
                listOfPlayers.forEach( el -> el.setPosition(0));
                gameBoardModel.setCurrentPlayer(listOfPlayers.get(0));
                gameBoardModel.setGameFinished(false);
                gameBoardView.getPlayButton().setText("Play");
                showBoard();
            } else {
                // Create question card
                Stage newStage = new Stage();
                QuestionCardView questionCardView = new QuestionCardView();
                List<String> words = questionCardModel.getWords();
                questionCardModel.resetCounters();
                for (int i=0; i<words.size(); i++) {
                    ToggleButton wordToggle = questionCardView.getToggleButtons(words.get(i));
                    wordToggle.setOnAction((tog) -> {
                        if (wordToggle.isSelected()) {
                            questionCardModel.correct();
                        } else {
                            questionCardModel.incorrect();
                        }
                    });
                }
                questionCardView.setWords(words);
                questionCardView.getDoneButton().setOnAction((e1) -> {
                    newStage.close();
                    gameBoardModel.getCurrentPlayer().moveSpaces(questionCardModel.getCorrect());
                    int currentPlayer = listOfPlayers.indexOf(gameBoardModel.getCurrentPlayer()) + 1;
                    if ((currentPlayer) == listOfPlayers.size()) {
                        currentPlayer = 0;
                    }
                    gameBoardModel.setCurrentPlayer(listOfPlayers.get(currentPlayer));
                    showBoard();
                });

                Scene scene = questionCardView.getView();
                newStage.setScene(scene);
                newStage.showAndWait();
            }
        });
    }

    private void showBoard(){
        List<List<Integer>> gameBoardPositions = gameBoardView.getBoardPositions();
        List<Player> listOfPlayers = gameBoardModel.getListOfPlayers();
        gameBoardView = new GameBoardView();
        Button playButton = gameBoardView.getPlayButton();
        playButton.setText("Play");
        listOfPlayers.forEach((el) -> {
            gameBoardView.addMarkers(el);
            if (el.getPosition()>=(gameBoardPositions.size()-1)){
                System.out.println("Game over");
                playButton.setText("New Game");
                finishedAlert(el).show();
                gameBoardModel.setGameFinished(true);
            }
        });
        playGame(listOfPlayers);
    }

    private void playGame(List<Player> listOfPlayers){
        setPlayButton(listOfPlayers);

        GridPane gridPane = gameBoardView.getGameBoard();
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.setHeight(1000);
        stage.setWidth(1000);
    }

    private Alert finishedAlert(Player player){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You won!!");
        alert.setHeaderText(String.format("Congratulations %s. You win!!",player.getName()));
        return alert;
    }
}
