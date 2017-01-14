/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import com.mysql.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Gabriel Rocha
 */
public class DataSource {
    
    private String hostname;
    private int porta;
    private String database;
    private String username;
    private String password;
    
    private Connection conection;

    public DataSource() {
       try{
           hostname = "localhost";
           porta=3306;
           database="figueredobd";
           username="root";
           password="root";
           String url="jdbc:mysql://"+hostname+":"+porta+"/"+database+"?useTimezone=false"; 
           
           DriverManager.registerDriver( new Driver());
           conection = DriverManager.getConnection(url,username,password);
           
           System.out.println("Conectado");
       } catch(SQLException e){
           System.err.println("Erro da conexão: "+e.getMessage());
       } catch(Exception e){
           System.err.println("Erro da geral: "+e.getMessage());
       }
    }
    
    public Connection getConection(){
        return conection;
    } 
    
    public void closeDataSource(){
        try{
            conection.close();
        } catch(SQLException e){
           System.err.println("Erro da conexão: "+e.getMessage());
        } catch(Exception e){
           System.err.println("Erro da geral: "+e.getMessage());
       }
    }
}
