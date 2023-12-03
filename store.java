/* UNIVERSITY OF SUFFOLK - INTRODUCTION TO PROGRAMMING 
 * Module assignment
 * 
 * Module Lead: Dr. Kakia Chatsiou
 * Last updated 2022-02-25
 * 
 * The assignment starter code consists of 3 files:
 * 
 * a) store.java: this file contains starting code for the inventory
 * management control system. See assignment brief document for 
 * more information on how to build the rest of the application.
 * 
 * b) items.txt: this file contains a list of all items in the inventory
 * with information about their quantities and total price in stock. See 
 * assignment text for more information.
 * 
 * c) transactions.txt: this file contains a list of all the transactions
 * for the day. You will be using it to print out the report of transactions
 * Each time a transaction happens i.e. an item is added or removed, 
 * a record should be stored in transactions.txt
 *  
 *
 * You are asked to work on expanding the starter code so that your Java app can do the following:
 * 
 *  - read and output to the 2 files (transactions.txt, items.txt) as appropriate
 *  - autogenerate a (5-digit) item id ie. 00001 for each new item
 *  - add a new item to the inventory (by appending a line to items.txt) 
 *  - update the quantity of an item already in store (in items.txt)
 *  - remove an item from the inventory (by removing relevant entry in items.txt)
 *  - search for an item in the inventory (items.txt)
 *  - generate and print a daily transaction report (using transactions.txt)
 * 
 * Check out the full assignment brief for more information about the report.
 */





public class store
{
	public static void main(String[] args)
	{
		String itemsFile = "items.txt";
		storeActions storeInstance = new storeActions();
		userInput takeUserInput = new userInput();
		boolean running = true;

		do
		{
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