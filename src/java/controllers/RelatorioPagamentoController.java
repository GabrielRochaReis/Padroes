/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import DAO.ApartamentoDAO;
import DAO.InquilinoDAO;
import DAO.PagamentoDAO;
import DAO.ProprietarioDAO;
import Enum.MesesEnum;
import Model.Apartamento;
import Model.Pagamento;
import filtros.FiltroPagamento;
import filtros.FiltroRelatorioPagamento;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Gabriel Rocha
 */
@ViewScoped
@ManagedBean(name = "relatorioPagamentoController")
public class RelatorioPagamentoController implements Serializable {

    private InquilinoDAO inquilinoDAO;
    private ProprietarioDAO proprietarioDAO;
    private ApartamentoDAO apartamentoDAO;
    private List<Pagamento> pagamento;
    private PagamentoDAO pagamentoDAO;
    private Pagamento item;
    private FiltroPagamento filtro;
    private String obs;

    @PostConstruct
    public void init() {
        iniciar();
    }

    private void iniciar() {
        filtro = new FiltroPagamento();
        try {
            pagamento = pagamentoDAO.obterPagamentosPorDatas(filtro);

        } catch (SQLException ex) {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao carregar as informações. "));
        }
    }

    public StreamedContent imgComprovanteDeposito() {
        if (item != null && item.getComprovanteDeposito() != null) {
            return new DefaultStreamedContent(item.getComprovanteDeposito());
        }
        return null;
    }

    public String getDataBoleto(Integer mes) {
        if (mes != null) {
            return MesesEnum.getMes(mes);
        }
        return null;
    }

