/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamj.distribuidas.ui;

import com.teamj.distribuidas.facade.FacadeNegocioGeneral;
import com.teamj.distribuidas.model.database.PepacPacien;
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
public class PacienteBean implements Serializable {

    private List<PepacPacien> listaPacientes;
    private PepacPacien pacienteSeleccionado;
    private String nombrePaciente;
    private String apellido;
    private String areaGeografica;
    private Boolean tarifaServicio;
    private Boolean flagActualizarEditar;
    private String buscar;

    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    public Boolean getFlagActualizarEditar() {
        return flagActualizarEditar;
    }

    public void setFlagActualizarEditar(Boolean flagActualizarEditar) {
        this.flagActualizarEditar = flagActualizarEditar;
    }

    public List<PepacPacien> getListaPacientes() {
        return listaPacientes;
    }

    public void setListaPacientes(List<PepacPacien> listaPacientes) {
        this.listaPacientes = listaPacientes;
    }

    public PepacPacien getPacienteSeleccionado() {
        return pacienteSeleccionado;
    }

    public void setPacienteSeleccionado(PepacPacien pacienteSeleccionado) {
        this.pacienteSeleccionado = pacienteSeleccionado;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getAreaGeografica() {
        return areaGeografica;
    }

    public void setAreaGeografica(String areaGeografica) {
        this.areaGeografica = areaGeografica;
    }

    public Boolean getTarifaServicio() {
        return tarifaServicio;
    }

    public void setTarifaServicio(Boolean tarifaServicio) {
        this.tarifaServicio = tarifaServicio;
    }

    public PacienteBean() {
        init();
    }

    private void init() {
        loadPacientes();
    }

    private void loadPacientes() {
        try {
            listaPacientes = FacadeNegocioGeneral.retrieveAllPacientes();
        } catch (Exception ex) {
            Logger.getLogger(PacienteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public void nuevo() {
        flagActualizarEditar = true;
        clearInputs();
    }

    private void clearInputs() {
        nombrePaciente = "";
        apellido = "";
        areaGeografica = "-1";
        tarifaServicio = false;
    }

    public void editar() {
        if (pacienteSeleccionado != null) {
            flagActualizarEditar = false;
            nombrePaciente = pacienteSeleccionado.getPepacNompac();
            apellido = pacienteSeleccionado.getPepacApepac();
            areaGeografica = pacienteSeleccionado.getPepacAregeo();
            tarifaServicio = pacienteSeleccionado.getPepacTarser().compareTo("1") == 0;
            RequestContext.getCurrentInstance().execute("PF('nuevoDialog').show();");
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Seleccione un paciente para editar."));
        }
    }

    public void eliminar() {
        if (pacienteSeleccionado != null) {
            try {
                if (FacadeNegocioGeneral.deletePaciente(pacienteSeleccionado)) {
                    loadPacientes();
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

    public void metodoVacio() {

    }

    public Boolean validateNombre() {
        Boolean success = false;
        if (nombrePaciente != null && nombrePaciente.compareTo("") != 0) {
            if (ValidationUtil.soloLetrasSeparadas(nombrePaciente)) {
                success = true;
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Ingrese solo letras."));
            }
        }
        return success;
    }

    public Boolean validateApellido() {
        Boolean success = false;
        if (apellido != null && apellido.compareTo("") != 0) {
            if (ValidationUtil.soloLetrasSeparadas(apellido)) {
                success = true;
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Ingrese solo letras."));
            }
        }
        return success;
    }

    public void guardarPaciente() {
        if (validateNombre() && validateApellido() && areaGeografica.compareTo("-1") != 0) {
            try {
                String auxTarifaServicio = "0";
                if (tarifaServicio) {
                    auxTarifaServicio = "1";
                }
                if (FacadeNegocioGeneral.insertPaciente(nombrePaciente, apellido, areaGeografica, auxTarifaServicio) != null) {
                    loadPacientes();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro insertado con éxito."));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "No se pudo insertar el registro."));
                }
            } catch (Exception ex) {
                Logger.getLogger(PacienteBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            RequestContext.getCurrentInstance().execute("PF('nuevoDialog').hide();");
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Complete la información antes de continuar"));
        }

    }

    public void actualizarPaciente() {
        if (validateNombre() && validateApellido() && areaGeografica.compareTo("-1") != 0) {
            pacienteSeleccionado.setPepacNompac(nombrePaciente);
            pacienteSeleccionado.setPepacApepac(apellido);
            pacienteSeleccionado.setPepacAregeo(areaGeografica);
            String auxTarifaServicio = "0";
            if (tarifaServicio) {
                auxTarifaServicio = "1";
            }
            pacienteSeleccionado.setPepacTarser(auxTarifaServicio);
            try {
                if (FacadeNegocioGeneral.updatePaciente(pacienteSeleccionado)) {
                    loadPacientes();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Registro modificado con éxito."));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "No se pudo modificar el registro."));
                }
            } catch (Exception ex) {
                Logger.getLogger(PacienteBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            RequestContext.getCurrentInstance().execute("PF('nuevoDialog').hide();");
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Complete la información antes de continuar"));
        }
    }

    public String processTarifaServicio(String value) {
        String valor = "";
        if (value.compareTo("0") == 0) {
            valor = "No";
        } else if (value.compareTo("1") == 0) {
            valor = "Si";
        }

        return valor;
    }

    public void buscarPaciente() {
        if (buscar != null && buscar.compareTo("") != 0) {
            try {
                PepacPacien serv = FacadeNegocioGeneral.retrievePacienteByNombre(buscar);
                if (serv != null) {
                    listaPacientes.clear();
                    listaPacientes.add(serv);
                } else {
                    loadPacientes();
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No existe un registro con el nombre " + buscar));
                }
            } catch (Exception ex) {
                System.err.println("no existe un usuario con ese nombre");
                ex.printStackTrace();
            }
        } else {
            loadPacientes();
        }
    }
}
