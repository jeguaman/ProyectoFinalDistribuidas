/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamj.distribuidas.ui;

import com.teamj.distribuidas.facade.FacadeNegocio;
import com.teamj.distribuidas.model.database.Usuario;
import com.teamj.distribuidas.util.EncriptacionUtil;
import com.teamj.distribuidas.util.HttpSessionHandler;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Jose Guaman
 */
@ManagedBean
@ViewScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;
    private HttpSessionHandler session;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HttpSessionHandler getSession() {
        return session;
    }

    public void setSession(HttpSessionHandler session) {
        this.session = session;
    }

    public LoginBean() {
        session = new HttpSessionHandler();
    }

    public void checkAccess() {
        if (username != null && username.compareTo("") != 0 && password != null && password.compareTo("") != 0) {
            try {
                Boolean successUser = false;
                Usuario user = FacadeNegocio.autentificacionUsuario(username, EncriptacionUtil.encriptarMD5(password));
                if (user != null) {
                    successUser = true;
                }
                if (successUser) {

                    session.setNombreUsuario(username);
                    RequestContext.getCurrentInstance().execute("PF('dlgLogin').hide();");
                    String pagina = "";
                    String context = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getContextPath();
                    pagina = context + "/pages/principal.xhtml";
                    FacesContext.getCurrentInstance().getExternalContext().redirect(pagina);
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Nombre de usuario o contraseña incorrectos."));
                }
            } catch (IOException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Complete los campos antes de continuar."));
        }
    }
}
