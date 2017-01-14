/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Pagamento;
import filtros.FiltroPagamento;
import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabriel Rocha
 */
public class PagamentoDAO extends BaseDao{
    private static PagamentoDAO pagamentoDAO;
    
    public static PagamentoDAO getInstance(){
        if(pagamentoDAO==null){
            pagamentoDAO=new PagamentoDAO();
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
        
        if(pag.getMes()!=null){
            pag.getMes().setDate(15);
            mes=fmt.format(pag.getMes());
        }
        if(pag.getDataDeposito()!=null){
            dataDeposito=fmt.format(pag.getDataDeposito());
        }
        if(pag.getDataPagamento()!=null){
            dataPagamento=fmt.format(pag.getDataPagamento());
        }
        
        query = "insert into pagamento (id , mes, id_apartemento, data_pagamento, comprovante_pagamento, nome_comprovante_pagamento, data_deposito, comprovante_deposito, nome_comprovante_deposito, valor_deposito)";
        query += "values(null, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement ps = data.getConection().prepareStatement(query);
        ps.setString(1, mes );
        ps.setInt(2, pag.getApartemento() );
        ps.setString(3, dataPagamento );
        ps.setBytes(4, pag.getComprovantePagamento() );
        ps.setString(5, pag.getNomeComprovantePagamento());
        ps.setString(6, dataDeposito );
        ps.setBytes(7, pag.getComprovanteDeposito() );
        ps.setString(8, pag.getNomeComprovanteDeposito());
        ps.setDouble(9, pag.getValorDeposito() );
        ps.executeUpdate();
    }
    
    public void atualizar(Pagamento pag) throws SQLException {
        String query;
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        
        String dataPagamento = null;
        String dataDeposito = null;
        String mes = null;
        
        if(pag.getMes()!=null){
            mes=fmt.format(pag.getMes());
        }
        if(pag.getDataDeposito()!=null){
            dataDeposito=fmt.format(pag.getDataDeposito());
        }
        if(pag.getDataPagamento()!=null){
            dataPagamento=fmt.format(pag.getDataPagamento());
        }
        
        query = "update pagamento ";
        query += "SET mes="+set(mes)+", id_apartemento="+set(pag.getApartemento())+", data_pagamento="+set(dataPagamento)+", comprovante_pagamento="+set(pag.getComprovantePagamento())+", nome_comprovante_pagamento="+set(pag.getNomeComprovantePagamento())+", data_deposito="+set(dataDeposito)+", comprovante_deposito="+set(pag.getComprovanteDeposito())+", nome_comprovante_deposito="+set(pag.getNomeComprovanteDeposito())+",valor_deposito="+set(pag.getValorDeposito());
        query += " where id="+pag.getId();
        PreparedStatement ps = data.getConection().prepareStatement(query);
        ps.execute(query);
    }
    
    public List<Pagamento> obterPagamentos(Integer apartamento) throws SQLException{
        String query = "SELECT * FROM pagamento" ;
         query+=" where id_apartemento="+apartamento+" ";
        query+=" order by mes;";
        PreparedStatement ps = data.getConection().prepareStatement(query);
        
        ResultSet result = ps.executeQuery(query);
        ArrayList<Pagamento> retorno = new ArrayList<>();
        while(result.next()){
            Pagamento pag = new Pagamento();
            pag.setId(result.getInt("id"));
            pag.setMes(result.getDate("mes"));
            pag.setApartemento(result.getInt("id_apartemento"));
            pag.setDataPagamento(result.getDate("data_pagamento"));
            pag.setComprovantePagamento(result.getBytes("comprovante_pagamento"));
            pag.setNomeComprovantePagamento(result.getString("nome_comprovante_pagamento"));
            pag.setDataDeposito(result.getDate("data_deposito"));
            pag.setComprovanteDeposito(result.getBytes("comprovante_deposito"));
            pag.setNomeComprovanteDeposito(result.getString("nome_comprovante_deposito"));
            pag.setValorDeposito(result.getDouble("valor_deposito"));
            retorno.add(pag);
        }
        return retorno;
    }
    
    public List<Pagamento> obterPagamentosPorDatas(FiltroPagamento filtro) throws SQLException{
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String query = "SELECT * FROM pagamento" ;
                query+=" where id_apartemento="+filtro.getApartamento();
        if(filtro.getMesInicial()!=null)
            filtro.getMesInicial().setDate(1);
            query+=" and mes >= '"+fmt.format(filtro.getMesInicial())+"'";
        if(filtro.getMesFinal()!=null)
            filtro.getMesFinal().setDate(30);
            query+=" and mes <= '"+fmt.format(filtro.getMesFinal())+"'";
        if(filtro.getPagamentoInicial()!=null)
            query+=" and data_pagamento BETWEEN "+filtro.getPagamentoInicial()+" AND "+filtro.getPagamentoFinal()+" ";
        if(filtro.getDepositoFinal()!=null)
            query+=" and data_deposito BETWEEN "+filtro.getDepositoInicial()+" AND "+filtro.getDepositoFinal()+" ";
        query+=" order by mes;";
        PreparedStatement ps = data.getConection().prepareStatement(query);

        ResultSet result = ps.executeQuery(query);
        ArrayList<Pagamento> retorno = new ArrayList<>();
        while(result.next()){
            Pagamento pag = new Pagamento();
            pag.setId(result.getInt("id"));
            pag.setMes(result.getDate("mes"));
            pag.setApartemento(result.getInt("id_apartemento"));
            pag.setDataPagamento(result.getDate("data_pagamento"));
            pag.setComprovantePagamento(result.getBytes("comprovante_pagamento"));
            pag.setNomeComprovantePagamento(result.getString("nome_comprovante_pagamento"));
            pag.setDataDeposito(result.getDate("data_deposito"));
            pag.setComprovanteDeposito(result.getBytes("comprovante_deposito"));
            pag.setNomeComprovanteDeposito(result.getString("nome_comprovante_deposito"));
            pag.setValorDeposito(result.getDouble("valor_deposito"));
            retorno.add(pag);
        }
        return retorno;
    }
}
