/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Inquilino;
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
public class InquilinoDAO extends BaseDao{

    public static InquilinoDAO getInstance(){
        if(inquilinoDAO==null){
            inquilinoDAO= new InquilinoDAO();
        }
        return inquilinoDAO;
    }
    
    public void persistir(Inquilino i) throws SQLException {
        
        String query = "insert into Inquilino (id , nome, telefone, telefone1, telefone2, email, cpf, rg, data_boleto, mes_contrato, ativo) ";
            query += "values(null, '"+i.getNome()+"', '"+i.getTelefone()+"', '"+i.getTelefone1()+"', '"+i.getTelefone2()+"', '"+i.getEmail()+"', '"+i.getCpf()+"', '"+i.getRg()+"', "+i.getDataBoleto()+", '"+i.getMesContrato()+"', "+i.isAtivo()+");";
            PreparedStatement ps = data.getConection().prepareStatement(query);
            
            ps.execute(query);
    }

    public void atualizar(Inquilino i) throws SQLException {
        String query;
        
        query = "update Inquilino ";
        query += "set"  ;
        query += " nome="+set(i.getNome());
        query += ", telefone="+set(i.getTelefone());
        query += ", telefone1="+set(i.getTelefone1());
        query += ", telefone2="+set(i.getTelefone2());
        query += ", email="+set(i.getEmail());
        query += ", cpf="+set(i.getCpf());
        query += ", rg="+set(i.getRg());
        query += ", data_boleto="+i.getDataBoleto();
        query += ", mes_contrato="+i.getMesContrato();
        query += ", ativo="+i.isAtivo();
        query += " where id="+i.getId()+";";
        PreparedStatement ps = data.getConection().prepareStatement(query);

        ps.execute(query);
    }
    
    public void remover(int id) throws SQLException{
        String query;
        query = "DELETE from Inquilino where id = "+id+";";

        PreparedStatement ps = data.getConection().prepareStatement(query);
        ps.execute(query);
    }

    public List<Inquilino> obterInquilinoPorNome(String nome) throws SQLException {
        String query = "SELECT * FROM inquilino" ;
                if(nome!=null)
                    query+=" WHERE UCASE(nome) like '%"+nome.toUpperCase()+"%'";
                query+=";";
            PreparedStatement ps = data.getConection().prepareStatement(query);
            
            ResultSet result = ps.executeQuery(query);
            ArrayList<Inquilino> retorno = new ArrayList<>();
            while(result.next()){
                Inquilino i = new Inquilino();
                montarInquilino(i, result);
                retorno.add(i);
            }
            return retorno;
    }

    private void montarInquilino(Inquilino i, ResultSet result) throws SQLException {
        i.setId(result.getInt("id"));
        i.setNome(result.getString("nome"));
        i.setCpf(result.getString("cpf"));
        i.setEmail(result.getString("email"));
        i.setTelefone(result.getString("telefone"));
        i.setTelefone1(result.getString("telefone1"));
        i.setTelefone2(result.getString("telefone2"));
        i.setDataBoleto(result.getInt("data_boleto"));
        i.setMesContrato(result.getInt("mes_contrato"));
        i.setRg(result.getString("rg"));
        i.setAtivo(result.getBoolean("ativo"));
    }
    
    public List<Inquilino> obterInquilinoAtivoPorNome(String nome) throws SQLException {
        String query = "SELECT * FROM inquilino" ;
                if(nome!=null)
                    query+=" WHERE UCASE(nome) like '%"+nome.toUpperCase()+"%'";
                query+=";";
            PreparedStatement ps = data.getConection().prepareStatement(query);
            
            ResultSet result = ps.executeQuery(query);
            ArrayList<Inquilino> retorno = new ArrayList<>();
            while(result.next()){
                Inquilino i = new Inquilino();
                montarInquilino(i, result);
                retorno.add(i);
            }
            return retorno;
    }
    
    public Inquilino obterInquilinoPorId(String id) throws SQLException {
        String query = "SELECT * FROM inquilino" ;
                if(id!=null)
                    query+=" WHERE id like "+id+"";
                query+=";";
            PreparedStatement ps = data.getConection().prepareStatement(query);
            
            ResultSet result = ps.executeQuery(query);
            result.next();
            Inquilino i = new Inquilino();
            montarInquilino(i, result);
            return i;
    }
    
    private InquilinoDAO() {
        super();
    }
    
    private static InquilinoDAO inquilinoDAO;
}

