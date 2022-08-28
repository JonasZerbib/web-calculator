package com.handson.basic.model;

public class Division extends Operator{
    @Override
    public Integer evaluate() {
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

}
