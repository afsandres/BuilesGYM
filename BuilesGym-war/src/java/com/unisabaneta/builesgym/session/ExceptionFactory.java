/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.session;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;
/**
 *
 * @author Andres
 */
public class ExceptionFactory extends ExceptionHandlerFactory {
 
private javax.faces.context.ExceptionHandlerFactory parent;
 
    public ExceptionFactory(ExceptionHandlerFactory  parent) {
        this.parent = parent;
    }
 
    @Override
    public ExceptionHandler getExceptionHandler() {
        ExceptionHandler result = new ViewExpiredExceptionExceptionHandler(parent.getExceptionHandler());
        return result;
    }
 
}
