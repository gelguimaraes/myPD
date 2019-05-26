package br.edu.ifpb.pd;

public class Banco {
    private String ag;
    private String conta;
    private String senha;
    private String numero_cartao;
    private Double valor;

    public Banco(String agencia, String conta, String senha, String cartao, Double valor) {
        this.ag = agencia;
        this.conta = conta;
        this.senha = senha;
        this.numero_cartao = cartao;
        this.valor = valor;
    }

    public String getAgencia() {
        return this.ag;
    }

    public void setAgencia(String agencia) {
        this.ag = agencia;
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
        return this.numero_cartao;
    }

    public void setCartao(String cartao) {
        this.numero_cartao = cartao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Banco{" +
                "ag='" + ag + '\'' +
                ", conta='" + conta + '\'' +
                ", senha='" + senha + '\'' +
                ", numero_cartao='" + numero_cartao + '\'' +
                ", valor=" + valor +
                '}';
    }
}