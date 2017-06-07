/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.converter;

import com.unisabaneta.builesgym.entity.Employee;
import com.unisabaneta.builesgym.entity.Service;
import java.util.HashMap;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Andres 
 */
@FacesConverter("serviceConverter")
public class ServiceConverter implements Converter {

    public ServiceConverter() {
    }

    private static HashMap<String, Object> map = new HashMap<>();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Object retrievedValue = map.get(value);
        return retrievedValue;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Service) {
            Service data = (Service) value;
            map.put(data.getWeekday(), value);
            return data.getWeekday();
        }
        return null;
    }

}
