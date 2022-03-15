package com.blockchian.proto;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BalanceManager {

    private Map<String, BigInteger[]> balanceMap;

    public BalanceManager(Map<String, BigInteger[]> balanceMap) {
        this.balanceMap = balanceMap;
    }

    public void receiveBlock(Block block) {
        for (Transaction tx : block.getTransactions()) {
            List<UTXO> inUTXOs = tx.getInUTXOs();
            List<UTXO> outUTXOs = tx.getOutUTXOs();

            for (UTXO inUTXO: inUTXOs) {
                BigInteger[] balances = balanceMap.get(inUTXO.getAddress());
                if (balances != null) {
                    System.out.println("================================== Found in balance");
                    balances[0] = balances[0].subtract(inUTXO.getAmount());
                    //balances[1] = balances[1].add(inUTXO.getAmount());
                    balanceMap.put(inUTXO.getAddress(), balances);
                }
                System.out.println("balanceMap = " + balanceMap);
            }
            for (UTXO outUTXO: outUTXOs) {
                BigInteger[] balances = balanceMap.get(outUTXO.getAddress());
                if (balances != null) {
                    System.out.println("================================== Found out balance");
                    balances[0] = balances[0].add(outUTXO.getAmount());
                    //balances[1] = balances[1].subtract(outUTXO.getAmount());
                    balanceMap.put(outUTXO.getAddress(), balances);
                }

            }
        }
    }

    public void receiveTransaction(Transaction tx) {
        List<UTXO> inUTXOs = tx.getInUTXOs();
        List<UTXO> outUTXOs = tx.getInUTXOs();

        for (UTXO inUTXO: inUTXOs) {
            BigInteger[] balances = balanceMap.get(inUTXO.getAddress());
            if (balances != null) {
                balances[1].subtract(inUTXO.getAmount());
            }
        }
        for (UTXO outUTXO: outUTXOs) {
            BigInteger[] balances = balanceMap.get(outUTXO.getAddress());
            if (balances != null) {
                balances[1].add(outUTXO.getAmount());
            }
        }
    }

    private BigInteger[] getBalance(String address) {
        return balanceMap.get(address);
    }
}
