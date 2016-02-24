/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamj.distribuidas.ui;

import com.teamj.distribuidas.facade.FacadeNegocioGeneral;
import com.teamj.distribuidas.model.database.PepacPacien;
import com.teamj.distribuidas.model.database.PepagPagser;
import com.teamj.distribuidas.model.database.PeproProvee;
import com.teamj.distribuidas.model.database.PeserServic;
import com.teamj.distribuidas.util.ValidationUtil;
import java.io.Serializable;
import java.util.Date;
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
public class PagoServicioBean implements Serializable {

    private List<PeproProvee> listaProveedores;
    private Integer proveedorSeleccionado;
    private List<PepacPacien> listaPacientes;
    private Integer pacienteSeleccionado;
    private List<PeserServic> listaServicios;
    private PepagPagser pagoSeleccionado;
    private List<PepagPagser> listaPagos;
    private Integer servicioSeleccionado;
    private Date fechaServicio;
    private String costoDeducible;
    private String formaPago;
    private String costoTotal;
    private Boolean flagActualizarEditar;

    public Boolean getFlagActualizarEditar() {
        return flagActualizarEditar;
    }

    public void setFlagActualizarEditar(Boolean flagActualizarEditar) {
        this.flagActualizarEditar = flagActualizarEditar;
    }

    public PepagPagser getPagoSeleccionado() {
        return pagoSeleccionado;
    }

    public void setPagoSeleccionado(PepagPagser pagoSeleccionado) {
        this.pagoSeleccionado = pagoSeleccionado;
    }

    public List<PepagPagser> getListaPagos() {
        return listaPagos;
    }

    public void setListaPagos(List<PepagPagser> listaPagos) {
        this.listaPagos = listaPagos;
    }

    public List<PeproProvee> getListaProveedores() {
        return listaProveedores;
    }

    public void setListaProveedores(List<PeproProvee> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }

    public Integer getProveedorSeleccionado() {
        return proveedorSeleccionado;
    }

    public void setProveedorSeleccionado(Integer proveedorSeleccionado) {
        this.proveedorSeleccionado = proveedorSeleccionado;
    }

    public List<PepacPacien> getListaPacientes() {
        return listaPacientes;
    }

    public void setListaPacientes(List<PepacPacien> listaPacientes) {
        this.listaPacientes = listaPacientes;
    }

    public Integer getPacienteSeleccionado() {
        return pacienteSeleccionado;
    }

    public void setPacienteSeleccionado(Integer pacienteSeleccionado) {
        this.pacienteSeleccionado = pacienteSeleccionado;
    }

    public List<PeserServic> getListaServicios() {
        return listaServicios;
    }

    public void setListaServicios(List<PeserServic> listaServicios) {
        this.listaServicios = listaServicios;
    }

    public Integer getServicioSeleccionado() {
        return servicioSeleccionado;
    }

    public void setServicioSeleccionado(Integer servicioSeleccionado) {
        this.servicioSeleccionado = servicioSeleccionado;
    }

    public Date getFechaServicio() {
        return fechaServicio;
    }

    public void setFechaServicio(Date fechaServicio) {
        this.fechaServicio = fechaServicio;
    }

    public String getCostoDeducible() {
        return costoDeducible;
    }

    public void setCostoDeducible(String costoDeducible) {
        this.costoDeducible = costoDeducible;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(String costoTotal) {
        this.costoTotal = costoTotal;
    }

    public PagoServicioBean() {
        init();
    }

    private void init() {
        loadPagos();
    }

    private void loadPagos() {
        try {
            listaPagos = FacadeNegocioGeneral.retrieveAllPagosServicios();
        } catch (Exception ex) {
            Logger.getLogger(PagoServicioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String processTipoPago(String value) {
        if (value.compareTo("1") == 0) {
            return "Efectivo";
        } else if (value.compareTo("2") == 0) {
            return "Débito";
        }
        return "Gratis";
    }

    public void metodoVacio() {

    }

    public void nuevo() {
        flagActualizarEditar = true;
        clearInputs();
        loadProveedores();
        loadPacientes();
        loadServicios();
    }

    private void loadProveedores() {
        try {
            listaProveedores = FacadeNegocioGeneral.retrieveAllProveedores();
        } catch (Exception ex) {
            Logger.getLogger(ProveedorBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadPacientes() {
        try {
            listaPacientes = FacadeNegocioGeneral.retrieveAllPacientes();
        } catch (Exception ex) {
            Logger.getLogger(PacienteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadServicios() {
        try {
            listaServicios = FacadeNegocioGeneral.retrieveAllServicios();
        } catch (Exception ex) {
            Logger.getLogger(ServicioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clearInputs() {
        proveedorSeleccionado = -1;
        pacienteSeleccionado = -1;
        servicioSeleccionado = -1;
        costoDeducible = "";
        formaPago = "-1";
        fechaServicio = null;
        costoTotal = "";
    }

    public Boolean validateCostoDeducible() {
        Boolean success = false;
        if (costoDeducible != null && costoDeducible.compareTo("") != 0) {
            if (ValidationUtil.soloNumerosDecimales(costoDeducible)) {
                if (Double.parseDouble(costoDeducible) > 0f) {
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

    public Boolean validateCostoTotal() {
        Boolean success = false;
        if (costoTotal != null && costoTotal.compareTo("") != 0) {
            if (ValidationUtil.soloNumerosDecimales(costoTotal)) {
                if (Double.parseDouble(costoTotal) > 0f) {
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
//    public Boolean validateFechas() {
//        Boolean success = false;
//        if (fechaInicioContrato != null) {
//            if (fechaFinContrato.compareTo(fechaInicioContrato) == 0) {
//                FacesContext.getCurrentInstance().addMessage(null,
//                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "La fecha fin de contrato no puede ser igual a la fecha de Inicio de Contrato."));
//            } else {
//                if (fechaFinContrato.compareTo(fechaInicioContrato) < 0) {
//                    FacesContext.getCurrentInstance().addMessage(null,
//                            new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "La fecha fin de contrato no puede ser menor a la fecha de Inicio de Contrato."));
//                } else {
//                    success = true;
//                }
//            }
//        } else {
//            FacesContext.getCurrentInstance().addMessage(null,
//                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Ingrese una fecha de inicio de contrato."));
//        }
//        return success;
//    }

    public void guardar() {
        if (proveedorSeleccionado.compareTo(-1) != 0 && pacienteSeleccionado.compareTo(-1) != 0 && servicioSeleccionado.compareTo(-1) != 0
                && validateCostoDeducible() && formaPago.compareTo("-1") != 0 && fechaServicio != null && validateCostoTotal()) {
            try {
                if (FacadeNegocioGeneral.insertPagoService(pacienteSeleccionado, proveedorSeleccionado, servicioSeleccionado, fechaServicio, costoDeducible, formaPago, costoTotal) != null) {
                    loadPagos();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro insertado con éxito."));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "No se pudo insertar el registro."));
                }
            } catch (Exception ex) {
                Logger.getLogger(PagoServicioBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            RequestContext.getCurrentInstance().execute("PF('nuevoDialog').hide();");
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Complete la información antes de continuar"));
        }
    }

    public void actualizar() {
        if (proveedorSeleccionado.compareTo(-1) != 0 && pacienteSeleccionado.compareTo(-1) != 0 && servicioSeleccionado.compareTo(-1) != 0
                && validateCostoDeducible() && formaPago.compareTo("-1") != 0 && fechaServicio != null && validateCostoTotal()) {

        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Complete la información antes de continuar"));
        }
    }
}
