/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.faces.bean.ManagedBean;

/**
 *
 * @author Gabriel Rocha
 */
@ManagedBean(name="telaInicialController")
public class TelaInicialController {
    
    public String inquilino(){
        return "cadastro_inquilino";
    }
    
    public String proprietario(){
        return "cadastro_proprietario";
    }
    
    public String apartamento(){
        return "cadastro_apartamento";
    }
    
    public String pesquisarInquilino(){
        return "pesquisarInquilino";
    }
    
    public String pesquisarProprietario(){
        return "pesquisarProprietario";
    }
    
    public String pesquisarApartamento(){
        return "pesquisarApartamento";
    }
}
