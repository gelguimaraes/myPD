package br.edu.ifpb.pd;

public class Banco {
    private String agencia;
    private String conta;
    private String senha;
    private String cartao;

    public Banco(String agencia, String conta, String senha, String cartao) {
        this.agencia = agencia;
        this.conta = conta;
        this.senha = senha;
        this.cartao = cartao;
    }

    public String getAgencia() {
        return this.agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return this.conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCartao() {
        return this.cartao;
    }

    public void setCartao(String cartao) {
        this.cartao = cartao;
    }

    public String toString() {
        return "Banco{agencia='" + this.agencia + '\'' + ", conta='" + this.conta + '\'' + ", senha='" + this.senha + '\'' + ", cartao='" + this.cartao + '\'' + '}';
    }
}