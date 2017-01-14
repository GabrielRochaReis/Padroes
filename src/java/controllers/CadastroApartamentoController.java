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
import RNs.ApartamentoRN;
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
@ViewScoped
@ManagedBean(name="cadastroApartamentoController")
public class CadastroApartamentoController {
    
    private InquilinoDAO inquilinoDAO;
    private Inquilino inquilino;
    private Proprietario proprietario;
    private ProprietarioDAO proprietarioDAO;
    private Apartamento apartamento;
    private ApartamentoRN apartamentoRN;
    private ApartamentoDAO apartamentoDAO;
    private boolean editar;
    
    @PostConstruct
    public void init(){
        apartamento=(Apartamento)RequestContext.getCurrentInstance().getAttributes().get("apartamento");
        if(apartamento==null){
            apartamento=new Apartamento();
        }
        apartamentoRN = ApartamentoRN.getInstance();
        
        if(apartamento.getId()!=null){
            editar=true;
            try {
                proprietario=proprietarioDAO.obterProprietarioPorId(apartamento.getProprietario()+"");
                inquilino=inquilinoDAO.obterInquilinoPorId(apartamento.getInquilino()+"");
            } catch (SQLException ex) {
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao gravar: "+ex.getMessage())); 
            }
        }
    }
    
    public CadastroApartamentoController() {
        inquilinoDAO = InquilinoDAO.getInstance();
        proprietarioDAO = ProprietarioDAO.getInstance();
        apartamentoDAO = ApartamentoDAO.getInstance();
    }
    
    public String gravar(){
        if(proprietario!=null){
            apartamento.setProprietario(proprietario.getId());
        }
        if(inquilino!=null){
            apartamento.setInquilino(inquilino.getId());
        }
        try {
            apartamentoRN.iserirAtualizar(apartamento);
            limpar();
            if(editar){
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Apartamento atualizado com sucesso."));
            }
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Apartamento cadastrado com sucesso."));
        } catch (SQLException ex) {
           RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao gravar: "+ex.getMessage())); 
        }
        return "pesquisarApartamento";
    }
    
    public String voltar(){
    return "pesquisarApartamento";
    }
    
    public void limpar(){
        apartamento= new Apartamento();
        inquilino=null;
        proprietario=null;
    }
    
    public List<Inquilino> inquilinos(String nome){
        try {
            return inquilinoDAO.obterInquilinoAtivoPorNome(nome);
        } catch (SQLException ex) {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro : "+ex.getMessage()));
            return null;
        }
    }
    
    public List<Proprietario> proprietarios(String nome){
        try {
            return proprietarioDAO.obterProprietarioAtivoPorNome(nome);
        } catch (SQLException ex) {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro : "+ex.getMessage()));
            return null;
        }
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

    public Apartamento getApartamento() {
        return apartamento;
    }

    public void setApartamento(Apartamento apartamento) {
        this.apartamento = apartamento;
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }
    
}
