/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Proprietario;
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


public class ProprietarioDAO extends BaseDao{

    private static ProprietarioDAO proprietarioDAO;
    
    public static ProprietarioDAO getInstance(){
        if(proprietarioDAO==null){
            proprietarioDAO=new ProprietarioDAO();
        }
        return proprietarioDAO;
    }
    
    private ProprietarioDAO() {
        super();
    }
       
    public void persistir(Proprietario p) throws SQLException {
        String query;
        query = "insert into Proprietario (id , nome, telefone, email, num_conta, agencia, instituicao, operacao, endereco, ativo, data_deposito, tipo_conta, telefone1, telefone2) ";
        query += "values(null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement ps = data.getConection().prepareStatement(query);
        ps.setString(1, p.getNome());
        ps.setString(2, p.getTelefone());
        ps.setString(3, p.getEmail());
        ps.setString(4, p.getNumConta());
        ps.setString(5, p.getAgencia());
        ps.setString(7, p.getOperacao());
        ps.setString(6, p.getInstituicao());
        ps.setString(8, p.getEndereco());
        ps.setBoolean(9, p.isAtivo());
        ps.setString(10, p.getDataDeposito());
        ps.setString(11, p.getTipoConta());
        ps.setString(12, p.getTelefone1());
        ps.setString(13, p.getTelefone2());
        ps.executeUpdate();
    }
    
    public void atualizar(Proprietario p) throws SQLException {
        String query;
        PreparedStatement ps;
        
        query = "update Proprietario ";
        query += "SET nome= ?, telefone= ?, email= ?, num_conta= ?, operacao= ?, instituicao= ?, agencia= ?, endereco= ?, ativo= ?, data_deposito= ?, tipo_conta= ?, telefone1= ?, telefone2= ?"
                +" where id="+p.getId()+";";
        ps = data.getConection().prepareStatement(query);
        ps.setString(1, p.getNome());
        ps.setString(2, p.getTelefone());
        ps.setString(3, p.getEmail());
        ps.setString(4, p.getNumConta());
        ps.setString(5, p.getOperacao());
        ps.setString(6, p.getInstituicao());
        ps.setString(7, p.getAgencia());
        ps.setString(8, p.getEndereco());
        ps.setBoolean(9, p.isAtivo());
        ps.setString(10, p.getDataDeposito());
        ps.setString(11, p.getTipoConta());
        ps.setString(12, p.getTelefone1());
        ps.setString(13, p.getTelefone2());
        
        ps.executeUpdate();
    }

    public List<Proprietario> obterProprietarioPorNome(String nome) throws SQLException{
        String query = "SELECT * FROM proprietario" ;
        if(nome!=null)
            query+=" WHERE UCASE(nome) like '%"+nome.toUpperCase()+"%'";
        query+=";";
        PreparedStatement ps = data.getConection().prepareStatement(query);

        ResultSet result = ps.executeQuery(query);
        ArrayList<Proprietario> retorno = new ArrayList<>();
        while(result.next()){
            Proprietario i = new Proprietario();
            montarProprietario(i, result);
            retorno.add(i);
        }
        return retorno;
    }

    private void montarProprietario(Proprietario i, ResultSet result) throws SQLException {
        i.setId(result.getInt("id"));
        i.setNome(result.getString("nome"));
        i.setAtivo(result.getBoolean("ativo"));
        i.setDataDeposito(result.getString("data_deposito"));
        i.setEmail(result.getString("email"));
        i.setTelefone(result.getString("telefone"));
        i.setTelefone1(result.getString("telefone1"));
        i.setTelefone2(result.getString("telefone2"));
        i.setEndereco(result.getString("endereco"));
        i.setNumConta(result.getString("num_conta"));
        i.setOperacao(result.getString("operacao"));
        i.setInstituicao(result.getString("instituicao"));
        i.setAgencia(result.getString("agencia"));
        i.setTipoConta(result.getString("tipo_conta"));
    }
    
    public List<Proprietario> obterProprietarioAtivoPorNome(String nome) throws SQLException{
        String query = "SELECT * FROM proprietario" ;
        if(nome!=null)
            query+=" WHERE UCASE(nome) like '%"+nome.toUpperCase()+"%'";
        query+=";";
        PreparedStatement ps = data.getConection().prepareStatement(query);

        ResultSet result = ps.executeQuery(query);
        ArrayList<Proprietario> retorno = new ArrayList<>();
        while(result.next()){
            Proprietario i = new Proprietario();
            montarProprietario(i, result);
            retorno.add(i);
        }
        return retorno;
    }
    
    public Proprietario obterProprietarioPorId(String id) throws SQLException{
        String query = "SELECT * FROM proprietario" ;
                if(id!=null)
                    query+=" WHERE id like "+id+"";
                query+=";";
            PreparedStatement ps = data.getConection().prepareStatement(query);
            
            ResultSet result = ps.executeQuery(query);
            result.next();
            Proprietario i=new Proprietario();
            montarProprietario(i, result);
            return i;
    }
    
}
