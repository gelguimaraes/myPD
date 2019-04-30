package com.gugawag.rpc.banco;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class AppClienteBanco {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        // procura o serviço no RMI Registry local. Perceba que o cliente não connhece a implementação do servidor,
        // apenas a interface
        Registry registry = LocateRegistry.getRegistry("192.168.15.11", 1099);
        BancoServiceIF banco = (BancoServiceIF) registry.lookup("BancoService");


        menu();
        Scanner entrada = new Scanner(System.in);
        int opcao = entrada.nextInt();

        while(opcao != 9) {
            switch (opcao) {
                case 1: {
                    System.out.println("Digite o número da conta:");
                    String conta = entrada.next();
                    //chamada ao método remoto, como se fosse executar localmente
                    System.out.println("Saldo: " + banco.saldo(conta));
                    break;
                }
                case 2: {
                    //chamada ao método remoto, como se fosse executar localmente
                    System.out.println("Número de contas; " + banco.quantidadeContas());
                    break;
                }
                case 3: {
                    System.out.println("Digite o número da conta para adicionar: ");
                    String numero = entrada.next();
                    Conta conta = new Conta(numero, 0.0);
                    banco.addConta(conta);
                    System.out.println("Conta adicionada");
                    break;
                }
                case 4: {
                    System.out.println("Digite o número da conta para localizar: ");
                    String numero = entrada.next();

                    Conta c = banco.findConta(numero);
                    if(c != null){
                        System.out.println("Conta encontrada: " + c.getNumero() + " com saldo: " + c.getSaldo());
                    }
                    else
                        System.out.println("Conta não localizada");
                    break;
                }
                case 5: {
                    System.out.println("Digite o número da conta para remover: ");
                    String numero = entrada.next();
                    if(banco.delConta(numero))
                        System.out.println("Conta removida");
                    else
                        System.out.println("Conta não localizada");
                    break;
                }
            }
            menu();
            opcao = entrada.nextInt();
        }
    }

    public static void menu() {
        System.out.println("\n=== BANCO RMI (ou FMI?!) ===");
        System.out.println("1 - Saldo da conta");
        System.out.println("2 - Quantidade de contas");
        System.out.println("3 - Adicionar conta");
        System.out.println("4 - Localizar conta");
        System.out.println("5 - Remover conta");
        System.out.println("9 - Sair");
    }

}
