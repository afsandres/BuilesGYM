/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.inventory;

import com.unisabaneta.builesgym.dao.InventaryFacade;
import com.unisabaneta.builesgym.entity.Inventary;
import com.unisabaneta.builesgym.model.InventoryModel;
import com.unisabaneta.builesgym.tools.MessageTools;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import resources.business.ResourcesMgr;

/**
 *
 * @author Andres
 */
@Named(value = "inventoryBean")
@ViewScoped
public class InventoryBean implements Serializable {

    //INJECT
    @Inject
    private InventaryFacade inventaryFacade;
    @Inject
    private MessageTools messageTools;
    @Inject
    private ResourcesMgr resourcesMgr;

    //VARIABLES
    /**
     * Inventary seleccionado
     */
    private Inventary selectedInventary;
    /**
     * toma la vista raiz para luego poder tomar el lenguaje local
     */
    private UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();

    /**
     * modelo de Inventory para utilizar la tabla
     */
    private InventoryModel inventoryModel;

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
    public InventoryBean() {
    }

    public void initInventory() {
        setSelectedInventary(null);
        setPropertyPanelVisible(false);
        setPropertyPanelNoVisible(false);
        inventoryModel = null;
    }

    //<editor-fold defaultstate="collapsed" desc="CRUD INVENTORY">
    /**
     * crea un "Inventory y limpia el formulario para ingresar la información.
     */
    public void createInventory() {
        Inventary inventary = new Inventary();
        setSelectedInventary(inventary);
        setPropertyPanelVisible(true);
        messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("rfid_tag_msg_fill", viewRoot.getLocale()), "");
    }

    /**
     * edita el RfidTag seleccionado.
     */
    public void editInventory() {
        try {
            inventaryFacade.edit(getSelectedInventary());
        } catch (Exception e) {
            Logger.getLogger(InventoryBean.class.getName()).log(Level.FINEST, "editInventory", e);
        }

    }

    /**
     * crea un Inventory siempre y cuando el Inventory id se encuentre vacio de
     * lo contrario modifica el Inventory seleccionado si no es capaz de
     * persistir y realizar cambios en bd saldá error.
     */
    public void saveInventoryChanges() {
        boolean isNew = getSelectedInventary().getIdInventary() == null;
        boolean success = false;
        Exception ex;
        try {

            if (isNew) {
                inventaryFacade.create(getSelectedInventary());
                messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("rfid_tag_msg_create", viewRoot.getLocale()), "");
                inventoryModel = null;

            } else {
                editInventory();
                messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("rfid_tag_msg_edit", viewRoot.getLocale()), "");
                inventoryModel = null;
            }
            success = true;

        } catch (Exception e) {
            success = false;
            ex = e;
            Logger.getLogger(InventoryBean.class.getName()).log(Level.FINEST, "saveInventoryChanges", e);

        }

        if (!success) {
            if (isNew) {
                messageTools.displayMessage(FacesMessage.SEVERITY_ERROR, resourcesMgr.getText("rfid_tag_msg_error_create", viewRoot.getLocale()), "");
            } else {
                messageTools.displayMessage(FacesMessage.SEVERITY_ERROR, resourcesMgr.getText("rfid_tag_msg_error_edit", viewRoot.getLocale()), "");

            }

        }

    }

    /**
     * remueve el Inventory seleccionado, si no hay un Inventory seleccionado le
     * pide por medio de un mensaje que seleccione un grupo.
     */
    public void removeInventory() {

        boolean success;
        boolean isNoUsedInventory = false;
        Exception ex;
        try {
            if (getSelectedInventary().getIdInventary() == null) {
                messageTools.displayMessage(FacesMessage.SEVERITY_WARN, resourcesMgr.getText("rfid_tag_msg_error_deleted", viewRoot.getLocale()), "");
                return;
            } else {

                inventaryFacade.remove(getSelectedInventary());
                messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("rfid_tag_msg_deleted", viewRoot.getLocale()), "");
                selectedInventary = null;
                inventoryModel = null;
                success = true;

            }
        } catch (Exception e) {
            success = false;
            ex = e;
            Logger.getLogger(InventoryBean.class.getName()).log(Level.FINEST, "removeInventory", e);

        }

        if (!success) {
            if (!isNoUsedInventory) {
                messageTools.displayMessage(FacesMessage.SEVERITY_ERROR, resourcesMgr.getText("rfid_tag_msg_error_deleted", viewRoot.getLocale()), "");

            } else {
                messageTools.displayMessage(FacesMessage.SEVERITY_ERROR, resourcesMgr.getText("rfid_tag_msg_error_deleted", viewRoot.getLocale()), "");

            }
        }

    }

    /**
     * obtiene toda la lista de Inventory
     *
     * @return
     */
    public List<Inventary> getInventoryList() {
        return inventaryFacade.findAll();
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
     * @return the getInventoryModel
     */
    public InventoryModel getInventoryModel() {
        if (inventoryModel == null) {
            inventoryModel = new InventoryModel(getInventoryList());
        }
        return inventoryModel;
    }

    /**
     * @param inventoryModel the InventoryModel to set
     */
    public void setInventaryModel(InventoryModel inventoryModel) {
        this.inventoryModel = inventoryModel;
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
     * @return the Inventary
     */
    public Inventary getSelectedInventary() {
        if (selectedInventary == null) {
            Inventary inventary = new Inventary();
            selectedInventary = inventary;
        }
        return selectedInventary;
    }

    /**
     * @param inventary
     */
    public void setSelectedInventary(Inventary inventary) {
        if (inventary != null) {
            setPropertyPanelVisible(true);
            setPropertyPanelNoVisible(true);
        }
        this.selectedInventary = inventary;
    }
    //</editor-fold>

}
