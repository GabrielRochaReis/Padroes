/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editortexto;

import frameworkinterfaces.Editor;
import frameworkinterfaces.IAbstractFactory;
import frameworkinterfaces.ICore;
import frameworkinterfaces.IPlugin;
import frameworkinterfaces.ISerializer;
import frameworkinterfaces.IToolBox;
import frameworkinterfaces.IVerifier;
import java.util.ArrayList;

/**
 *
 * @author Gabriel Rocha
 */
public class EditorTexto implements IAbstractFactory, IPlugin{

    private static EditorTexto factory;
    
    private EditorTexto() {
    }
    
    public static EditorTexto getInstance(){
        if(factory==null){
            factory=new EditorTexto();
        }
        return factory;
    }
    
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

    @Override
    public boolean initialize(ICore core) {
        return true;
    }

    @Override
    public IToolBox createtoolBox() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ISerializer createSerializer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Editor createEditor() {
        return new TextEditor();
    }

    @Override
    public IVerifier createVerifier() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
