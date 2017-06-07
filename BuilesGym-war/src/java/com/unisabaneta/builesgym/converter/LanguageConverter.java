/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.converter;

import com.unisabaneta.builesgym.entity.Languagelocale;
import java.util.HashMap;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Andres 
 */
@FacesConverter("languajeConverter")
public class LanguageConverter implements Converter {

    public LanguageConverter() {
    }

    private static HashMap<String, Object> map = new HashMap<>();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Object retrievedValue = map.get(value);
        return retrievedValue;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Languagelocale) {
            Languagelocale data = (Languagelocale) value;
            map.put(data.getName(), value);
            return data.getName();
        }
        return null;
    }

}
