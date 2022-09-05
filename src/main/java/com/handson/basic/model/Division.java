package com.handson.basic.model;

public class Division extends Operator{

    public Division(){}
    public Division(int order){
        this.order=order;
    }
    @Override
    public double evaluate() {
        return left.evaluate() / right.evaluate();
    }
    @Override
    public Expression getOperator(Expression left, Expression right) {
        Division res = new Division();
        res.left = left;
        res.right = right;
        return res;
    }

    @Override
    public boolean isSupported(String candidateOperator) {
        return candidateOperator.equals("/");
    }

    public int getOrder(){
        return order;
    }

}
