package dominion.commands;

import com.fasterxml.jackson.annotation.*;
import dominion.routing.*;

public class StartGameCommand extends CommandBase {

    private String cardSet;
    private String code;
    private String[] playerNames;

    public StartGameCommand() {
        super("startGame");
    }
    public String getCardSet() {
        return this.cardSet;
    }

    public void setCardSet(String value) {
        this.cardSet = value;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String value) {
        this.code = value;
    }

    public String[] getPlayerNames() {
        return this.playerNames;
    }

    public void setPlayerNames(String[] value) {
        this.playerNames = value;
    }
}
