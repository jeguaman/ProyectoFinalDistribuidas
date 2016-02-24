/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamj.distribuidas.ui;

import com.teamj.distribuidas.util.HttpSessionHandler;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Jose Guaman
 */
@ManagedBean
@ViewScoped
public class MainBean implements Serializable {

    private String nombreUsuario;

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public MainBean() {
        init();
    }

    private void init() {
        HttpSessionHandler session = new HttpSessionHandler();
        nombreUsuario = session.getNombreUsuario();
    }
}
