package com.handson.basic.model;

import java.security.PublicKey;

public class Addition extends Operator{

    @Override
    public Integer evaluate() {
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

}
