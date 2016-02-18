/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamj.distribuidas.ui;

import com.teamj.distribuidas.facade.FacadeNegocio;
import com.teamj.distribuidas.model.database.Perfil;
import com.teamj.distribuidas.model.database.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Jose Guaman
 */
@ManagedBean
@ViewScoped
public class UsuarioPerfilBean implements Serializable {

    private List<Perfil> listaPerfiles;
    private Integer perfilSeleccionado;
    private List<Usuario> listaUsuario;
    private List<Usuario> listaTodosUsuario;
    private DualListModel<Usuario> listaDualUsuario;
    private List<Usuario> listaOriginalAsignada;
    private List<Usuario> listaOriginalGeneral;

    public UsuarioPerfilBean() {
        init();
    }

    public Integer getPerfilSeleccionado() {
        return perfilSeleccionado;
    }

    public void setPerfilSeleccionado(Integer perfilSeleccionado) {
        this.perfilSeleccionado = perfilSeleccionado;
    }

    public DualListModel<Usuario> getListaDualUsuario() {
        return listaDualUsuario;
    }

    public void setListaDualUsuario(DualListModel<Usuario> listaDualUsuario) {
        this.listaDualUsuario = listaDualUsuario;
    }

    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public List<Usuario> getListaTodosUsuario() {
        return listaTodosUsuario;
    }

    public void setListaTodosUsuario(List<Usuario> listaTodosUsuario) {
        this.listaTodosUsuario = listaTodosUsuario;
    }

    public List<Perfil> getListaPerfiles() {
        return listaPerfiles;
    }

    public void setListaPerfiles(List<Perfil> listaPerfiles) {
        this.listaPerfiles = listaPerfiles;
    }

    private void init() {
        loadPerfiles();
        loadUsuarios();
    }

    public void loadUsuarios() {
        List<Usuario> source = new ArrayList<>();
        List<Usuario> target = new ArrayList<>();

        listaOriginalAsignada = new ArrayList<>();
        listaOriginalGeneral = new ArrayList<>();
        if (listaPerfiles != null) {
            if (perfilSeleccionado != null && perfilSeleccionado.compareTo(-1) != 0) {
                try {
                    listaUsuario = FacadeNegocio.retrieveTodosUsuariosByPerfil(perfilSeleccionado);
                    if (listaUsuario != null) {
                        for (int i = 0; i < listaUsuario.size(); i++) {
                            target.add(listaUsuario.get(i));
                        }
                        listaOriginalAsignada.addAll(target);
                    }
                } catch (Exception ex) {

                }
                try {
                    listaTodosUsuario = FacadeNegocio.retrieveListUsuariosNoAsignados();
                    if (listaTodosUsuario != null) {
                        for (int i = 0; i < listaTodosUsuario.size(); i++) {
                            source.add(listaTodosUsuario.get(i));
                        }
                        listaOriginalGeneral.addAll(source);
                    }
                } catch (Exception ex) {

                }
            }
        }
        listaDualUsuario = new DualListModel<>(source, target);
    }

    private void loadPerfiles() {
        try {
            listaPerfiles = FacadeNegocio.retrieveTodosPerfil();
        } catch (Exception ex) {
            Logger.getLogger(PrincipalBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void testMetodo() {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Ingrese todos los campos antes de guardar."));
    }

    public void metodoVacio() {

    }

    public void guardarAsignacion() {
        if (eliminarUsuariosAsignados() || agregarUsuarioPerfil()) {
            loadUsuarios();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Registros actualizados con éxito."));
        }
    }

    private Boolean eliminarUsuariosAsignados() {
        List<Usuario> usuariosGeneral = new ArrayList<>();
        List<Usuario> auxEl = new ArrayList<>();
        usuariosGeneral = listaDualUsuario.getSource();
        Boolean success = false;
        if (!usuariosGeneral.isEmpty() && !listaOriginalGeneral.containsAll(usuariosGeneral)) {
            auxEl = usuariosGeneral;
            for (Usuario us : listaOriginalGeneral) {
                if (auxEl.contains(us)) {
                    auxEl.remove(us);
                }
            }
            try {
                if (FacadeNegocio.eliminarUsuariosXPerfil(perfilSeleccionado, auxEl)) {
                    success = true;
                }
            } catch (Exception ex) {
                Logger.getLogger(UsuarioPerfilBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return success;
    }

    private Boolean agregarUsuarioPerfil() {
        List<Usuario> usuariosXperfil = new ArrayList<>();
        List<Usuario> auxIns = new ArrayList<>();
        Boolean success = false;
        usuariosXperfil = listaDualUsuario.getTarget();
        //eliminar los usuarios Asignados

        if (!usuariosXperfil.isEmpty() && !listaOriginalAsignada.containsAll(usuariosXperfil)) {
            auxIns = usuariosXperfil;
            for (Usuario us : listaOriginalAsignada) {
                if (auxIns.contains(us)) {
                    auxIns.remove(us);
                }
            }
            try {
                if (FacadeNegocio.insertarUsuariosXPerfil(perfilSeleccionado, auxIns)) {
                    success = true;
                }
            } catch (Exception ex) {
                Logger.getLogger(UsuarioPerfilBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Está tal y como estába ");
        }
        return success;
    }
}
