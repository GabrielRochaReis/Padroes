/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RNs;

import DAO.ApartamentoDAO;
import DAO.InquilinoDAO;
import DAO.PagamentoDAO;
import DAO.ProprietarioDAO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Gabriel Rocha
 */
public class BackupRN {

    private List<String> fileList;
    private static final String OUTPUT_ZIP_FILE = "C:\\Users\\Gabriel Rocha\\Documents\\Backup.zip";
    private static final String SOURCE_FOLDER = "C:\\Users\\Gabriel Rocha\\Documents\\Documentos"; // SourceFolder path

    public BackupRN() {
        fileList = new ArrayList< String>();
    }

    public static void fazerBackup() {
        BackupRN appZip = new BackupRN();
        appZip.generateFileList(new File(SOURCE_FOLDER));
        appZip.zipIt(OUTPUT_ZIP_FILE);
    }

    public static StreamedContent downloadBackup() {
        fazerBackup();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            InputStream inputStream = new FileInputStream(OUTPUT_ZIP_FILE);
            return new DefaultStreamedContent(inputStream, "application/zip", "Backup-"+fmt.format(new Date())+".zip");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BackupRN.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void zipIt(String zipFile) {
        try {
            inserts();
        } catch (SQLException ex) {
            Logger.getLogger(BackupRN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BackupRN.class.getName()).log(Level.SEVERE, null, ex);
        }
        File delete = new File(OUTPUT_ZIP_FILE);
        delete.delete();
        byte[] buffer = new byte[16094];
        String source = new File(SOURCE_FOLDER).getName();
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(fos);
            FileInputStream in = null;
            for (String file : this.fileList) {
                ZipEntry ze = new ZipEntry(source + File.separator + file);
                zos.putNextEntry(ze);
                try {
                    in = new FileInputStream(SOURCE_FOLDER + File.separator + file);
                    int len;
                    while ((len = in.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                } finally {
                    in.close();
                }
            }
            zos.closeEntry();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                zos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void generateFileList(File node) {
        if (node.isFile()) {
            fileList.add(generateZipEntry(node.toString()));
        }
        if (node.isDirectory()) {
            String[] subNote = node.list();
            for (String filename : subNote) {
                generateFileList(new File(node, filename));
            }
        }
    }

    private String generateZipEntry(String file) {
        return file.substring(SOURCE_FOLDER.length() + 1, file.length());
    }

    public void inserts() throws SQLException, IOException {
        String backup = "";
        InquilinoDAO inquilinoDAO = InquilinoDAO.getInstance();
        backup += inquilinoDAO.insertsInquilino();
        ProprietarioDAO proprietarioDAO = ProprietarioDAO.getInstance();
        backup += proprietarioDAO.insertsProprietario();
        ApartamentoDAO apartamentoDAO = ApartamentoDAO.getInstance();
        backup += apartamentoDAO.insertsApartamento();
        PagamentoDAO pagamentoDAO = PagamentoDAO.getInstance();
        backup += pagamentoDAO.insertsPagamento();
        File arquivo = new File(SOURCE_FOLDER + "\\inserts.txt");
        FileWriter fw = new FileWriter(arquivo);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(backup);
        bw.flush();
        bw.close();
    }

}
