/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamj.distribuidas.ui;

import com.teamj.distribuidas.facade.FacadeNegocio;
import com.teamj.distribuidas.model.database.Persona;
import com.teamj.distribuidas.model.database.Usuario;
import com.teamj.distribuidas.util.EncriptacionUtil;
import com.teamj.distribuidas.util.HttpSessionHandler;
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
public class UsuarioBean implements Serializable {

    private List<Usuario> listaUsuarios;
    private Usuario usuarioSeleccionado;
    private String password;
    private Integer codigoPersona;
    private List<Persona> listaPersona;
    private String nombreUsuario;

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public List<Persona> getListaPersona() {
        return listaPersona;
    }

    public void setListaPersona(List<Persona> listaPersona) {
        this.listaPersona = listaPersona;
    }

    public Integer getCodigoPersona() {
        return codigoPersona;
    }

    public void setCodigoPersona(Integer codigoPersona) {
        this.codigoPersona = codigoPersona;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsuarioBean() {
        init();
    }

    private void init() {
        HttpSessionHandler session = new HttpSessionHandler();
        nombreUsuario = session.getNombreUsuario();
        loadUsuarios();
    }

    private void loadUsuarios() {
        try {
            listaUsuarios = FacadeNegocio.retrieveTodosUsuarios();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void nuevoUsuario() {
        loadPersonal();
        usuarioSeleccionado = new Usuario();
        codigoPersona = -1;
    }

    public Boolean validateNombreUsuario() {
        Boolean success = false;
        Usuario usuario;
        if (usuarioSeleccionado.getNombreUsuario() != null && !usuarioSeleccionado.getNombreUsuario().isEmpty()) {
            if (ValidationUtil.soloLetrasNumerosSeparadasCiertosCaracteres(usuarioSeleccionado.getNombreUsuario().trim())) {
                try {
                    usuario = FacadeNegocio.retrieveUsuarioByNombreUsuario(usuarioSeleccionado.getNombreUsuario());
                    if (usuario == null) {
                        success = true;
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "El nombre de usuario " + usuarioSeleccionado.getNombreUsuario() + " ya existe."));
                    }
                } catch (Exception ex) {
                    System.err.println("No se puede traer el usuario ");
                    ex.printStackTrace();
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "No ingrese caracteres especiales en el nombre de usuario."));
            }
        }
        return success;
    }

    public void metodoVacio() {

    }

    public Boolean validateContrasenias() {
        Boolean success = false;
        if (password.trim().compareTo(usuarioSeleccionado.getPassword().trim()) == 0) {
            success = true;
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Las contraseñas no coindicen."));
        }
        return success;
    }

    public void guardarUsuario() {
        if (validateNombreUsuario() && validateContrasenias() && codigoPersona.compareTo(-1) != 0) {
            Boolean success = false;
            String hash = EncriptacionUtil.encriptarMD5(password);
            usuarioSeleccionado.setFechaCreacion(new Date());
            usuarioSeleccionado.setPassword(hash);
            try {
                success = FacadeNegocio.insertarUsuario(usuarioSeleccionado, codigoPersona);
            } catch (Exception ex) {
                Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (success) {
                RequestContext.getCurrentInstance().execute("PF('nuevoUsuario').hide();");
                loadUsuarios();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Registro insertado con éxito."));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Complete los campos antes de continuar."));
        }
    }

    public void loadPersonal() {
        try {
            listaPersona = FacadeNegocio.retrieveTodosPersonal();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
