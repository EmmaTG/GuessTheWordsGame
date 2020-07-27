package Game.Views;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class StartView {

    private List<TextField> nameFields;
    private GridPane gridPane;
    private Button addPlayer;
    private Button done;
    private Button exit;
    private HBox buttons;

    public StartView() {
        this.gridPane = new GridPane();
        this.gridPane.setAlignment(Pos.CENTER);
        this.nameFields = new ArrayList<>();
        this.addPlayer = new Button("Add team (Max 4)");
        this.done = new Button("Done");
        this.exit = new Button("Exit");
        this.buttons = new HBox(this.done, this.exit);
        this.buttons.setAlignment(Pos.CENTER);
        setAddPlayerStyle();
        setDoneButtonStyle();
        setExitButtonStyle();
    }


    private void setDoneButtonStyle(){
        this.done.setStyle("-fx-background-color: rgb(127,255,0); -fx-border-color: black;" +
                "-fx-text-fill: black; -fx-border-radius: 5; -fx-background-radius: 5");
        HBox.setHgrow(this.done, Priority.ALWAYS);
        this.done.maxWidth(Double.MAX_VALUE);
        this.done.setMaxWidth(100);
    }

    private void setExitButtonStyle(){
        this.exit.setStyle("-fx-background-color: rgb(128,0,255); -fx-border-color: black;" +
                "-fx-text-fill: black; -fx-border-radius: 5; -fx-background-radius: 5");
        HBox.setHgrow(this.exit, Priority.ALWAYS);
        this.exit.maxWidth(Double.MAX_VALUE);
        this.exit.setMaxWidth(100);
    }

    private void setAddPlayerStyle(){
        this.addPlayer.setStyle("-fx-border-color: black;" +
                "-fx-text-fill: black; -fx-border-radius: 5; -fx-background-radius: 5");
        this.addPlayer.maxWidth(Double.MAX_VALUE);

    }

    public Scene getScene() {
        Label welcomeLabel = new Label("Let's play!");
        welcomeLabel.setStyle("-fx-font-size: 22; -fx-font-weight:bold");
        welcomeLabel.setPrefWidth(300);
        welcomeLabel.setAlignment(Pos.CENTER);
        this.gridPane.add(welcomeLabel,0,0);
        this.gridPane.add(new Label(),0,1);
        this.gridPane.add(this.addPlayer,0,2);
        this.gridPane.add(new Label(),0,3);
        Label newLabel = new Label("Team name:");
        newLabel.maxWidth(Double.MAX_VALUE);
        newLabel.setStyle("-fx-font-size: 14; -fx-font-weight:bold");
        this.gridPane.add(newLabel,0,4);
        this.done.setDisable(true);
        this.gridPane.add(this.buttons,0,5);
        GridPane.setHalignment(this.addPlayer, HPos.CENTER);
        GridPane.setHalignment(this.buttons, HPos.CENTER);

        return new Scene(gridPane);
    }
    public List<String> getPlayerNames(){
        List<String> playerNames = new ArrayList<>();
        this.nameFields.forEach(el -> playerNames.add(el.getText()));
        return playerNames;
    }

    public VBox addPlayerVBox(){
        this.done.setDisable(true);
        TextField newTextField = new TextField();
        newTextField.maxWidth(Double.MAX_VALUE);
        newTextField.setOnKeyReleased(e -> {
            if (emptyTextFields()){
                this.done.setDisable(true);
            } else {
                this.done.setDisable(false);
            }
        });
        this.nameFields.add(newTextField);
        return new VBox(newTextField);
    }

    private boolean emptyTextFields(){
        for (int i=0; i<this.nameFields.size();i++){
            if (this.nameFields.get(i).getText().isEmpty()){
                return true;
            }
        }
        return false;
    }

    public List<TextField> getNameFields() {
        return nameFields;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public Button getAddPlayer() {
        return addPlayer;
    }

    public Button getDone() {
//        this.done.setDisable(true);
        return done;
    }

    public Button getExit() {
        return exit;
    }

    public HBox getButtons() {
        return buttons;
    }
}
