/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Pagamento;
import filtros.FiltroPagamento;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Gabriel Rocha
 */
public class PagamentoDAO extends BaseDao {

    private static PagamentoDAO pagamentoDAO;

    public static PagamentoDAO getInstance() {
        if (pagamentoDAO == null) {
            pagamentoDAO = new PagamentoDAO();
        }
        return pagamentoDAO;
    }

    private PagamentoDAO() {
        super();
    }
    
    public boolean gravarComprovante(Pagamento pagamento) {
        if (pagamento.getComprovanteDeposito() != null) {
            try {
                InputStream file = pagamento.getComprovanteDeposito();
                String suffix = "pdf";
                File files = null;
                String path = getPathComprovateDeposito();
                File diretorio = new File(path); // ajfilho é uma pasta!
                if (!diretorio.exists()) {
                    diretorio.mkdirs(); //mkdir() cria somente um diretório, mkdirs() cria diretórios e subdiretórios.
                }
                files = new File(path + pagamento.getNomeComprovanteDeposito());
                OutputStream outputStream = new FileOutputStream(files);
                IOUtils.copy(file, outputStream);
                outputStream.close();
            } catch (Exception ex) {
                RequestContext.getCurrentInstance()
                        .showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possivel salvar o Comprovante de deposito. Erro :" + ex.getMessage()));
                return false;
            }
        }
        if (pagamento.getComprovantePagamento() != null) {
            try {
                InputStream file = pagamento.getComprovantePagamento();
                String suffix = ".pdf";
                File files = null;
                Writer output = null;
                String path = getPathComprovatePagamento();
                File diretorio = new File(path); // ajfilho é uma pasta!
                if (!diretorio.exists()) {
                    diretorio.mkdirs(); //mkdir() cria somente um diretório, mkdirs() cria diretórios e subdiretórios.
                }
                files = new File(path + pagamento.getNomeComprovantePagamento());
                OutputStream outputStream = new FileOutputStream(files);
                IOUtils.copy(file, outputStream);
                outputStream.close();
            } catch (Exception ex) {
                RequestContext.getCurrentInstance()
                        .showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possivel salvar o Comprovante de pagamento. Erro :" + ex.getMessage()));
                return false;
            }
        }
        return true;
    }

    private static String getPathComprovatePagamento() {
        return getPath() + "Documentos\\ComprovatePagamento\\";
    }

    private static String getPathComprovateDeposito() {
        return getPath() + "Documentos\\ComprovateDeposito\\";
    }

    public void persistir(Pagamento pag) throws SQLException {
        String query;
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

        String dataPagamento = null;
        String dataDeposito = null;
        String mes = null;

        if (pag.getMes() != null) {
            pag.getMes().setDate(15);
            mes = fmt.format(pag.getMes());
        }
        if (pag.getDataDeposito() != null) {
            dataDeposito = fmt.format(pag.getDataDeposito());
        }
        if (pag.getDataPagamento() != null) {
            dataPagamento = fmt.format(pag.getDataPagamento());
        }

        query = "insert into pagamento (id , mes, id_apartemento, data_pagamento, nome_comprovante_pagamento, data_deposito, nome_comprovante_deposito, valor_deposito)";
        query += "values(null, ?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement ps = data.getConection().prepareStatement(query);
        ps.setString(1, mes);
        ps.setInt(2, pag.getApartemento());
        ps.setString(3, dataPagamento);
        ps.setString(4, pag.getNomeComprovantePagamento());
        ps.setString(5, dataDeposito);
        ps.setString(6, pag.getNomeComprovanteDeposito());
        ps.setDouble(7, pag.getValorDeposito());
        ps.executeUpdate();
        gravarComprovante(pag);
    }

    private static String getPath() {
        return "C:\\Users\\Gabriel Rocha\\Documents\\";
    }

