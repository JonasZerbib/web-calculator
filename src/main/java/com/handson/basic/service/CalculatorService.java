package com.handson.basic.service;

import com.handson.basic.model.*;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Service
public class CalculatorService {
    private ArrayList<Operator> operators = getOperatorList();

    private ArrayList<Operator> getOperatorList() {
        ArrayList<Operator> res = new ArrayList<>();
        res.add(new Addition());
        res.add(new Substraction());
        res.add(new Division());
        res.add(new Multiplication());
        return res;
    }


    public Expression parse(String exp) {
        String[] tokens = exp.split(" ");
        int pos = 0;
        return recursiveEval(tokens, pos);
    }

    private Expression recursiveEval(String[] tokens, int pos) {
        if (pos >= tokens.length) throw new RuntimeException("missing arguments");
        String curVal = tokens[pos].trim().toLowerCase();
        if (pos == tokens.length-1) {
            return getAtomFromLastValue(curVal);
        }
        if (pos > tokens.length-1) throw new RuntimeException("not enough arguments");

        String nextVal = tokens[pos +1].trim().toLowerCase();
        Expression side1;
        Operator operator;
        Expression side2;
        if (isAtom(curVal)) {
            side1 = new Atom(Integer.valueOf(curVal));
        } else {
            throw new RuntimeException("expecting a number");
        }
        if (isOperator(nextVal)) {
            operator = findOperator(nextVal);
        }else {
            throw new RuntimeException("expecting an operator");
        }
        side2 = recursiveEval(tokens, pos +2);
        return operator.getOperator(side1, side2);


    }

    private Atom getAtomFromLastValue(String curVal) {
        if (isAtom(curVal)) {
            return new Atom(Integer.valueOf(curVal));
        } else {
            throw new RuntimeException("expecting a number");
        }
    }

    private boolean isOperator(String nextVal) {
        return  (findOperator(nextVal) != null);
    }

    private Operator findOperator(String nextVal) {
        for (Operator op : operators) {
            if (op.isSupported(nextVal)) {
                return op;
            }
        }
        return null;
    }

    private boolean isAtom(String curVal) {
        try {
            Integer.valueOf(curVal);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
