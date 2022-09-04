package com.handson.basic.model;

import java.util.Objects;

public interface Expression {
    // It can be atom or operator(division, multpl,...).
    // show that operator inherit from expression
    public  Integer evaluate();
}
