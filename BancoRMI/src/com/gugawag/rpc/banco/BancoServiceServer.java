package com.gugawag.rpc.banco;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BancoServiceServer extends UnicastRemoteObject implements BancoServiceIF {

    private Map<String, Double> saldoContas;
    private List<Conta> contas = new ArrayList<>();

    public BancoServiceServer() throws RemoteException {
        saldoContas = new HashMap<>();
        saldoContas.put("1", 100.0);
        saldoContas.put("2", 156.0);
        saldoContas.put("3", 950.0);
        Conta c1 = new Conta("1", saldoContas.get("1"));
        contas.add(c1);
        Conta c2 = new Conta("2", saldoContas.get("2"));
        contas.add(c2);
        Conta c3 = new Conta("3", saldoContas.get("3"));
        contas.add(c3);

    }

    @Override
    public double saldo(String conta) throws RemoteException {
        return saldoContas.get(conta);
    }

    @Override
    public int quantidadeContas() throws RemoteException {
        return contas.size();
    }

    @Override
    public void addConta(Conta conta) throws RemoteException {
        this.contas.add(conta);
        this.saldoContas.put(conta.getNumero(), conta.getSaldo());
    }

    @Override
    public Conta findConta(String numero) throws RemoteException {
        for(Conta c : contas){
            if(c.getNumero().equals(numero)){
                return c;
            }
        }
        return null;
    }

    @Override
    public boolean delConta(String numero) throws RemoteException {
        for(Conta c : contas){
            if(c.getNumero().equals(numero)){
                contas.remove(c);
                saldoContas.remove(c.getNumero());
                return true;
            }
        }
        return false;
    }




}
