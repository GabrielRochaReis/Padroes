/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework;

import frameworkinterfaces.ICore;
import frameworkinterfaces.IDocumentController;
import frameworkinterfaces.IPluginController;
import frameworkinterfaces.IUiController;

/**
 *
 * @author aluno
 */
public class Core implements ICore {

    public Core() {
        uiController = new UiController();
        pluginController = new PluginController(this);
        if (!pluginController.loadPlugins())
            System.out.println("Error when loading plugins!");
        documentController = new DocumentController();
        uiController.createMenuItemFileOpen(this);
    }

    @Override
    public IPluginController getPluginController() {
        return pluginController;
    }

    @Override
    public IDocumentController getDocumentController() {
        return documentController;
    }

    @Override
    public IUiController getUiController() {
        return uiController;
    }

    private PluginController pluginController;
    private DocumentController documentController;
    private UiController uiController;
}
