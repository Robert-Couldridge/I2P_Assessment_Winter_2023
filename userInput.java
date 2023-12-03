import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A selection of methods that allow for easy input requests from the user
 * @author Robert Couldridge
 * @version 1.0
 * @since 1.0
 */
public class userInput {

    /**
     * This method takes a string as input, this string is provided to the user
     * the function then takes the user's response as an integer and returns it
     *
     * @param question This is the question the program will ask the user
     * @return Int - The integer the user entered
	 */
    protected Integer takeUserInputInteger(String question) {
        boolean validInput;
        int integerInput = 0;
        do {
            Scanner input = new Scanner(System.in);
            System.out.print(question);
            try {
                integerInput = input.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.print("INVALID INPUT\nPlease enter an Integer\n");
                validInput = false;
            }
        }
        while (!validInput);
        return integerInput;
    }

    /**
     * This method takes a string as input, this string is provided to the user
     * the function then takes the user's response as a string and returns it
     *
     * @param question This is the question the program will ask the user
     * @return String - The string the user entered
	 */
    protected String takeUserInputString(String question){
        boolean validInput;
        String stringInput = "";
        do {
            Scanner input = new Scanner(System.in);
            System.out.print(question);
            try {
                stringInput = input.nextLine();
                validInput = true;
            } catch (InputMismatchException e){
                System.out.print("INVALID INPUT\nPlease enter a String\n");
                validInput = false;
            }
        }
        while (!validInput);
        return stringInput.toLowerCase();
    }

    /**
     * This method takes a string as input, this string is provided to the user
     * the function then takes the user's response as a float and returns it
     *
     * @param question This is the question the program will ask the user
     * @return Float - The float the user entered
    */
    protected Float takeUserInputFloat(String question){
        boolean validInput;
        float floatInput = 0.0F;
        do {
            Scanner input = new Scanner(System.in);
            System.out.print(question);
            try {
                floatInput = input.nextFloat();
                validInput = true;
            }
            catch (InputMismatchException e){
                System.out.print("INVALID INPUT\nPlease enter a Float\n");
                validInput = false;
            }
        }
        while (!validInput);
        return floatInput;
    }

}
