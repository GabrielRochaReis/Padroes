/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RNs;

import DAO.ApartamentoDAO;
import Model.Apartamento;
import java.sql.SQLException;

/**
 *
 * @author Gabriel Rocha
 */
public class ApartamentoRN {
    
    private final ApartamentoDAO apartamentoDAO;
    private static ApartamentoRN apartamentoRN;
    
    private ApartamentoRN() {
        apartamentoDAO = ApartamentoDAO.getInstance();
    }
    
    public static ApartamentoRN getInstance(){
        if(apartamentoRN==null) {
            apartamentoRN = new ApartamentoRN();
        }
        
        return apartamentoRN;
    }
    
    public void iserirAtualizar(Apartamento apartamento) throws SQLException{
        if(apartamento.getId()==null){
            apartamentoDAO.persistir(apartamento);
        } else {
            apartamentoDAO.atualizar(apartamento);
        }
    }
    
}
