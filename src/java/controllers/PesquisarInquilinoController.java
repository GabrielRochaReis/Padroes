/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import DAO.InquilinoDAO;
import Model.Inquilino;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Gabriel Rocha
 */
@ManagedBean(name="pesquisarInquilinoController")
@ViewScoped
public class PesquisarInquilinoController {
    private List<Inquilino> list;
    private InquilinoDAO inquilinoDAO;
    private Inquilino inquilino;

    @PostConstruct
    public void init(){
    inquilinoDAO = InquilinoDAO.getInstance();
    FacesMessage mensagem =(FacesMessage) RequestContext.getCurrentInstance().getAttributes().get("mensagem");

    if(mensagem!=null) {
         RequestContext.getCurrentInstance().showMessageInDialog(mensagem);
    }
        try {
            list = inquilinoDAO.obterInquilinoPorNome(null);
        } catch (SQLException ex) {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro : "+ex.getMessage()));
        }
    }
    
    public PesquisarInquilinoController() {
    }
    
    public void pesquisar(){
        try {
            list = inquilinoDAO.obterInquilinoPorNome(inquilino==null?null:inquilino.getNome());
        } catch (SQLException ex) {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro : "+ex.getMessage()));
        }
    }

    public List<Inquilino> inquilinos(String nome){
        try {
            return inquilinoDAO.obterInquilinoPorNome(nome);
        } catch (SQLException ex) {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro : "+ex.getMessage()));
            return null;
        }
    }
    
    public void remover(int id){
        try {
            inquilinoDAO.remover(id);
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sucesso", "Inquilino removido com sucesso."));
        } catch (SQLException ex) {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro : "+ex.getMessage()));
        }
    }

    public String voltar(){
        return "tela_inicial.xhtml";
    }
    
    public String cadastrar(){
        return "cadastro_inquilino";
    }
    
    public String editar(Inquilino i){
        RequestContext.getCurrentInstance().getAttributes().put("inquilino", i);
        return "cadastro_inquilino";
    }
    
    public List<Inquilino> getList() {
        return list;
    }

    public void setList(List<Inquilino> list) {
        this.list = list;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }
    
}
