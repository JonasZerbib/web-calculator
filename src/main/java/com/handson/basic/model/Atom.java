package com.handson.basic.model;

public class Atom implements Expression {
    Integer value;

    public Atom(Integer value) {
        this.value = value;
    }

    public Integer evaluate(){
        return value;
    }
}
