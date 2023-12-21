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
    public Button addItem;
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

    protected void clearGui(){
        itemName.setVisible(false);
        itemUnitPrice.setVisible(false);
        itemQuantity.setVisible(false);
        submit.setVisible(false);
        outputText.setText("");
        addItem.setStyle("-fx-border-color: #bfbfbf; -fx-background-color: #666769;");
        updateQuantity.setStyle("-fx-border-color: #bfbfbf; -fx-background-color: #666769;");
        removeItem.setStyle("-fx-border-color: #bfbfbf; -fx-background-color: #666769;");
        searchItem.setStyle("-fx-border-color: #bfbfbf; -fx-background-color: #666769;");
        displayTransaction.setStyle("-fx-border-color: #bfbfbf; -fx-background-color: #666769;");
        itemName.clear();
        itemQuantity.clear();
        itemUnitPrice.clear();
    }

    @FXML
    private Label welcomeText;

    // option 1 add an item
    @FXML
    protected void addAnItem(ActionEvent event) {
        clearGui();
        itemName.setVisible(true);
        itemQuantity.setVisible(true);
        itemUnitPrice.setVisible(true);
        submit.setVisible(true);
        addItem.setStyle("-fx-border-color: #b79224;");
        submitAction = "addItem";
    }

    // option 2 update quantity of an item
    @FXML
    protected void updateQuantity(){
        clearGui();
        itemName.setVisible(true);
        itemQuantity.setVisible(true);
        submit.setVisible(true);
        updateQuantity.setStyle("-fx-border-color: #b79224;");
        submitAction = "updateQuantity";
    }

    // option 3 remove an item
    @FXML
    protected void removeAnItem(){
        clearGui();
        itemName.setVisible(true);
        submit.setVisible(true);
        removeItem.setStyle("-fx-border-color: #b79224;");
        submitAction = "removeItem";
    }

    // option 4 search for an item
    @FXML
    protected void searchForItem(){
        clearGui();
        itemName.setVisible(true);
        submit.setVisible(true);
        searchItem.setStyle("-fx-border-color: #b79224;");
        submitAction = "searchItem";
    }

    // option 5 display transaction report
    @FXML
    protected void displayTransactionReport(){
        clearGui();
        displayTransaction.setStyle("-fx-border-color: #b79224;");
        storeInstance.displayTransactionReport();
    }

    // option 6 exit
    @FXML
    protected void exit(){
        clearGui();
        exit.setStyle("-fx-border-color: #b79224;");
        storeInstance.clearTransactionReport();
        Platform.exit();
    }

    @FXML
    protected void submit(){
        boolean actionComplete = true;
        String displayedText = "";
        switch (submitAction){
            case "addItem":
                if (Objects.equals(itemQuantity.getText(), "") ||
                        Objects.equals(itemName.getText(), "") ||
                                Objects.equals(itemUnitPrice.getText(), "")){
                    outputText.setText("enter a value for all fields");
                    actionComplete = false;
                    break;}
                quantity = takeUserInput.integerConversion(itemQuantity.getText());
                if (quantity <= 0){
                    displayedText += "invalid value for quantity ";
                    outputText.setText(displayedText);
                    actionComplete = false;
                }
                float unitPrice = takeUserInput.floatConversion(itemUnitPrice.getText());
                if (unitPrice <= 0){
                    displayedText += "invalid value for unit price ";
                    outputText.setText(displayedText);
                    actionComplete = false;
                }
                if (!actionComplete){break;}
                if (storeInstance.addItem(itemsFile, itemName.getText().toLowerCase(), quantity, unitPrice) == 1){
                    outputText.setText("item already in inventory");
                    actionComplete = false;
                }
                if (actionComplete) {
                    outputText.setText(quantity + " " + itemName.getText().toLowerCase() + "'s added to inventory at £" + unitPrice + " each");
                }
                break;
            case "updateQuantity":
                if (Objects.equals(itemQuantity.getText(), "") ||
                        Objects.equals(itemName.getText(), "")){
                    outputText.setText("enter a value for all fields");
                    actionComplete = false;
                    break;}
                quantity = takeUserInput.integerConversion(itemQuantity.getText());
                if (quantity <= 0){
                    displayedText += "invalid value for quantity ";
                    outputText.setText(displayedText);
                    actionComplete = false;
                }
                if (storeInstance.updateQuantity(itemsFile, itemName.getText().toLowerCase(), quantity) != 0){
                    if (storeInstance.updateQuantity(itemsFile, itemName.getText().toLowerCase(), quantity) == 1){
                    displayedText += "item not in inventory ";}
                    outputText.setText(displayedText);
                    actionComplete = false;
                }
                if (actionComplete)
                    outputText.setText("inventory now contains " + quantity + " " + itemName.getText().toLowerCase() + "'s");
                break;
            case "removeItem":
                if (Objects.equals(itemName.getText(), "")){
                    outputText.setText("enter a value for all fields");
                    actionComplete = false;
                    break;}
                if (storeInstance.removeItem(itemsFile, itemName.getText().toLowerCase()) == 1) {
                    outputText.setText(itemName.getText().toLowerCase() + " not found in inventory");
                    actionComplete = false;
                }
                else {outputText.setText("Inventory no longer contains " + itemName.getText().toLowerCase());}
                break;
            case "searchItem":
                if (Objects.equals(itemName.getText(), "")){
                    outputText.setText("enter a value for all fields");
                    actionComplete = false;
                    break;}
                if (storeInstance.isItemInInventory(itemName.getText().toLowerCase(), itemsFile, false)) {
                    outputText.setText(itemName.getText().toLowerCase() + " is in inventory");
                } else {
                    outputText.setText(itemName.getText().toLowerCase() + " is not in inventory");
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