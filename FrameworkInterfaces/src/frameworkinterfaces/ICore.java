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
public interface ICore {
    public IUiController getUiController();
    public IPluginController getPluginController();
    public IDocumentController getDocumentController();
}
