package framework;

import java.io.File;
import java.util.ArrayList;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Gabriel Rocha
 */
public class FactoryFilter extends FileFilter{
    public FactoryFilter(String typeName, ArrayList<String> supportedExtensions)
    {
        this.typeName = typeName;
        this.supportedExtensions = supportedExtensions;
    }
    
    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        
        String []parts = file.getName().split("\\.");
        if (parts.length == 1)
            return false;

        String extension = parts[parts.length - 1];
        for (String extension1 : supportedExtensions) {
            if (extension.equals(extension1)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getDescription() {
        return typeName;
    }
    
    private String typeName;
    private ArrayList<String> supportedExtensions;
}