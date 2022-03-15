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
            List<UTXO> inUTXOs = tx.getInUTXOs();
            List<UTXO> outUTXOs = tx.getOutUTXOs();

            for (UTXO inUTXO: inUTXOs) {
                BigInteger[] balances = balanceMap.get(inUTXO.getAddress());
                if (balances != null) {
                    System.out.println("================================== Found in balance");
                    balances[0] = balances[0].subtract(inUTXO.getAmount());
                }
                System.out.println("balanceMap = " + balanceMap);
            }
            for (UTXO outUTXO: outUTXOs) {
                BigInteger[] balances = balanceMap.get(outUTXO.getAddress());
                if (balances != null) {
                    System.out.println("================================== Found out balance");
                    balances[0] = balances[0].add(outUTXO.getAmount());
                }
            }
        }
    }

    public void receiveTransaction(Transaction tx) {
        if (tx.getConfirmations() >= CONFIRMATION_THRESHOLD) {
            //This transaction has already been confirmed
            System.out.println("WARN: receiveTransaction called with confirmed transaction");
            return;
        }
        List<UTXO> inUTXOs = tx.getInUTXOs();
        List<UTXO> outUTXOs = tx.getOutUTXOs();

        for (UTXO inUTXO: inUTXOs) {
            BigInteger[] balances = balanceMap.get(inUTXO.getAddress());
            if (balances != null) {
                balances[1] = balances[1].subtract(inUTXO.getAmount());
            }
        }
        for (UTXO outUTXO: outUTXOs) {
            BigInteger[] balances = balanceMap.get(outUTXO.getAddress());
            if (balances != null) {
                balances[1] = balances[1].add(outUTXO.getAmount());
            }
        }
    }

    public BigInteger[] getBalance(String address) {
        return balanceMap.get(address);
    }
}
