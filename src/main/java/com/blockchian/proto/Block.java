package com.blockchian.proto;

import java.util.ArrayList;
import java.util.List;

public class Block {

    private List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction tx) {
        transactions.add(tx);
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }
}
