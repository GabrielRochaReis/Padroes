/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel Rocha
 */
public class UsuarioDAO extends BaseDao{
    
    public Usuario validarUsuario(String login, String senha){
        Usuario usuario= null;
        
        String query = "SELECT * FROM Usuario where login like '"+login+"' and senha like '"+senha+"'";
        try {
            PreparedStatement ps = data.getConection().prepareStatement(query);
            
            ResultSet rs = ps.executeQuery();
            
            ArrayList<Object> list= new ArrayList<>();
            while(rs.next()){
                usuario = new Usuario();
                System.out.println(rs.getString("login")+rs.getString("senha")); 
                usuario.setLogin(rs.getString("login"));
                usuario.setSenha(rs.getString("senha"));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuario;
    }
}
