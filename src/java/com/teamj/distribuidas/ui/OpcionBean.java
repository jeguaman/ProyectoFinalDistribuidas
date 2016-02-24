/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamj.distribuidas.ui;

import com.teamj.distribuidas.facade.FacadeNegocio;
import com.teamj.distribuidas.model.database.Opcion;
import com.teamj.distribuidas.model.database.Sistema;
import com.teamj.distribuidas.util.ValidationUtil;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class OpcionBean implements Serializable {

    private String nombreUsuario;
    private Opcion opcionNuevoIngreso;
    private List<Opcion> listaOpciones;
    private Integer sistemaSeleccionado;
    private List<Sistema> listaSistemas;
    private Boolean flagShow;
    private Integer opcionSeleccionada;
    private List<Opcion> listaTodasOpciones;

    public List<Opcion> getListaTodasOpciones() {
        return listaTodasOpciones;
    }

    public void setListaTodasOpciones(List<Opcion> listaTodasOpciones) {
        this.listaTodasOpciones = listaTodasOpciones;
    }

    public Integer getOpcionSeleccionada() {
        return opcionSeleccionada;
    }

    public void setOpcionSeleccionada(Integer opcionSeleccionada) {
        this.opcionSeleccionada = opcionSeleccionada;
    }

    public Integer getSistemaSeleccionado() {
        return sistemaSeleccionado;
    }

    public void setSistemaSeleccionado(Integer sistemaSeleccionado) {
        this.sistemaSeleccionado = sistemaSeleccionado;
    }

    public Boolean getFlagShow() {
        return flagShow;
    }

    public void setFlagShow(Boolean flagShow) {
        this.flagShow = flagShow;
    }

    public List<Sistema> getListaSistemas() {
        return listaSistemas;
    }

    public void setListaSistemas(List<Sistema> listaSistemas) {
        this.listaSistemas = listaSistemas;
    }

    public Opcion getOpcionNuevoIngreso() {
        return opcionNuevoIngreso;
    }

    public void setOpcionNuevoIngreso(Opcion opcionNuevoIngreso) {
        this.opcionNuevoIngreso = opcionNuevoIngreso;
    }

    public List<Opcion> getListaOpciones() {
        return listaOpciones;
    }

    public void setListaOpciones(List<Opcion> listaOpciones) {
        this.listaOpciones = listaOpciones;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public OpcionBean() {
        uploadTodasOpciones();
    }

    public void metodoVacio() {

    }

    public void nuevoOpcion() {
        opcionNuevoIngreso = new Opcion();
        opcionNuevoIngreso.setNivel("1");
        flagShow = false;
        uploadSistemas();
    }

    public Boolean validateNombre() {
        Boolean success = false;
        if (opcionNuevoIngreso.getNombreOpcion() != null && !opcionNuevoIngreso.getNombreOpcion().isEmpty()) {
            if (ValidationUtil.soloLetrasNumerosSeparadasCiertosCaracteres(opcionNuevoIngreso.getNombreOpcion())) {
                success = true;
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "No ingrese caracteres especiales para el nombre."));
            }
        }
        return success;
    }

    private void uploadSistemas() {
        try {
            listaSistemas = FacadeNegocio.retrieveTodosSistemasActivos();
        } catch (Exception ex) {
            Logger.getLogger(OpcionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guardarOpcion() {
        Boolean success = false;
        if (validateNombre() && validateDescripcion() && sistemaSeleccionado.compareTo(-1) != 0) {
            if (listaOpciones == null) {
                opcionSeleccionada = 1;
            }
            try {
                opcionNuevoIngreso.setSistema(FacadeNegocio.retrieveSistemaById(sistemaSeleccionado));
                if (opcionNuevoIngreso.getNivel().compareTo("1") != 0) {
                    opcionNuevoIngreso.setOpcion(FacadeNegocio.retrieveOpcionByCodigo(opcionSeleccionada));
                } else {
                    opcionNuevoIngreso.setOpcion(null);
                }
                success = FacadeNegocio.insertarOpcion(opcionNuevoIngreso);
            } catch (Exception ex) {
                Logger.getLogger(OpcionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (success) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro ingresado con éxito."));
                RequestContext.getCurrentInstance().execute("PF('nuevoUsuario').hide();");
                uploadTodasOpciones();
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Complete la información por favor."));
        }
    }

    public void showOpcionesByNivel() {
        flagShow = opcionNuevoIngreso.getNivel().compareTo("1") != 0;
        uploadOpcionesByNivel();
    }

    private void uploadOpcionesByNivel() {
        try {
            BigDecimal resta = new BigDecimal(opcionNuevoIngreso.getNivel());
            resta = resta.subtract(new BigDecimal(1));
            listaOpciones = FacadeNegocio.retrieveOpcionByNivel(resta.toPlainString());
        } catch (Exception ex) {
            Logger.getLogger(OpcionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Boolean validateDescripcion() {
        Boolean success = false;
        if (opcionNuevoIngreso.getDescripcion() != null && !opcionNuevoIngreso.getDescripcion().isEmpty()) {
            if (ValidationUtil.soloLetrasNumerosSeparadasCiertosCaracteres(opcionNuevoIngreso.getDescripcion().trim())) {
                success = true;
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "No ingrese caracteres especiales para la descripción."));
            }
        }
        return success;
    }

    private void uploadTodasOpciones() {
        try {
            listaTodasOpciones = FacadeNegocio.retrieveTodasOpciones();
        } catch (Exception ex) {
            Logger.getLogger(OpcionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String processNivel(String valor) {
        String ret = "Menú Principal";
        if (valor.compareTo("2") == 0) {
            ret = "Sub Menú";
        } else if (valor.compareTo("3") == 0) {
            ret = "Item";
        }
        return ret;
    }
}
