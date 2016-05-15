package dominion.frontend;

import dominion.frontend.collections.*;
import dominion.frontend.responses.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class Game {

    private final String state;
    private final HashMap<Integer, GameCard> cards;
    private final HashMap<Integer, Player> players;
    private Player currentPlayer;

    public Game(StartGameResult data) {
        this.cards = new HashMap();
        this.players = new HashMap();
        this.setCards(data.cards);
        this.setPlayers(data.players);
        this.state = data.state;
    }

    public ArrayList<PlayerCard> getCurrentPlayerActionCards() {
        ArrayList<PlayerCard> cards = new ArrayList();
        Player player = getCurrentPlayer();
        for (int i = 0; i < player.cards.length; i++) {
            GameCard card = this.getCard(player.cards[i].cardId);
            if (card.isAction) {
                cards.add(player.cards[i]);
            }
        }
        return cards;
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

    public Player getCurrentPlayer() {
        return this.players.values().stream().filter(p -> p.cards != null).findFirst().get();
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
            System.out.print(String.format("%12s $%-3d+%-3d ", cards[i].name, cards[i].cost, cards[i].count));
            if ((i & 3) == 3) {
                System.out.println("");
            }
        }
        System.out.println(i == 4 ? "" : "\n");
    }

    public void printGameCards() {
        ArrayList<GameCard> sorted = new ArrayList(this.cards.values());
        Collections.sort(sorted, new CardCostComparator());
        printGameCards(">>> Kingdom cards <<<", sorted.stream().filter(gc -> gc.deck.equalsIgnoreCase("Kingdom")).toArray(size -> new GameCard[size]));
        printGameCards(">>> Victory cards <<<", sorted.stream().filter(gc -> gc.deck.equalsIgnoreCase("Victory")).toArray(size -> new GameCard[size]));
        printGameCards(">>> Treasure cards <<<", sorted.stream().filter(gc -> gc.deck.equalsIgnoreCase("Treasure")).toArray(size -> new GameCard[size]));
    }

    public void printPlayers() {
        System.out.print("\t>>> Players <<<\n\n");
        System.out.print(String.join(", ", this.players.values().stream().map(p -> p.name).toArray(size -> new String[size])) + "\n");
    }

    public void printCurrentPlayer() {

    }

    private void setCards(GameCard[] cards) {
        this.cards.clear();
        for (GameCard card : cards) {
            this.cards.put(card.id, card);
        }
    }

    private void setPlayers(Player[] players) {
        this.players.clear();
        for (Player player : players) {
            this.players.put(player.id, player);
            if (player.cards != null) {
                this.currentPlayer = player;
            }
        }
    }
}
