import java.util.InputMismatchException;
import java.util.Scanner;

public class userInput {

    /*
	This method takes a string as input, this string is provided to the user
	the function then takes the user's response as an integer and returns it
	 */
    public Integer takeUserInputInteger(String question) {
        boolean validInput = true;
        int integer = 0;
        do {
            Scanner input = new Scanner(System.in);
            System.out.print(question);
            try {
                integer = input.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.print("INVALID INPUT\nPlease enter an Integer\n");
                validInput = false;
            }
        }
        while (!validInput);
        return integer;
    }

    /*
	This method takes a string as input, this string is provided to the user
	the function then takes the user's response as a string and returns it
	 */
    public String takeUserInputString(String question){
        Scanner input = new Scanner(System.in);
        System.out.print(question);
        return input.nextLine();
    }

    /*
   This method takes a string as input, this string is provided to the user
   the function then takes the user's response as a float and returns it
    */
    public Float takeUserInputFloat(String question){
        Scanner input = new Scanner(System.in);
        System.out.print(question);
        return input.nextFloat();
    }

}
