package com.example.demo;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * The file that handles all interactions with the primary FXML file (gui.fxml)
 * @author Robert Couldridge
 * @version 2.0
 * @since 2.0
 */
public class controller{

    // initialising all elements in 'gui.fxml'
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

    // initialising an instance of the 'storeActions' class

    storeActions storeInstance = new storeActions();

    // initialising an instance of the 'userInput' class
    userInput takeUserInput = new userInput();

    // defining the path to 'items.txt'
    String itemsFile = "src/main/java/com/example/demo/items.txt";

    // initialising the 'submitAction variable'
    String submitAction = "";

    // initialising the 'quantity' variable
    int quantity;

    /**
     * This method clears and hides all text entry fields, as well as resetting the 'submitAction'
     * variable and ensuring all buttons are 'unselected'
     */
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

    /**
     * This method clears the gui before displaying the required text entry boxes for
     * adding an item, as well as 'selecting' the 'addItem' button and setting the
     * 'submitAction' variable to 'addItem'
     */
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

    /**
     * This method clears the gui before displaying the required text entry boxes for updating
     * the quantity of an item, as well as 'selecting' the 'updateQuantity' button and setting the
     * 'submitAction' variable to 'updateQuantity'
     */
    @FXML
    protected void updateQuantity(){
        clearGui();
        itemName.setVisible(true);
        itemQuantity.setVisible(true);
        submit.setVisible(true);
        updateQuantity.setStyle("-fx-border-color: #b79224;");
        submitAction = "updateQuantity";
    }

    /**
     * This method clears the gui before displaying the required text entry boxes for
     * removing an item, as well as 'selecting' the 'removeItem' button and setting the
     * 'submitAction' variable to 'removeItem'
     */
    @FXML
    protected void removeAnItem(){
        clearGui();
        itemName.setVisible(true);
        submit.setVisible(true);
        removeItem.setStyle("-fx-border-color: #b79224;");
        submitAction = "removeItem";
    }

    /**
     * This method clears the gui before displaying the required text entry boxes for
     * searching for an item, as well as 'selecting' the 'searchItem' button and setting the
     * 'submitAction' variable to 'searchItem'
     */
    @FXML
    protected void searchForItem(){
        clearGui();
        itemName.setVisible(true);
        submit.setVisible(true);
        searchItem.setStyle("-fx-border-color: #b79224;");
        submitAction = "searchItem";
    }

    /**
     * This method clears the gui before 'selecting' the 'displayTransaction' button
     * and opening 'transactionReport.fxml'
     */
    @FXML
    protected void displayTransactionReport(){
        clearGui();
        displayTransaction.setStyle("-fx-border-color: #b79224;");

        // try opening 'transactionReport.fxml'
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("transactionReport.fxml"));
            Parent root = fxmlLoader.load();
            Stage transactionReport = new Stage();

            // disable the abilty to resize the window
            transactionReport.setResizable(false);

            // set the title of the window to 'Transaction Report'
            transactionReport.setTitle("Transaction Report");
            transactionReport.setScene(new Scene(root));

            // show the window
            transactionReport.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method clears the gui before 'selecting' the 'exit' button and
     * clearing the transaction report before exiting the program
     */
    @FXML
    protected void exit(){
        clearGui();
        exit.setStyle("-fx-border-color: #b79224;");
        storeInstance.clearTransactionReport();
        Platform.exit();
    }

    /**
     * This method checks the 'submitAction' variable and depending on the value
     * carries out the selected task
     */
    @FXML
    protected void submit(){

        // initialises the 'actionComplete' variable to allow the user to re-enter values if not successful
        boolean actionComplete = true;

        // clears the 'displayedText' variable
        String displayedText = "";

        switch (submitAction){

            // if the user selected 'addItem'
            case "addItem":

                // checks the user has entered a value for all text fields and fails if not
                if (Objects.equals(itemQuantity.getText(), "") ||
                        Objects.equals(itemName.getText(), "") ||
                                Objects.equals(itemUnitPrice.getText(), "")){
                    outputText.setText("enter a value for all fields");
                    actionComplete = false;
                    break;}
                quantity = takeUserInput.integerConversion(itemQuantity.getText());

                // checks if the user has entered a value higher than 0 for quantity and fails if not
                if (quantity <= 0){
                    displayedText += "invalid value for quantity ";
                    outputText.setText(displayedText);
                    actionComplete = false;
                }
                float unitPrice = takeUserInput.floatConversion(itemUnitPrice.getText());

                // checks if the user has entered a value higher than 0 for unit price and fails if not
                if (unitPrice <= 0){
                    displayedText += "invalid value for unit price ";
                    outputText.setText(displayedText);
                    actionComplete = false;
                }
                if (!actionComplete){break;}

                // runs the 'addItem' method and if item already in inventory lets user know
                if (storeInstance.addItem(itemsFile, itemName.getText().toLowerCase(), quantity, unitPrice) == 1){
                    outputText.setText("item already in inventory");
                    actionComplete = false;
                }
                if (actionComplete) {
                    outputText.setText(quantity + " " + itemName.getText().toLowerCase() + "'s added to inventory at £" + unitPrice + " each");
                }
                break;

            // if the user selected 'updateQuantity'
            case "updateQuantity":

                // checks the user has entered a value for all text fields and fails if not
                if (Objects.equals(itemQuantity.getText(), "") ||
                        Objects.equals(itemName.getText(), "")){
                    outputText.setText("enter a value for all fields");
                    actionComplete = false;
                    break;}
                quantity = takeUserInput.integerConversion(itemQuantity.getText());

                // checks if the user has entered a value higher than 0 for quantity and fails if not
                if (quantity <= 0){
                    displayedText += "invalid value for quantity ";
                    outputText.setText(displayedText);
                    actionComplete = false;
                }

                // runs the 'updateQuantity' method and if item not already in inventory lets user know
                if (storeInstance.updateQuantity(itemsFile, itemName.getText().toLowerCase(), quantity) != 0){
                    if (storeInstance.updateQuantity(itemsFile, itemName.getText().toLowerCase(), quantity) == 1){
                    displayedText += "item not in inventory ";}
                    outputText.setText(displayedText);
                    actionComplete = false;
                }
                if (actionComplete)
                    outputText.setText("inventory now contains " + quantity + " " + itemName.getText().toLowerCase() + "'s");
                break;

            // if the user selected 'removeItem'
            case "removeItem":

                // checks the user has entered a value for all text fields and fails if not
                if (Objects.equals(itemName.getText(), "")){
                    outputText.setText("enter a value for all fields");
                    actionComplete = false;
                    break;}

                // runs the 'removeItem' method and if item not already in inventory lets user know
                if (storeInstance.removeItem(itemsFile, itemName.getText().toLowerCase()) == 1) {
                    outputText.setText(itemName.getText().toLowerCase() + " not found in inventory");
                    actionComplete = false;
                }
                else {outputText.setText("Inventory no longer contains " + itemName.getText().toLowerCase());}
                break;

            // if the user selected 'searchItem'
            case "searchItem":

                // checks the user has entered a value for all text fields and fails if not
                if (Objects.equals(itemName.getText(), "")){
                    outputText.setText("enter a value for all fields");
                    actionComplete = false;
                    break;}

                // runs the 'isItemInInventory' method and lets user know if the item is present
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

        // if user successfully completed their action clear gui
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