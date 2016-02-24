/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamj.distribuidas.ui;

import com.teamj.distribuidas.facade.FacadeNegocio;
import com.teamj.distribuidas.facade.FacadeNegocioGeneral;
import com.teamj.distribuidas.model.database.Persona;
import com.teamj.distribuidas.util.HttpSessionHandler;
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
public class PrincipalBean implements Serializable {

    private String nombreUsuario;
    private List<Persona> listaPersona;
    private Persona personaSeleccionada;

    public List<Persona> getListaPersona() {
        return listaPersona;
    }

    public void setListaPersona(List<Persona> listaPersona) {
        this.listaPersona = listaPersona;
    }

    public Persona getPersonaSeleccionada() {
        return personaSeleccionada;
    }

    public void setPersonaSeleccionada(Persona personaSeleccionada) {
        this.personaSeleccionada = personaSeleccionada;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public PrincipalBean() {
        init();
    }

    public void metodoVacio() {

    }

    public void nuevoPersona() {
        personaSeleccionada = new Persona();
    }

    public void guardaPersona() {
        if (validateApellido() && validateCedula() && validateNombre()) {
            Boolean success = false;
            try {
                success = FacadeNegocio.insertarPersona(personaSeleccionada);
            } catch (Exception ex) {
                Logger.getLogger(PrincipalBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (success) {
                RequestContext.getCurrentInstance().execute("PF('nuevoUsuario').hide();");
                loadPersonas();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro insertado con éxito."));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Ingrese todos los campos antes de guardar."));
        }
    }

    public Boolean validateCedula() {
        Boolean success = false;
        Persona persona = null;
        if (personaSeleccionada.getCedula() != null && !personaSeleccionada.getCedula().isEmpty()) {
            if (ValidationUtil.soloNumeros(personaSeleccionada.getCedula())) {
                if (ValidationUtil.validarDocumentoEcuador(personaSeleccionada.getCedula())) {
                    try {
                        persona = FacadeNegocio.retrievePersonaByIdentificacion(personaSeleccionada.getCedula());
                        if (persona == null) {
                            success = true;
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "La cédula " + personaSeleccionada.getCedula() + " ya existe."));
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(PrincipalBean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Ingrese una identificación válida."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Ingrese solo números para la identificación."));
            }
        }
        return success;
    }

    public Boolean validateNombre() {
        Boolean success = false;
        if (personaSeleccionada.getNombre() != null && !personaSeleccionada.getNombre().isEmpty()) {
            if (ValidationUtil.soloLetrasNumerosSeparadasCiertosCaracteres(personaSeleccionada.getNombre())) {
                success = true;
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "No ingrese caracteres especiales para el nombre."));
            }
        }
        return success;
    }

    public Boolean validateApellido() {
        Boolean success = false;
        if (personaSeleccionada.getApellido() != null && !personaSeleccionada.getApellido().isEmpty()) {
            if (ValidationUtil.soloLetrasNumerosSeparadasCiertosCaracteres(personaSeleccionada.getApellido())) {
                success = true;
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "No ingrese caracteres especiales para el apellido."));
            }
        }
        return success;
    }

    private void init() {
        HttpSessionHandler session = new HttpSessionHandler();
        nombreUsuario = session.getNombreUsuario();
        loadPersonas();
    }

    private void loadPersonas() {
        try {
            listaPersona = FacadeNegocio.retrieveTodosPersonal();
        } catch (Exception ex) {
            Logger.getLogger(PrincipalBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
