package br.com.upsk.banco.conta;

import br.com.upsk.banco.cliente.Cliente;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Conta {

    //Constantes para apoioar nas regras de negócio

    private final static BigDecimal VALOR_RENDIMENTO_POUPANCA_PF = new BigDecimal(0.01);
    private final static BigDecimal VALOR_RENDIMENTO_DEPOSITO_PF = new BigDecimal(0.015);
    private final static BigDecimal VALOR_RENDIMENTO_DEPOSITO_PJ = new BigDecimal(0.035);
    private final static BigDecimal VALOR_TAXA_SAQUE_PJ = new BigDecimal(0.005);

    //Atributos (apenas na classe)
    private Integer idConta;
    private String tipo;
    private BigDecimal saldoConta = new BigDecimal(0);

    //Variaveis de trabalho
    Cliente cliente;

    //Construtor herdado da classe object
    //public Conta(){
        //precisa de gest set publico
        // get =     variavel = pegando o conteudo (GET)
        // set =     variavel (SET) = indicando um valor
    //}

    //Construtor especializado da classe
    public Conta(Integer idConta, String tipo){
        //get set pode ser private
        this.idConta = idConta;
        this.tipo = tipo;

    }

    //get e set => para aplicar o encapsulamento (evitando que as variáveis internas
    // sejam manipulados diretmente ou externamente)
    public String getTipo() {
        return tipo;
    }

    private void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public Integer getIdConta() {
        return idConta;
    }

    private void setIdConta(Integer idConta) {
        this.idConta = idConta;
    }

    public BigDecimal getSaldoConta() {
        return saldoConta;
    }

    private void setSaldoConta(BigDecimal saldoConta) {
        this.saldoConta = saldoConta;
    }

    private void imprimirSaldo () {
        System.out.println("---------------------------------------------------------------");
        System.out.println("SALDO ATUALIZADO: R$ " + getSaldoConta());
        System.out.println("---------------------------------------------------------------");
    }

    public void efetuarDeposito(Integer idConta, BigDecimal valorDeposito, String TipoConta){
        //pessoa física rende 1% poupança e 1,5% conta investimento
        //pessoa jurídica rende 3,5% conta investimento

        //Consultar a conta ok
        //verificar o tipo OK
        //verificar o tipo cliente

        if (getTipo().equals("PF")){
            switch (TipoConta){
                case "CI":
                    valorDeposito.multiply(VALOR_RENDIMENTO_DEPOSITO_PF).add(valorDeposito);
                    break;
                case  "CP":
                    valorDeposito.multiply(VALOR_RENDIMENTO_POUPANCA_PF).add(valorDeposito);
                    break;
                default:
                    valorDeposito.multiply(BigDecimal.ZERO).add(valorDeposito);
            }
        }
        else{
            switch (TipoConta){
                case "CI":
                    valorDeposito.multiply(VALOR_RENDIMENTO_DEPOSITO_PJ).add(valorDeposito);
                    break;
                case  "CP":
                    System.out.println("Pessoa juridica não pode operar CP");
                    break;
                default:
                    valorDeposito.multiply(BigDecimal.ZERO).add(valorDeposito);
            }
        }
        //aplicar rendimento de acordo tipo conta e tipo cliente
        //atualizar saldo
        setSaldoConta(valorDeposito);

        imprimirSaldo();

    }

    public void efetuarSaque(Integer idConta, BigDecimal valorSaque, String TipoCliente){
        //pessoa juridica paga 0,5%
        BigDecimal saldo = getSaldoConta();
        if (TipoCliente.equals("PF")) {
            saldo = saldo.subtract(valorSaque).setScale(2, RoundingMode.HALF_EVEN);
            setSaldoConta(saldo);
            }
        else if (TipoCliente.equals("PJ"))
        {
            BigDecimal saque = valorSaque.multiply(VALOR_TAXA_SAQUE_PJ).add(valorSaque);
            saldo = saldo.subtract(saque).setScale(2, RoundingMode.HALF_EVEN);
            setSaldoConta(saldo);
        }
        System.out.println("Saque realizado com SUCESSO!");
        imprimirSaldo();

    }

    //TODO: Andreia
    public void EfetuarTransferencia(BigDecimal valorTransferencia, Integer idContaOrigem, Integer idContaDestino, String tipoCliente){
        //pessoa juridica paga 0,5% para transferir outra titularidade


    }

    public BigDecimal ConsultarSaldo(String idConta){
        return getSaldoConta();
    }

    public void abrirConta(String tipo){
        //conta poupança apenas para pessoa física
        //TODO Arraylist com abrirConta
    }

    //TODO: IZA Investir/resgate

}
