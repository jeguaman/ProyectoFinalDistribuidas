/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamj.distribuidas.ui;

import com.teamj.distribuidas.util.HttpSessionHandler;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Jose Guaman
 */
@ManagedBean
@SessionScoped
public class GeneralBean implements Serializable {

    private String activeIndex;
    private String nombreUsuario;

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(String activeIndex) {
        this.activeIndex = activeIndex;
    }

    public GeneralBean() {
        init();
    }

    public void setValorIndex(String valor) {
        activeIndex = valor;
    }

    private void init() {
        HttpSessionHandler session = new HttpSessionHandler();
        nombreUsuario = session.getNombreUsuario();
    }
}
