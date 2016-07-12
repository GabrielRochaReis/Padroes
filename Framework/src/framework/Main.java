/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework;

/**
 *
 * @author aluno
 */
public class Main {
    public static void main(String args[]) {
        Core core = new Core();
        System.out.println(core.getPluginController().getLoadedPlugins().size() + " plugins foram carregados!");
        System.out.println("Plugins: " + core.getPluginController().getLoadedPlugins());
    }
}
