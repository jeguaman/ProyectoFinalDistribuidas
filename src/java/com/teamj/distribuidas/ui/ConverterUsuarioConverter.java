/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamj.distribuidas.ui;

import com.teamj.distribuidas.model.database.Usuario;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Jose Guaman
 */
@FacesConverter(value = "converterPickList")
public class ConverterUsuarioConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return getObjectFromUIPickListComponent(component, value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        String string;
        if (value == null) {
            string = "";
        } else {
            try {
                string = String.valueOf(((Usuario) value).getCodigo());
            } catch (ClassCastException cce) {
                throw new ConverterException();
            }
        }
        return string;
    }

    @SuppressWarnings("unchecked")
    private Usuario getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<Usuario> dualList;
        try {
            dualList = (DualListModel<Usuario>) ((PickList) component).getValue();
            Usuario team = getObjectFromList(dualList.getSource(), Integer.valueOf(value));
            if (team == null) {
                team = getObjectFromList(dualList.getTarget(), Integer.valueOf(value));
            }

            return team;
        } catch (ClassCastException | NumberFormatException cce) {
            throw new ConverterException();
        }
    }

    private Usuario getObjectFromList(final List<?> list, final Integer identifier) {
        for (final Object object : list) {
            final Usuario team = (Usuario) object;
            if (team.getCodigo().equals(identifier)) {
                return team;
            }
        }
        return null;
    }
}
