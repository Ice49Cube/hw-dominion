package dominion.commands;

import dominion.model.Game;

public class Player {

    private int id;
    private String name;

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(int value) {
        this.id = value;
    }

    public void setName(String value) {
        this.name = value;
    }

    public static Player fromGame(Game game, dominion.model.Player source) {
        boolean isCurrent = source.getId() == game.getCurrentPlayerId();
        Player player = isCurrent ? new CurrentPlayer() : new Player();
        player.setId(source.getId());
        player.setName(source.getName());
        if (isCurrent) {
            CurrentPlayer current = (CurrentPlayer) player;
            current.setCards(source.getCards(c
                    -> c.getPile().equals("Hand")
                    || c.getPile().equals("Table")
                    || c.getPile().equals("Playing"))
            );
            current.setActions(source.getActions());
            current.setBuys(source.getBuys());
            //current.setCoins(source.getCoins());
        }
        return player;
    }
}
