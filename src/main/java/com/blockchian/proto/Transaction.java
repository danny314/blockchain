package com.blockchian.proto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Transaction {

    private List<UTXO> inUTXOs = new ArrayList<>();
    private List<UTXO> outUTXOs = new ArrayList<>();

    public List<UTXO> getOutUTXOs() {
        return outUTXOs;
    }

    public List<UTXO> getInUTXOs() {
        return inUTXOs;
    }

    public void addInUTXO(UTXO utxo) {
        inUTXOs.add(utxo);
    }

    public void addOutUTXO(UTXO utxo) {
        outUTXOs.add(utxo);
    }

    @Override
    public String toString() {
        String str = "TX[input=";
        str += inUTXOs.stream().map(UTXO::toString).collect(Collectors.joining(","));
        str += "]\n   output=" + outUTXOs.stream().map(UTXO::toString).collect(Collectors.joining(","));
        str += "\n]";
        return str;
    }

}
