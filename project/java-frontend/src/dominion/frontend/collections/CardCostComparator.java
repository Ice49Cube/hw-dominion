package dominion.frontend.collections;

import dominion.results.*;

import java.util.Comparator;

public class CardCostComparator implements Comparator<GameCardInfo> {

    private final boolean ascending;

    public CardCostComparator(boolean ascending) {
        this.ascending = ascending;
    }

    public CardCostComparator() {
        this(true);
    }

    @Override
    public int compare(GameCardInfo o1, GameCardInfo o2) {
        if (this.ascending) {
            return o1.getCost() - o2.getCost();
        } else {
            return o2.getCost() - o1.getCost();
        }
    }

}
