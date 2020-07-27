package Game.Views;

import Game.Model.Player;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;

import java.util.*;

public class GameBoardView {
    private static Map<String,Integer> markerPositions = new HashMap<>();
    private List<List<Integer>> boardPositions = new ArrayList<>();
    private static GridPane boardGridPane;
    private Button playButton = new Button();
    private boolean blue = true;

    public GameBoardView() {
        setGameBoardPositions();
        setPlayButtonStyle();
        boardGridPane = new GridPane();
        boardPositions.forEach( el -> {
            if (boardPositions.indexOf(el) == 0){
                boardGridPane.add(getBorderLabel("START"), el.get(0), el.get(1));
            } else if (boardPositions.indexOf(el) == (boardPositions.size()-1)){
                boardGridPane.add(getBorderLabel("FINISH"), el.get(0), el.get(1));
            } else {
                boardGridPane.add(getBorderLabel(""), el.get(0), el.get(1));
            }
        });
        boardGridPane.add(playButton,4,3);
        GridPane.setHalignment(playButton,HPos.CENTER);

    }

    private void setPlayButtonStyle(){
        playButton.setStyle("-fx-font-weight:bold; -fx-font-size: 22; -fx-text-fill: black; " +
                "-fx-border-color: black; -fx-border-radius: 5;" +
                "-fx-background-color: rgb(128,0,255); -fx-background-radius: 5");
    }

    public GridPane getGameBoard(){
        return boardGridPane;
    }

    public void addMarkers(Player player){
        int markerPosition = player.getPosition();
        int maxPosition = boardPositions.size();
        if (markerPosition<maxPosition) {
            boardGridPane.getChildren().remove(player.getGridPane());
            boardGridPane.add(player.getGridPane(), boardPositions.get(markerPosition).get(0), boardPositions.get(markerPosition).get(1));
        } else {
            boardGridPane.getChildren().remove(player.getGridPane());
            boardGridPane.add(player.getGridPane(), boardPositions.get(maxPosition-1).get(0), boardPositions.get(maxPosition-1).get(1));
        }
    }
    
    private Label getBorderLabel(String text){
        Label label = new Label(text);
        label.setAlignment(Pos.CENTER);
        label.setPrefWidth(100);
        label.setPrefHeight(100);
        if (blue) {
            blue = false;
            label.setStyle("-fx-border-color: black; -fx-background-color: rgba(0,128,255); -fx-text-fill: black; -fx-font-weight: bold");
        } else {
            blue = true;
            label.setStyle("-fx-border-color: black; -fx-background-color: rgba(255,255,0); -fx-text-fill: black; -fx-font-weight: bold");
        }
        return label;
    }

    public static Map<String,Integer> getMarkerPositions() {
        return markerPositions;
    }

    public List<List<Integer>> getBoardPositions() {
        return boardPositions;
    }

    public Button getPlayButton() {
        return playButton;
    }

    private static int getStartColumn() {
        return 3;
    }

    private static int getStartRow() {
        return 4;
    }

    private void setGameBoardPositions(){
        boardPositions.add(Arrays.asList(getStartColumn(),getStartRow()));
        boardPositions.add(Arrays.asList(3,5));
        boardPositions.add(Arrays.asList(3,6));
        boardPositions.add(Arrays.asList(2,6));
        boardPositions.add(Arrays.asList(1,6));
        boardPositions.add(Arrays.asList(0,6));
        boardPositions.add(Arrays.asList(0,5));
        boardPositions.add(Arrays.asList(0,4));
        boardPositions.add(Arrays.asList(0,3));
        boardPositions.add(Arrays.asList(0,2));
        boardPositions.add(Arrays.asList(0,1));
        boardPositions.add(Arrays.asList(0,0));
        boardPositions.add(Arrays.asList(1,0));
        boardPositions.add(Arrays.asList(2,0));
        boardPositions.add(Arrays.asList(3,0));
        boardPositions.add(Arrays.asList(3,1));
        boardPositions.add(Arrays.asList(3,2));
        boardPositions.add(Arrays.asList(4,2));
        boardPositions.add(Arrays.asList(5,2));
        boardPositions.add(Arrays.asList(5,1));
        boardPositions.add(Arrays.asList(5,0));
        boardPositions.add(Arrays.asList(6,0));
        boardPositions.add(Arrays.asList(7,0));
        boardPositions.add(Arrays.asList(8,0));
        boardPositions.add(Arrays.asList(8,1));
        boardPositions.add(Arrays.asList(8,2));
        boardPositions.add(Arrays.asList(8,3));
        boardPositions.add(Arrays.asList(8,4));
        boardPositions.add(Arrays.asList(8,5));
        boardPositions.add(Arrays.asList(8,6));
        boardPositions.add(Arrays.asList(7,6));
        boardPositions.add(Arrays.asList(6,6));
        boardPositions.add(Arrays.asList(5,6));
        boardPositions.add(Arrays.asList(5,5));
        boardPositions.add(Arrays.asList(5,4));
    }
}
