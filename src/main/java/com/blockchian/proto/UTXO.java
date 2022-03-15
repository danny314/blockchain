package com.blockchian.proto;

import java.math.BigInteger;

public class UTXO {

    private String address;
    private BigInteger amount;

    public UTXO(String address, BigInteger amount) {
        this.address = address;
        this.amount = amount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "UTXO[" + this.address + "=" + this.amount + "]";
    }
}
