/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.tools;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Andres Sanchez nebulae.com.co
 */
@Stateless
public class MessageTools {

    public MessageTools() {
    }
    
     /**
     * Encola un mensaje para ser visualizado por el usuario
     *
     * @param severity FacesMessage.Severity
     * @param summary Resumen ó Titulo
     * @param detail Descripción detallada
     */
    public void displayMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    } 
    
    

    
}
