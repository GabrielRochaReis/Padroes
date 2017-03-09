/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import DAO.ProprietarioDAO;
import Model.Proprietario;
import RNs.ProprietarioRN;
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
@ManagedBean(name="cadastroProprietarioController")
public class CadastroProprietarioController {
    
    private Proprietario proprietario;
    private ProprietarioDAO proprietarioDao;
    private ProprietarioRN proprietarioRN;
    private boolean editar;
    
    @PostConstruct
    public void init(){
        proprietario=(Proprietario)RequestContext.getCurrentInstance().getAttributes().get("proprietario");
        if(proprietario==null){
            proprietario=new Proprietario();
            proprietario.setAtivo(true);
        }
        proprietarioRN = ProprietarioRN.getInstance();
        
        if(proprietario.getId()!=null){
            editar=true;
        }
    }
    
    public String gravar(){
        try{
        proprietarioRN.iserirAtualizar(proprietario);
        limpar();
        if(editar){
            RequestContext.getCurrentInstance().getAttributes().put("mensagem",new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Proprietario atualizado com sucesso."));
        } else {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Proprietario cadastrado com sucesso."));
        }
        } catch(Exception e){
            e.printStackTrace();
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao gravar: "+e.getLocalizedMessage()));
            return null;
        }
            return"pesquisarProprietario.xhtml";
    }
    
    public String voltar(){
        return "pesquisarProprietario.xhtml";
    }
    
    public void limpar(){
        proprietario = new Proprietario();
        proprietario.setAtivo(true);
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

}
