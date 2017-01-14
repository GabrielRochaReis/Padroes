/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Gabriel Rocha
 */
public class Inquilino extends Entidade{

    private String nome;
    private String telefone;
    private String email;
    private String cpf;
    private String rg;
    private Integer dataBoleto;
    private Integer mesContrato;
    private Boolean ativo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Integer getDataBoleto() {
        return dataBoleto;
    }

    public void setDataBoleto(Integer dataBoleto) {
        this.dataBoleto = dataBoleto;
    }

    public Integer getMesContrato() {
        return mesContrato;
    }

    public void setMesContrato(Integer mesContrato) {
        this.mesContrato = mesContrato;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public enum Filds{
        id , nome, telefone, email, cpf, rg, data_boleto, mes_contrato, ativo;
    }

    @Override
    public int hashCode() {
        Integer hash = 7;
        hash = 53 * hash + Objects.hashCode(this.nome);
        hash = 53 * hash + Objects.hashCode(this.telefone);
        hash = 53 * hash + Objects.hashCode(this.email);
        hash = 53 * hash + Objects.hashCode(this.cpf);
        hash = 53 * hash + Objects.hashCode(this.rg);
        hash = 53 * hash + Objects.hashCode(this.dataBoleto);
        hash = 53 * hash + this.mesContrato;
        hash = 53 * hash + (this.ativo ? 1 : 0);
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
        final Inquilino other = (Inquilino) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.telefone, other.telefone)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.cpf, other.cpf)) {
            return false;
        }
        if (!Objects.equals(this.rg, other.rg)) {
            return false;
        }
        if (!Objects.equals(this.dataBoleto, other.dataBoleto)) {
            return false;
        }
        if (this.mesContrato != other.mesContrato) {
            return false;
        }
        if (this.ativo != other.ativo) {
            return false;
        }
        return true;
    }
    
}
