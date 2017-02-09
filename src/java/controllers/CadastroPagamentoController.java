/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Model.Apartamento;
import Model.Pagamento;
import RNs.PagamentoRN;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Gabriel Rocha
 */
@ViewScoped
@ManagedBean(name="cadastroPagamentoController")
public class CadastroPagamentoController implements Serializable{
    
    private Pagamento pagamento;
    private Apartamento apartamento;
    private PagamentoRN pagamentoRN;
    private boolean editar; 
    
    @PostConstruct
    public void inicializar(){
        apartamento=(Apartamento)RequestContext.getCurrentInstance().getAttributes().get("apartamento");
        pagamentoRN = PagamentoRN.getInstance();
        pagamento=(Pagamento)RequestContext.getCurrentInstance().getAttributes().get("pagamento");
        if(pagamento==null) {
            pagamento = new Pagamento();
            if(apartamento!= null){
                pagamento.setApartemento(apartamento.getId());
            }
        } else {
            editar=true;
        }
    }

    public String gravar(){
        try{
            pagamentoRN.iserirAtualizar(pagamento);
            RequestContext.getCurrentInstance().getAttributes().put("apartamento", apartamento);
            limpar();
            if(editar){
                RequestContext.getCurrentInstance().getAttributes().put("mensagem",new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Pagamento atualizado com sucesso."));
            } else {
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Pagamento cadastrado com sucesso."));
            }
        } catch(Exception e){
            e.printStackTrace();
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao gravar: "+e.getMessage()));
        }
            return"pagamentos";
    }
    
    public String voltar(){
        if(editar){
            RequestContext.getCurrentInstance().getAttributes().put("apartamento", apartamento);
        }
            return "pagamentos"; 
    }
    
    private void limpar(){
    }
    
    public StreamedContent imgComprovantePagamento(){
        return new DefaultStreamedContent(pagamento.getComprovanteDeposito(), "application/jpg","img.jpg");
    }
    
    public void carregarComprovantePagamento(FileUploadEvent event){
        try {
            final UploadedFile file = event.getFile();
            pagamento.setComprovantePagamento(file.getInputstream());
            pagamento.setNomeComprovantePagamento(file.getFileName());
        } catch (IOException ex) {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possivel carregar o documento "+event.getFile().getFileName()));
        }
    }
    public void carregarComprovanteDeposito(FileUploadEvent event){
        try {
            final UploadedFile file = event.getFile();
            pagamento.setComprovanteDeposito(file.getInputstream());
            pagamento.setNomeComprovanteDeposito(file.getFileName());
        } catch (IOException ex) {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possivel carregar o documento "+event.getFile().getFileName()));
        }
        
    }
    
    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }
}
