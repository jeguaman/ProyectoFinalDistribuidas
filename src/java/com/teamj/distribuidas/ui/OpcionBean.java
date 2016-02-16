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
import java.util.List;
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
public class OpcionBean implements Serializable {

    private String nombreUsuario;
    private Opcion opcionSeleccionada;
    private List<Opcion> listaOpciones;
    private Integer sistemaSeleccionado;
    private List<Sistema> listaSistemas;

    public Integer getSistemaSeleccionado() {
        return sistemaSeleccionado;
    }

    public void setSistemaSeleccionado(Integer sistemaSeleccionado) {
        this.sistemaSeleccionado = sistemaSeleccionado;
    }

    public List<Sistema> getListaSistemas() {
        return listaSistemas;
    }

    public void setListaSistemas(List<Sistema> listaSistemas) {
        this.listaSistemas = listaSistemas;
    }

    public Opcion getOpcionSeleccionada() {
        return opcionSeleccionada;
    }

    public void setOpcionSeleccionada(Opcion opcionSeleccionada) {
        this.opcionSeleccionada = opcionSeleccionada;
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

    }

    public void metodoVacio() {

    }

    public void nuevoOpcion() {
        opcionSeleccionada = new Opcion();
        uploadSistemas();
    }

    public Boolean validateNombre() {
        Boolean success = false;
        if (opcionSeleccionada.getNombreOpcion() != null && !opcionSeleccionada.getNombreOpcion().isEmpty()) {
            if (ValidationUtil.soloLetrasNumerosSeparadasCiertosCaracteres(opcionSeleccionada.getNombreOpcion())) {
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

    }
}
