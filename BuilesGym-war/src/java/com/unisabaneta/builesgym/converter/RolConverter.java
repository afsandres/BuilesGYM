/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.converter;

import com.unisabaneta.builesgym.entity.Languagelocale;
import com.unisabaneta.builesgym.entity.Rol;
import java.util.HashMap;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Andres 
 */
@FacesConverter("rolConverter")
public class RolConverter implements Converter {

    public RolConverter() {
    }

    private static HashMap<String, Object> map = new HashMap<>();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Object retrievedValue = map.get(value);
        return retrievedValue;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Rol) {
            Rol data = (Rol) value;
            map.put(data.getNameRol(), value);
            return data.getNameRol();
        }
        return null;
    }

}
