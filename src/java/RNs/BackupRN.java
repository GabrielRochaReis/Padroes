/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RNs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
        try {
            InputStream inputStream = new FileInputStream(OUTPUT_ZIP_FILE);
            return new DefaultStreamedContent(inputStream);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BackupRN.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void zipIt(String zipFile) {
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
}
