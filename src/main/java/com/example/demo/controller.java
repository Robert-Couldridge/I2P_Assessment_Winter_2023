package com.example.demo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    storeActions storeInstance = new storeActions();
    userInput takeUserInput = new userInput();
    String itemsFile = "src/main/java/com/example/demo/items.txt";

    String submitAction = "";

    @FXML
    private Label welcomeText;

    // option 1 add an item
    @FXML
    protected void addAnItem(ActionEvent event) {
        itemName.setVisible(true);
        submit.setVisible(true);
        submitAction = "addItem";
    }

    // option 2 update quantity of an item
    @FXML
    protected void updateQuantity(){
        itemName.setVisible(true);
        itemQuantity.setVisible(true);
        submit.setVisible(true);
        submitAction = "updateQuantity";
    }

    // option 3 remove an item
    @FXML
    protected void removeAnItem(){
        itemName.setVisible(true);
        submit.setVisible(true);
        submitAction = "removeItem";
    }

    // option 4 search for an item
    @FXML
    protected void searchForItem(){
        itemName.setVisible(true);
        submit.setVisible(true);
        submitAction = "searchItem";
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

    @FXML
    protected void submit(){
        switch (submitAction){
            case "addItem":
                storeInstance.addItem(itemsFile);
                break;
            case "updateQuantity":
                storeInstance.updateQuantity(itemsFile);
                break;
            case "removeItem":
                storeInstance.removeItem(itemsFile);
                break;
            case "searchItem":
                System.out.println("searching...");
            default:
                System.out.println("invalid input");
        }
        itemName.setVisible(false);
        if (Objects.equals(submitAction, "updateQuantity")){
            itemQuantity.setVisible(false);
        }
        submit.setVisible(false);
    }
}