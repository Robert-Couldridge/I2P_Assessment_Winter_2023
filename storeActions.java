import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class storeActions {

    /*
	This method asks the user to supply an item name, unit price and quantity
	It then auto generates an item id and calculates the total price of the stock
	This information is all concatenated into a string that is appended to the itemsFile doc
	 */
    public void addItem(String itemsFile){

        // gather data from the user
        String itemName = takeUserInputString("NAME OF ITEM: ");
        float unitPrice = takeUserInputFloat("UNIT PRICE: ");
        int quantity = takeUserInputInteger("QUANTITY: ");

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
	This method takes a string as input, this string is provided to the user
	the function then takes the user's response as an integer and returns it
	 */
    public Integer takeUserInputInteger(String question){
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
	This method
	 */
    public void updateQuantity(String itemsFile){
        String itemName = "";

        // check whether the desired item exists in the items file
        while (!isItemInInventory(itemName, itemsFile)){
            itemName = takeUserInputString("NAME OF ITEM: ");
            if (!isItemInInventory(itemName, itemsFile)){
                System.out.printf("%s not found in inventory\n", itemName);
            }
        }
        System.out.printf("%s located in inventory\n", itemName);

        // locates the item's records in the item file
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(itemsFile))){
            String line;
            while ((line = bufferedReader.readLine()) != null){
                String[] currentItem = line.split(",");
                if (Objects.equals(currentItem[1], itemName)) {

                    // asks the user for the desired new quantity of the item
                    int quantity = takeUserInputInteger("UPDATED QUANTITY OF ITEM: ");
                    float unitPrice = Float.parseFloat(currentItem[2]);
                    float totalPrice = unitPrice * quantity;

                    // updates the records for the desired new quantity of item
                    currentItem[4] = String.valueOf(totalPrice);
                    currentItem[3] = String.valueOf(quantity);
                    System.out.print(String.join(",", currentItem));
                }
            }
        }
        catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }




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
