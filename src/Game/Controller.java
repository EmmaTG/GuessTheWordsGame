package Game;

import Game.Model.GameBoardModel;
import Game.Model.Player;
import Game.Model.QuestionCardModel;
import Game.Views.GameBoardView;
import Game.Views.QuestionCardView;
import Game.Views.StartView;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private StartView startView;
    private GameBoardModel gameBoardModel;
    private GameBoardView gameBoardView;
    private QuestionCardModel questionCardModel;
    private Stage stage;

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
            GridPane startSceneGridPane = startView.getGridPane();
            if (startSceneGridPane.getRowCount()<22) {
                HBox buttonsHbox = startView.getButtons();
                System.out.println(startSceneGridPane.getRowCount());
                startSceneGridPane.add(startView.addPlayerVBox(), 0, startSceneGridPane.getRowCount()+1);
                startSceneGridPane.getChildren().remove(buttonsHbox);
                startSceneGridPane.add(buttonsHbox, 0, startSceneGridPane.getRowCount()+2);
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
                List<String> words = questionCardModel.getWords();
                QuestionCardView questionCardView = new QuestionCardView(words);
                questionCardModel.resetCounters();
                for (int i=0; i<words.size(); i++) {
                    ToggleButton wordToggle = questionCardView.getToggleButtons(words.get(i));
                    String originalStyle = wordToggle.getStyle();
                    wordToggle.setOnAction((tog) -> {
                        if (wordToggle.isSelected()) {
                            questionCardModel.correct();
                            wordToggle.setStyle("-fx-background-color: grey ; -fx-background-radius: 5; " +
                                    "-fx-border-color: black; -fx-border-radius: 5");
                        } else {
                            questionCardModel.incorrect();
                            wordToggle.setStyle(originalStyle);
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
                    gameBoardView.getPlayButton().setDisable(false);
                    showBoard();
                });

                Scene scene = questionCardView.getView();
                gameBoardView.getPlayButton().setDisable(true);
                newStage.setScene(scene);
                newStage.setAlwaysOnTop(true);
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
        stage.setHeight(700);
        stage.setWidth(900);
    }

    private Alert finishedAlert(Player player){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You won!!");
        alert.setHeaderText(String.format("Congratulations %s. You win!!",player.getName()));
        return alert;
    }
}