    private void loadComprovantes(Pagamento pag) {
        if (pag.getNomeComprovanteDeposito() != null) {
            try {
                InputStream inputStream = new FileInputStream(getPathComprovateDeposito() + pag.getNomeComprovanteDeposito());
                pag.setComprovanteDeposito(inputStream);
            } catch (FileNotFoundException ex) {
                pag.setNomeComprovanteDeposito(null);
            }
        }
        if (pag.getNomeComprovantePagamento() != null) {
            try {
                InputStream inputStream = new FileInputStream(getPathComprovatePagamento() + pag.getNomeComprovantePagamento());
                pag.setComprovantePagamento(inputStream);
            } catch (FileNotFoundException ex) {
                pag.setNomeComprovantePagamento(null);
            }
        }
    }

    public void atualizar(Pagamento pag) throws SQLException {
        String query;
        query = "delete from pagamento where id=" + pag.getId();
        PreparedStatement ps = data.getConection().prepareStatement(query);
        ps.execute();
        persistir(pag);
    }

    public List<Pagamento> obterPagamentos(Integer apartamento) throws SQLException {
        String query = "SELECT * FROM pagamento";
        query += " where id_apartemento=" + apartamento + " ";
        query += " order by mes;";
        PreparedStatement ps = data.getConection().prepareStatement(query);

        ResultSet result = ps.executeQuery(query);
        ArrayList<Pagamento> retorno = carregarPagamento(result);
        return retorno;
    }

    private ArrayList<Pagamento> carregarPagamento(ResultSet result) throws SQLException {
        ArrayList<Pagamento> retorno = new ArrayList<>();
        while (result.next()) {
            Pagamento pag = new Pagamento();
            pag.setId(result.getInt("id"));
            pag.setMes(result.getDate("mes"));
            pag.setApartemento(result.getInt("id_apartemento"));
            pag.setDataPagamento(result.getDate("data_pagamento"));
            pag.setNomeComprovantePagamento(result.getString("nome_comprovante_pagamento"));
            pag.setDataDeposito(result.getDate("data_deposito"));
            pag.setNomeComprovanteDeposito(result.getString("nome_comprovante_deposito"));
            pag.setValorDeposito(result.getDouble("valor_deposito"));
            loadComprovantes(pag);
            retorno.add(pag);
        }
        return retorno;
    }

    public List<Pagamento> obterPagamentosPorDatas(FiltroPagamento filtro) throws SQLException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        String query = "SELECT * FROM pagamento";
        query += " where 1=1 ";
        if (filtro.getApartamento() != null) {
            query += " id_apartemento=" + filtro.getApartamento();
        }
        if (filtro.getMesInicial() != null) {
            c.setTime(filtro.getMesInicial());
            c.set(Calendar.DAY_OF_MONTH, 1);
            filtro.setMesInicial(c.getTime());
            query += " and mes >= '" + fmt.format(filtro.getMesInicial()) + "'";
        }
        if (filtro.getMesFinal() != null) {
            c.setTime(filtro.getMesFinal());
            c.set(Calendar.DAY_OF_MONTH, c.getMaximum(Calendar.DAY_OF_MONTH));
            filtro.setMesFinal(c.getTime());
            query += " and mes <= '" + fmt.format(filtro.getMesFinal()) + "'";
        }
        if (filtro.getPagamentoInicial() != null) {
            query += " and data_pagamento >= '" + fmt.format(filtro.getPagamentoInicial()) + "' ";
        }
        if (filtro.getPagamentoFinal() != null) {
            query += " and data_pagamento <= '" + fmt.format(filtro.getPagamentoFinal()) + "' ";
        }
        if (filtro.getDepositoInicial() != null) {
            query += " and data_deposito >= '" + fmt.format(filtro.getDepositoInicial()) + "' ";
        }
        if (filtro.getDepositoFinal() != null) {
            query += " and data_deposito <= '" + fmt.format(filtro.getDepositoFinal()) + "' ";
        }
        query += " order by mes;";
        PreparedStatement ps = data.getConection().prepareStatement(query);

        ResultSet result = ps.executeQuery(query);
        ArrayList<Pagamento> retorno = carregarPagamento(result);
        return retorno;
    }
}
