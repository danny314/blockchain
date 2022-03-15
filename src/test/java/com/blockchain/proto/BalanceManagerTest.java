package com.blockchain.proto;

import com.blockchian.proto.BalanceManager;
import com.blockchian.proto.Block;
import com.blockchian.proto.Transaction;
import com.blockchian.proto.UTXO;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BalanceManagerTest {

    @Test
    public void testConfirmedBalance() {
        System.out.println("Running test 1");

        Map<String, BigInteger[]> balanceMap = new HashMap<>();
        balanceMap.put("abc", new BigInteger[] { new BigInteger("10"), new BigInteger("20") } );

        Block block = new Block();
        Transaction tx = getTransaction1();
        System.out.println("Using tx\n" + tx);

        block.addTransaction(tx);

        BalanceManager balanceManager = new BalanceManager(balanceMap);
        balanceManager.receiveBlock(block);

        assertEquals(3, balanceMap.get("abc")[0].intValue());
    }

    private Transaction getTransaction1() {
        String[] inAddresses = new String[] {"abc"};
        String[] outAddresses = new String[] {"abc", "def"};

        int[] inAmts = new int[] {10};
        int[] outAmts = new int[] {3, 7};

        return buildTransaction(inAddresses, outAddresses, inAmts, outAmts);
    }

    private Transaction buildTransaction(String[] inAddresses, String[] outAddresses, int[] inputAmts, int[] outputAmts) {
        Transaction tx = new Transaction();

        for (int i = 0; i < inAddresses.length; i++) {
            UTXO in = new UTXO(inAddresses[i], new BigInteger(String.valueOf(inputAmts[i])));
            tx.addInUTXO(in);
        }
        for (int i = 0; i < outAddresses.length; i++) {
            UTXO out = new UTXO(outAddresses[i], new BigInteger(String.valueOf(outputAmts[i])));
            tx.addOutUTXO(out);
        }
        return tx;
    }
}
