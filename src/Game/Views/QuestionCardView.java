package Game.Views;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

public class QuestionCardView {
    private GridPane gridPane;
    private List<String> words;
    private Button doneButton = new Button("Done");
    private  static boolean blue = true;


    public QuestionCardView(List<String> words) {
        this.gridPane = new GridPane();
        this.words = words;
        setDoneButtonStyle();
    }

    private void setDoneButtonStyle(){
        this.doneButton.setStyle("-fx-font-weight:bold; -fx-font-size: 16; -fx-text-fill: black; " +
                "-fx-border-color: black; -fx-border-radius: 5;" +
                "-fx-background-color: rgb(128,0,255); -fx-background-radius: 5");
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public ToggleButton getToggleButtons(String word){
        ToggleButton toggleButton = new ToggleButton(word);
        String color = "white";
        if(blue){
            color = "rgba(0,128,255)";
            blue = false;
        } else {
            color = "rgba(255,255,0)";
            blue = true;
        }
        toggleButton.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 5; " +
                "-fx-border-color: black; -fx-border-radius: 5");
        toggleButton.setPrefWidth(200);
        toggleButton.setPrefHeight(25);
        if (gridPane.getChildren().isEmpty()){
            gridPane.add(toggleButton,0,2);
        } else {
            gridPane.add(toggleButton, 0, gridPane.getRowCount());
        }
        return toggleButton;
    }

    public Scene getView(){
        Label questionHeading = new Label("Questions");
        questionHeading.setStyle("-fx-font-size: 22; -fx-font-weight: bold");
        gridPane.add(questionHeading,0,0);
        gridPane.add(new Label(),0,1);
        Label instructions = new Label("Click on the ones your team \n guess correctly");
        instructions.setTextAlignment(TextAlignment.CENTER);
        gridPane.add(new Label(),0,gridPane.getRowCount());
        gridPane.add(instructions,0,gridPane.getRowCount());
        gridPane.add(doneButton,0,gridPane.getRowCount());
        GridPane.setHalignment(doneButton, HPos.CENTER);
        GridPane.setHalignment(questionHeading,HPos.CENTER);
        GridPane.setHalignment(instructions,HPos.CENTER);
        return new Scene(gridPane);
    }

    public Button getDoneButton() {
        return doneButton;
    }
}
