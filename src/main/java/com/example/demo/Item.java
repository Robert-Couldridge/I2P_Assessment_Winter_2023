package com.example.demo;

public class Item {

    private String itemId;
    private String itemDescription;

    private int qtySold;

    private int amount;

    private int stockRemaining;

    private String transactionType;

    public Item(String itemId, String itemDescription, int qtySold, int amount, int stockRemaining, String transactionType) {
        this.itemId = itemId;
        this.itemDescription = itemDescription;
        this.qtySold = qtySold;
        this.amount = amount;
        this.stockRemaining = stockRemaining;
        this.transactionType = transactionType;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public int getQtySold() {
        return qtySold;
    }

    public int getAmount() {
        return amount;
    }

    public int getStockRemaining() {
        return stockRemaining;
    }

    public String getTransactionType() {
        return transactionType;
    }
}
