/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editortexto;

import frameworkinterfaces.IAbstractFactory;
import java.util.ArrayList;

/**
 *
 * @author Gabriel Rocha
 */
public class EditorTextoFactory implements IAbstractFactory{

    @Override
    public ArrayList<String> extension() {
        ArrayList<String> extensao = new ArrayList<String>();
        extensao.add("txt");
        return extensao;
    }

    @Override
    public String nameType() {
        return "Tipo Texto";
    }
    
}
