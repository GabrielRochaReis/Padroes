/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import DAO.ProprietarioDAO;
import Model.Proprietario;
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
@ManagedBean(name="pesquisarProprietarioController")
@ViewScoped
public class PesquisarProprietarioController {
    private List<Proprietario> list;
    private ProprietarioDAO proprietarioDAO;
    private Proprietario proprietario;

    @PostConstruct
    public void init(){
    proprietarioDAO = ProprietarioDAO.getInstance();
    FacesMessage mensagem =(FacesMessage) RequestContext.getCurrentInstance().getAttributes().get("mensagem");

    if(mensagem!=null) {
         RequestContext.getCurrentInstance().showMessageInDialog(mensagem);
    }
        try {
            list = proprietarioDAO.obterProprietarioPorNome(null);
        } catch (SQLException ex) {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro : "+ex.getMessage()));
        }
    }
    
    public PesquisarProprietarioController() {
    }
    
    public void pesquisar(){
        try {
            list = proprietarioDAO.obterProprietarioPorNome(proprietario==null?null:proprietario.getNome());
        } catch (SQLException ex) {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro : "+ex.getMessage()));
        }
    }

    public List<Proprietario> proprietarios(String nome){
        try {
            return proprietarioDAO.obterProprietarioPorNome(nome);
        } catch (SQLException ex) {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro : "+ex.getMessage()));
            return null;
        }
    }

    public String cadastrar(){
        return "cadastro_proprietario.xhtml";
    }    

    public String voltar(){
        return "tela_inicial.xhtml";
    }
    
    public String editar(Proprietario i){
    RequestContext.getCurrentInstance().getAttributes().put("proprietario", i);
    return "cadastro_proprietario";
    }
    
    public List<Proprietario> getList() {
        return list;
    }

    public void setList(List<Proprietario> list) {
        this.list = list;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }
    
}
