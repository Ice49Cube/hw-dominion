package dominion.frontend;

import dominion.frontend.collections.CardCostComparator;
import dominion.frontend.responses.GameCard;
import dominion.frontend.responses.Player;
import dominion.frontend.responses.StartGameResult;

import java.util.ArrayList;
import java.util.Collections;

import java.util.HashMap;
import java.util.function.Predicate;

public class Game {
    
    private final HashMap<Integer, GameCard> cards;
    private final HashMap<Integer, Player> players;
    private Player currentPlayer;

    public Game(StartGameResult data) {
        this.cards = new HashMap();
        this.players = new HashMap();
        this.setCards(data.cards);
        this.setPlayers(data.players);
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

    public void printGameCards() {
        ArrayList<GameCard> sorted = new ArrayList(this.cards.values());
        Collections.sort(sorted, new CardCostComparator());
        sorted.stream().forEach((card) -> {
            System.out.println("Name: " + card.name + "\tCost: " + card.cost);
        });
    }
    
    private void setCards(GameCard[] cards) {
        this.cards.clear();
        for(GameCard card : cards)
            this.cards.put(card.id, card);
    }
    
    private void setPlayers(Player[] players) {
        this.players.clear();
        for(Player player : players) {
            this.players.put(player.id, player);
            if(player.cards != null) {
                this.currentPlayer = player;
            }
        }
    }
}
    /* private void startGame(CommandBase command) throws Exception
    {
        StartGameResult result = (StartGameResult);
        System.out.println("game id: " + result.id);
        for(Player player : result.players) {
            System.out.println("\t" + player.id + ": " + player.name);
            if(player.cards != null) {
                for(PlayerCard card : player.cards) {
                    System.out.println("\t\tcard: " + card.id +
                    "; " + card.order +
                    "; " + card.pile +
                    "; " + this.cards.get(card.cardId).name);
                }
            }
        }
    } */