    public String obterApartamento(Integer apartamento) {
        if (apartamento != null) {
            try {
                return apartamentoDAO.obterApartamentoPorId(apartamento.toString()).getNumero();
            } catch (SQLException ex) {
                Logger.getLogger(RelatorioPagamentoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public String obterProprietario(Integer apartamento) {
        Apartamento ap = null;
        if (apartamento != null) {
            try {
                ap = apartamentoDAO.obterApartamentoPorId(apartamento.toString());
            } catch (SQLException ex) {
                Logger.getLogger(RelatorioPagamentoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (ap.getProprietario() != null) {
            try {
                return proprietarioDAO.obterProprietarioPorId(ap.getProprietario().toString()).getNome();
            } catch (SQLException ex) {
                Logger.getLogger(RelatorioPagamentoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public String obterInquilino(Integer apartamento) {
        Apartamento ap = null;
        if (apartamento != null) {
            try {
                ap = apartamentoDAO.obterApartamentoPorId(apartamento.toString());
            } catch (SQLException ex) {
                Logger.getLogger(RelatorioPagamentoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (ap.getInquilino() != null) {
            try {
                return inquilinoDAO.obterInquilinoPorId(ap.getInquilino().toString()).getNome();
            } catch (SQLException ex) {
                Logger.getLogger(RelatorioPagamentoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public String editar(Pagamento a) {
        try {
            RequestContext.getCurrentInstance().getAttributes().put("apartamento", apartamentoDAO.obterApartamentoPorId(a.getApartemento().toString()));
        } catch (SQLException ex) {
            Logger.getLogger(RelatorioPagamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        RequestContext.getCurrentInstance().getAttributes().put("pagamento", a);
        RequestContext.getCurrentInstance().getAttributes().put("voltar", "relatorioPagamento");
        return "cadastroPagamento";
    }

    public void pesquisar() {
        try {
            pagamento = pagamentoDAO.obterPagamentosPorDatas(filtro);
        } catch (SQLException ex) {
            Logger.getLogger(RelatorioPagamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public StreamedContent imgComprovantePagamento(Pagamento item) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        StreamedContent imagem = null;
        if (item != null && item.getComprovantePagamento() != null) {
            InputStream is = item.getComprovantePagamento();
            String nome = "Pagamento-Ap" + item.getApartemento() + "-";
            if (item.getDataDeposito() != null) {
                nome += fmt.format(item.getDataPagamento());
            }
            nome += "." + FilenameUtils.getExtension(item.getNomeComprovantePagamento());
            StreamedContent image;
            if (FilenameUtils.getExtension(item.getNomeComprovantePagamento()).equals("pdf")) {
                image = new DefaultStreamedContent(is, "application/pdf", nome);
            } else {
                image = new DefaultStreamedContent(is, "image/jpg", nome);
            }
            return image;
        }
        return null;
    }

    public StreamedContent imgComprovanteDeposito(Pagamento item) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        StreamedContent imagem = null;
        if (item != null && item.getComprovanteDeposito() != null) {
            InputStream is = item.getComprovanteDeposito();
            String nome = "Deposito-Ap" + item.getApartemento() + "-";
            if (item.getDataDeposito() != null) {
                nome += fmt.format(item.getDataDeposito());
            }
            nome += "." + FilenameUtils.getExtension(item.getNomeComprovanteDeposito());
            StreamedContent image;
            if (FilenameUtils.getExtension(item.getNomeComprovantePagamento()).equals("pdf")) {
                image = new DefaultStreamedContent(is, "application/pdf", nome);
            } else {
                image = new DefaultStreamedContent(is, "image/jpg", nome);
            }
            return image;
        }
        return null;
    }

    public RelatorioPagamentoController() {
        inquilinoDAO = InquilinoDAO.getInstance();
        proprietarioDAO = ProprietarioDAO.getInstance();
        apartamentoDAO = ApartamentoDAO.getInstance();
        pagamentoDAO = PagamentoDAO.getInstance();
    }

    public String voltar() {
        return "tela_inicial";
    }

    private byte[] gerarRelatorio() throws JRException {
        Map parameters = new HashMap();
        parameters.put("obs", obs);
        String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("relatorios/reports/RelatorioPagamentos.jasper");
        JasperReport report = (JasperReport) JRLoader.loadObjectFromFile(realPath);
        JasperPrint print = JasperFillManager.fillReport(report, parameters,
                new JRBeanCollectionDataSource(montarListaRelatorio()));
        return JasperExportManager.exportReportToPdf(print);
    }

    public List<FiltroRelatorioPagamento> montarListaRelatorio() {
        List<FiltroRelatorioPagamento> list = new ArrayList<FiltroRelatorioPagamento>();

        for (Pagamento pag : pagamento) {
            FiltroRelatorioPagamento fil = new FiltroRelatorioPagamento(pag, obterApartamento(pag.getApartemento()), obterInquilino(pag.getApartemento()), obterProprietario(pag.getApartemento()));
            list.add(fil);
        }
        return list;
    }

    public DefaultStreamedContent donwloadRelatorio() {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        if (pagamento != null && !pagamento.isEmpty()) {
            try {
                return new DefaultStreamedContent(new ByteArrayInputStream(gerarRelatorio()), "application/pdf", "Relatorio-Pagamentos-" + fmt.format(new Date()) + ".pdf");
            } catch (JRException ex) {
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", "Erro ao gerar relatorio : " + ex.getMessage()));
                return null;
            }
        } else {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", "Não existe pagamentos cadastrados para gerar o relatorio"));
            return null;
        }
    }

    public void modalObs() {
        if (!pagamento.isEmpty()) {
            RequestContext.getCurrentInstance().execute("PF('dialogObs').show()");
        } else {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não possui pagamentos cadastrados para gerar o relatorio. "));
        }
    }

    public void chamarRelatorio() {
        RequestContext.getCurrentInstance().execute("PF('dialogObs').show()");
    }

    public void limpar() {
//        apartamento = new Apartamento();
//        inquilino = null;
//        proprietario = null;
    }

    public List<Pagamento> getPagamento() {
        return pagamento;
    }

    public void setPagamento(List<Pagamento> pagamento) {
        this.pagamento = pagamento;
    }

    public Pagamento getItem() {
        return item;
    }

    public void setItem(Pagamento item) {
        this.item = item;
    }

    public FiltroPagamento getFiltro() {
        return filtro;
    }

    public void setFiltro(FiltroPagamento filtro) {
        this.filtro = filtro;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

}
