package com.handson.basic.model;

public class Power extends Operator{
    public Power(){}
    public Power(int order){
        this.order=order;
    }
    @Override
    public double evaluate() {
        return Math.pow(left.evaluate(), right.evaluate());
    }

    @Override
    public Expression getOperator(Expression left, Expression right) {
        Power res = new Power();
        res.left = left;
        res.right = right;
        return res;
    }

    @Override
    public boolean isSupported(String candidateOperator) {
        return candidateOperator.equals("^");
    }

    @Override
    public int getOrder(){
        return order;
    }
}
