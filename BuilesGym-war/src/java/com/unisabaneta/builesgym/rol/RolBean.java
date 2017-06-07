/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.rol;

import com.unisabaneta.builesgym.dao.LanguagelocaleFacade;
import com.unisabaneta.builesgym.dao.RolFacade;
import com.unisabaneta.builesgym.entity.Languagelocale;
import com.unisabaneta.builesgym.entity.Rol;
import com.unisabaneta.builesgym.model.RolModel;
import com.unisabaneta.builesgym.tools.HashTools;
import com.unisabaneta.builesgym.tools.MessageTools;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.inject.Inject;
import resources.business.ResourcesMgr;

/**
 *
 * @author Andres
 */
@Named(value = "rolBean")
@SessionScoped
public class RolBean implements Serializable {

    //INJECT
    @Inject
    private RolFacade rolFacade;
    @Inject
    private MessageTools messageTools;
    @Inject
    private ResourcesMgr resourcesMgr;
    @Inject
    private HashTools hashTools;
    @Inject
    private LanguagelocaleFacade languagelocaleFacade;

    //VARIABLES
    /**
     * Rol seleccionado
     */
    private Rol selectedRol;
    /**
     * toma la vista raiz para luego poder tomar el lenguaje local
     */
    private UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();

    /**
     * modelo de Rol para utilizar la tabla
     */
    private RolModel rolModel;

    /**
     * indica el password actual del usuario seleccionado
     */
    private String selectedrolPassword;

    /**
     * Indica si se debe renderizar el panel de propiedades para edicion del
     * grupo
     *
     */
    private boolean propertyPanelVisible;
    /**
     * Indica no se renderiza el panel de propiedades cuando se está mostrando
     * los parametros genericos
     */
    private boolean propertyPanelNoVisible;

    /**
     * Creates a new instance of RolBean
     */
    public RolBean() {
    }

    public void initRol() {
        setSelectedRol(null);
        setPropertyPanelVisible(false);
        setPropertyPanelNoVisible(false);
        selectedrolPassword = null;
        rolModel = null;
    }

