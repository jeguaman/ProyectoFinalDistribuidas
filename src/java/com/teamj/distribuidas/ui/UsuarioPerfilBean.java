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
    private List<Usuario> source;
    private List<Usuario> target;
    private List<Usuario> auxListUsuarioAsignadoPerfil;

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
        source = new ArrayList<>();
        target = new ArrayList<>();
        if (listaPerfiles != null) {
            if (perfilSeleccionado != null && perfilSeleccionado.compareTo(-1) != 0) {
                try {
                    listaUsuario = FacadeNegocio.retrieveTodosUsuariosByPerfil(perfilSeleccionado);
                    if (listaUsuario != null) {
                        for (int i = 0; i < listaUsuario.size(); i++) {
                            target.add(listaUsuario.get(i));
                        }
                        auxListUsuarioAsignadoPerfil.addAll(target);
                    }
                } catch (Exception ex) {

                }
                try {
                    listaTodosUsuario = FacadeNegocio.retrieveListUsuariosNoAsignados();
                    if (listaTodosUsuario != null) {
                        for (int i = 0; i < listaTodosUsuario.size(); i++) {
                            source.add(listaTodosUsuario.get(i));
                        }
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
        List<Usuario> usuarios;
        List<Integer> usuarioInsertar = new ArrayList<>();
        List<Integer> usuarioEliminar = new ArrayList<>();
        usuarios = listaDualUsuario.getTarget();
        if (!auxListUsuarioAsignadoPerfil.containsAll(usuarios)) {//si no contiene los mismos elementos hace lo siguiente
            for (int i = 0; i < auxListUsuarioAsignadoPerfil.size(); i++) {
                for (int j = 0; j < usuarios.size(); j++) {
                    if (auxListUsuarioAsignadoPerfil.get(i).getCodigo().compareTo(usuarios.get(j).getCodigo()) != 0) {
                        usuarioInsertar.add(usuarios.get(j).getCodigo());
                    }
                }
                usuarioEliminar.add(auxListUsuarioAsignadoPerfil.get(i).getCodigo());
            }

        }

    }

}
