package controllers;

import DAO.UsuarioDAO;
import Model.Usuario;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import org.primefaces.context.RequestContext;

@ManagedBean(name="indexController")
public class IndexController {
    
    UsuarioDAO usuarioDAO;

    public IndexController() {
        usuarioDAO = new UsuarioDAO();
    }
    
    
    public String submit(){
        if(usuario!= null && senha != null){
             Usuario usu = usuarioDAO.validarUsuario(usuario, senha);

             if(usu!=null){
                  return "tela_inicial.xhtml";
             }
        }
        RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Login ou senha incorreto."));
        usuario=null;
        senha=null;
        return "";
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    
    private String usuario;
    private String senha;
    private String label="Login";
}