package com.example.demo;

import java.io.*;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 * A selection of methods that represent the majority of actions the inventory
 * management system (store.java) uses
 * @author Robert Couldridge
 * @version 1.0
 * @since 1.0
 */
public class storeActions {


    // create an instance of the userInput class for the following methods to utilise.
    userInput takeUserInput = new userInput();

    /**
     * This method asks the user to supply an item name, unit price and quantity
     * it then auto generates an item id and calculates the total price of the stock
     * this information is all concatenated into a string that is appended to the items file.
     *
     * @param itemsFile This the name of the file that stores all the inventory records i.e "items.txt"
	 */
    protected int addItem(String itemsFile, String itemName, int quantity, float unitPrice){

        // check whether the desired item exists in the items file
        if (isItemInInventory(itemName, itemsFile, false)){
            return 1;
            }

        // create 'totalPrice' and 'itemId' values
        float totalPrice = quantity * unitPrice;
        String itemId = generateItemID(itemsFile);

        // append data to itemsFile
        try{
            FileWriter out = new FileWriter(itemsFile, true);
            PrintWriter output = new PrintWriter(out);
            output.printf("%n%s,%s,%.2f,%d,%.2f", itemId, itemName, unitPrice, quantity, totalPrice);
            output.close();
        }
        catch (FileNotFoundException e){
            System.out.printf("%s NOT FOUND", itemsFile);
        }
        catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }

        // update the transaction report
        addToTransactionReport(itemId,itemName,0,unitPrice,quantity,"Item Added To Inventory");

