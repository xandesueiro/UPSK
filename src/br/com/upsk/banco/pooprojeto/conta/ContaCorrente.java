package br.com.upsk.banco.pooprojeto.conta;

import br.com.upsk.banco.pooprojeto.cliente.Cliente;
import br.com.upsk.banco.pooprojeto.cliente.ClientePJ;
import br.com.upsk.banco.pooprojeto.cliente.TipoContas;

import java.math.BigDecimal;

public class ContaCorrente extends Conta {

    public ContaCorrente(){
        super();
        this.setLabelConta(TipoContas.CONTA_CORRENTE.toString());
    }

    @Override
    public void sacar(Cliente cliente, BigDecimal valorSaque)  throws Exception{
        if (   this.consultarSaldo().doubleValue() <= 0
                || this.consultarSaldo().doubleValue() < valorSaque.doubleValue() ){
            throw new Exception("INFO: Nao ha saldo suficiente na " + TipoContas.CONTA_CORRENTE);
        }

        double taxa = pegarTaxa(cliente);

        double novoSaldo = this.consultarSaldo().doubleValue();
        double imposto = valorSaque.doubleValue() * taxa;
        novoSaldo -= valorSaque.doubleValue() - imposto;

        this.atualizarSaldo(new BigDecimal(novoSaldo));
    }

    @Override
    public void transferir(Cliente cliente, BigDecimal valorTransferencia)  throws Exception{
        if (   this.consultarSaldo().doubleValue() <= 0
                || this.consultarSaldo().doubleValue() < valorTransferencia.doubleValue() ){
            throw new Exception("INFO: Nao ha saldo suficiente para transferir o valor desejado" );
        }

        double taxa = pegarTaxa(cliente);

        double novoSaldo = this.consultarSaldo().doubleValue();
        double imposto = valorTransferencia.doubleValue() * taxa;
        novoSaldo -= valorTransferencia.doubleValue() - imposto;

        this.atualizarSaldo(new BigDecimal(novoSaldo));
    }


    @Override
    public void depositar(Cliente cliente, BigDecimal valorDeposito)  throws Exception{
        if ( valorDeposito!= null && valorDeposito.doubleValue() <= 0 ){
            throw new Exception("INFO: Valor de deposito invalido" );
        }

        double novoSaldo =  this.consultarSaldo().doubleValue() + valorDeposito.doubleValue();
        this.atualizarSaldo(new BigDecimal(novoSaldo));
    }

    @Override
    public void investir(Cliente cliente, BigDecimal valorInvestimento)  throws Exception{
        if ( valorInvestimento != null && valorInvestimento.doubleValue() <= 0 ){
            throw new Exception("INFO: Valor invalido para investimento na " + TipoContas.CONTA_CORRENTE );
        }
        double novoSaldo = this.consultarSaldo().doubleValue() + valorInvestimento.doubleValue();
        this.atualizarSaldo(new BigDecimal(novoSaldo));
    }

    private double pegarTaxa(Cliente cliente){
        double taxa = 0.00;
        if (  cliente != null && cliente instanceof ClientePJ){
            taxa = - ((ClientePJ) cliente).TAXA_MOVIMENTACAO.doubleValue();
        }
        return taxa;
    }

    @Override
    protected void setIdConta(Integer idConta) {
        super.setIdConta(idConta);
    }

    @Override
    protected void setSaldo(BigDecimal saldo) {
        super.setSaldo(saldo);
    }

    @Override
    public Integer getIdConta() {
        return super.getIdConta();
    }

    @Override
    public String getLabelConta() {
        return super.getLabelConta();
    }

    @Override
    protected void setLabelConta(String labelConta) {
        super.setLabelConta(labelConta);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Conta{");
        sb.append("idConta=").append(getIdConta());
        sb.append(", Tipo Conta= ").append(TipoContas.CONTA_CORRENTE);
        sb.append(", saldo=R$").append(consultarSaldo());
        sb.append('}');
        return sb.toString();
    }

}
