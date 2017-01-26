/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import DAO.ApartamentoDAO;
import DAO.InquilinoDAO;
import DAO.ProprietarioDAO;
import Model.Apartamento;
import Model.Inquilino;
import Model.Proprietario;
import java.io.Serializable;
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
@ManagedBean(name="pesquisarApartamentoController")
@ViewScoped
public class PesquisarApartamentoController implements Serializable{
    private List<Apartamento> list;
    private ApartamentoDAO apartamentoDAO;
    private Apartamento apartamento;
    private InquilinoDAO inquilinoDAO;
    private ProprietarioDAO proprietarioDAO;

    @PostConstruct
    public void init(){
    apartamentoDAO = ApartamentoDAO.getInstance();
    proprietarioDAO = ProprietarioDAO.getInstance();
    inquilinoDAO = InquilinoDAO.getInstance();
    FacesMessage mensagem =(FacesMessage) RequestContext.getCurrentInstance().getAttributes().get("mensagem");

    if(mensagem!=null) {
         RequestContext.getCurrentInstance().showMessageInDialog(mensagem);
    }
        try {
            list = apartamentoDAO.obterApartamentoTodos();
        } catch (SQLException ex) {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro : "+ex.getMessage()));
        }
    }
    
    public String cadastrar(){
        return "cadastro_apartamento";
    }
    
    public PesquisarApartamentoController() {
    }
    
    public String obterProprietario(String p){
        if(!p.equals("0")){
            Proprietario prop;
            try {
                prop = proprietarioDAO.obterProprietarioPorId(p);
            if(prop==null)
                return null;
            return prop.getNome();
            } catch (SQLException ex) {
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro : "+ex.getMessage()));
            }
        }
        return null;
    }
    
    public String obterInquilino(String p){
        if(!p.equals("0")){
            Inquilino prop;
            try {
                prop = inquilinoDAO.obterInquilinoPorId(p);
            if(prop==null)
                return null;
            return prop.getNome();
            } catch (SQLException ex) {
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro : "+ex.getMessage()));
            }
        }
        return null;
    }
    
    public void pesquisar(){
        try {
            list = apartamentoDAO.obterApartamentoAutoComplete(apartamento==null?null:apartamento);
        } catch (SQLException ex) {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro : "+ex.getMessage()));
        }
    }

    public List<Apartamento> apartamentos(String nome){
        try {
            Apartamento ap = new Apartamento();
            ap.setNumero(nome);
            return apartamentoDAO.obterApartamentoAutoComplete(ap);
        } catch (SQLException ex) {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro : "+ex.getMessage()));
            return null;
        }
    }

    public String voltar(){
        return "tela_inicial.xhtml";
    }
    
    public String editar(Apartamento a){
        RequestContext.getCurrentInstance().getAttributes().put("apartamento", a);
        return "cadastro_apartamento";
    }
    
    public String pagamentos(Apartamento a){
        RequestContext.getCurrentInstance().getAttributes().put("apartamento", a);
        return "pagamentos";
    }
    
    public List<Apartamento> getList() {
        return list;
    }

    public void setList(List<Apartamento> list) {
        this.list = list;
    }

    public Apartamento getApartamento() {
        return apartamento;
    }

    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento;
    }
    
}
