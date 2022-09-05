package com.handson.basic.controller;

import com.handson.basic.model.Expression;
import com.handson.basic.model.UserExpression;
import com.handson.basic.service.CalculatorService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

    @Autowired
    CalculatorService calculatorService;
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> calculateExpression(@RequestBody UserExpression expression)
    {
        try {
            Expression exp = calculatorService.parse(expression.getExpression());
            double result =  exp.evaluate();
//            Color c = new Color(-16726016, true);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}