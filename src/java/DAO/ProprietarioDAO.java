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
        query = "insert into Proprietario (id , nome, telefone, email, num_conta, agencia, instituicao, operacao, endereco, ativo, data_deposito) ";
        query += "values(null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

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
        ps.setInt(10, p.getDataDeposito());
        ps.executeUpdate();
    }
    
    public void atualizar(Proprietario p) throws SQLException {
        String query;
        PreparedStatement ps;
        
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String str=null;
        if(p.getDataDeposito()!=null){
            str = fmt.format(p.getDataDeposito());
        }
        query = "update Proprietario ";
        query += "SET nome="+set(p.getNome())+", telefone="+set(p.getTelefone())+", email="+set(p.getEmail())+", num_conta="+set(p.getNumConta())+", instituicao="+set(p.getInstituicao())+", operacao="+set(p.getOperacao())+", agencia="+set(p.getAgencia())+", endereco="+set(p.getEndereco())+", ativo="+p.isAtivo()+", data_deposito="+set(str)
                +" where id="+p.getId()+";";
        ps = data.getConection().prepareStatement(query);
        ps.execute(query);
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
            i.setId(result.getInt("id"));
            i.setNome(result.getString("nome"));
            i.setAtivo(result.getBoolean("ativo"));
            i.setDataDeposito(result.getInt("data_deposito"));
            i.setEmail(result.getString("email"));
            i.setTelefone(result.getString("telefone"));
            i.setEndereco(result.getString("endereco"));
            i.setNumConta(result.getString("num_conta"));
            i.setOperacao(result.getString("operacao"));
            i.setInstituicao(result.getString("instituicao"));
            i.setAgencia(result.getString("agencia"));
            retorno.add(i);
        }
        return retorno;
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
            i.setId(result.getInt("id"));
            i.setNome(result.getString("nome"));
            i.setAtivo(result.getBoolean("ativo"));
            i.setDataDeposito(result.getInt("data_deposito"));
            i.setEmail(result.getString("email"));
            i.setTelefone(result.getString("telefone"));
            i.setEndereco(result.getString("endereco"));
            i.setNumConta(result.getString("num_conta"));
            i.setOperacao(result.getString("operacao"));
            i.setInstituicao(result.getString("instituicao"));
            i.setAgencia(result.getString("agencia"));
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
            i.setId(result.getInt("id"));
            i.setNome(result.getString("nome"));
            i.setAtivo(result.getBoolean("ativo"));
            i.setDataDeposito(result.getInt("data_deposito"));
            i.setEmail(result.getString("email"));
            i.setTelefone(result.getString("telefone"));
            i.setEndereco(result.getString("endereco"));
            i.setNumConta(result.getString("num_conta"));
            i.setOperacao(result.getString("operacao"));
            i.setInstituicao(result.getString("instituicao"));
            i.setAgencia(result.getString("agencia"));
            return i;
    }
    
}
