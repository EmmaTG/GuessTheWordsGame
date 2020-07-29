package Game.Model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player {
    private static int noOfPlayers = 0;
    private final String name;
    private final GridPane gridPane;
    private final String color;
    private int position;
    private Label marker;
    public static final String CURRENT_PLAYER_STYLE = "-fx-border-width: 3; -fx-border-color: black; -fx-background-color: %s; -fx-border-radius: 15; -fx-background-radius: 15; -fx-text-fill: black";
    public static final String DORMANT_PLAYER_STYLE = "-fx-border-width: 1; -fx-border-color: black; -fx-background-color: %s; -fx-border-radius: 15; -fx-background-radius: 15; -fx-text-fill: black";


    private static final List<List<Integer>> positions = new ArrayList<>(Arrays.asList(Arrays.asList(0,0),
            Arrays.asList(0,1),
            Arrays.asList(1,0),
            Arrays.asList(1,1)));

    public Player(String name) {
        List<String> colors = new ArrayList<>(Arrays.asList("red","blue","green","yellow"));
        this.name = name;
        this.color = colors.get(noOfPlayers);
        this.gridPane = markerGrid(noOfPlayers);
        this.position = 0;
        noOfPlayers++;
    }

    public void moveSpaces(int noOfSpaces){
        marker.setStyle(String.format(DORMANT_PLAYER_STYLE,color));
        this.position = this.position + noOfSpaces;
    }

    private GridPane markerGrid(int i){
        GridPane gridPane = new GridPane();
        Label blank1 = blankLabel();
        Label blank2 = blankLabel();
        Label blank3 = blankLabel();
        Label blank4 = blankLabel();
        gridPane.add(blank1,0,0);
        gridPane.add(blank2,0,1);
        gridPane.add(blank3,1,1);
        gridPane.add(blank4,1,0);
        if (i>=0 && i<4) {
            Label marker = getMarker();
            gridPane.add(marker,positions.get(i).get(0),positions.get(i).get(1));
            gridPane.setAlignment(Pos.CENTER);
        } else {
            System.out.println("Only 1-4 players can play at one time");
        }
        return gridPane;
    }

    private Label blankLabel(){
        Label blankMarker = new Label();
        blankMarker.setPrefWidth(25);
        blankMarker.setPrefHeight(25);
        blankMarker.setStyle("-fx-border-color: transparent; -fx-background-color: transparent");
        return blankMarker;
    }

    private Label getMarker(){
        marker = new Label(this.name.substring(0,1).toUpperCase());
        marker.setAlignment(Pos.CENTER);
        marker.setPrefWidth(25);
        marker.setPrefHeight(25);
        marker.setStyle(String.format(DORMANT_PLAYER_STYLE,color));
        return marker;
    }

    public void setCurrentPlayerStyle(){
        this.marker.setStyle(String.format(CURRENT_PLAYER_STYLE,color));
    }

    public void setDormantPlayerStyle(){
        this.marker.setStyle(String.format(DORMANT_PLAYER_STYLE,color));
    }


    public String getName() {
        return name;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public String getColor() {
        return color;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
