/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.routine;

import com.unisabaneta.builesgym.dao.RoutineFacade;
import com.unisabaneta.builesgym.entity.Routine;
import com.unisabaneta.builesgym.model.RoutineModel;
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
@Named(value = "routineBean")
@SessionScoped
public class RoutineBean implements Serializable {

    //INJECT
    @Inject
    private RoutineFacade routineFacade;
    @Inject
    private MessageTools messageTools;
    @Inject
    private ResourcesMgr resourcesMgr;

    //VARIABLES
    /**
     * Routine seleccionado
     */
    private Routine selectedRoutine;
    /**
     * toma la vista raiz para luego poder tomar el lenguaje local
     */
    private UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();

    /**
     * modelo de Routine para utilizar la tabla
     */
    private RoutineModel routineModel;

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
     * Creates a new instance of InventoryBean
     */
    public RoutineBean() {
    }

    public void initRoutine() {
        setSelectedRoutine(null);
        setPropertyPanelVisible(false);
        setPropertyPanelNoVisible(false);
        routineModel = null;
    }

    //<editor-fold defaultstate="collapsed" desc="CRUD ROUTINE">
    /**
     * crea un "Routine y limpia el formulario para ingresar la información.
     */
    public void createRoutine() {
        Routine routine = new Routine();
        setSelectedRoutine(routine);
        setPropertyPanelVisible(true);
        messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("routine_msg_fill", viewRoot.getLocale()), "");
    }

    /**
     * edita el Routine seleccionada.
     */
    public void editRoutine() {
        try {
            routineFacade.edit(getSelectedRoutine());
        } catch (Exception e) {
            Logger.getLogger(RoutineBean.class.getName()).log(Level.FINEST, "editRoutine", e);
        }

    }

    /**
     * crea un Routine siempre y cuando el Inventory id se encuentre vacio de
     * lo contrario modifica el Routine seleccionado si no es capaz de
     * persistir y realizar cambios en bd saldá error.
     */
    public void saveRoutineChanges() {
        boolean isNew = getSelectedRoutine().getIdRoutine() == null;
        boolean success = false;
        Exception ex;
        try {
            if (isNew) {
                routineFacade.create(getSelectedRoutine());
                messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("msg_create_successfully", viewRoot.getLocale()), "");
                routineModel = null;
            } else {
                editRoutine();
                messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("msg_edit_successfully", viewRoot.getLocale()), "");
                routineModel = null;
            }
            success = true;

        } catch (Exception e) {
            success = false;
            ex = e;
            Logger.getLogger(RoutineBean.class.getName()).log(Level.FINEST, "saveRoutineChanges", e);
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
     * remueve el Routine seleccionado, si no hay un Routine seleccionado le
     * pide por medio de un mensaje que seleccione una rutina.
     */
    public void removeRoutine() {
        boolean success;
        Exception ex;
        try {
            if (getSelectedRoutine().getIdRoutine() == null) {
                messageTools.displayMessage(FacesMessage.SEVERITY_WARN, resourcesMgr.getText("routine_msg_selected_deleted", viewRoot.getLocale()), "");
                return;
            } else {
                routineFacade.remove(getSelectedRoutine());
                messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("msg_remove_successfully", viewRoot.getLocale()), "");
                setPropertyPanelVisible(false);
                selectedRoutine = null;
                routineModel = null;
                success = true;
            }
        } catch (Exception e) {
            success = false;
            ex = e;
            Logger.getLogger(RoutineBean.class.getName()).log(Level.FINEST, "removeRoutine", e);
        }

        if (!success) {
            messageTools.displayMessage(FacesMessage.SEVERITY_ERROR, resourcesMgr.getText("routine_msg_error_deleted", viewRoot.getLocale()), "");
        }
    }

    /**
     * obtiene toda la lista de Routine
     *
     * @return
     */
    public List<Routine> getRoutineList() {
        return routineFacade.findAll();
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
     * @return the getRoutineModel
     */
    public RoutineModel getRoutineModel() {
        if (routineModel == null) {
            routineModel = new RoutineModel(getRoutineList());
        }
        return routineModel;
    }

    /**
     * @param routineModel the routineModel to set
     */
    public void setRoutineModel(RoutineModel routineModel) {
        this.routineModel = routineModel;
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
     * @return the routine
     */
    public Routine getSelectedRoutine() {
        if (selectedRoutine == null) {
            Routine routine = new Routine();
            selectedRoutine = routine;
        }
        return selectedRoutine;
    }

    /**
     * @param routine
     */
    public void setSelectedRoutine(Routine routine) {
        if (routine != null) {
            setPropertyPanelVisible(true);
            setPropertyPanelNoVisible(true);
        }
        this.selectedRoutine = routine;
    }
    //</editor-fold>

}
