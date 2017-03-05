/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Model.Inquilino;
import RNs.InquilinoRN;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Gabriel Rocha
 */
@ManagedBean(name="cadastroInquilinoController")
@ViewScoped
public class CadastroInquilinoController implements Serializable {
    
    private Inquilino inquilino;
    private InquilinoRN inquilinoRN;
    private String legenda;
    private boolean editar;
    private static final long serialVersionUID = 3152248670694285690L;
    
    @PostConstruct
    public void init(){
        inquilino=(Inquilino)RequestContext.getCurrentInstance().getAttributes().get("inquilino");
        if(inquilino==null){
            inquilino=new Inquilino();
            inquilino.setAtivo(true);
        }
        inquilinoRN = InquilinoRN.getInstance();
        
        if(inquilino.getId()!=null){
            editar=true;
        }
    }
    
    public String gravar(){
        try{
            inquilinoRN.iserirAtualizar(inquilino);
            limpar();
            if(editar){
                RequestContext.getCurrentInstance().getAttributes().put("mensagem",new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Inquilino atualizado com sucesso."));
            } else {
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Inquilino cadastrado com sucesso."));
            }
        } catch(Exception e){
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao gravar: "+e.getMessage()));
            return null;
        }
        return "pesquisarInquilino";
    }
    
    public String voltar(){
        return "pesquisarInquilino";
    }
    
    public void limpar(){
        inquilino = new Inquilino();
        inquilino.setAtivo(true);
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    public String getLegenda() {
        return legenda;
    }

    public void setLegenda(String legenda) {
        this.legenda = legenda;
    }
    
}
