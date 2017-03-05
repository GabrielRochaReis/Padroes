/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frameworkinterfaces;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JMenuItem;

/**
 *
 * @author aluno
 */
public interface IUiController {
    public JMenuItem addMenuItem(String menu, String menuItem);
    public File fileOpen(ICore core, ArrayList<IPlugin> loadedPlugins);
    public void createMenuItemFileOpen(final ICore core);
}
