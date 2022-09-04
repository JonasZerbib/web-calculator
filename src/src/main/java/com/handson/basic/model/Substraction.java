package com.handson.basic.model;

public class Substraction extends Operator{

    public Substraction() {}
    public Substraction(int order){
        this.order=order;
    }
    @Override
    public double evaluate() {
        return left.evaluate() - right.evaluate();
    }
    @Override
    public Expression getOperator(Expression left, Expression right) {
        Substraction res = new Substraction();
        res.left = left;
        res.right = right;
        return res;
    }

    @Override
    public boolean isSupported(String candidateOperator) {
        return candidateOperator.equals("-");
    }

    public int getOrder(){
        return order;
    }
}
