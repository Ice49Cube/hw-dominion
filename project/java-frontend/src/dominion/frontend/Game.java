package dominion.frontend;

import dominion.results.*;
import dominion.frontend.collections.*;
//import dominion.frontend.responses.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class Game {

    private final GameInfo info;
    private final HashMap<Integer, GameCardInfo> cards;
    private final HashMap<Integer, PlayerInfo> players;
    private PlayerInfo currentPlayer;
    
    public Game(StartGameResult data) {
        this.cards = new HashMap();
        this.players = new HashMap();
        this.info = data.getGame();
        this.setCards(data.getGameCards());
        this.setPlayers(data.getPlayers());
    }

    public ArrayList<PlayerCardInfo> getCurrentPlayerActionCards() {
        PlayerInfo player = getCurrentPlayer();
        PlayerCardInfo[] playerCards = player.getCards(c -> c.getPile().equals("Hand"));
        ArrayList<PlayerCardInfo> result = new ArrayList<>();
        for (PlayerCardInfo playerCard : playerCards) {
            GameCardInfo card = this.getCard(playerCard.getCardId());
            if (card.getIsAction()) {
                result.add(playerCard);
            }
        }
        return result;
    }

    public GameCardInfo getCard(int id) {
        return this.cards.get(id);
    }

    public GameCardInfo[] getCards() {
        return this.cards.values().toArray(new GameCardInfo[this.cards.size()]);
    }

    public GameCardInfo[] getCards(Predicate<? super GameCardInfo> filter) {
        return this.cards.values().stream().filter(filter).toArray(size -> new GameCardInfo[size]);
    }

    public PlayerInfo getCurrentPlayer() {
        return this.currentPlayer; // this.players.values().stream().filter(p -> p.cards != null).findFirst().get();
    }

    public PlayerInfo getPlayer(int id) {
        return this.players.get(id);
    }

    public PlayerInfo[] getPlayers() {
        return this.players.values().stream().toArray(size -> new PlayerInfo[size]);
    }

    public GameInfo getInfo() {
        return this.info;
    }
    
    public void nextPlayer(PlayerInfo next) {
        this.currentPlayer = next;
        this.players.put(next.getId(), next);        
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // <editor-fold desc="Print Methods" defaultstate="collapsed">

    public void printAll() {
        System.out.println("\n\n");
        this.printGameCards();
        this.printGameInfo();
        this.printPlayers();
        this.printTable();
        this.printCurrentPlayer();
    }
    
    public void printCurrentPlayer() {
        PlayerInfo player = this.getCurrentPlayer();
        System.out.print("> Player " + player.getName());
        PlayerCardInfo[] cards = player.getCards(c -> c.getPile().equals("Hand"));
        if (cards.length > 0) {
            System.out.print((cards.length == 1 ? " has card" : " has cards") + " in hand: ");
            for(int i = 0; ; ) {
                System.out.print(getCard(cards[i].getCardId()).getName());
                if (++i < cards.length) {
                    System.out.print(", ");
                } else {
                    System.out.println();
                    break;
                }
            }
        } else {
            System.out.println(" has no cards!");
        }
    }

    private void printGameCards(String title, GameCardInfo[] cards) {
        System.out.println("> " + title + "\n");
        int i;
        for (i = 0; i < cards.length; i++) {
            System.out.print(String.format("%12s $%d +%-3d ", cards[i].getName(), cards[i].getCost(), cards[i].getCount()));
            if ((i & 3) == 3) {
                System.out.println();
            }
        }
        System.out.println(i == 4 ? "" : "\n");
    }

    public void printGameCards() {
        ArrayList<GameCardInfo> sorted = new ArrayList(this.cards.values());
        Collections.sort(sorted, new CardCostComparator());
        printGameCards("Kingdom cards", sorted.stream().filter(gc -> gc.getDeck().equalsIgnoreCase("Kingdom")).toArray(size -> new GameCardInfo[size]));
        printGameCards("Victory cards", sorted.stream().filter(gc -> gc.getDeck().equalsIgnoreCase("Victory")).toArray(size -> new GameCardInfo[size]));
        printGameCards("Treasure cards", sorted.stream().filter(gc -> gc.getDeck().equalsIgnoreCase("Treasure")).toArray(size -> new GameCardInfo[size]));
    }

    public void printGameInfo() {
        System.out.println("> Game id: " + this.info.getId() + " state is >>> " + this.info.getState() + " <<<");
    }
    public void printPlayers() {
        System.out.println("> Players: [<" + String.join("> , <", this.players.values().stream().map(p -> p.getName()).toArray(size -> new String[size])) + ">]");
    }

    public void printTable() {
        PlayerInfo player = this.getCurrentPlayer();
        PlayerCardInfo [] playerCards = player.getCards(c->c.getPile().equals("Table") || c.getPile().equals("Playing"));
        if (playerCards.length>0) {
            System.out.print("> " + (playerCards.length == 1 ? "Card" : "Cards") + " on the table are: ");
            for(int i = 0;;) {
                System.out.print(this.getCard(playerCards[i].getCardId()).getName());
                if (++i == playerCards.length) {
                    System.out.println();
                    break;
                }
                System.out.print(", ");
            }
        } else {
            System.out.println("> No cards on the table!");
        }
    }
    
    // </editor-fold>
    ////////////////////////////////////////////////////////////////////////////

    private void setCards(GameCardInfo[] cards) {
        this.cards.clear();
        for (GameCardInfo card : cards) {
            this.cards.put(card.getId(), card);
        }
    }

    private void setPlayers(PlayerInfo[] players) {
        this.players.clear();
        for (PlayerInfo player : players) {
            this.players.put(player.getId(), player);
            if (player.getCards() != null) {
                this.currentPlayer = player;
            }
        }
    }
}
