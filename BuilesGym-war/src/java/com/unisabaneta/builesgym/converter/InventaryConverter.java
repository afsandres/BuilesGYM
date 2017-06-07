/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.converter;

import com.unisabaneta.builesgym.entity.Inventary;
import java.util.HashMap;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Andres 
 */
@FacesConverter("inventaryConverter")
public class InventaryConverter implements Converter {

    public InventaryConverter() {
    }

    private static HashMap<String, Object> map = new HashMap<>();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Object retrievedValue = map.get(value);
        return retrievedValue;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Inventary) {
            Inventary data = (Inventary) value;
            map.put(data.getNameObject(), value);
            return data.getNameObject();
        }
        return null;
    }

}
