/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enum;

/**
 *
 * @author Gabriel Rocha
 */
public enum MesesEnum {
    JANEIRO("Janeiro",1),
    FEREREIRO("Fevereiro",2),
    MARCO("Marco",3),
    ABRIL("Abril",4),
    MAIO("Maio",5),
    JUNHO("Junho",6),
    JULHO("Julho",7),
    AGOSTO("Agosto",8),
    SETEMBRO("Setembro",9),
    OUTUBRO("Outubro",10),
    NOVEMBRO("Novembro",11),
    DEZEMBRO("Dezembro",12),
    ;

    String mes;
    int indice;
    
    private MesesEnum(String mes, int indice) {
        this.mes = mes;
        this.indice = indice;
        
    }
    
    public static String getMes(int m){
        for(MesesEnum mes : values()){
            if(mes.getIndice()==m){
                return mes.getMes();
            }
        }
        return null;
    }
    
    private MesesEnum(int indice) {
        this.indice = indice;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }
    
    
}