        // display item added to user
        System.out.printf("%d %s's added at £%.2f each\n", quantity, itemName, unitPrice);
        return 0;

    }

    /**
     * This method iterates through the items file and finds the biggest item ID
     * it then adds 1 to that number before adding any leading 0s to create a 5-digit number
     * this is then converted to a string and returned.
     *
     * @param itemsFile This the name of the file that stores all the inventory records i.e "items.txt"
     * @return String- This returns a 5 digit unique item ID
	 */
    protected String generateItemID(String itemsFile){
        int itemIdInt = 0;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(itemsFile))){
            String line;
            int lineNumber = 0;

            // Iterate through the itemsFile file line by line finding the largest item ID and adding 1
            while ((line = bufferedReader.readLine()) != null){
                String[] currentItemId = line.split(",");

                // Skip the column header line
                if (lineNumber > 0){

                    int currentItemIdNumber = Integer.parseInt(currentItemId[0]);
                    if (currentItemIdNumber >= itemIdInt){
                        itemIdInt = currentItemIdNumber + 1;
                    }
                }
                lineNumber++;
            }
        }
        catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }

        // Return item ID as a 5-digit number in string form
        return String.format("%05d", itemIdInt);
    }

    /**
     * This method asks the user for an item, checks the item is in the items file
     * then asks the user for an updated quantity, this is then written to the items file.
     *
     * @param itemsFile This the name of the file that stores all the inventory records i.e "items.txt"
	 */
    protected int updateQuantity(String itemsFile, String itemName, int quantity){

        // check whether the desired item exists in the itemsFile
        if (!isItemInInventory(itemName, itemsFile,false)){return 1;}

        if (quantity <= 0){return 2;}

        String[] item = {"Null", "Null", "Null", "Null", "Null"};

        // create a temporary file to write the updated inventory to
        try {
            Path tempFilePath = Files.createTempFile(null,null);


            // opens the itemsFile in reader and the temporary file in writer
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(itemsFile));
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tempFilePath.toFile()));

                // iterates through the itemsFile line by line
                String line;
                int lines = 0;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] currentItem = line.split(",");

                    // locates the specific item
                    if (Objects.equals(currentItem[1], itemName)) {

                        // stores the information about the item for later in the method
                        item = Arrays.copyOf(currentItem, 4);

                        // asks the user for the desired new quantity of the item
                        float unitPrice = Float.parseFloat(currentItem[2]);
                        float totalPrice = unitPrice * quantity;

                        // updates the records for the desired new quantity of item
                        currentItem[4] = String.valueOf(totalPrice);
                        currentItem[3] = String.valueOf(quantity);
                    }
                    if (lines > 0){
                        bufferedWriter.newLine();
                    }
                    bufferedWriter.write(String.join(",", currentItem));
                    lines++;
                }
                // close reader and writer
                bufferedReader.close();
                bufferedWriter.close();
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }

            // replace the itemsFile with the new temporary file
            Files.move(tempFilePath, Paths.get(itemsFile), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // update the transaction report
        int qtySold;
        if (Integer.parseInt(item[3]) > quantity){
            qtySold = Integer.parseInt(item[3]) - quantity;
        }
        else {qtySold=0;}
        addToTransactionReport(item[0], item[1], qtySold, Float.parseFloat(item[2]), quantity, "Item Quantity Updated");
        return 0;
    }

    /**
     * This method asks the user for an item, checks the item is in the items file
     * then removes the item from the itemsFile.
     *
     * @param itemsFile This the name of the file that stores all the inventory records i.e "items.txt"
     */
    protected int removeItem(String itemsFile, String itemName){

        // check whether the desired item exists in the itemsFile
        if (!isItemInInventory(itemName, itemsFile,false)) {
            return 1;
        }

        String[] item = {"Null", "Null", "Null", "Null", "Null"};

        // create a temporary file to write the updated inventory to
        try {
            Path tempFilePath = Files.createTempFile(null,null);


            // opens the itemsFile in reader and the temporary file in writer
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(itemsFile));
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tempFilePath.toFile()));

                // iterates through the itemsFile line by line
                String line;
                int lines = 0;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] currentItem = line.split(",");


                    // Adds every item to the temporary file other than the specified item
                    if (!Objects.equals(currentItem[1], itemName)) {
                        if (lines > 0){
                            bufferedWriter.newLine();
                        }
                        lines++;
                        bufferedWriter.write(String.join(",", currentItem));
                    } else {
                        // stores the information about the item for later in the method
                        item = currentItem;
                    }

                }
                // close reader and writer
                bufferedReader.close();
                bufferedWriter.close();
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }

            // replace the itemsFile with the new temporary file
            Files.move(tempFilePath, Paths.get(itemsFile), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // update the transaction report
        addToTransactionReport(item[0], item[1], 0, Float.parseFloat(item[2]), 0, "Item Removed From Inventory");

        System.out.printf("Inventory no longer contains %s\n", itemName);
        return 0;
    }

    /**
     * This method iterates through the items file and checks if the
     * provided item name is located in the file, it returns 'true' if found
     * it can then print out the details of that file if desired.
     *
     * @param itemName This is the name of the item to search for
     * @param itemsFile This the name of the file that stores all the inventory records i.e "items.txt"
     * @param displayFindings This gives the user the option to have the item's information displayed in
     *                        the console if located in the itemsFile
     * @return boolean This returns 'true' if the item is in the itemsFile and 'false' if not
	 */
    protected boolean isItemInInventory(String itemName, String itemsFile, boolean displayFindings){
        boolean inFile = false;
        String[] itemDetails;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(itemsFile))){
            String line;

            // Iterate through the itemsFile file line by line checking for the desired item
            while ((line = bufferedReader.readLine()) != null){
                String[] currentItemName = line.split(",");
                if (Objects.equals(currentItemName[1], itemName)) {
                    if (displayFindings){
                        System.out.printf("ITEM ID: %s%nITEM DESCRIPTION: %s%nUNIT PRICE: %s%nQUANTITY: %s%nTOTAL PRICE: %s%n",
                                currentItemName[0],currentItemName[1],currentItemName[2],currentItemName[3],currentItemName[4]);
                    }
                    itemDetails = currentItemName;
                    inFile = true;
                }
            }
        }
        catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }

        // Return the results of the search in boolean form
        return inFile;
    }

    /**
     * This method takes 6 inputs, an itemIO, itemDescription, qtySold, amount, stockRemaining, transactionType
     * using these values it creates a new line in the transaction report to reflect the action that has occurred
     *
     * @param itemID This is the item ID of the item edited
     * @param itemDescription This is the item description of the item edited
     * @param qtySold This is the quantity of the item sold
     * @param amount This is the unit price of the item edited
     * @param stockRemaining This is the stock of the item left in the inventory
     * @param transactionType This is the type of transaction that has occurred
     */
    protected void addToTransactionReport(String itemID, String itemDescription, int qtySold, float amount, int stockRemaining, String transactionType){

        try{
            FileWriter out = new FileWriter("src/main/java/com/example/demo/transactions.txt", true);
            PrintWriter output = new PrintWriter(out);
            output.printf("%n%s,%s,%d,%.2f,%d,%s", itemID, itemDescription, qtySold, amount, stockRemaining, transactionType);
            output.close();
        }
        catch (FileNotFoundException e){
            System.out.print("'transactions.txt' NOT FOUND");
        }
        catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * This method prints the transaction report out line by line in the console
     */
    protected void displayTransactionReport(){

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/java/com/example/demo/transactions.txt"))) {
            String line;

            // print out the transaction report line by line
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * This method wipes all transactions from the 'transactions.txt' file
     * whilst leaving the top line or 'column headers' in place
     */
    protected void clearTransactionReport(){
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/java/com/example/demo/transactions.txt"))) {

            // copy the contents of the transactions.txt file
            String contents = bufferedReader.readLine();

            // erase the file and then reinstate the contents line
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/java/com/example/demo/transactions.txt"))){
                bufferedWriter.write(contents);
            }
        }
        catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("\n--------------------------\nTransaction Report Cleared\n--------------------------");
    }
}
