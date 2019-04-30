package com.gugawag.rpc.banco;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BancoServiceIF extends Remote {

    double saldo(String conta) throws RemoteException;
    int quantidadeContas() throws RemoteException;
    void addConta(Conta conta) throws RemoteException;
    Conta findConta(String numero) throws RemoteException;
    boolean delConta(String numero) throws RemoteException;
}
