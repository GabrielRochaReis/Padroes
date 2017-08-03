/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtros;

import Model.Pagamento;
import java.util.Date;

/**
 *
 * @author Gabriel Rocha
 */
public class FiltroRelatorioPagamento {
    
    private Date mes;
    private Date dataPagamento;
    private Date dataDeposito;
    private String apartamento;
    private String inquilino;
    private String proprietario;
    private Double valorDeposito;

    public FiltroRelatorioPagamento(Pagamento pagamento, String apartamento, String inquilino, String proprietario) {
        this.mes = pagamento.getMes();
        this.dataPagamento = pagamento.getDataPagamento();
        this.dataDeposito = pagamento.getDataDeposito();
        this.apartamento = apartamento;
        this.inquilino = inquilino;
        this.proprietario = proprietario;
        this.valorDeposito = pagamento.getValorDeposito();
    }
    
    

    public Date getMes() {
        return mes;
    }

    public void setMes(Date mes) {
        this.mes = mes;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Date getDataDeposito() {
        return dataDeposito;
    }

    public void setDataDeposito(Date dataDeposito) {
        this.dataDeposito = dataDeposito;
    }

    public String getApartamento() {
        return apartamento;
    }

    public void setApartamento(String apartamento) {
        this.apartamento = apartamento;
    }

    public String getInquilino() {
        return inquilino;
    }

    public void setInquilino(String inquilino) {
        this.inquilino = inquilino;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public Double getValorDeposito() {
        return valorDeposito;
    }

    public void setValorDeposito(Double valorDeposito) {
        this.valorDeposito = valorDeposito;
    }
    
    
}
