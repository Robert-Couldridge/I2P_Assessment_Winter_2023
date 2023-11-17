import java.io.*;
import java.nio.file.StandardCopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class storeActions {

        userInput takeUserInput = new userInput();
    /*
	This method asks the user to supply an item name, unit price and quantity
	It then auto generates an item id and calculates the total price of the stock
	This information is all concatenated into a string that is appended to the itemsFile doc
	 */
    public void addItem(String itemsFile){

        // check whether the desired item exists in the items file
        String itemName = "";
        do {
            itemName = takeUserInput.takeUserInputString("NAME OF ITEM: ");
            if (isItemInInventory(itemName, itemsFile)){
                System.out.printf("%s already in inventory\n", itemName);
            }
        } while (isItemInInventory(itemName, itemsFile));

        // gather data from the user
        float unitPrice = takeUserInput.takeUserInputFloat("UNIT PRICE: ");
        int quantity = takeUserInput.takeUserInputInteger("QUANTITY: ");

        // create 'totalPrice' and 'itemId' values
        float totalPrice = quantity * unitPrice;
        String itemId = generateItemID(itemsFile);

        // append data to itemsFile
        try{
            FileWriter out = new FileWriter(itemsFile, true);
            PrintWriter output = new PrintWriter(out);
            output.printf("%s,%s,%.1f,%d,%.1f%n", itemId, itemName, unitPrice, quantity, totalPrice);
            output.close();
        }
        catch (FileNotFoundException e){
            System.out.printf("%s NOT FOUND", itemsFile);
        }
        catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }

        // display item added to user
        System.out.printf("%d %s's added at £%.1f each", quantity, itemName, unitPrice);

    }

    /*
	This method iterates through the items file and finds the biggest item ID
	It then adds 1 to that number before adding any leading 0s to create a 5-digit number
	This is then converted to a string and returned
	 */
    public String generateItemID(String itemsFile){
        String itemIdString = "";
        int itemIdInt = 0;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(itemsFile))){
            String line;

            // Iterate through the itemsFile file line by line finding the largest item ID and adding 1
            while ((line = bufferedReader.readLine()) != null){
                String[] currentItemId = line.split(",");
                try{
                    int currentItemIdNumber = Integer.parseInt(currentItemId[0]);
                    if (currentItemIdNumber >= itemIdInt){
                        itemIdInt = currentItemIdNumber + 1;
                    }
                }catch (NumberFormatException e){;}
            }
        }
        catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }

        // Return item ID as a 5-digit number in string form
        itemIdString = String.format("%05d", itemIdInt);
        return itemIdString;
    }

    /*
	This method asks the user for an item, checks the item is in the inventory
	then asks the user for an updated quantity, this is then written to the itemsFile
	 */
    public void updateQuantity(String itemsFile){

        // check whether the desired item exists in the itemsFile
        String itemName = "";
        int quantity = 0;
        do {
            itemName = takeUserInput.takeUserInputString("NAME OF ITEM: ");
            if (!isItemInInventory(itemName, itemsFile)){
                System.out.printf("%s not found in inventory\n", itemName);
            }
        } while (!isItemInInventory(itemName, itemsFile));
        System.out.printf("%s located in inventory\n", itemName);

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

                        // asks the user for the desired new quantity of the item
                        quantity = takeUserInput.takeUserInputInteger("UPDATED QUANTITY OF ITEM: ");
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
        System.out.printf("Inventory now contains %d %s's", quantity, itemName);
    }

    /*
	This method iterates through the items file and checks if the
	provided item name is located in the file, it returns TRUE if found
	 */
    public boolean isItemInInventory(String itemName, String itemsFile){
        boolean inFile = false;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(itemsFile))){
            String line;

            // Iterate through the itemsFile file line by line checking for the desired item
            while ((line = bufferedReader.readLine()) != null){
                String[] currentItemName = line.split(",");
                if (Objects.equals(currentItemName[1], itemName)) {
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

}
