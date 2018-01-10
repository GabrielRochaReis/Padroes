/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import RNs.BackupRN;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Gabriel Rocha
 */
@ManagedBean(name = "telaInicialController")
public class TelaInicialController {

    public String inquilino() {
        return "cadastro_inquilino";
    }

    public String proprietario() {
        return "cadastro_proprietario";
    }

    public String apartamento() {
        return "cadastro_apartamento";
    }

    public String pesquisarInquilino() {
        return "pesquisarInquilino";
    }

    public String pesquisarProprietario() {
        return "pesquisarProprietario";
    }

    public String pesquisarApartamento() {
        return "pesquisarApartamento";
    }

    public String relatorioPagamento() {
        return "relatorioPagamento";
    }

    public String relatorioApartamento() {
        return "relatorioApartamento";
    }

    public StreamedContent backup() {
        System.out.println("controllers.TelaInicialController.backup()");
       return BackupRN.downloadBackup();
    }
}
