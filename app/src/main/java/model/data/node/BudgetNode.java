package model.data.node;

/**
 * Created by xlo on 2015/12/11.
 * it's the budget node
 */
public class BudgetNode {

    private String name;
    private double income, expenditure, nowIncome, nowExpenditure;

    public BudgetNode(String name, double income, double expenditure, double nowIncome, double nowExpenditure) {
        this.name = name;
        this.income = income;
        this.expenditure = expenditure;
        this.nowIncome = nowIncome;
        this.nowExpenditure = nowExpenditure;
    }

    public String getName() {
        return name;
    }

    public double getExpenditure() {
        return expenditure;
    }

    public double getIncome() {
        return income;
    }

    public double getNowExpenditure() {
        return nowExpenditure;
    }

    public double getNowIncome() {
        return nowIncome;
    }

}
