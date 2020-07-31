package Game;

import Game.Model.GameBoardModel;
import Game.Model.Player;
import Game.Model.QuestionCardModel;
import Game.Model.WordDataSource;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
    private List<Integer> dataBaseIDs = new ArrayList<>();

    public Controller(StartView startView,
                      GameBoardView gameBoardView, GameBoardModel gameBoardModel,
                      QuestionCardModel questionCardModel,
                      Stage stage) {
        this.startView = startView;
        this.gameBoardModel = gameBoardModel;
        this.gameBoardView = gameBoardView;
        this.questionCardModel = questionCardModel;
        this.stage = stage;
        this.dataBaseIDs = WordDataSource.getInstance().getIds();
    }

    public void init(){
        WordDataSource.getInstance().open();
        setEventHandlers();
    }

    private void setEventHandlers(){
                addPlayer();
                setExitButton();
                setDoneButton();
                setRemovePlayer();
    }

    private void addPlayer(){
        startView.getAddPlayer().setOnAction(addPlayerEvent ->{
            if (startView.getNameFields().size()<4) {
                startView.addPlayerFields(stage);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Sorry, Max of four players");
                alert.show();
            }
        });
    }

    public void setRemovePlayer() {
        startView.getRemovePlayer().setOnAction((e) -> {
            if (startView.getvBoxFields().size()>1) {
                startView.removeFields(stage);
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
                gameBoardModel.resetStyles();
                gameBoardModel.setCurrentPlayer(listOfPlayers.get(0));
                gameBoardModel.setGameFinished(false);
                gameBoardView.getPlayButton().setText("Play");
                showBoard();
            } else {
                // Create question card
                Stage newStage = new Stage();
                List<ToggleButton> listOfToggles = new ArrayList<>();
                questionCardModel.getRandomIds(dataBaseIDs);
                List<String> words = questionCardModel.getWords();
                QuestionCardView questionCardView = new QuestionCardView(words);
                questionCardModel.resetCounters();
                for (int i=0; i<words.size(); i++) {
                    ToggleButton wordToggle = questionCardView.getToggleButtons(words.get(i));
                    listOfToggles.add(wordToggle);
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
                    // Change to the next players turn.
                    newStage.close();
                    Player currentPlayer = gameBoardModel.getCurrentPlayer();
                    currentPlayer.moveSpaces(questionCardModel.getCorrect());
                    int currentPlayerIndex = listOfPlayers.indexOf(currentPlayer) + 1;
                    if ((currentPlayerIndex) == listOfPlayers.size()) {
                        currentPlayerIndex = 0;
                    }
                    gameBoardModel.setCurrentPlayer(listOfPlayers.get(currentPlayerIndex));
                    gameBoardView.getPlayButton().setDisable(false);
                    showBoard();
                });

                Scene scene = questionCardView.getView();
                gameBoardView.getPlayButton().setDisable(true);
                newStage.setScene(scene);
                newStage.setAlwaysOnTop(true);
                newStage.show();
                new Thread(() -> {
                    try {
                        Thread.sleep(30000);
                        for (ToggleButton tog : listOfToggles) {
                            tog.setDisable(true);
                            tog.setStyle("-fx-background-color: black ; -fx-background-radius: 5; " +
                                    "-fx-border-color: black; -fx-border-radius: 5; -fx-text-fill: grey");
                        }
                    } catch (InterruptedException ie) {
                        System.out.println("Interrupted exception: " + ie.getMessage());
                    }
                }).start();
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
                playButton.setText("New\nGame");
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
