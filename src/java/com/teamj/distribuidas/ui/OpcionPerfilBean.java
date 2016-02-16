/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamj.distribuidas.ui;

import com.teamj.distribuidas.facade.FacadeNegocio;
import com.teamj.distribuidas.model.database.Opcion;
import com.teamj.distribuidas.model.database.Perfil;
import com.teamj.distribuidas.model.database.Rol;
import com.teamj.distribuidas.model.database.Sistema;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Jose Guaman
 */
@ManagedBean
@ViewScoped
public class OpcionPerfilBean implements Serializable {

    private List<Perfil> listaPerfiles;
    private Perfil perfilSeleccionado;
    private List<Sistema> listaSistemas;
    private Sistema sistemaSeleccionado;
    private List<Opcion> listaOpciones;
    private Opcion opcionSeleccionada;
    private List<Rol> listaRoles;
    private Rol rolSeleccionado;
    private TreeNode listaGeneral;
    @ManagedProperty(value = "#{documentoServiceBean}")
    private DocumentoServiceBean documentoServiceBean;

    public OpcionPerfilBean() {
        init();
    }

    public DocumentoServiceBean getDocumentoServiceBean() {
        return documentoServiceBean;
    }

    public void setDocumentoServiceBean(DocumentoServiceBean documentoServiceBean) {
        this.documentoServiceBean = documentoServiceBean;
    }

    public TreeNode getListaGeneral() {
        return listaGeneral;
    }

    public void setListaGeneral(TreeNode listaGeneral) {
        this.listaGeneral = listaGeneral;
    }

    public List<Perfil> getListaPerfiles() {
        return listaPerfiles;
    }

    public void setListaPerfiles(List<Perfil> listaPerfiles) {
        this.listaPerfiles = listaPerfiles;
    }

    public Perfil getPerfilSeleccionado() {
        return perfilSeleccionado;
    }

    public void setPerfilSeleccionado(Perfil perfilSeleccionado) {
        this.perfilSeleccionado = perfilSeleccionado;
    }

    public List<Sistema> getListaSistemas() {
        return listaSistemas;
    }

    public void setListaSistemas(List<Sistema> listaSistemas) {
        this.listaSistemas = listaSistemas;
    }

    public Sistema getSistemaSeleccionado() {
        return sistemaSeleccionado;
    }

    public void setSistemaSeleccionado(Sistema sistemaSeleccionado) {
        this.sistemaSeleccionado = sistemaSeleccionado;
    }

    public List<Opcion> getListaOpciones() {
        return listaOpciones;
    }

    public void setListaOpciones(List<Opcion> listaOpciones) {
        this.listaOpciones = listaOpciones;
    }

    public Opcion getOpcionSeleccionada() {
        return opcionSeleccionada;
    }

    public void setOpcionSeleccionada(Opcion opcionSeleccionada) {
        this.opcionSeleccionada = opcionSeleccionada;
    }

    public List<Rol> getListaRoles() {
        return listaRoles;
    }

    public void setListaRoles(List<Rol> listaRoles) {
        this.listaRoles = listaRoles;
    }

    public Rol getRolSeleccionado() {
        return rolSeleccionado;
    }

    public void setRolSeleccionado(Rol rolSeleccionado) {
        this.rolSeleccionado = rolSeleccionado;
    }

    private void init() {
        loadPerfiles();
        loadSistemas();
    }

    private void loadPerfiles() {
        try {
            listaPerfiles = FacadeNegocio.retrieveTodosPerfil();
        } catch (Exception ex) {

        }
    }

    private void loadSistemas() {
        try {
            listaSistemas = FacadeNegocio.retrieveTodosSistemas();
        } catch (Exception ex) {

        }
    }

    public void displaySelectedMultiple(TreeNode[] nodes) {
        if (nodes != null && nodes.length > 0) {
            StringBuilder builder = new StringBuilder();

            for (TreeNode node : nodes) {
                builder.append(node.getData().toString());
                builder.append("<br />");
            }
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Alerta", "Ingrese todos los campos antes de guardar."));
//            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", builder.toString());
//            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
}
