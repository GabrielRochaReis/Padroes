/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtros;

import java.util.Date;

/**
 *
 * @author Gabriel Rocha
 */
public class FiltroPagamento {
    private Date mesInicial;
    private Date mesFinal;
    private Date depositoInicial;
    private Date depositoFinal;
    private Date pagamentoInicial;
    private Date pagamentoFinal;
    
    private Integer apartamento;

    public Date getMesInicial() {
        return mesInicial;
    }

    public void setMesInicial(Date mesInicial) {
        this.mesInicial = mesInicial;
    }

    public Date getMesFinal() {
        return mesFinal;
    }

    public void setMesFinal(Date mesFinal) {
        this.mesFinal = mesFinal;
    }

    public Date getDepositoInicial() {
        return depositoInicial;
    }

    public void setDepositoInicial(Date depositoInicial) {
        this.depositoInicial = depositoInicial;
    }

    public Date getDepositoFinal() {
        return depositoFinal;
    }

    public void setDepositoFinal(Date depositoFinal) {
        this.depositoFinal = depositoFinal;
    }

    public Date getPagamentoInicial() {
        return pagamentoInicial;
    }

    public void setPagamentoInicial(Date pagamentoInicial) {
        this.pagamentoInicial = pagamentoInicial;
    }

    public Date getPagamentoFinal() {
        return pagamentoFinal;
    }

    public void setPagamentoFinal(Date pagamentoFinal) {
        this.pagamentoFinal = pagamentoFinal;
    }

    public Integer getApartamento() {
        return apartamento;
    }

    public void setApartamento(Integer apartamento) {
        this.apartamento = apartamento;
    }
}
