import java.util.InputMismatchException;
import java.util.Scanner;

public class userInput {

    /*
	This method takes a string as input, this string is provided to the user
	the function then takes the user's response as an integer and returns it
	 */
    public Integer takeUserInputInteger(String question) {
        boolean validInput = true;
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

    /*
	This method takes a string as input, this string is provided to the user
	the function then takes the user's response as a string and returns it
	 */
    public String takeUserInputString(String question){
        boolean validInput = true;
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

    /*
   This method takes a string as input, this string is provided to the user
   the function then takes the user's response as a float and returns it
    */
    public Float takeUserInputFloat(String question){
        boolean validInput = true;
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
