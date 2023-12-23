package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;


public class transactionReportController implements Initializable {

    public TableColumn<Item, Integer> quantitySold;
    public TableColumn<Item, Integer> amount;
    public TableColumn<Item, Integer> stockRemaining;
    public TableColumn<Item, String> transactionType;
    @FXML
    private TableColumn<Item, String> itemDescription;

    @FXML
    private TableColumn<Item, String> itemId;

    @FXML
    private TableView<Item> transactionTable;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // table tests
        itemId.setCellValueFactory(new PropertyValueFactory<Item, String>("itemId"));
        itemDescription.setCellValueFactory(new PropertyValueFactory<Item, String>("itemDescription"));
        quantitySold.setCellValueFactory(new PropertyValueFactory<Item, Integer>("qtySold"));
        amount.setCellValueFactory(new PropertyValueFactory<Item, Integer>("amount"));
        stockRemaining.setCellValueFactory(new PropertyValueFactory<Item, Integer>("stockRemaining"));
        transactionType.setCellValueFactory(new PropertyValueFactory<Item, String>("transactionType"));
        ObservableList<Item> list = FXCollections.observableArrayList(
                new Item("00001", "Spark Plugs",3,4,7,"Updating Quantity")
        );
        transactionTable.setItems(list);
    }
}


