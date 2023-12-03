public class testing {

    public static void main(String args[]){

        // initialise userInput class
        userInput takeUserInput = new userInput();

        // test int
        int numberInput = takeUserInput.takeUserInputInteger("Enter A Number: ");
        System.out.print(numberInput);

//        // test string
//        String stringInput = takeUserInput.takeUserInputString("Enter A String: ");
//        System.out.print(stringInput);

//        // test float
//        float floatInput = takeUserInput.takeUserInputFloat("Enter A Float: ");
//        System.out.print(floatInput);
    }


}
