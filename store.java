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


import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class store
{
	public static void main(String args[])
	{
		String items_file = "items.txt";

		Scanner input = new Scanner(System.in);
		
		System.out.println("I N V E N T O R Y    M A N A G E M E N T    S Y S T E M");
		System.out.println("-----------------------------------------------");
		System.out.println("1. ADD NEW ITEM");
		System.out.println("2. UPDATE QUANTITY OF EXISTING ITEM");
		System.out.println("3. REMOVE ITEM");
		System.out.println("4. SEARCH FOR AN ITEM");
		System.out.println("5. VIEW DAILY TRANSACTION REPORT");
		System.out.println("---------------------------------");
		System.out.println("6. Exit");
		
		
		System.out.print("\n Enter a choice and Press ENTER to continue[1-5]:");
		int userinput = input.nextInt();
			

		while(userinput !=6)
		{
			if (userinput>5 || userinput<1) {
				System.out.println("This doesn't appear to be a valid option...!");
				break;
			}
			if (userinput == 1)	{
				addItem(items_file);
				System.out.print("\n New Item Added");	
				break;
			}
			else if (userinput == 2) {
				updateQuantity(items_file);
				System.out.print("\n Item quantity updated");
				break;
			}
			else if (userinput == 3) {
				System.out.print("\n Item Removed");
				break;
			}	
			else if (userinput == 4) {
				System.out.print("\n Item Searched");
				break;
			}
			else if (userinput == 5){
				System.out.print("\n Report printed");
				break;
			}
			
		}
		
	System.out.println("\n\n Thanks for using this program...!");
	}

	/*
	This method asks the user to supply an item name, unit price and quantity
	It then auto generates an item id and calculates the total price of the stock
	This information is all concatenated into a string that is appended to the items.txt doc
	 */
	static void addItem(String items_file){

		// gather data from the user
		String item_name = takeUserInputString("NAME OF ITEM: ");
		int unit_price = takeUserInputInteger("UNIT PRICE: ");
		int quantity = takeUserInputInteger("QUANTITY: ");

		// create 'total_price' and 'item_id' values
		int total_price = quantity * unit_price;
		String item_id = generateItemID(items_file);

		// append data to items.txt
		try{
			FileWriter out = new FileWriter(items_file, true);
			PrintWriter output = new PrintWriter(out);
			output.printf("%s,%s,%d,%d,%d%n", item_id, item_name, unit_price, quantity, total_price);
			output.close();
		}
		catch (FileNotFoundException e){
			System.out.printf("%s NOT FOUND", items_file);
		}
		catch (IOException e){
			System.out.println("Error: " + e.getMessage());
		}

		// display item added to user
		System.out.printf("%d %s's added at £%d each", quantity, item_name, unit_price);

	}

	static void updateQuantity(String items_file){
		String item_name = "";

		// check whether the desired item exists in the items file
		while (!isItemInInventory(item_name, items_file)){
			item_name = takeUserInputString("NAME OF ITEM: ");
			if (!isItemInInventory(item_name, items_file)){
				System.out.printf("%s not found in inventory\n", item_name);
			}
		}

		// locates the item's records in the item file
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(items_file))){
			String line;
			while ((line = bufferedReader.readLine()) != null){
				String[] current_item = line.split(",");
				if (Objects.equals(current_item[1], item_name)) {

					// asks the user for the desired new quantity of the item
					int quantity = takeUserInputInteger("UPDATED QUANTITY OF ITEM: ");
					int unit_price = Integer.parseInt(current_item[3]);
					int total_price = unit_price * quantity;

					// updates the records for the desired new quantity of item
					current_item[4] = String.valueOf(total_price);
					current_item[2] = String.valueOf(quantity);
				}
			}
		}
		catch (IOException e){
			System.out.println("Error: " + e.getMessage());
		}




	}

	/*
	This method takes a string as input, this string is provided to the user
	the function then takes the user's response as a string and returns it
	 */
	static String takeUserInputString(String question){
		Scanner input = new Scanner(System.in);
		System.out.print(question);
		return input.nextLine();
	}

	/*
	This method takes a string as input, this string is provided to the user
	the function then takes the user's response as an integer and returns it
	 */
	static Integer takeUserInputInteger(String question){
		Scanner input = new Scanner(System.in);
		System.out.print(question);
		return input.nextInt();
	}

	/*
	This method iterates through the "items.txt" file and finds the biggest item ID
	It then adds 1 to that number before adding any leading 0s to create a 5-digit number
	This is then converted to a string and returned
	 */
	static String generateItemID(String items_file){
		String item_id_string = "";
		int item_id_int = 0;

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(items_file))){
			String line;

			// Iterate through the .txt file line by line finding the largest item ID and adding 1
			while ((line = bufferedReader.readLine()) != null){
				String[] current_item_id = line.split(",");
				try{
					int current_item_id_number = Integer.parseInt(current_item_id[0]);
					if (current_item_id_number >= item_id_int){
						item_id_int = current_item_id_number + 1;
					}
				}catch (NumberFormatException e){;}
			}
		}
		catch (IOException e){
			System.out.println("Error: " + e.getMessage());
		}

		// Return item ID as a 5-digit number in string form
		item_id_string = String.format("%05d", item_id_int);
		return item_id_string;
	}

	/*
	This method iterates through the "items.txt" file and checks if the
	provided item name is located in the file, it returns TRUE if found
	 */
	static boolean isItemInInventory(String item_name, String items_file){
		boolean in_file = false;
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(items_file))){
			String line;

			// Iterate through the .txt file line by line checking for the desired item
			while ((line = bufferedReader.readLine()) != null){
				String[] current_item_name = line.split(",");
				if (Objects.equals(current_item_name[1], item_name)) {
					in_file = true;
				}
			}
		}
		catch (IOException e){
			System.out.println("Error: " + e.getMessage());
		}

		// Return the results of the search in boolean form
		return in_file;
	}



}