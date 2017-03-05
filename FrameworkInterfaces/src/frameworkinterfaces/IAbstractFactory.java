/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frameworkinterfaces;

import java.util.ArrayList;

/**
 *
 * @author Gabriel Rocha
 */
public interface IAbstractFactory {
  
    ArrayList<String> extension();
  
    String nameType();
    
    IToolBox createtoolBox();
  
    ISerializer createSerializer();

    Editor createEditor();

    IVerifier createVerifier();
}
