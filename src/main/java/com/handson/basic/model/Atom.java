package com.handson.basic.model;

public class Atom implements Expression {
    double value;

    public Atom(double value) {
        this.value = value;
    }

    public double evaluate(){
        return value;
    }
}
