/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamj.distribuidas.ui;

import com.teamj.distribuidas.facade.FacadeNegocio;
import com.teamj.distribuidas.facade.FacadeNegocioGeneral;
import com.teamj.distribuidas.model.database.Perfil;
import com.teamj.distribuidas.util.ValidationUtil;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Jose Guaman
 */
@ManagedBean
@ViewScoped
public class PerfilBean implements Serializable {

    private String nombreUsuario;
    private List<Perfil> listaPerfiles;
    private Perfil perfilSeleccionado;

    public List<Perfil> getListaPerfiles() {
        return listaPerfiles;
    }

    public void setListaPerfiles(List<Perfil> listaPerfiles) {
        this.listaPerfiles = listaPerfiles;
    }

    public Perfil getPerfilSeleccionado() {
        return perfilSeleccionado;
    }

    public void setPerfilSeleccionado(Perfil perfilSeleccionado) {
        this.perfilSeleccionado = perfilSeleccionado;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public PerfilBean() {
        init();
    }

    public void nuevoPerfil() {
        perfilSeleccionado = new Perfil();
    }

    public void metodoVacio() {

    }

    private void init() {
        loadPerfiles();
    }

    public void guardarPerfil() {
        Boolean success = false;
        if (validateNombre()) {
            try {
                success = FacadeNegocio.insertarPerfil(perfilSeleccionado);
            } catch (Exception e) {
            }
            if (success) {
                RequestContext.getCurrentInstance().execute("PF('nuevoUsuario').hide();");
                loadPerfiles();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro insertado con éxito."));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Ingrese todos los campos antes de guardar."));
        }
    }

    private void loadPerfiles() {
        try {
            listaPerfiles = FacadeNegocio.retrieveTodosPerfil();
        } catch (Exception ex) {
            Logger.getLogger(PrincipalBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Boolean validateNombre() {
        Boolean success = false;
        if (perfilSeleccionado.getNombrePerfil() != null && !perfilSeleccionado.getNombrePerfil().isEmpty()) {
            if (ValidationUtil.soloLetrasNumerosSeparadasCiertosCaracteres(perfilSeleccionado.getNombrePerfil())) {
                success = true;
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "No ingrese caracteres especiales para el nombre."));
            }
        }
        return success;
    }
}
