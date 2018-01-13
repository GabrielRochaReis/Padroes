/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import DAO.ApartamentoDAO;
import DAO.InquilinoDAO;
import Model.Apartamento;
import Model.Inquilino;
import RNs.BackupRN;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Gabriel Rocha
 */
@ManagedBean(name = "telaInicialController")
public class TelaInicialController {

    private List<Apartamento> vencidos;
    private ApartamentoDAO apartamentoDAO = ApartamentoDAO.getInstance();
    private InquilinoDAO inquilinoDAO = InquilinoDAO.getInstance();

    @PostConstruct
    public void init() {
        try {
            vencidos = apartamentoDAO.obterApartamentoVencidos();
        } catch (SQLException ex) {
            Logger.getLogger(TelaInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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

    public String dias(String id) {
        String retorno = "Não Pago";
        if (id != null) {
            Inquilino inquilino = null;
            try {
                inquilino = inquilinoDAO.obterInquilinoPorId(id);
            } catch (SQLException ex) {
                Logger.getLogger(TelaInicialController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            int dayMonth = calendar.get(Calendar.DAY_OF_MONTH);
            if (inquilino != null && inquilino.getDataBoleto() != null) {
                Integer day = inquilino.getDataBoleto() - dayMonth;
                if (day > 0) {
                    retorno = "Falta " + day + " para vencer o pagamento.";
                } else if (day == 0) {
                    retorno = "Pagamento vence hoje";
                } else {
                    retorno = "Pagamento vencido";
                }
            }
        }
        return retorno;
    }

    public StreamedContent backup() {
        return BackupRN.downloadBackup();
    }

    public List<Apartamento> getVencidos() {
        return vencidos;
    }

    public void setVencidos(List<Apartamento> vencidos) {
        this.vencidos = vencidos;
    }

    public ApartamentoDAO getApartamentoDAO() {
        return apartamentoDAO;
    }

    public void setApartamentoDAO(ApartamentoDAO apartamentoDAO) {
        this.apartamentoDAO = apartamentoDAO;
    }
}
