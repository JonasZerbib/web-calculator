package com.handson.basic.model;

public class Multiplication extends Operator{

    public Multiplication(){}
    public Multiplication(int order){
        this.order=order;
    }
    @Override
    public double evaluate() {
        return left.evaluate() * right.evaluate();
    }

    @Override
    public Expression getOperator(Expression left, Expression right) {
        Multiplication res = new Multiplication();
        res.left = left;
        res.right = right;
        return res;
    }

    @Override
    public boolean isSupported(String candidateOperator) {
        return candidateOperator.equals("*");
    }

    public int getOrder(){
        return order;
    }
}
