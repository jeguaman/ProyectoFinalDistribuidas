package com.teamj.distribuidas.util;

import java.util.Enumeration;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author José
 */
public class HttpSessionHandler {

    private HttpSession session;

    public HttpSessionHandler() {//cuado se utiliza primefaces
        try {
            session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        } catch (Exception ex) {
            System.err.println("Error " + ex);
        }
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public HttpSessionHandler(HttpSession _session) {//no se utiliza jsf (primefaces)
        session = _session;
    }

    public void logoff() {
        Enumeration<String> e = session.getAttributeNames();
        while (e.hasMoreElements()) {
            session.removeAttribute(e.nextElement());
        }
    }

    public String getPageXHTML() {
        String attrName = "pageXhtml";
        if (session.getAttribute(attrName) != null) {
            return (String) session.getAttribute(attrName);
        } else {
            return null;
        }
    }

    public void setPageXHTML(String page) {
        String attrName = "pageXhtml";
        session.setAttribute(attrName, page);
    }

    public String getNombreUsuario() {
        String attrName = "nameUser";
        if (session.getAttribute(attrName) != null) {
            return (String) session.getAttribute(attrName);
        }
        return null;
    }

    public void setNombreUsuario(String nombre) {
        String attrName = "nameUser";
        session.setAttribute(attrName, nombre);
    }

    public String getPerfil() {
        String attrName = "perfil";
        if (session.getAttribute(attrName) != null) {
            return (String) session.getAttribute(attrName);
        }
        return null;
    }

    public void setPerfil(String perfil) {
        String attrName = "perfil";
        session.setAttribute(attrName, perfil);
    }

    public String getRol() {
        String attrName = "rol";
        if (session.getAttribute(attrName) != null) {
            return (String) session.getAttribute(attrName);
        }
        return null;
    }

    public void setRol(String rol) {
        String attrName = "rol";
        session.setAttribute(attrName, rol);
    }

}
