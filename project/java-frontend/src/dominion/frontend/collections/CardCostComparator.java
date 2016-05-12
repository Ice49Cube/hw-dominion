package dominion.frontend.collections;

import dominion.frontend.responses.GameCard;

import java.util.Comparator;

public class CardCostComparator implements Comparator<GameCard> {

    private final boolean ascending;

    public CardCostComparator(boolean ascending) {
        this.ascending = ascending;
    }

    public CardCostComparator() {
        this(true);
    }

    @Override
    public int compare(GameCard o1, GameCard o2) {
        if (this.ascending) {
            return o1.cost - o2.cost;
        } else {
            return o2.cost - o1.cost;
        }
    }

}
