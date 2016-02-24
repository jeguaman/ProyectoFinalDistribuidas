/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamj.distribuidas.ui;

import com.teamj.distribuidas.facade.FacadeNegocio;
import com.teamj.distribuidas.facade.FacadeNegocioGeneral;
import com.teamj.distribuidas.model.database.Opcion;
import com.teamj.distribuidas.model.database.Perfil;
import com.teamj.distribuidas.model.database.Rol;
import com.teamj.distribuidas.model.database.Sistema;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author Jose Guaman
 */
@ManagedBean
@ApplicationScoped
public class DocumentoServiceBean {

    private List<Perfil> listaPerfiles;
    private List<Sistema> listaSistemas;
    private List<Rol> listaRoles;
    private List<Opcion> listaOpciones;

//    public TreeNode createCheckboxDocuments() {
//        TreeNode root = new CheckboxTreeNode(new Document("Files", "-", "Folder"), null);
//
//        TreeNode documents = new CheckboxTreeNode(new Document("Documents", "-", "Folder"), root);
//        TreeNode pictures = new CheckboxTreeNode(new Document("Pictures", "-", "Folder"), root);
//        TreeNode movies = new CheckboxTreeNode(new Document("Movies", "-", "Folder"), root);
//
//        TreeNode work = new CheckboxTreeNode(new Document("Work", "-", "Folder"), documents);
//        TreeNode primefaces = new CheckboxTreeNode(new Document("PrimeFaces", "-", "Folder"), documents);
//
//        //Documents
//        TreeNode expenses = new CheckboxTreeNode("document", new Document("Expenses.doc", "30 KB", "Word Document"), work);
//        TreeNode resume = new CheckboxTreeNode("document", new Document("Resume.doc", "10 KB", "Word Document"), work);
//        TreeNode refdoc = new CheckboxTreeNode("document", new Document("RefDoc.pages", "40 KB", "Pages Document"), primefaces);
//
//        //Pictures
//        TreeNode barca = new CheckboxTreeNode("picture", new Document("barcelona.jpg", "30 KB", "JPEG Image"), pictures);
//        TreeNode primelogo = new CheckboxTreeNode("picture", new Document("logo.jpg", "45 KB", "JPEG Image"), pictures);
//        TreeNode optimus = new CheckboxTreeNode("picture", new Document("optimusprime.png", "96 KB", "PNG Image"), pictures);
//
//        //Movies
//        TreeNode pacino = new CheckboxTreeNode(new Document("Al Pacino", "-", "Folder"), movies);
//        TreeNode deniro = new CheckboxTreeNode(new Document("Robert De Niro", "-", "Folder"), movies);
//
//        TreeNode scarface = new CheckboxTreeNode("mp3", new Document("Scarface", "15 GB", "Movie File"), pacino);
//        TreeNode carlitosWay = new CheckboxTreeNode("mp3", new Document("Carlitos' Way", "24 GB", "Movie File"), pacino);
//
//        TreeNode goodfellas = new CheckboxTreeNode("mp3", new Document("Goodfellas", "23 GB", "Movie File"), deniro);
//        TreeNode untouchables = new CheckboxTreeNode("mp3", new Document("Untouchables", "17 GB", "Movie File"), deniro);
//
//        return root;
//    }
    public List<Perfil> getListaPerfiles() {
        return listaPerfiles;
    }

    public void setListaPerfiles(List<Perfil> listaPerfiles) {
        this.listaPerfiles = listaPerfiles;
    }

    public List<Sistema> getListaSistemas() {
        return listaSistemas;
    }

    public void setListaSistemas(List<Sistema> listaSistemas) {
        this.listaSistemas = listaSistemas;
    }

    public List<Rol> getListaRoles() {
        return listaRoles;
    }

    public void setListaRoles(List<Rol> listaRoles) {
        this.listaRoles = listaRoles;
    }

    public List<Opcion> getListaOpciones() {
        return listaOpciones;
    }

    public void setListaOpciones(List<Opcion> listaOpciones) {
        this.listaOpciones = listaOpciones;
    }

    public DocumentoServiceBean() {

    }

    public TreeNode createCheckboxDocuments() {
        init();
//        TreeNode documents = new CheckboxTreeNode(new Document("Documents", "-", "Folder"), root);
//        TreeNode pictures = new CheckboxTreeNode(new Document("Pictures", "-", "Folder"), root);
//        TreeNode movies = new CheckboxTreeNode(new Document("Movies", "-", "Folder"), root);
//
//        TreeNode work = new CheckboxTreeNode(new Document("Work", "-", "Folder"), documents);
//        TreeNode primefaces = new CheckboxTreeNode(new Document("PrimeFaces", "-", "Folder"), documents);
//
//        //Documents
//        TreeNode expenses = new CheckboxTreeNode("document", new Document("Expenses.doc", "30 KB", "Word Document"), work);
//        TreeNode resume = new CheckboxTreeNode("document", new Document("Resume.doc", "10 KB", "Word Document"), work);
//        TreeNode refdoc = new CheckboxTreeNode("document", new Document("RefDoc.pages", "40 KB", "Pages Document"), primefaces);
        TreeNode root = new CheckboxTreeNode(new Opcion(), null);
        for (int i = 0; i < listaSistemas.size(); i++) {
            Sistema auxSistema = listaSistemas.get(i);
//            TreeNode auxSistema = new CheckboxTreeNode(new Opcion(), root);

        }

        return root;

    }

    private void init() {
        loadSistemas();
    }

    private void loadSistemas() {
        try {
            listaSistemas = FacadeNegocio.retrieveTodosSistemasActivos();
        } catch (Exception ex) {
            Logger.getLogger(PrincipalBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
