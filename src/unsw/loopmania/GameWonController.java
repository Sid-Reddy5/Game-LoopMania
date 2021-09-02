package unsw.loopmania;

import java.io.IOException;

import javafx.fxml.FXML;

public class GameWonController {
    private MenuSwitcher gameSwitcher;

    public void setGameSwitcher(MenuSwitcher gameSwitcher){
        this.gameSwitcher = gameSwitcher;
    }

    /**
     * facilitates switching to main game upon button click
     * @throws IOException
     */
    @FXML
    private void switchToGame() throws IOException {
        gameSwitcher.switchMenu();
    }
}

 
   