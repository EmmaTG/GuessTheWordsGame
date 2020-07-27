package Game.Model;

import java.util.ArrayList;
import java.util.List;

public class GameBoardModel {

    private List<Player> listOfPlayers;
    private Player currentPlayer;
    private boolean gameFinished;

    public GameBoardModel() {
        this.listOfPlayers = new ArrayList<>();
        this.gameFinished = false;
    }

    public void setListOfPlayers(List<Player> listOfPlayers) {
        this.listOfPlayers = listOfPlayers;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean getGameFinished() {
        return gameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    public List<Player> getListOfPlayers() {
        return listOfPlayers;
    }
}
