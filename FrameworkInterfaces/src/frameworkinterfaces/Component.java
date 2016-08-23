/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frameworkinterfaces;

/**
 *
 * @author aluno
 */
public abstract class Component {
    public abstract void operation();
    public void addComponent(Component c) throws Exception{
        throw new Exception("Instacia de folha!");
    }
    public void removeComponent(Component c) throws Exception{
        throw new Exception("Instacia de folha!");
    }
}
