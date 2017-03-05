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
import Model.Inquilino;
import Model.Pagamento;
import Model.Proprietario;
import filtros.FiltroPagamento;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Gabriel Rocha
 */
@ViewScoped
@ManagedBean(name="pagamentosController")
public class PagamentosController implements Serializable{
    
    private InquilinoDAO inquilinoDAO;
    private Inquilino inquilino;
    private Proprietario proprietario;
    private ProprietarioDAO proprietarioDAO;
    private Apartamento apartamento;
    private ApartamentoDAO apartamentoDAO;
    private List<Pagamento> pagamento;
    private PagamentoDAO pagamentoDAO;
    private Pagamento item;
    private FiltroPagamento filtro;
    private String obs;
    
    @PostConstruct
    public void init(){
        apartamento=(Apartamento)RequestContext.getCurrentInstance().getAttributes().get("apartamento");
        filtro = new FiltroPagamento();
        if(apartamento!=null){
            try {
                pagamento=pagamentoDAO.obterPagamentos(apartamento.getId());
                if(apartamento.getProprietario()!=0){
                    proprietario=proprietarioDAO.obterProprietarioPorId(apartamento.getProprietario()+"");
                }
                if(apartamento.getInquilino()!=0){
                    inquilino=inquilinoDAO.obterInquilinoPorId(apartamento.getInquilino()+"");
                }
            } catch (SQLException ex) {
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao carregar as informações. ")); 
            }
        }
    }
    
    public String cadastrar(){
        RequestContext.getCurrentInstance().getAttributes().put("apartamento", apartamento);
        return "cadastroPagamento";
    }
    
    public StreamedContent imgComprovanteDeposito(){
        if(item!= null && item.getComprovanteDeposito()!= null)
            return new DefaultStreamedContent(item.getComprovanteDeposito());
        return null;
    }
    
    public String getDataBoleto(){
        if(inquilino!=null && inquilino.getDataBoleto()!=null){
            return MesesEnum.getMes(inquilino.getDataBoleto());
        }
        return null;
    }
    
    public String getVenContrato(){
        if(inquilino!=null && inquilino.getMesContrato()!=null){
            return MesesEnum.getMes(inquilino.getMesContrato());
        }
        return null;
    }
    
    public String editar(Pagamento a){
        RequestContext.getCurrentInstance().getAttributes().put("apartamento", apartamento);
        RequestContext.getCurrentInstance().getAttributes().put("pagamento", a);
        return "cadastroPagamento";
    }
    
    public void pesquisar(){
        try {
            filtro.setApartamento(apartamento.getId());
            pagamento=pagamentoDAO.obterPagamentosPorDatas(filtro);
        } catch (SQLException ex) {
            Logger.getLogger(PagamentosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public StreamedContent imgComprovantePagamento(Pagamento item) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        StreamedContent imagem=null;
        if(item!= null && item.getComprovantePagamento()!= null){
            InputStream is = item.getComprovantePagamento();
            String nome = "Pagamento-Ap"+item.getApartemento()+"-";
            if(item.getDataDeposito()!=null){
                nome+=fmt.format(item.getDataPagamento());
            }
            nome+="."+FilenameUtils.getExtension(item.getNomeComprovantePagamento());
            StreamedContent image;
            if(FilenameUtils.getExtension(item.getNomeComprovantePagamento()).equals("pdf")){
                image = new DefaultStreamedContent(is,"application/pdf",nome);
            } else {
                image = new DefaultStreamedContent(is,"image/jpg",nome);
            }
        return image;
        }
        return null;
    }
    
    public StreamedContent imgComprovanteDeposito(Pagamento item) {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        StreamedContent imagem=null;
        if(item!= null && item.getComprovanteDeposito()!= null){
            InputStream is = item.getComprovanteDeposito();
            String nome = "Deposito-Ap"+item.getApartemento()+"-";
            if(item.getDataDeposito()!=null){
                nome+=fmt.format(item.getDataDeposito());
            }
            nome+="."+FilenameUtils.getExtension(item.getNomeComprovanteDeposito());
            StreamedContent image;
            if(FilenameUtils.getExtension(item.getNomeComprovantePagamento()).equals("pdf")){
                image = new DefaultStreamedContent(is,"application/pdf",nome);
            } else {
                image = new DefaultStreamedContent(is,"image/jpg",nome);
            }
        return image;
        }
        return null;
    }
    
    public PagamentosController() {
        inquilinoDAO = InquilinoDAO.getInstance();
        proprietarioDAO = ProprietarioDAO.getInstance();
        apartamentoDAO = ApartamentoDAO.getInstance();
        pagamentoDAO = PagamentoDAO.getInstance();
    }
    
    public String voltar(){
    return "pesquisarApartamento";
    }
    
    public String infProprietario(){
        String inf="";
        inf+="Nome: "+proprietario.getNome()+" Tel: "+proprietario.getTelefone()+"Email: "+proprietario.getEmail()+"\n";
        inf+="Endereço: "+proprietario.getEndereco();
        inf+="Dados Bancarios \n"+"Banco: "+proprietario.getInstituicao()+"Operacao: "+proprietario.getOperacao()+"\n";
        inf+="N Conta: "+proprietario.getNumConta();
        inf+="Agencia: "+proprietario.getAgencia();
        return inf;
    }
    
    private byte[] gerarRelatorio() throws JRException{
        Map parameters = new HashMap();
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        parameters.put("apartamento", "Apartamento "+apartamento.getNumero());
        parameters.put("inquilino", inquilino==null?"":inquilino.getNome());
        parameters.put("dataVencimento", inquilino.getDataBoleto().toString());
        parameters.put("valorAluguel", apartamento.getAluguel()+"");
        parameters.put("proprietario", proprietario==null?"":proprietario.getNome());
        parameters.put("telefone", inquilino.getTelefone());
        parameters.put("vencimentoContrato", MesesEnum.getMes(inquilino.getMesContrato()));
        parameters.put("obs", obs);
        parameters.put("dataDeposito", proprietario.getDataDeposito().toString());
        
        JasperReport report = JasperCompileManager.compileReport("C:/Users/Gabriel Rocha/Documents/Systema IMOBI/Teste/relatorios/RelatorioPagamentosPorApartamentp.jrxml");
        JasperPrint print = JasperFillManager.fillReport(report, parameters,
        new JRBeanCollectionDataSource(pagamento));
        return JasperExportManager.exportReportToPdf(print);
    }
    
    public DefaultStreamedContent donwloadRelatorio(){
        if(pagamento!=null && !pagamento.isEmpty()){
            try {
                return new DefaultStreamedContent(new ByteArrayInputStream(gerarRelatorio()),"application/pdf","Relatorio.pdf");
            } catch (JRException ex) {
                ex.printStackTrace();
                RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao gerar o relatorio.")); 
                return null;
            }
        } else {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", "Não existe pagamentos cadastrados para gerar o relatorio")); 
            return null;
        }
    }
    
    public void modalObs(){
        if(!pagamento.isEmpty()){
            RequestContext.getCurrentInstance().execute("PF('dialogObs').show()");
        } else {
            RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não possui pagamentos cadastrados para o apartamento "+apartamento.getNumero()+"para gerar o relatorio. ")); 
        }
    }
    
    public void chamarRelatorio(){
        RequestContext.getCurrentInstance().execute("PF('dialogObs').show()");
    }
    
    public void limpar(){
        apartamento= new Apartamento();
        inquilino=null;
        proprietario=null;
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
