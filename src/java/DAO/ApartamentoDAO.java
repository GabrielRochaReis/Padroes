/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Apartamento;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Gabriel Rocha
 */
public class ApartamentoDAO extends BaseDao {

    private static ApartamentoDAO apartamentoDAO;

    private ApartamentoDAO() {
        super();
    }

    public static ApartamentoDAO getInstance() {
        if (apartamentoDAO == null) {
            apartamentoDAO = new ApartamentoDAO();
        }
        return apartamentoDAO;
    }

    public List<Apartamento> obterApartamentoAutoComplete(Apartamento apartamento) throws SQLException {
        String query = "SELECT * FROM apartamento";
        query += " WHERE 1=1 ";
        if (apartamento != null) {
            if (apartamento.getId() == null || apartamento.getId() == 0) {
                if (apartamento.getNumero() != null) {
                    query += " and (edificio like '" + apartamento.getNumero() + "%' "
                            + " or numero like '" + apartamento.getNumero() + "%') ";
                }
                if (apartamento.getProprietario() != null) {
                    query += " and id_proprietario = " + apartamento.getProprietario() + "";
                }
                if (apartamento.getInquilino() != null) {
                    query += " and id_inquilino = " + apartamento.getInquilino() + "";
                }
            } else {
                query += " and id = " + apartamento.getId();
            }
        }
        query += " order by numero;";
        PreparedStatement ps = data.getConection().prepareStatement(query);

        ResultSet result = ps.executeQuery(query);
        ArrayList<Apartamento> retorno = resultList(result);
        return retorno;
    }

    private Apartamento uniqueResult(ResultSet result) throws SQLException {
        Apartamento a = new Apartamento();
        result.next();
        a.setId(result.getInt("id"));
        a.setEdificio(result.getString("edificio"));
        a.setNumero(result.getString("numero"));
        a.setInquilino(result.getInt("id_inquilino"));
        a.setProprietario(result.getInt("id_proprietario"));
        a.setAluguel(result.getDouble("aluguel"));
        a.setObservacao(result.getString("observacao"));
        a.setNomeContrato(result.getString("nome_contrato"));
        loadContrato(a);
        return a;
    }

    private ArrayList<Apartamento> resultList(ResultSet result) throws SQLException {
        ArrayList<Apartamento> retorno = new ArrayList<>();
        while (result.next()) {
            Apartamento a = new Apartamento();
            a.setId(result.getInt("id"));
            a.setEdificio(result.getString("edificio"));
            a.setNumero(result.getString("numero"));
            a.setInquilino(result.getInt("id_inquilino"));
            a.setProprietario(result.getInt("id_proprietario"));
            a.setAluguel(result.getDouble("aluguel"));
            a.setObservacao(result.getString("observacao"));
            a.setNomeContrato(result.getString("nome_contrato"));
            loadContrato(a);
            retorno.add(a);
        }
        return retorno;
    }

    public List<Apartamento> obterApartamentoTodos() throws SQLException {
        String query = "SELECT * FROM apartamento ";
        query += "order by numero;";
        PreparedStatement ps = data.getConection().prepareStatement(query);

        ResultSet result = ps.executeQuery(query);
        ArrayList<Apartamento> retorno = resultList(result);
        return retorno;
    }

    public Apartamento obterApartamentoPorId(String id) throws SQLException {
        String query = "SELECT * FROM apartamento";
        if (id != null) {
            query += " WHERE id like " + id + "";
        }
        query += ";";
        PreparedStatement ps = data.getConection().prepareStatement(query);

        ResultSet result = ps.executeQuery(query);
        Apartamento a = uniqueResult(result);

        return a;
    }

    public void persistir(Apartamento apartamento) throws SQLException {
        String query = null;
        gravarContrato(apartamento);
        query = "insert into Apartamento ( edificio, numero, id_inquilino, id_proprietario, aluguel, observacao, nome_contrato)";
        query += "values( '" + apartamento.getEdificio() + "', '" + apartamento.getNumero() + "', "
                + set(apartamento.getInquilino()) + ", " + set(apartamento.getProprietario()) + ", "
                + apartamento.getAluguel() + ", '" + (apartamento.getObservacao() == null ? "Sem Observações" : apartamento.getObservacao()) + "', '" + apartamento.getNomeContrato() + "' );";
        PreparedStatement ps = data.getConection().prepareStatement(query);

        ps.execute(query);
    }

    public void atualizar(Apartamento apartamento) throws SQLException {
        String query = null;
        gravarContrato(apartamento);
        query = "update Apartamento ";
        query += "set edificio=" + set(apartamento.getEdificio()) + ", numero=" + set(apartamento.getNumero());
        query += ", observacao=" + set(apartamento.getObservacao());
        query += ", nome_contrato=" + set(apartamento.getNomeContrato());
        if (apartamento.getInquilino() != 0 && apartamento.getInquilino() != null) {
            query += ", id_inquilino=" + set(apartamento.getInquilino());
        }
        if (apartamento.getProprietario() != 0 && apartamento.getProprietario() != null) {
            query += ", id_proprietario=" + set(apartamento.getProprietario());
        }
        query += ", aluguel=" + set(apartamento.getAluguel()) + " where id=" + apartamento.getId() + ";";
        PreparedStatement ps = data.getConection().prepareStatement(query);

        ps.execute(query);
    }

    public boolean gravarContrato(Apartamento apartamento) {
        if (apartamento.getContrato() != null) {
            try {
                InputStream file = apartamento.getContrato();
                String suffix = "pdf";
                apartamento.setNomeContrato(apartamento.getNumero() + "-" + (apartamento.getEdificio() == null ? "" : apartamento.getEdificio()) + "." + suffix);
                File files = null;
                String path = getPathContrato();
                File diretorio = new File(path); // ajfilho é uma pasta!
                if (!diretorio.exists()) {
                    diretorio.mkdirs(); //mkdir() cria somente um diretório, mkdirs() cria diretórios e subdiretórios.
                }
                files = new File(path + apartamento.getNomeContrato());
                OutputStream outputStream = new FileOutputStream(files);
                IOUtils.copy(file, outputStream);
                outputStream.close();
            } catch (Exception ex) {
                RequestContext.getCurrentInstance()
                        .showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possivel salvar o Comprovante de deposito. Erro :" + ex.getMessage()));
                return false;
            }
        }
        return true;
    }

    private static String getPath() {
        return "C:\\Users\\Gabriel Rocha\\Documents\\";
    }

    private void loadContrato(Apartamento apartamento) {
        if (apartamento.getNomeContrato() != null) {
            try {
                InputStream inputStream = new FileInputStream(getPathContrato() + apartamento.getNomeContrato());
                apartamento.setContrato(inputStream);
            } catch (FileNotFoundException ex) {
                apartamento.setNomeContrato(null);
            }
        }
    }

    private static String getPathContrato() {
        return getPath() + "Documentos\\Contrato\\";
    }
}
