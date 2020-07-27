package Game.Views;

import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class QuestionCardView {
    private GridPane gridPane;
    private List<String> words;
    private Button doneButton = new Button("Done");


    public QuestionCardView() {
        this.gridPane = new GridPane();
        this.words = new ArrayList<>();
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public ToggleButton getToggleButtons(String word){
        ToggleButton toggleButton = new ToggleButton(word);
        toggleButton.setPrefWidth(200);
        toggleButton.setPrefHeight(25);
        toggleButton.setStyle("-fx-border-color: black");
        if (gridPane.getChildren().isEmpty()){
            gridPane.add(toggleButton,0,2);
        } else {
            gridPane.add(toggleButton, 0, gridPane.getRowCount());
        }
        return toggleButton;
    }

    public Scene getView(){
        Label questionHeading = new Label("Questions");
        gridPane.add(questionHeading,0,0);
        gridPane.add(new Label(),0,1);
        gridPane.add(doneButton,0,gridPane.getRowCount());
        GridPane.setHalignment(doneButton, HPos.CENTER);
        GridPane.setHalignment(questionHeading,HPos.CENTER);
        return new Scene(gridPane);
    }

    public Button getDoneButton() {
        return doneButton;
    }
}
