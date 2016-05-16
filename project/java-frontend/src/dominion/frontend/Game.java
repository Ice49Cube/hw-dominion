package dominion.frontend;

import dominion.frontend.collections.*;
import dominion.frontend.responses.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class Game {

    private final int id;
    private final String state;
    private final HashMap<Integer, GameCard> cards;
    private final HashMap<Integer, Player> players;
    private Player currentPlayer;
    private final String cardSet;

    public Game(StartGameResult data) {
        this.cards = new HashMap();
        this.players = new HashMap();
        this.id = data.getId();
        this.cardSet = data.getCardSet();
        this.setCards(data.getCards());
        this.setPlayers(data.getPlayers());
        this.state = data.getState();
    }

    public ArrayList<PlayerCard> getCurrentPlayerActionCards() {
        Player player = getCurrentPlayer();
        PlayerCard[] playerCards = player.getCards();
        ArrayList<PlayerCard> result = new ArrayList<>();
        for (PlayerCard playerCard : playerCards) {
            GameCard card = this.getCard(playerCard.getCardId());
            if (card.getIsAction()) {
                result.add(playerCard);
            }
        }
        return result;
    }

    public GameCard getCard(int id) {
        return this.cards.get(id);
    }

    public GameCard[] getCards() {
        return this.cards.values().toArray(new GameCard[this.cards.size()]);
    }

    public GameCard[] getCards(Predicate<? super GameCard> filter) {
        return this.cards.values().stream().filter(filter).toArray(size -> new GameCard[size]);
    }

    public String getCardSet() {
        return this.cardSet;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer; // this.players.values().stream().filter(p -> p.cards != null).findFirst().get();
    }

    public int getId() {
        return this.id;
    }

    public Player getPlayer(int id) {
        return this.players.get(id);
    }

    public Player[] getPlayers() {
        return this.players.values().stream().toArray(size -> new Player[size]);
    }

    public String getState() {
        return this.state;
    }

    private void printGameCards(String title, GameCard[] cards) {
        System.out.println("\t" + title + "\n");
        int i;
        for (i = 0; i < cards.length; i++) {
            System.out.print(String.format("%12s $%d +%-3d ", cards[i].getName(), cards[i].getCost(), cards[i].getCount()));
            if ((i & 3) == 3) {
                System.out.println("");
            }
        }
        System.out.println(i == 4 ? "" : "\n");
    }

    public void printGameCards() {
        ArrayList<GameCard> sorted = new ArrayList(this.cards.values());
        Collections.sort(sorted, new CardCostComparator());
        printGameCards(">>> Kingdom cards <<<", sorted.stream().filter(gc -> gc.getDeck().equalsIgnoreCase("Kingdom")).toArray(size -> new GameCard[size]));
        printGameCards(">>> Victory cards <<<", sorted.stream().filter(gc -> gc.getDeck().equalsIgnoreCase("Victory")).toArray(size -> new GameCard[size]));
        printGameCards(">>> Treasure cards <<<", sorted.stream().filter(gc -> gc.getDeck().equalsIgnoreCase("Treasure")).toArray(size -> new GameCard[size]));
    }

    public void printPlayers() {
        System.out.print("\t>>> Players <<<\n\n");
        System.out.print(String.join(", ", this.players.values().stream().map(p -> p.getName()).toArray(size -> new String[size])) + "\n");
    }

    public void printCurrentPlayer() {

    }

    private void setCards(GameCard[] cards) {
        this.cards.clear();
        for (GameCard card : cards) {
            this.cards.put(card.getId(), card);
        }
    }

    private void setPlayers(Player[] players) {
        this.players.clear();
        for (Player player : players) {
            this.players.put(player.getId(), player);
            if (player.getCards() != null) {
                this.currentPlayer = player;
            }
        }
    }
}
