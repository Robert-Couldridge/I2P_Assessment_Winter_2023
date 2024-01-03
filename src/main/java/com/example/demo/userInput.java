package com.example.demo;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A selection of methods that allow for input conversion for initialisation by other classes
 * @author Robert Couldridge
 * @version 2.0
 * @since 1.0
 */
public class userInput {

    protected Integer integerConversion(String value) {
        int integerInput;
            try {
                integerInput = Integer.parseInt(value);
            } catch (InputMismatchException | NumberFormatException e) {
                integerInput = -1;
            }
        return integerInput;
    }

    protected Float floatConversion(String value) {
        float floatInput;
        try {
            floatInput = Float.parseFloat(value);
        } catch (InputMismatchException | NumberFormatException e) {
            floatInput = -1f;
        }
        return floatInput;
    }
}
