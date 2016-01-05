package model.data.node;

/**
 * Created by xlo on 2015/12/11.
 * it's the money node
 */
public class MoneyNode {

    private String name;
    private double value;

    public MoneyNode(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

}
