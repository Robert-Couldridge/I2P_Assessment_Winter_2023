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


    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        System.out.print("hi");
    }

    // option 4 search for an item
    @FXML
    protected void searchForItem(){

    }

    // option 5 display transaction report
    @FXML
    protected void displayTransactionReport(){
        storeInstance.displayTransactionReport();
    }

    // option 6 exit
    @FXML
    protected void exit(){
        Platform.exit();
    }
}