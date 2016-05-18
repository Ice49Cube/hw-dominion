package dominion.model;

public class Order {

    private int max;
    private int min;

    public Order() {
        this(0, 0);
    }

    public Order(int min, int max) {
        if (min > max) {
            throw new IllegalStateException();
        }
        this.min = min;
        this.max = max;
    }

    public int newMax() {
        return ++this.max;
    }

    public int newMin() {
        return --this.min;
    }

    public int getMax() {
        return this.max;
    }

    public void setMax(int value) {
        if (value < max) {
            throw new IllegalStateException();
        }
        this.max = value;
    }

    public int getMin() {
        return this.max;
    }

    public void setMin(int value) {
        if (value > min) {
            throw new IllegalStateException();
        }
        this.min = value;
    }

}
