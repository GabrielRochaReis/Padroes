/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework;

import frameworkinterfaces.IUiController;
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
    
    private FrameworkMainWindow mainWindow;
}
