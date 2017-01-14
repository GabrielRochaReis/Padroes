/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RNs;

import DAO.InquilinoDAO;
import Model.Inquilino;
import java.sql.SQLException;

/**
 *
 * @author Gabriel Rocha
 */
public class InquilinoRN {
    
    private final InquilinoDAO inquilinoDAO;
    private static InquilinoRN inquilinoRN;
    
    private InquilinoRN() {
        inquilinoDAO = InquilinoDAO.getInstance();
    }
    
    public static InquilinoRN getInstance(){
        if(inquilinoRN==null) {
            inquilinoRN = new InquilinoRN();
        }
        
        return inquilinoRN;
    }
    
    public void iserirAtualizar(Inquilino inquilino) throws SQLException{
        if(inquilino.getId()==null){
            inquilinoDAO.persistir(inquilino);
        } else {
            inquilinoDAO.atualizar(inquilino);
        }
    }
    
    public void remover(int id) throws SQLException{
        inquilinoDAO.remover(id);
    }
    
}
