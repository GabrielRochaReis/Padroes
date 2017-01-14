/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RNs;

import DAO.ProprietarioDAO;
import Model.Proprietario;
import java.sql.SQLException;

/**
 *
 * @author Gabriel Rocha
 */
public class ProprietarioRN {
    private final ProprietarioDAO proprietarioDAO;
    private static ProprietarioRN proprietarioRN;
    
    private ProprietarioRN() {
        proprietarioDAO = ProprietarioDAO.getInstance();
    }
    
    public static ProprietarioRN getInstance(){
        if(proprietarioRN==null) {
            proprietarioRN = new ProprietarioRN();
        }
        
        return proprietarioRN;
    }
    
    public void iserirAtualizar(Proprietario proprietario) throws SQLException{
        if(proprietario.getId()==null){
            proprietarioDAO.persistir(proprietario);
        } else {
            proprietarioDAO.atualizar(proprietario);
        }
    }
}
