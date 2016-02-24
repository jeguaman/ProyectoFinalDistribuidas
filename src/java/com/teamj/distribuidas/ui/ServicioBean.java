/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamj.distribuidas.ui;

import com.teamj.distribuidas.facade.FacadeNegocioGeneral;
import com.teamj.distribuidas.model.database.PeserServic;
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
public class ServicioBean implements Serializable {

    private List<PeserServic> listaServicios;
    private PeserServic servicioSeleccionado;
    private Boolean flagActualizarEditar;
    private String nombreServicio;
    private String tipoServicio;
    private String costoServicio;
    private String headerName;
    private String buscar;

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getCostoServicio() {
        return costoServicio;
    }

    public void setCostoServicio(String costoServicio) {
        this.costoServicio = costoServicio;
    }

    public Boolean getFlagActualizarEditar() {
        return flagActualizarEditar;
    }

    public void setFlagActualizarEditar(Boolean flagActualizarEditar) {
        this.flagActualizarEditar = flagActualizarEditar;
    }

    public List<PeserServic> getListaServicios() {
        return listaServicios;
    }

    public void setListaServicios(List<PeserServic> listaServicios) {
        this.listaServicios = listaServicios;
    }

    public PeserServic getServicioSeleccionado() {
        return servicioSeleccionado;
    }

    public void setServicioSeleccionado(PeserServic servicioSeleccionado) {
        this.servicioSeleccionado = servicioSeleccionado;
    }

    public ServicioBean() {
        init();
    }

    private void loadServicios() {
        try {
            listaServicios = FacadeNegocioGeneral.retrieveAllServicios();
        } catch (Exception ex) {
            Logger.getLogger(ServicioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void init() {
        loadServicios();
    }

    public void nuevoServicio() {
        flagActualizarEditar = true;
        clearInputs();
    }

    private void clearInputs() {
        nombreServicio = "";
        tipoServicio = "";
        costoServicio = "";
    }

    public void editar() {
        if (servicioSeleccionado != null) {
            flagActualizarEditar = false;
            nombreServicio = servicioSeleccionado.getPeserNomser();
            tipoServicio = servicioSeleccionado.getPeserTipser();
            costoServicio = servicioSeleccionado.getPeserCosser();
            RequestContext.getCurrentInstance().execute("PF('nuevoDialog').show();");
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Seleccione un servicio para editar."));
        }
    }

    public Boolean validateTipoServicio() {
        Boolean success = false;
        if (tipoServicio != null && tipoServicio.compareTo("") != 0) {
            if (ValidationUtil.soloLetrasNumerosSeparadasCiertosCaracteres(tipoServicio)) {
                success = true;
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "No ingrese caracteres especiales."));
            }
        }
        return success;
    }

    public Boolean validateNombreServicio() {
        Boolean success = false;
        if (nombreServicio != null && nombreServicio.compareTo("") != 0) {
            if (ValidationUtil.soloLetrasNumerosSeparadasCiertosCaracteres(nombreServicio)) {
                success = true;
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "No ingrese caracteres especiales."));
            }
        }
        return success;
    }

    public Boolean validateCostoServicio() {
        Boolean success = false;
        if (costoServicio != null && costoServicio.compareTo("") != 0) {
            if (ValidationUtil.soloNumerosDecimales(costoServicio)) {
                if (Double.parseDouble(costoServicio) > 0f) {
                    success = true;
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Ingrese un costo mayor a 0 (cero)."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Ingrese números para el costo."));
            }
        }
        return success;
    }

    public void guardarServicio() {
        if (validateNombreServicio() && validateCostoServicio() && validateTipoServicio()) {
            try {
                if (FacadeNegocioGeneral.insertService(nombreServicio, tipoServicio, costoServicio) != null) {
                    loadServicios();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro insertado con éxito."));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "No se pudo insertar el registro."));
                }
                RequestContext.getCurrentInstance().execute("PF('nuevoDialog').hide();");
            } catch (Exception ex) {
                Logger.getLogger(ProveedorBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Complete la información antes de continuar"));
        }
    }

    public void actualizarServicio() {
        if (validateNombreServicio() && validateCostoServicio() && validateTipoServicio()) {
            try {
                servicioSeleccionado.setPeserNomser(nombreServicio);
                servicioSeleccionado.setPeserTipser(tipoServicio);
                servicioSeleccionado.setPeserCosser(costoServicio);
                if (FacadeNegocioGeneral.updateServicios(servicioSeleccionado)) {
                    loadServicios();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro modificado con éxito."));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "No se pudo modificar el registro."));
                }
                RequestContext.getCurrentInstance().execute("PF('nuevoDialog').hide();");
            } catch (Exception ex) {
                Logger.getLogger(ProveedorBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Complete la información antes de continuar"));
        }
    }

    public void eliminar() {
        if (servicioSeleccionado != null) {
            try {
                if (FacadeNegocioGeneral.deleteServicio(servicioSeleccionado)) {
                    loadServicios();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro eliminado con éxito."));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "No se puede eliminar ya que esta siendo utilizado."));
                }
            } catch (Exception ex) {

            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Seleccione un registro para eliminar."));
        }
    }

    public void buscarServicio() {
        if (buscar != null && buscar.compareTo("") != 0) {
            try {
                PeserServic serv = FacadeNegocioGeneral.retrieveServicioByNombre(buscar);
                if (serv != null) {
                    listaServicios.clear();
                    listaServicios.add(serv);
                } else {
                    loadServicios();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No existe un registro con el nombre " + buscar));
                }
            } catch (Exception ex) {
                System.err.println("no existe un usuario con ese nombre");
                ex.printStackTrace();
            }
        } else {
            loadServicios();
        }
    }
}
