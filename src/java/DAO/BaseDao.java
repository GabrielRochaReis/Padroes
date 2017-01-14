/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/**
 *
 * @author Gabriel Rocha
 */
public abstract class BaseDao {
    DataSource data;

    public BaseDao() {
        data = new DataSource();
    }
    
    public String setArq(byte[] arq){
        if(arq == null)
            return arq.toString();
        else
            return "'"+arq+"'";
    }
    
    public Object set(Object o){
        if(o == null)
            return o;
        else
            return "'"+o+"'";
    }
//   public abstract void persistir(Entidade e);
//   
//   public abstract void atualizar(Entidade e);
    
}
