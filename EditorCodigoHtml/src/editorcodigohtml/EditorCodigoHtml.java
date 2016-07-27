/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package editorcodigohtml;

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
 * @author aluno
 */
public class EditorCodigoHtml implements IPlugin, IAbstractFactory{

    private EditorCodigoHtml factory;
    
    private EditorCodigoHtml() {
    }
    
    public EditorCodigoHtml getInstance(){
        if(factory==null){
            factory=new EditorCodigoHtml();
        }
        return factory;
    }
    
    @Override
    public boolean initialize(ICore core) {
        return true;
    }

    @Override
    public ArrayList<String> extension() {
        ArrayList<String> ex = new ArrayList<String>();
        ex.add("html");
        return ex;
    }

    @Override
    public String nameType() {
        return "HTML";
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IVerifier createVerifier() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
