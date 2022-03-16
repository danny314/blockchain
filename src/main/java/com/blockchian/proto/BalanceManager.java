package com.blockchian.proto;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BalanceManager {

    private Map<String, BigInteger[]> balanceMap;
    private final int CONFIRMATION_THRESHOLD = 3;

    public BalanceManager(Map<String, BigInteger[]> balanceMap) {
        this.balanceMap = balanceMap;
    }

    public void receiveBlock(Block block) {
        for (Transaction tx : block.getTransactions()) {
            if (tx.getConfirmations() < CONFIRMATION_THRESHOLD) {
                //This transaction is not confirmed
                continue;
            }
            processTransaction(tx, true);
        }
    }

    public void receiveTransaction(Transaction tx) {
        if (tx.getConfirmations() >= CONFIRMATION_THRESHOLD) {
            //This transaction has already been confirmed
            System.out.println("WARN: receiveTransaction called with confirmed transaction");
            return;
        }
        processTransaction(tx, false);
    }

    private void processTransaction(Transaction tx, boolean isFromBlock) {
        List<UTXO> inUTXOs = tx.getInUTXOs();
        List<UTXO> outUTXOs = tx.getOutUTXOs();

        int balanceIdx = isFromBlock ? 0 : 1;

        for (UTXO inUTXO: inUTXOs) {
            BigInteger[] balances = balanceMap.get(inUTXO.getAddress());
            if (balances != null) {
                balances[balanceIdx] = balances[balanceIdx].subtract(inUTXO.getAmount());
            }
        }
        for (UTXO outUTXO: outUTXOs) {
            BigInteger[] balances = balanceMap.get(outUTXO.getAddress());
            if (balances != null) {
                balances[balanceIdx] = balances[balanceIdx].add(outUTXO.getAmount());
            }
        }
    }


    public BigInteger[] getBalance(String address) {
        return balanceMap.get(address);
    }
}
