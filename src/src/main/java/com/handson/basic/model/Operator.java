package com.handson.basic.model;

public abstract class Operator implements Expression{
    Expression left;
    Expression right;
    int order;
    public Operator()  {
    }

    public abstract Expression getOperator(Expression left, Expression right) ;
    public abstract boolean isSupported(String candidateOperator);
    public abstract int getOrder();

}
