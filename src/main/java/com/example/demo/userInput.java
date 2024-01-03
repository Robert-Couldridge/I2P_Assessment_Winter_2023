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
            try {
                int integerInput = Integer.parseInt(value);
            } catch (InputMismatchException e) {
                System.out.print("INVALID INPUT\nPlease enter an Integer\n");
                return -1;
            }
        return Integer.parseInt(value);
    }

    protected Float floatConversion(String value) {
        try {
            float floatInput = Float.parseFloat(value);
        } catch (InputMismatchException e) {
            System.out.print("INVALID INPUT\nPlease enter an Integer\n");
            return -1f;
        }
        return Float.parseFloat(value);
    }
}
