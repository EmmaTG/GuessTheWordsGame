package Game.Views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
        this.addPlayer = new Button("Add player (Max 4)");
        this.done = new Button("Done");
        this.exit = new Button("Exit");
        this.buttons = new HBox(this.done, this.exit);
    }

    public Scene getScene() {
        Label welcomeLabel = new Label("Let's play 30 Seconds");
        welcomeLabel.maxWidth(Double.MAX_VALUE);
        this.gridPane.add(welcomeLabel,0,0);
        this.gridPane.add(this.addPlayer,0,1);
        Label newLabel = new Label("Name:");
        newLabel.maxWidth(Double.MAX_VALUE);
        this.gridPane.add(newLabel,0,2);
        this.done.setDisable(true);
        this.gridPane.add(this.buttons,0,3);
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
        newTextField.setOnKeyPressed(e -> {
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
