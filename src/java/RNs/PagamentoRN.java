/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RNs;

import DAO.PagamentoDAO;
import Model.Pagamento;
import java.io.Serializable;
import java.sql.SQLException;

/**
 *
 * @author Gabriel Rocha
 */
public class PagamentoRN implements Serializable{
    private final PagamentoDAO pagamentoDAO;
    private static PagamentoRN pagamentoRN;
    
    private PagamentoRN() {
        pagamentoDAO = PagamentoDAO.getInstance();
    }
    
    public static PagamentoRN getInstance(){
        if(pagamentoRN==null) {
            pagamentoRN = new PagamentoRN();
        }
        
        return pagamentoRN;
    }
    
    public void iserirAtualizar(Pagamento pagamento) throws SQLException{
        if(pagamento.getId()==null){
            pagamentoDAO.persistir(pagamento);
        } else {
            pagamentoDAO.atualizar(pagamento);
        }
    }
}
