/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Model.Apartamento;
import Model.Pagamento;
import RNs.PagamentoRN;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
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

    @PostConstruct
    public void inicializar() {
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
            if (gravarComprovante()) {
                pagamentoRN.iserirAtualizar(pagamento);
                RequestContext.getCurrentInstance().getAttributes().put("apartamento", apartamento);
                limpar();
                if (editar) {
                    RequestContext.getCurrentInstance().getAttributes().put("mensagem", new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Pagamento atualizado com sucesso."));
                } else {
                    RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Pagamento cadastrado com sucesso."));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao gravar: " + e.getMessage()));
            return null;
        }
        return "pagamentos";
    }

    private String formatarData(Date data) {
        if (data != null) {
            SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
            return fmt.format(data);
        }
        return "";
    }

    public boolean gravarComprovante() {
        if (pagamento.getComprovanteDeposito() != null) {
            try {
                UploadedFile file = pagamento.getComprovanteDeposito();
                String prefix = FilenameUtils.getBaseName(file.getFileName());
                String suffix = FilenameUtils.getExtension(file.getFileName());
                File files = null;
                OutputStream output = null;
                String pathRoot = "C:\\Users\\Gabriel Rocha\\Documents\\";
                String path = pathRoot + "Documentos\\ComprovateDeposito\\";
                File diretorio = new File(path); // ajfilho é uma pasta!
                if (!diretorio.exists()) {
                    diretorio.mkdirs(); //mkdir() cria somente um diretório, mkdirs() cria diretórios e subdiretórios.
                }
                files = new File(path+apartamento.getEdificio() + "-" + apartamento.getNumero() + "-" + formatarData(pagamento.getDataDeposito()) + "-Deposito", "." + suffix);
                output = new FileOutputStream(files);
                IOUtils.copy(file.getInputstream(), output);
                String fileName;
                fileName = files.getName();
            } catch (Exception ex) {
                RequestContext.getCurrentInstance()
                        .showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possivel salvar o Comprovante de deposito. Erro :" + ex.getMessage()));
                return false;
            }
        }
        if (pagamento.getComprovantePagamento() != null) {
            try {
                UploadedFile file = pagamento.getComprovantePagamento();
                String prefix = FilenameUtils.getBaseName(file.getFileName());
                String suffix = FilenameUtils.getExtension(file.getFileName());
                File files = null;
                OutputStream output = null;
                String pathRoot = "C:\\Users\\Gabriel Rocha\\Documents\\";
                String path = pathRoot + "Documentos\\ComprovatePagamento\\";
                File diretorio = new File(path); // ajfilho é uma pasta!
                if (!diretorio.exists()) {
                    diretorio.mkdirs(); //mkdir() cria somente um diretório, mkdirs() cria diretórios e subdiretórios.
                }
                files = new File(path + apartamento.getEdificio() + "-" + apartamento.getNumero() + "-" + formatarData(pagamento.getDataPagamento()) + "-Pagamento" + "." + suffix);
                files.createNewFile();
                files.renameTo(new File(apartamento.getEdificio() + "-" + apartamento.getNumero() + "-" + formatarData(pagamento.getDataPagamento()) + "-Pagamento", "." + suffix));
                output = new FileOutputStream(files);
                IOUtils.copy(file.getInputstream(), output);
                String fileName;
                fileName = files.getName();
            } catch (Exception ex) {
                RequestContext.getCurrentInstance()
                        .showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possivel salvar o Comprovante de pagamento. Erro :" + ex.getMessage()));
                return false;
            }
        }
        return true;
    }

    public String voltar() {
        if (editar) {
            RequestContext.getCurrentInstance().getAttributes().put("apartamento", apartamento);
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
                pagamento.setComprovantePagamento(file);
                pagamento.setNomeComprovantePagamento(apartamento.getEdificio() + "-" + apartamento.getNumero() + "-Pagamento-" + new Date() + "." + suffix);
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
                pagamento.setComprovanteDeposito(file);
                pagamento.setNomeComprovanteDeposito(apartamento.getEdificio() + "-" + apartamento.getNumero() + "-Deposito-" + new Date() + "." + suffix);
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
