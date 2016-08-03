/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editortexto;

import frameworkinterfaces.IDocument;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author aluno
 */
public class TextDocument implements IDocument{

    static final int TAMANHO_BUFFER = 4096; // 4kb
    
    @Override
    public void open() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //    /home/aluno/smart-pointer.txt
    
    @Override
    public void comprass(File arqEntrada) {
        int cont;
       byte[] dados = new byte[TAMANHO_BUFFER];
                   
       BufferedInputStream origem = null;
       FileInputStream streamDeEntrada = null;
       FileOutputStream destino = null;
       ZipOutputStream saida = null;
       ZipEntry entry = null;
              
       try {
            destino = new FileOutputStream(new File(arqEntrada.getAbsolutePath().split("\\.")[0]+".zip"));
            saida = new ZipOutputStream(new BufferedOutputStream(destino));
            streamDeEntrada = new FileInputStream(arqEntrada);
            origem = new BufferedInputStream(streamDeEntrada, TAMANHO_BUFFER);
            entry = new ZipEntry(arqEntrada.getName());
            saida.putNextEntry(entry);
                       
            while((cont = origem.read(dados, 0, TAMANHO_BUFFER)) != -1) {
                saida.write(dados, 0, cont);
            }
            origem.close();
            saida.close();
        } catch(IOException e) {
            System.out.println("(e.getMessage())");
        }
    }

    @Override
    public void deComprass(File arquivo) throws  FileNotFoundException,IOException{
        int cont;
 byte[] dados = new byte[TAMANHO_BUFFER];
        FileInputStream input = new FileInputStream(arquivo);
        FileOutputStream output = new FileOutputStream(new File(arquivo.getAbsolutePath().split("\\.")[0]+".txt"));
        BufferedInputStream origem = new BufferedInputStream(input);
        ZipInputStream saida = new ZipInputStream(origem);
        ZipEntry entry = saida.getNextEntry();

        
        while((cont = saida.read(dados)) > -1) {
                output.write(dados, cont, 0);
            }
        output.close();
        saida.close();
    }
    
}
