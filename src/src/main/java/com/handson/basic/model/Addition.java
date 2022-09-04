package com.handson.basic.model;

public class Addition extends Operator{

    public Addition(){}
    public Addition(int order){ this.order=order; }
    @Override
    public double evaluate() {
        return left.evaluate() + right.evaluate();
    }

    @Override
    public Expression getOperator(Expression left, Expression right) {
        Addition res = new Addition();
        res.left = left;
        res.right = right;
        return res;
    }

    @Override
    public boolean isSupported(String candidateOperator) {
        return candidateOperator.equals("+");
    }

    @Override
    public int getOrder(){
        return order;
    }

}
