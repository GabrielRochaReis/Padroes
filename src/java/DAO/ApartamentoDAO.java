/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Apartamento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gabriel Rocha
 */
public class ApartamentoDAO extends BaseDao{
    
    private static ApartamentoDAO apartamentoDAO;

    private ApartamentoDAO() {
        super();
    }
    
    public static ApartamentoDAO getInstance(){
        if(apartamentoDAO==null){
            apartamentoDAO = new ApartamentoDAO();
        }
        return apartamentoDAO;
    }
    
    public List<Apartamento> obterApartamentoAutoComplete(Apartamento apartamento) throws SQLException {
        String query = "SELECT * FROM apartamento" ;
                    query+=" WHERE 1=1 ";
                if(apartamento!=null){
                    if(apartamento.getNumero()!=null){
                                query+= " and (edificio like '%"+apartamento.getNumero()+"%' "
                                + " or numero like '%"+apartamento.getNumero()+"%') ";
                    }
                    if(apartamento.getProprietario() !=null){
                        query+= " and id_proprietario = "+apartamento.getProprietario()+"";
                    }
                    if(apartamento.getInquilino() !=null){
                        query+= " and id_inquilino = "+apartamento.getInquilino()+"";
                    }
                }
                query+=";";
            PreparedStatement ps = data.getConection().prepareStatement(query);
            
            
            ResultSet result = ps.executeQuery(query);
            ArrayList<Apartamento> retorno = new ArrayList<>();
            while(result.next()){
            Apartamento a = new Apartamento();
            a.setId(result.getInt("id"));
            a.setEdificio(result.getString("edificio"));
            a.setNumero(result.getString("numero"));
            a.setInquilino(result.getInt("id_inquilino"));
            a.setProprietario(result.getInt("id_proprietario"));
            a.setAluguel(result.getDouble("aluguel"));
            retorno.add(a);
            }
            return retorno;
    }
    
    public List<Apartamento> obterApartamentoTodos() throws SQLException {
        String query = "SELECT * FROM apartamento " ;
                query+="order by numero;";
            PreparedStatement ps = data.getConection().prepareStatement(query);
            
            
            ResultSet result = ps.executeQuery(query);
            ArrayList<Apartamento> retorno = new ArrayList<>();
            while(result.next()){
            Apartamento a = new Apartamento();
            a.setId(result.getInt("id"));
            a.setEdificio(result.getString("edificio"));
            a.setNumero(result.getString("numero"));
            a.setInquilino(result.getInt("id_inquilino"));
            a.setProprietario(result.getInt("id_proprietario"));
            a.setAluguel(result.getDouble("aluguel"));
            retorno.add(a);
            }
            return retorno;
    }
    
    public Apartamento obterApartamentoPorId(String id) throws SQLException {
        String query = "SELECT * FROM apartamento" ;
                if(id!=null)
                    query+=" WHERE id like "+id+"";
                query+=";";
            PreparedStatement ps = data.getConection().prepareStatement(query);
            
            ResultSet result = ps.executeQuery(query);
            result.next();
            Apartamento a = new Apartamento();
            a.setId(result.getInt("id"));
            a.setEdificio(result.getString("edificio"));
            a.setNumero(result.getString("numero"));
            a.setInquilino(result.getInt("inquilino"));
            a.setProprietario(result.getInt("proprietario"));
            a.setAluguel(result.getDouble("aluguel"));
            
            return a;
    }
    
    public void persistir(Apartamento apartamento) throws SQLException{
        String query = null;
            query = "insert into Apartamento (id , edificio, numero, id_inquilino, id_proprietario, aluguel)";
            query += "values(null, '"+apartamento.getEdificio()+"', '"+apartamento.getNumero()+"', "+set(apartamento.getInquilino())+", "+set(apartamento.getProprietario())+", "+apartamento.getAluguel()+");";
            PreparedStatement ps = data.getConection().prepareStatement(query);
            
            ps.execute(query);
    }
    
    public void atualizar(Apartamento apartamento) throws SQLException {
        String query = null;
            query = "update Apartamento ";
            query += "set edificio="+set(apartamento.getEdificio())+", numero="+set(apartamento.getNumero())+", id_inquilino="+set(apartamento.getInquilino())+", id_proprietario="+set(apartamento.getProprietario())+", aluguel="+set(apartamento.getAluguel())+" where id="+apartamento.getId()+";";
            PreparedStatement ps = data.getConection().prepareStatement(query);
            
            ps.execute(query);
    }
}
