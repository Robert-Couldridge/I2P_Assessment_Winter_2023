import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class storeActions {

    /*
	This method asks the user to supply an item name, unit price and quantity
	It then auto generates an item id and calculates the total price of the stock
	This information is all concatenated into a string that is appended to the items.txt doc
	 */
    public void addItem(String items_file){

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
	This method iterates through the items file and finds the biggest item ID
	It then adds 1 to that number before adding any leading 0s to create a 5-digit number
	This is then converted to a string and returned
	 */
    public String generateItemID(String items_file){
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

    public void updateQuantity(String items_file){
        String item_name = "";

        // check whether the desired item exists in the items file
        while (!isItemInInventory(item_name, items_file)){
            item_name = takeUserInputString("NAME OF ITEM: ");
            if (!isItemInInventory(item_name, items_file)){
                System.out.printf("%s not found in inventory\n", item_name);
            }
        }
        System.out.printf("%s located in inventory\n", item_name);

        // locates the item's records in the item file
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(items_file))){
            String line;
            while ((line = bufferedReader.readLine()) != null){
                String[] current_item = line.split(",");
                if (Objects.equals(current_item[1], item_name)) {

                    // asks the user for the desired new quantity of the item
                    int quantity = takeUserInputInteger("UPDATED QUANTITY OF ITEM: ");
                    int unit_price = Integer.parseInt(current_item[2]);
                    int total_price = unit_price * quantity;

                    // updates the records for the desired new quantity of item
                    current_item[4] = String.valueOf(total_price);
                    current_item[3] = String.valueOf(quantity);
                    System.out.print(String.join(",", current_item));
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
    public boolean isItemInInventory(String item_name, String items_file){
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
