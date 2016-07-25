/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework;

import frameworkinterfaces.IAbstractFactory;
import frameworkinterfaces.ICore;
import frameworkinterfaces.IPlugin;
import frameworkinterfaces.IUiController;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author aluno
 */
public class UiController implements IUiController {
    public UiController() {
        mainWindow = new FrameworkMainWindow();
        mainWindow.setJMenuBar(new javax.swing.JMenuBar());
        JMenu jMenu1 = new javax.swing.JMenu();
        jMenu1.setText("File");
        mainWindow.getJMenuBar().add(jMenu1);
        mainWindow.pack();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                mainWindow.setVisible(true);
            }
        });
    }

    @Override
    public JMenuItem addMenuItem(String menu, String menuItem) {
        JMenuBar menubar = mainWindow.getJMenuBar();
        int menuCount = menubar.getMenuCount();
        JMenu jmenu = null;
        for (int i = 0; i < menuCount; i++) {
            JMenu currentMenu = menubar.getMenu(i);
            if (currentMenu.getText().equals(menu))
                jmenu = currentMenu;
        }

        if (jmenu == null) {
            jmenu = new JMenu(menu);
            menubar.add(jmenu);
        }

        JMenuItem menuitem = new JMenuItem(menuItem);
        jmenu.add(menuitem);

        return menuitem;
    }
    
    @Override
    public void createMenuItemFileOpen(final ICore core) {
        ArrayList<IPlugin> loadedPlugins = core.getPluginController().getLoadedPlugins();
        Iterator interator = loadedPlugins.iterator();
        JMenuItem newItem = this.addMenuItem("File", "Open");
        if (newItem != null)
            newItem.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    file = fileOpen(core, loadedPlugins);
                    for (IPlugin iplugin : core.getPluginController().getLoadedPlugins()) {
                       if (iplugin instanceof IAbstractFactory) {
                           IAbstractFactory factory = (IAbstractFactory) iplugin;
                       }      
                    }
                }
            });
    }
   
    @Override
    public File fileOpen(ICore core, ArrayList<IPlugin> loadedPlugins){
        Iterator i = loadedPlugins.iterator();
        JFileChooser jfc = new JFileChooser();
	jfc.setDialogTitle("Open Document");
	jfc.setDialogType(JFileChooser.OPEN_DIALOG);
        while (i.hasNext()) {
            IPlugin plugin = (IPlugin) i.next();
            if (plugin instanceof IAbstractFactory) {
                IAbstractFactory factoryPlugin = (IAbstractFactory) plugin;
                FactoryFilter ff = new FactoryFilter(factoryPlugin.nameType(), factoryPlugin.extension());
                jfc.addChoosableFileFilter(ff);
            }
        }
        jfc.showDialog(null, "Ok");  
        return jfc.getSelectedFile();
    }
    
    private File file;
    private FrameworkMainWindow mainWindow;
}
