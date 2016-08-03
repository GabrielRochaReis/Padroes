/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frameworkinterfaces;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author aluno
 */
public interface IDocument {
    public void open();
    public void comprass(File arqEntrada);
    public void deComprass(File arquivo) throws  FileNotFoundException,IOException;
}