    //<editor-fold defaultstate="collapsed" desc="CRUD ROUTINE">
    /**
     * crea un "Rol y limpia el formulario para ingresar la información.
     */
    public void createRol() {
        Rol rol = new Rol();
        setSelectedRol(rol);
        setPropertyPanelVisible(true);
        messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("rol_msg_fill", viewRoot.getLocale()), "");
    }

    /**
     * edita el Rol seleccionada.
     */
    public void editRol() {
        try {
            rolFacade.edit(getSelectedRol());
        } catch (Exception e) {
            Logger.getLogger(RolBean.class.getName()).log(Level.FINEST, "editRol", e);
        }

    }

    /**
     * crea un Rol siempre y cuando el Rol id se encuentre vacio de lo contrario
     * modifica el Routine seleccionado si no es capaz de persistir y realizar
     * cambios en bd saldá error.
     */
    public void saveRolChanges() {

        boolean isNew = selectedRol.getIdRol() == null;
        String password = selectedRol.getPasswordRol();
        boolean success = false;
        Exception ex;
        try {
            String passwordMD5 = hashTools.calculateMD5(password);
            if (isNew) {
                selectedRol.setPasswordRol(passwordMD5);
                rolFacade.create(selectedRol);
                messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("msg_create_successfully", viewRoot.getLocale()), "");
                rolModel = null;
            } else {
                boolean isUserPasswordChanged = !selectedrolPassword.equals(password);
                if (isUserPasswordChanged) {
                    selectedRol.setPasswordRol(passwordMD5);
                }
                editRol();
                selectedrolPassword = passwordMD5;
                messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("msg_edit_successfully", viewRoot.getLocale()), "");
                rolModel = null;
            }
            success = true;

        } catch (Exception e) {
            success = false;
            ex = e;
            Logger.getLogger(RolBean.class.getName()).log(Level.FINEST, "saveRolChanges", e);
        }

        if (!success) {
            if (isNew) {
                messageTools.displayMessage(FacesMessage.SEVERITY_ERROR, resourcesMgr.getText("msg_error_create", viewRoot.getLocale()), "");
            } else {
                messageTools.displayMessage(FacesMessage.SEVERITY_ERROR, resourcesMgr.getText("msg_error_edit", viewRoot.getLocale()), "");
            }
        }
    }

    /**
     * remueve el Rol seleccionado, si no hay un Rol seleccionado le pide por
     * medio de un mensaje que seleccione una rutina.
     */
    public void removeRol() {
        boolean success;
        Exception ex;
        try {
            if (getSelectedRol().getIdRol() == null) {
                messageTools.displayMessage(FacesMessage.SEVERITY_WARN, resourcesMgr.getText("rol_msg_selected_deleted", viewRoot.getLocale()), "");
                return;
            } else {
                rolFacade.remove(getSelectedRol());
                messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("msg_remove_successfully", viewRoot.getLocale()), "");
                setPropertyPanelVisible(false);
                selectedRol = null;
                rolModel = null;
                success = true;
            }
        } catch (Exception e) {
            success = false;
            ex = e;
            Logger
                    .getLogger(RolBean.class
                            .getName()).log(Level.FINEST, "removeRol", e);
        }

        if (!success) {
            messageTools.displayMessage(FacesMessage.SEVERITY_ERROR, resourcesMgr.getText("rol_msg_error_deleted", viewRoot.getLocale()), "");
        }
    }

    /**
     * obtiene toda la lista de Rol
     *
     * @return
     */
    public List<Rol> getRolList() {
        return rolFacade.findAll();
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="SETTERS & GETTERS">
    /**
     * @return the propertyPanelNoVisible
     */
    public boolean isPropertyPanelNoVisible() {
        return propertyPanelNoVisible;
    }

    /**
     * @param propertyPanelNoVisible the propertyPanelNoVisible to set
     */
    public void setPropertyPanelNoVisible(boolean propertyPanelNoVisible) {
        this.propertyPanelNoVisible = propertyPanelNoVisible;
    }

    /**
     * @return the getRolModel
     */
    public RolModel getRolModel() {
        if (rolModel == null) {
            rolModel = new RolModel(getRolList());
        }
        return rolModel;
    }

    /**
     * @param rolModel
     */
    public void setRolModel(RolModel rolModel) {
        this.rolModel = rolModel;
    }

    /**
     * @return the propertyPanelVisible
     */
    public boolean isPropertyPanelVisible() {
        return propertyPanelVisible;
    }

    /**
     * @param propertyPanelVisible the propertyPanelVisible to set
     */
    public void setPropertyPanelVisible(boolean propertyPanelVisible) {
        this.propertyPanelVisible = propertyPanelVisible;
    }

    /**
     * @return the rol
     */
    public Rol getSelectedRol() {
        if (selectedRol == null) {
            Rol rol = new Rol();
            selectedRol = rol;
        }
        return selectedRol;
    }

    /**
     * @param rol
     */
    public void setSelectedRol(Rol rol) {
        if (rol != null) {
            selectedrolPassword = rol.getPasswordRol();
            setPropertyPanelVisible(true);
            setPropertyPanelNoVisible(true);
        }
        this.selectedRol = rol;
    }

    /**
     * @return the selectedrolPassword
     */
    public String getSelectedrolPassword() {
        return selectedrolPassword;
    }

    /**
     * @param selectedrolPassword the selectedrolPassword to set
     */
    public void setSelectedrolPassword(String selectedrolPassword) {
        this.selectedrolPassword = selectedrolPassword;
    }

    /**
     * @return obtiene toda la lista de lenguajes
     */
    public List<Languagelocale> getLanguageLocaleList() {
        return languagelocaleFacade.findAll();
    }

    //</editor-fold>
}
