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

    public TableColumn<Item, String> quantitySold;
    public TableColumn<Item, String> amount;
    public TableColumn<Item, String> stockRemaining;
    public TableColumn<Item, String> transactionType;
    @FXML
    private TableColumn<Item, String> itemDescription;

    @FXML
    private TableColumn<Item, String> itemId;

    @FXML
    private TableView<Item> transactionTable;

    storeActions storeInstance = new storeActions();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // table tests
        itemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        itemDescription.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        quantitySold.setCellValueFactory(new PropertyValueFactory<>("qtySold"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        stockRemaining.setCellValueFactory(new PropertyValueFactory<>("stockRemaining"));
        transactionType.setCellValueFactory(new PropertyValueFactory<>("transactionType"));

        // get values from transaction report into table view
        String[][] transactionReport = storeInstance.returnTransactionReport();
        ObservableList<Item> list = FXCollections.observableArrayList();

        for (String[] row : transactionReport){
            list.add(new Item(row[0], row[1], row[2], row[3], row[4], row[5]));
        }

        transactionTable.setItems(list);
    }
}

