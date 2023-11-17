import java.util.Scanner;

public class userInput {

    /*
	This method takes a string as input, this string is provided to the user
	the function then takes the user's response as an integer and returns it
	 */
    public Integer takeUserInputInteger(String question) {
        Scanner input = new Scanner(System.in);
        System.out.print(question);
        return input.nextInt();
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
