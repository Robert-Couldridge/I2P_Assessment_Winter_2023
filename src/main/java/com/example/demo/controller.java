package com.example.demo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.Objects;

public class controller {
    public ToggleButton addItem;
    public Button updateQuantity;
    public Button removeItem;
    public Button searchItem;
    public Button displayTransaction;
    public Button exit;
    public TextField itemName;
    public TextField itemQuantity;
    public Button submit;
    public TextField itemUnitPrice;
    public Text outputText;

    storeActions storeInstance = new storeActions();
    userInput takeUserInput = new userInput();
    String itemsFile = "src/main/java/com/example/demo/items.txt";

    String submitAction = "";

    int quantity;

    protected void closeTextFields(){
        itemName.setVisible(false);
        itemUnitPrice.setVisible(false);
        itemQuantity.setVisible(false);
        submit.setVisible(false);
        outputText.setText("");
    }

    @FXML
    private Label welcomeText;

    // option 1 add an item
    @FXML
    protected void addAnItem(ActionEvent event) {
        closeTextFields();
        itemName.setVisible(true);
        itemQuantity.setVisible(true);
        itemUnitPrice.setVisible(true);
        submit.setVisible(true);
        submitAction = "addItem";
    }

    // option 2 update quantity of an item
    @FXML
    protected void updateQuantity(){
        closeTextFields();
        itemName.setVisible(true);
        itemQuantity.setVisible(true);
        submit.setVisible(true);
        submitAction = "updateQuantity";
    }

    // option 3 remove an item
    @FXML
    protected void removeAnItem(){
        closeTextFields();
        itemName.setVisible(true);
        submit.setVisible(true);
        submitAction = "removeItem";
    }

    // option 4 search for an item
    @FXML
    protected void searchForItem(){
        closeTextFields();
        itemName.setVisible(true);
        submit.setVisible(true);
        submitAction = "searchItem";
    }

    // option 5 display transaction report
    @FXML
    protected void displayTransactionReport(){
        closeTextFields();
        storeInstance.displayTransactionReport();
    }

    // option 6 exit
    @FXML
    protected void exit(){
        closeTextFields();
        storeInstance.clearTransactionReport();
        Platform.exit();
    }

    @FXML
    protected void submit(){
        boolean actionComplete = true;
        String displayedText = "";
        switch (submitAction){
            case "addItem":
                quantity = takeUserInput.integerConversion(itemQuantity.getText());
                if (quantity <= 0){
                    displayedText += "invalid value for quantity\n";
                    outputText.setText(displayedText);
                    actionComplete = false;
                }
                float unitPrice = takeUserInput.floatConversion(itemUnitPrice.getText());
                if (unitPrice <= 0){
                    displayedText += "invalid value for unit price\n";
                    outputText.setText(displayedText);
                    actionComplete = false;
                }
                if (!actionComplete){break;}
                if (storeInstance.addItem(itemsFile, itemName.getText(), quantity, unitPrice) == 1){
                    outputText.setText("item already in inventory\n");
                    actionComplete = false;
                }
                if (actionComplete) {
                    outputText.setText(quantity + " " + itemName.getText() + "'s added to inventory at £" + unitPrice + " each");
                }
                break;
            case "updateQuantity":
                quantity = takeUserInput.integerConversion(itemQuantity.getText());
                if (quantity <= 0){
                    displayedText += "invalid value for quantity\n";
                    outputText.setText(displayedText);
                    actionComplete = false;
                }
                if (storeInstance.updateQuantity(itemsFile, itemName.getText(), quantity) != 0){
                    if (storeInstance.updateQuantity(itemsFile, itemName.getText(), quantity) == 1){
                    displayedText += "item not in inventory\n";}
                    outputText.setText(displayedText);
                    actionComplete = false;
                }
                if (actionComplete)
                    outputText.setText("inventory now contains " + quantity + " " + itemName.getText() + "'s");
                break;
            case "removeItem":
                if (storeInstance.removeItem(itemsFile, itemName.getText()) == 1) {
                    outputText.setText(itemName.getText() + " not found in inventory");
                    actionComplete = false;
                }
                else {outputText.setText("Inventory no longer contains " + itemName.getText());}
                break;
            case "searchItem":
                if (storeInstance.isItemInInventory(itemName.getText(), itemsFile, true)) {
                    outputText.setText(itemName.getText() + " is in inventory");
                } else {
                    outputText.setText(itemName.getText() + " is not in inventory");
                    actionComplete = false;
                }
                break;
            default:
                System.out.println("invalid input");
        }
        if (actionComplete) {
            itemName.setVisible(false);
            itemName.clear();
            if (Arrays.asList(new String[]{"addItem", "updateQuantity"}).contains(submitAction)) {
                itemQuantity.setVisible(false);
                itemQuantity.clear();
            }
            if (Objects.equals(submitAction, "addItem")) {
                itemUnitPrice.setVisible(false);
                itemUnitPrice.clear();
            }
            submit.setVisible(false);
        }
    }
}