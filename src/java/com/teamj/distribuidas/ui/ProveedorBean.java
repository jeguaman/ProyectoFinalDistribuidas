/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamj.distribuidas.ui;

import com.teamj.distribuidas.facade.FacadeNegocioGeneral;
import com.teamj.distribuidas.model.database.PeproProvee;
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
public class ProveedorBean implements Serializable {

    private String nombre;
    private String nivelServicio;
    private String porcentajeDedicible;
    private String areaGeografica;
    private Boolean flagActualizarEditar;
    private List<PeproProvee> listaProveedores;
    private PeproProvee proveedorSeleccionado;
    private String headerName;
    private String buscar;

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNivelServicio() {
        return nivelServicio;
    }

    public void setNivelServicio(String nivelServicio) {
        this.nivelServicio = nivelServicio;
    }

    public String getPorcentajeDedicible() {
        return porcentajeDedicible;
    }

    public void setPorcentajeDedicible(String porcentajeDedicible) {
        this.porcentajeDedicible = porcentajeDedicible;
    }

    public String getAreaGeografica() {
        return areaGeografica;
    }

    public void setAreaGeografica(String areaGeografica) {
        this.areaGeografica = areaGeografica;
    }

    public List<PeproProvee> getListaProveedores() {
        return listaProveedores;
    }

    public void setListaProveedores(List<PeproProvee> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }

    public PeproProvee getProveedorSeleccionado() {
        return proveedorSeleccionado;
    }

    public void setProveedorSeleccionado(PeproProvee proveedorSeleccionado) {
        this.proveedorSeleccionado = proveedorSeleccionado;
    }

    public Boolean getFlagActualizarEditar() {
        return flagActualizarEditar;
    }

    public void setFlagActualizarEditar(Boolean flagActualizarEditar) {
        this.flagActualizarEditar = flagActualizarEditar;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public ProveedorBean() {
        init();
    }

    private void init() {
        loadProveedores();
    }

    private void loadProveedores() {
        try {
            listaProveedores = FacadeNegocioGeneral.retrieveAllProveedores();
        } catch (Exception ex) {
            Logger.getLogger(ProveedorBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guardarProveedor() {
        if (validateNombre() && validateNivelServicio() && validatePorcentajeDeducible() && areaGeografica.compareTo("-1") != 0) {
            try {
                if (FacadeNegocioGeneral.insertProvider(nombre, nivelServicio, porcentajeDedicible, areaGeografica) != null) {
                    loadProveedores();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro insertado con éxito."));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "No se pudo insertar el registro."));
                }
                RequestContext.getCurrentInstance().execute("PF('nuevoProveedorDialog').hide();");
            } catch (Exception ex) {
                Logger.getLogger(ProveedorBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Complete la información antes de continuar"));
        }

    }

    public void nuevoProveedor() {
        flagActualizarEditar = true;
        clearInputs();
    }

    public Boolean validateNombre() {
        Boolean success = false;
        if (nombre != null && nombre.compareTo("") != 0) {
            if (ValidationUtil.soloLetrasNumerosSeparadasCiertosCaracteres(nombre)) {
                success = true;
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "No ingrese caracteres especiales."));
            }
        }
        return success;
    }

    public Boolean validateNivelServicio() {
        Boolean success = false;
        if (nivelServicio != null && nivelServicio.compareTo("") != 0) {
            if (ValidationUtil.soloLetrasNumerosSeparadasCiertosCaracteres(nivelServicio)) {
                success = true;
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "No ingrese caracteres especiales."));
            }
        }
        return success;
    }

    public Boolean validatePorcentajeDeducible() {
        Boolean success = false;
        if (porcentajeDedicible != null && porcentajeDedicible.compareTo("") != 0) {
            if (ValidationUtil.soloNumerosDecimales(porcentajeDedicible)) {
                if (Double.parseDouble(porcentajeDedicible) > 0f) {
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

    public void metodoVacio() {

    }

    private void clearInputs() {
        nombre = "";
        nivelServicio = "";
        porcentajeDedicible = "";
        areaGeografica = "-1";
    }

    public String processAreaGeografica(String value) {
        String valor = "";
        if (value.compareTo("1") == 0) {
            valor = "San Juan";
        } else if (value.compareTo("2") == 0) {
            valor = "Bayamon (hasta Dorado)";
        } else if (value.compareTo("3") == 0) {
            valor = "Caguas - Cayey";
        } else if (value.compareTo("4") == 0) {
            valor = "Carolina (hasta Río Grande)";
        }

        return valor;
    }

    public void actualizarProveedor() {
        if (validateNombre() && validateNivelServicio() && validatePorcentajeDeducible() && areaGeografica.compareTo("-1") != 0) {
            proveedorSeleccionado.setPeproNompro(nombre);
            proveedorSeleccionado.setPeproNivser(nivelServicio);
            proveedorSeleccionado.setPeproPorded(porcentajeDedicible);
            proveedorSeleccionado.setPeproArgeog(areaGeografica);
            try {
                if (FacadeNegocioGeneral.updateProveedor(proveedorSeleccionado)) {
                    loadProveedores();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro modificado con éxito."));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "No se pudo modificar el registro."));
                }
            } catch (Exception ex) {
                Logger.getLogger(ProveedorBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            RequestContext.getCurrentInstance().execute("PF('nuevoProveedorDialog').hide();");
        }
    }

    public void editar() {
        if (proveedorSeleccionado != null) {
            flagActualizarEditar = false;
            nombre = proveedorSeleccionado.getPeproNompro();
            nivelServicio = proveedorSeleccionado.getPeproNivser();
            porcentajeDedicible = proveedorSeleccionado.getPeproPorded();
            areaGeografica = proveedorSeleccionado.getPeproArgeog();
            RequestContext.getCurrentInstance().execute("PF('nuevoProveedorDialog').show();");
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Seleccione un proveedor para editar."));
        }
    }

    public void buscarProveedor() {
        if (buscar != null && buscar.compareTo("") != 0) {
            try {
                PeproProvee prov = FacadeNegocioGeneral.retrieveProveedorByNombre(buscar);
                if (prov != null) {
                    listaProveedores.clear();
                    listaProveedores.add(prov);
                } else {
                    loadProveedores();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No existe un usuario registrado con el nombre " + buscar));
                }
            } catch (Exception ex) {
                System.err.println("no existe un usuario con ese nombre");
                ex.printStackTrace();
            }

        } else {
            loadProveedores();
        }
    }

    public void eliminar() {
        if (proveedorSeleccionado != null) {
            try {
                if (FacadeNegocioGeneral.deleteProveedor(proveedorSeleccionado)) {
                    loadProveedores();
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
}
