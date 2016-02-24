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
import com.teamj.distribuidas.util.ValidationUtil;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Jose Guaman
 */
@ManagedBean
@ViewScoped
public class CambioContraseniaBean implements Serializable {

    private String oldPassword;
    private String password;
    private String nuevaPassword;
    private Usuario user;

    public CambioContraseniaBean() {
        init();
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNuevaPassword() {
        return nuevaPassword;
    }

    public void setNuevaPassword(String nuevaPassword) {
        this.nuevaPassword = nuevaPassword;
    }

    private void init() {
        clearInputs();
    }

    public Boolean validateContrasenias() {
        Boolean success = false;
        if (password.trim().compareTo(nuevaPassword.trim()) == 0) {
            success = true;
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Las contraseñas no coindicen."));
        }
        return success;
    }

    public Boolean validateContraseniaAntigua() {
        HttpSessionHandler session = new HttpSessionHandler();
        Boolean success = false;
        try {
            user = FacadeNegocio.retrieveUsuarioByCode(Integer.parseInt(session.getCodigoUsuario()));
        } catch (Exception ex) {
            Logger.getLogger(CambioContraseniaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (user != null) {
            if (user.getPassword().compareTo(EncriptacionUtil.encriptarMD5(oldPassword)) == 0) {
                success = true;
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "La contraseña antigüa no coindice con la ingresada."));
            }
        }
        return success;
    }

    public void cambiarContrasenia() {
        Boolean success = false;
        if (validateContrasenias() && validateContraseniaAntigua()) {
            user.setPassword(EncriptacionUtil.encriptarMD5(nuevaPassword));
            try {
                success = FacadeNegocio.updateUsuario(user);
            } catch (Exception ex) {
                Logger.getLogger(CambioContraseniaBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (success) {
                clearInputs();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Contraseña actualizada con éxito."));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "COmplete la información antes de continuar."));
        }
    }

    private void clearInputs() {
        oldPassword = "";
        password = "";
        nuevaPassword = "";
    }

    public void metodoVacio() {

    }
}
