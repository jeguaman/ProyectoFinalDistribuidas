/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamj.distribuidas.ui;

import com.teamj.distribuidas.facade.FacadeNegocio;
import com.teamj.distribuidas.facade.FacadeNegocioGeneral;
import com.teamj.distribuidas.model.database.Sistema;
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
public class SistemaBean implements Serializable {

    private String nombreUsuario;
    private List<Sistema> listaSistemas;
    private Sistema sistemaSeleccionado;

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public List<Sistema> getListaSistemas() {
        return listaSistemas;
    }

    public void setListaSistemas(List<Sistema> listaSistemas) {
        this.listaSistemas = listaSistemas;
    }

    public Sistema getSistemaSeleccionado() {
        return sistemaSeleccionado;
    }

    public void setSistemaSeleccionado(Sistema sistemaSeleccionado) {
        this.sistemaSeleccionado = sistemaSeleccionado;
    }

    public SistemaBean() {
        init();
    }

    public void nuevoSistema() {
        sistemaSeleccionado = new Sistema();
    }

    public void metodoVacio() {

    }

    public void guardarSistema() {
        Boolean success = false;
        if (validateNombre()) {
            try {
                success = FacadeNegocio.insertarSistemas(sistemaSeleccionado);
            } catch (Exception e) {
            }
            if (success) {
                RequestContext.getCurrentInstance().execute("PF('nuevoUsuario').hide();");
                loadSistemas();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro insertado con éxito."));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Ingrese todos los campos antes de guardar."));
        }
    }

    private void init() {
        loadSistemas();
    }

    public void loadSistemas() {
        try {
            listaSistemas = FacadeNegocio.retrieveTodosSistemas();
        } catch (Exception ex) {
            Logger.getLogger(PrincipalBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Boolean validateNombre() {
        Boolean success = false;
        if (sistemaSeleccionado.getNombre() != null && !sistemaSeleccionado.getNombre().isEmpty()) {
            if (ValidationUtil.soloLetrasNumerosSeparadasCiertosCaracteres(sistemaSeleccionado.getNombre())) {
                success = true;
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "No ingrese caracteres especiales para el nombre."));
            }
        }
        return success;
    }
}
