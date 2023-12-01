public class testing {

    public static void main(String args[]){

        // initialise userInput class
        userInput takeUserInput = new userInput();

        // test
        int numberInput = takeUserInput.takeUserInputInteger("Enter A Number: ");
        System.out.print(numberInput);
    }
}
