/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Pagamento;
import filtros.FiltroPagamento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        if (filtro.getPagamentoInicial() != null ) {
            query += " and data_pagamento >= '" + fmt.format(filtro.getPagamentoInicial()) + "' ";
        }
        if (filtro.getPagamentoFinal()!= null ) {
            query += " and data_pagamento <= '" + fmt.format(filtro.getPagamentoFinal()) + "' ";
        }
        if (filtro.getDepositoInicial()!= null) {
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
