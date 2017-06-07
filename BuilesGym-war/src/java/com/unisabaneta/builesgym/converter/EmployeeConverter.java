/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.converter;

import com.unisabaneta.builesgym.entity.Employee;
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
@FacesConverter("employeeConverter")
public class EmployeeConverter implements Converter {

    public EmployeeConverter() {
    }

    private static HashMap<String, Object> map = new HashMap<>();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Object retrievedValue = map.get(value);
        return retrievedValue;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Employee) {
            Employee data = (Employee) value;
            map.put(data.getNameEmployee(), value);
            return data.getNameEmployee();
        }
        return null;
    }

}
