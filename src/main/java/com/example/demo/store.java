package com.example.demo;

/**
 * The main file for the inventory management system, this handles the interactions with the user
 * whilst calling functions from the other java files to orchestrate the running of the program
 * @author Robert Couldridge
 * @version 1.0
 * @since 1.0
 */


public class store
{
	public static void main(String[] args)
	{
		// prerequisite values and class instances required
		String itemsFile = "src/main/java/com/example/demo/items.txt";
		storeActions storeInstance = new storeActions();
		userInput takeUserInput = new userInput();
		boolean running = true;

		do
		{
			// print out cmd line interface
			System.out.println("I N V E N T O R Y    M A N A G E M E N T    S Y S T E M");
			System.out.println("-----------------------------------------------");
			System.out.println("1. ADD NEW ITEM");
			System.out.println("2. UPDATE QUANTITY OF EXISTING ITEM");
			System.out.println("3. REMOVE ITEM");
			System.out.println("4. SEARCH FOR AN ITEM");
			System.out.println("5. VIEW DAILY TRANSACTION REPORT");
			System.out.println("---------------------------------");
			System.out.println("6. Exit");


			int userInput = takeUserInput.takeUserInputInteger("\n Enter a choice and Press ENTER to continue[1-6]:");

				if (userInput>6 || userInput<1) {

					// catch invalid entries
					System.out.println("This doesn't appear to be a valid option...!\n");
					String continueScript = takeUserInput.takeUserInputString("Press ENTER to continue");
				}
				if (userInput == 1)	{

					// add an item
					storeInstance.addItem(itemsFile);
					String continueScript = takeUserInput.takeUserInputString("Press ENTER to continue\n\n");
				}
				else if (userInput == 2) {

					// update the quantity of an item
					storeInstance.updateQuantity(itemsFile);
					String continueScript = takeUserInput.takeUserInputString("Press ENTER to continue\n\n");
				}
				else if (userInput == 3) {

					// remove an item
					storeInstance.removeItem(itemsFile);
					String continueScript = takeUserInput.takeUserInputString("Press ENTER to continue\n\n");
				}
				else if (userInput == 4) {

					// search for an item
					boolean itemLocated;
					do {
						String itemName = takeUserInput.takeUserInputString("ITEM NAME: ");
						if (storeInstance.isItemInInventory(itemName, itemsFile, true)) {
							System.out.printf("%s is in inventory\n", itemName);
							itemLocated = true;
						} else {
							System.out.printf("%s is not in inventory\n", itemName);
							itemLocated = false;
						}
					} while (!itemLocated);
					String continueScript = takeUserInput.takeUserInputString("Press ENTER to continue\n\n");
				}
				else if (userInput == 5){

					// display the transaction report
					storeInstance.displayTransactionReport();
					System.out.print("\n Report printed\n");
					String continueScript = takeUserInput.takeUserInputString("Press ENTER to continue\n\n");
				}
				else if (userInput == 6){

					// end the program
					System.out.println("Quitting...");
					running = false;
				}

		// ensures the program remains active until the user requests to exit
		} while (running);
		
	System.out.println("\n\nThanks for using this program.");

	// clear the transaction report ready for the next time the program is run
	storeInstance.clearTransactionReport();
	System.out.println("\n--------------------------\nTransaction Report Cleared\n--------------------------");
	}









}