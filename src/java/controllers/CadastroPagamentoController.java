/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Model.Apartamento;
import Model.Pagamento;
import RNs.PagamentoRN;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Gabriel Rocha
 */
@ViewScoped
@ManagedBean(name = "cadastroPagamentoController")
public class CadastroPagamentoController implements Serializable {

    private Pagamento pagamento;
    private Apartamento apartamento;
    private PagamentoRN pagamentoRN;
    private boolean editar;
    private String voltar;

    @PostConstruct
    public void inicializar() {
        voltar = (String) RequestContext.getCurrentInstance().getAttributes().get("voltar");
        apartamento = (Apartamento) RequestContext.getCurrentInstance().getAttributes().get("apartamento");
        pagamentoRN = PagamentoRN.getInstance();
        pagamento = (Pagamento) RequestContext.getCurrentInstance().getAttributes().get("pagamento");
        if (pagamento == null) {
            pagamento = new Pagamento();
            if (apartamento != null) {
                pagamento.setApartemento(apartamento.getId());
            }
        } else {
            editar = true;
        }
    }

    public String gravar() {
        try {
            pagamentoRN.iserirAtualizar(pagamento);
            RequestContext.getCurrentInstance().getAttributes().put("apartamento", apartamento);
            limpar();
            if (editar) {
                RequestContext.getCurrentInstance().getAttributes().put("mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Pagamento atualizado com sucesso."));
            } else {
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Pagamento cadastrado com sucesso."));
            }
        } catch (Exception e) {
            e.printStackTrace();
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao gravar: " + e.getMessage()));
            return null;
        }
        return voltar();
    }

    private String formatarData(Date data) {
        if (data != null) {
            SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
            return fmt.format(data);
        }
        return "";
    }

    private static String getPath() {
        return "C:\\Users\\Gabriel Rocha\\Documents\\";
    }

    public String voltar() {
        if (voltar != null) {
            return voltar;
        } else {
            if (editar) {
                RequestContext.getCurrentInstance().getAttributes().put("apartamento", apartamento);
            }
        }
        return "pagamentos";
    }

    private void limpar() {
    }

    public void carregarComprovantePagamento(FileUploadEvent event) {
        try {
            final UploadedFile file = event.getFile();
            String suffix = FilenameUtils.getExtension(file.getFileName());
            if (suffix.equals("pdf")) {
                pagamento.setComprovantePagamento(file.getInputstream());
                pagamento.setNomeComprovantePagamento(apartamento.getNumero() + "-" + apartamento.getEdificio() + "-" + formatarData(pagamento.getDataPagamento()) + "-Pagamento" + "." + suffix);
            } else {
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Apenas documentos em pdf são permitidos"));
            }
        } catch (Exception ex) {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possivel carregar o documento " + event.getFile().getFileName()));
        }

    }

    public void removerComprovantePagamento() {
        pagamento.setComprovantePagamento(null);
        pagamento.setNomeComprovantePagamento(null);
    }

    public void removerComprovanteDeposito() {
        pagamento.setComprovanteDeposito(null);
        pagamento.setNomeComprovanteDeposito(null);
    }

    public void carregarComprovanteDeposito(FileUploadEvent event) {
        try {
            UploadedFile file = event.getFile();
            String suffix = FilenameUtils.getExtension(file.getFileName());
            if (suffix.equals("pdf")) {
                pagamento.setComprovanteDeposito(file.getInputstream());
                pagamento.setNomeComprovanteDeposito(apartamento.getNumero() + "-" + apartamento.getEdificio() + "-" + formatarData(pagamento.getDataDeposito()) + "-Deposito" + "." + suffix);
            } else {
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Apenas documentos em pdf são permitidos"));
            }
        } catch (Exception ex) {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possivel carregar o documento " + event.getFile().getFileName()));
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
