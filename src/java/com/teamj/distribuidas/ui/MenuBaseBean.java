/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamj.distribuidas.ui;

import com.teamj.distribuidas.facade.FacadeNegocio;
import com.teamj.distribuidas.model.database.Opcion;
import com.teamj.distribuidas.model.database.OpcionDePerfil;
import com.teamj.distribuidas.model.database.UsuarioXPerfil;
import com.teamj.distribuidas.util.HttpSessionHandler;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author Jose Guaman
 */
@ManagedBean
@ViewScoped
public class MenuBaseBean implements Serializable {

    private Opcion opcion;
    private List<Opcion> listaOpciones;
    private MenuModel model;
    private List<OpcionDePerfil> listaOpcionesPerfil;
    private List<Opcion> listaOpcionesDePerfil;

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }

    public List<OpcionDePerfil> getListaOpcionesPerfil() {
        return listaOpcionesPerfil;
    }

    public void setListaOpcionesPerfil(List<OpcionDePerfil> listaOpcionesPerfil) {
        this.listaOpcionesPerfil = listaOpcionesPerfil;
    }

    public Opcion getOpcion() {
        return opcion;
    }

    public void setOpcion(Opcion opcion) {
        this.opcion = opcion;
    }

    public List<Opcion> getListaOpciones() {
        return listaOpciones;
    }

    public void setListaOpciones(List<Opcion> listaOpciones) {
        this.listaOpciones = listaOpciones;
    }

    private void listarMenus() {
        HttpSessionHandler session = new HttpSessionHandler();
        String perfilSession = session.getPerfil();
        try {
            listaOpciones = FacadeNegocio.retrieveOpcionesByPerfil(Integer.parseInt(perfilSession));
        } catch (Exception ex) {
            Logger.getLogger(MenuBaseBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public MenuBaseBean() {
        init();
    }

    private void init() {
        listarMenus();
        model = new DefaultMenuModel();
        establecerPermisos();
    }

    //funciona
    public void establecerPermisos() {
        HttpSessionHandler session = new HttpSessionHandler();
//        String context = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getContextPath();
//        String page = context + "/pages/";
        String page = "/pages/";
        UsuarioXPerfil uxp;
        try {
            uxp = FacadeNegocio.retrieveUsuarioXPerfilBy_CodUsuario(Integer.parseInt(session.getCodigoUsuario()));
        } catch (Exception ex) {
            Logger.getLogger(MenuBaseBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Opcion o : listaOpciones) {
            if (o.getOpcion() == null) {
                DefaultSubMenu firstSubMenu = new DefaultSubMenu(o.getNombreOpcion());
                for (Opcion i : listaOpciones) {
                    Opcion subMenu = i.getOpcion();
                    if (subMenu != null) {
                        if (subMenu.getCodigo().compareTo(o.getCodigo()) == 0) {
                            DefaultMenuItem item = new DefaultMenuItem(i.getNombreOpcion());
                            item.setUrl(page + i.getDescripcion());
                            firstSubMenu.addElement(item);
                        }
                    }
                }
                model.addElement(firstSubMenu);
            } else {
                Opcion subMenu = o.getOpcion();
                //if (subMenu == null && String.valueOf(o.getPerfil().getCodigoPerfil()).compareTo(perfilSession) == 0) {
                if (subMenu == null) {
                    DefaultMenuItem item = new DefaultMenuItem(o.getNombreOpcion());
                    model.addElement(item);
                }
            }
        }
    }

    public void crearItem(Opcion opcion, DefaultSubMenu submenu) {

        DefaultMenuItem item = new DefaultMenuItem(opcion.getNombreOpcion());
//        String myUrl = MyUtil.basePathInicio() + opcion.getSeopcDesopc();
//        item.setUrl(myUrl);
        //colocar las urls  
        submenu.addElement(item);

    }

    public void crearSubmenu(Opcion opcion, MenuModel menuModel) {
        DefaultSubMenu submenu = new DefaultSubMenu(opcion.getNombreOpcion());
        menuModel.addElement(submenu);
    }

    public void crearSubSubmenu(Opcion opcion, DefaultSubMenu submenu) {
        MenuElement menuElement = new DefaultSubMenu(opcion.getNombreOpcion());
        submenu.addElement(menuElement);
    }

}
