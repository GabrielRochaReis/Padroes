/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Gabriel Rocha
 */
public class Pagamento extends Entidade {
    
    private Date mes;
    private  Integer apartemento;
    private Date dataPagamento;
    private byte[] comprovantePagamento;
    private String nomeComprovantePagamento;
    private Date  dataDeposito;
    private byte[] comprovanteDeposito;
    private String nomeComprovanteDeposito;
    private double valorDeposito;

    public Date getMes() {
        return mes;
    }

    public void setMes(Date mes) {
        this.mes = mes;
    }

    public Integer getApartemento() {
        return apartemento;
    }

    public void setApartemento(Integer apartemento) {
        this.apartemento = apartemento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public byte[] getComprovantePagamento() {
        return comprovantePagamento;
    }

    public void setComprovantePagamento(byte[] comprovantePagamento) {
        this.comprovantePagamento = comprovantePagamento;
    }

    public Date getDataDeposito() {
        return dataDeposito;
    }

    public void setDataDeposito(Date dataDeposito) {
        this.dataDeposito = dataDeposito;
    }

    public byte[] getComprovanteDeposito() {
        return comprovanteDeposito;
    }

    public void setComprovanteDeposito(byte[] comprovanteDeposito) {
        this.comprovanteDeposito = comprovanteDeposito;
    }

    public Double getValorDeposito() {
        return valorDeposito;
    }

    public String getNomeComprovantePagamento() {
        return nomeComprovantePagamento;
    }

    public void setNomeComprovantePagamento(String nomeComprovantePagamento) {
        this.nomeComprovantePagamento = nomeComprovantePagamento;
    }

    public String getNomeComprovanteDeposito() {
        return nomeComprovanteDeposito;
    }

    public void setNomeComprovanteDeposito(String nomeComprovanteDeposito) {
        this.nomeComprovanteDeposito = nomeComprovanteDeposito;
    }

    public void setValorDeposito(Double valorDeposito) {
        if(valorDeposito==null){
            this.valorDeposito =0;
        } else{
            this.valorDeposito = valorDeposito;
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.mes);
        hash = 97 * hash + Objects.hashCode(this.apartemento);
        hash = 97 * hash + Objects.hashCode(this.dataPagamento);
        hash = 97 * hash + Arrays.hashCode(this.comprovantePagamento);
        hash = 97 * hash + Objects.hashCode(this.dataDeposito);
        hash = 97 * hash + Arrays.hashCode(this.comprovanteDeposito);
        hash = 97 * hash + Objects.hashCode(this.valorDeposito);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pagamento other = (Pagamento) obj;
        if (!Objects.equals(this.mes, other.mes)) {
            return false;
        }
        if (!Objects.equals(this.apartemento, other.apartemento)) {
            return false;
        }
        if (!Objects.equals(this.dataPagamento, other.dataPagamento)) {
            return false;
        }
        if (!Arrays.equals(this.comprovantePagamento, other.comprovantePagamento)) {
            return false;
        }
        if (!Objects.equals(this.dataDeposito, other.dataDeposito)) {
            return false;
        }
        if (!Arrays.equals(this.comprovanteDeposito, other.comprovanteDeposito)) {
            return false;
        }
        if (!Objects.equals(this.valorDeposito, other.valorDeposito)) {
            return false;
        }
        return true;
    }
    
}
