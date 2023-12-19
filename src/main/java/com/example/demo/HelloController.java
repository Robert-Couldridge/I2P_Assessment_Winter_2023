package com.example.demo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HelloController {
    public Button addItem;
    public Button updateQuantity;
    public Button removeItem;
    public Button searchItem;
    public Button displayTransaction;
    public Button exit;

    storeActions storeInstance = new storeActions();
    userInput takeUserInput = new userInput();
    String itemsFile = "src/main/java/com/example/demo/items.txt";


    @FXML
    private Label welcomeText;

    // option 1 add an item
    @FXML
    protected void addAnItem(){
        storeInstance.addItem(itemsFile);
    }

    // option 2 update quantity of an item
    @FXML
    protected void updateQuantity(){
        storeInstance.updateQuantity(itemsFile);
    }

    // option 3 remove an item
    @FXML
    protected void removeAnItem(){
        storeInstance.removeItem(itemsFile);
    }

    // option 4 search for an item
    @FXML
    protected void searchForItem(){
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
    }

    // option 5 display transaction report
    @FXML
    protected void displayTransactionReport(){
        storeInstance.displayTransactionReport();
    }

    // option 6 exit
    @FXML
    protected void exit(){
        storeInstance.clearTransactionReport();
        Platform.exit();
    }
}