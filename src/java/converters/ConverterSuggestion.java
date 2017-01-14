package converters;
 
import DAO.InquilinoDAO;
import Model.Inquilino;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.primefaces.context.RequestContext;
 
@FacesConverter("converter")
@ViewScoped
public class ConverterSuggestion implements Converter {
 
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            return uic.getAttributes().get(value);
//            return fc.getCurrentInstance().getAttributes().get(value);
        }
        else {
            return null;
        }
    }
 
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            uic.getAttributes().put(object.hashCode()+"", object);
            fc.getCurrentInstance().getAttributes().put(object.hashCode()+"", object);
            return object.hashCode()+"";
        }
        else {
            return null;
        }
    }  
}