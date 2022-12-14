package com.handson.basic.service;
import com.google.common.collect.Lists;
import com.handson.basic.model.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CalculatorService {
    private final ArrayList<Operator> operators = getOperators();

    private ArrayList<Operator> getOperators(){
        ArrayList<Operator> res = new ArrayList<>();
        res.add(new Multiplication(1));
        res.add(new Division(1));
        res.add(new Addition(2));
        res.add(new Substraction(2));
        res.add(new Power(0));
        return res;
    }

    public Expression parse(String exp) {
        String[] tokens = exp.split(" ");
        Stack<Object> stack= new Stack<>();
        int pos=0, ord = 0, validBrackets=0;
        checkInput(tokens, pos, stack, validBrackets);
        List<Expression> expression = new ArrayList<>();
        while (!stack.isEmpty()){
            expression.add((Expression) stack.pop());
        }
        return evalWithOperatorOrder(0, Lists.reverse(expression), expression.size());
    }

    private void checkInput(String[] tokens, int pos, Stack<Object> stack, int validBrackets){
        if (pos >= tokens.length) throw new RuntimeException("missing arguments");
        String curVal = tokens[pos].trim().toLowerCase();
        if(pos==0 && pos==tokens.length-1){
            if(isAtom(curVal)){
                stack.push(new Atom(Integer.parseInt(curVal)));
            } else if (curVal.equals("(")){
                stack.push("(");
            } else {
                throw new RuntimeException("expecting a number or brackets");
            }
        } else if(pos == tokens.length - 1){
            checkEndExpression(stack, validBrackets, curVal);
        } else if(pos == 0){
            checkStartExpression(tokens, pos, stack, validBrackets, curVal);
        } else {
            checkMiddleExpression(tokens, pos, stack, validBrackets, curVal);
        }
    }

    private void checkMiddleExpression(String[] tokens, int pos, Stack<Object> stack, int validBrackets, String curVal) {
        if ((Objects.equals(curVal, "(")) && ((isAtom(tokens[pos +1].trim().toLowerCase()) || Objects.equals(tokens[pos + 1], "(")))){
            validBrackets++;
            stack.push("(");
            checkInput(tokens, pos +1, stack, validBrackets);
        } else if ((Objects.equals(curVal, ")")) && validBrackets >0 && (isOperator(tokens[pos +1].trim().toLowerCase()) || Objects.equals(tokens[pos + 1], ")"))){
            validBrackets--;
            evaluateSubExpression(stack);
            checkInput(tokens, pos +1, stack, validBrackets);
        } else if (isAtom(curVal) && (Objects.equals(tokens[pos + 1], ")")) || isOperator(tokens[pos +1].trim().toLowerCase())){
            stack.push(new Atom(Integer.parseInt(curVal)));
            checkInput(tokens, pos +1, stack, validBrackets);
        } else if (isOperator(curVal) && (Objects.equals(tokens[pos + 1], "(")) || isAtom(tokens[pos +1].trim().toLowerCase())){
            stack.push(findOperator(curVal));
            checkInput(tokens, pos +1, stack, validBrackets);
        } else {
            throw new RuntimeException("Wrong Input");
        }
    }

    private void checkEndExpression(Stack<Object> stack, int validBrackets, String curVal) {
        if (Objects.equals(curVal, ")") && validBrackets ==1){
            evaluateSubExpression(stack);
        } else if (isAtom(curVal)) {
            stack.push(new Atom(Integer.parseInt(curVal)));
        } else {
            throw new RuntimeException("expecting a number or brackets at the end");
        }
    }

    private void checkStartExpression(String[] tokens, int pos, Stack<Object> stack, int validBrackets, String curVal) {
        if (Objects.equals(curVal, "(") && (isAtom(tokens[pos + 1]) || Objects.equals(tokens[pos + 1], "("))){
            validBrackets++;
            stack.push("(");
            checkInput(tokens, pos +1, stack, validBrackets);
        } else if (isAtom(curVal) && isOperator(tokens[pos +1])){
            stack.push(new Atom(Integer.parseInt(curVal)));
            checkInput(tokens, pos +1, stack, validBrackets);
        } else {
            throw new RuntimeException("expecting a number or brackets at the beginning");
        }
    }

    private void evaluateSubExpression(Stack<Object> stack) {
        List<Expression> currentBrackets = new ArrayList<>();
        int ind = stack.size()-1;
        while (stack.get(ind--) != "("){
            currentBrackets.add((Expression) stack.pop());
        }
        stack.pop();
        stack.push(evalWithOperatorOrder(0, Lists.reverse(currentBrackets),currentBrackets.size()));
    }

    // calculate expression without brackets
    private Expression evalWithOperatorOrder(int currentOrder, List<Expression> express, int len) {
        int operatorsOrders = getOperatorsMaxOrder();
        if(currentOrder== operatorsOrders) {
            Expression res= express.get(0);
            express.clear();
            return res;
        }
        int ptr1=-1, ptr2=0;
        while (++ptr1<len){
            Expression current = express.get(ptr1);
            if(current instanceof Atom) {
                express.set(ptr2++, current);
            } else {
                Operator operator = (Operator) current;
                if(operator.getOrder()==currentOrder){
                    Expression side1= express.get(ptr2-1);
                    Expression side2= express.get(++ptr1);
                    Expression fillOperands = operator.getOperator(side1, side2);
                    express.set(ptr2-1, new Atom(fillOperands.evaluate()));
                } else {
                    express.set(ptr2++, operator);
                }
            }
        }
        return evalWithOperatorOrder(currentOrder+1, express, ptr2);
    }

    private int getOperatorsMaxOrder() {
        Set<Integer> orders  =  getOperators().stream().map(x->x.getOrder()).collect(Collectors.toSet());
        return orders.size();
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

    private Operator findOperator(String current) {
        for(Operator op: operators){
            if (op.isSupported(current)) {
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
