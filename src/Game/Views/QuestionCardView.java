package Game.Views;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
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
        toggleButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
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
        toggleButton.setPrefHeight(25);
        Button queryLabel = new Button("?");
        queryLabel.setStyle("-fx-border-radius:20; -fx-border-color: black; -fx-font-weight: bold; -fx-background-radius: 20");
        queryLabel.setAlignment(Pos.CENTER);
        queryLabel.setPrefWidth(20);
        hoverButton(queryLabel,word);
        HBox toggleHbox = new HBox(toggleButton,queryLabel);
        HBox.setHgrow(toggleButton, Priority.ALWAYS);
        if (gridPane.getChildren().isEmpty()){
            gridPane.add(toggleHbox,0,2);
        } else {
            gridPane.add(toggleHbox,0,gridPane.getRowCount());
        }
        return toggleButton;
    }

    private void hoverButton(Button query,String word){

        Label infoLabel = new Label();
        word = word.replaceAll(" ", "_");
        String urlRequest = "https://en.wikipedia.org/api/rest_v1/page/summary/"+ word;
        String result = apiRequest(urlRequest);
        JSONObject jObj = getJsonArrayResults(result);
        infoLabel.setText("From Wikipedia:\n" + jObj.get("extract").toString());
        infoLabel.setWrapText(true);
        StackPane stickyNotesPane = new StackPane(infoLabel);
        stickyNotesPane.setPrefSize(200, 200);
        stickyNotesPane.setStyle("-fx-background-color: rgb(127,255,0);");
        Popup popup = new Popup();
        popup.getContent().add(stickyNotesPane);

        query.hoverProperty().addListener((obs, oldVal, newValue) -> {
            if (newValue) {
                Bounds bnds = query.localToScreen(query.getLayoutBounds());
                double x = bnds.getMinX() + (query.getWidth());
                double y = bnds.getMinY() - (stickyNotesPane.getHeight()/2);
                popup.show(query, x, y);
            } else {
                popup.hide();
            }
        });
    }

    private static JSONObject getJsonArrayResults(String readString) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(readString);
            return jsonObject;
        } catch (ParseException jsonE) {
            System.out.println("Json-simple error: " + jsonE.getMessage());
            return null;
        }
    }

    private static String apiRequest(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int code = connection.getResponseCode();
            if (code != 200) {
                throw new RuntimeException("Http Response code: " + code);
            } else {
                InputStream input = connection.getInputStream();
                BufferedReader ioReader = new BufferedReader(new InputStreamReader(input));
                return ioReader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error in apiRequest: " + e.getMessage());
        }
        return null;
    }

    private static String createAPIRequest(int noOfQuestions, int categoryID, String difficulty) {
        return String.format("https://opentdb.com/api.php?amount=%d&category=%d&difficulty=%s", noOfQuestions, categoryID, difficulty);
    }

    public static String createAPIRequest(int noOfQuestions, int categoryID) {
        return String.format("https://opentdb.com/api.php?amount=%d&category=%d", noOfQuestions, categoryID);
    }

    private static String createAPIRequest(int noOfQuestions) {
        return String.format("https://opentdb.com/api.php?amount=%d", noOfQuestions);
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
