package com.example.demo;

/**
 * An object used to allow the tableview on 'transactions.fxml' to ingest
 * information from 'transactions.txt'
 * @author Robert Couldridge
 * @version 2.0
 * @since 2.0
 */
public class Item {

    // initialising variables
    private final String itemId;
    private final String itemDescription;

    private final String qtySold;

    private final String amount;

    private final String stockRemaining;

    private final String transactionType;

    // initializers

    public Item(String itemId, String itemDescription, String qtySold, String amount, String stockRemaining, String transactionType) {
        this.itemId = itemId;
        this.itemDescription = itemDescription;
        this.qtySold = qtySold;
        this.amount = amount;
        this.stockRemaining = stockRemaining;
        this.transactionType = transactionType;
    }

    // getters
    public String getItemId() {
        return itemId;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getQtySold() {
        return qtySold;
    }

    public String getAmount() {
        return amount;
    }

    public String getStockRemaining() {
        return stockRemaining;
    }

    public String getTransactionType() {
        return transactionType;
    }
}
