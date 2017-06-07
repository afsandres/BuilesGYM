/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.serviceInventory;

import com.unisabaneta.builesgym.dao.InventaryFacade;
import com.unisabaneta.builesgym.dao.ServiceFacade;
import com.unisabaneta.builesgym.dao.ServiceinventaryFacade;
import com.unisabaneta.builesgym.entity.Inventary;
import com.unisabaneta.builesgym.entity.Service;
import com.unisabaneta.builesgym.entity.Serviceinventary;
import com.unisabaneta.builesgym.inventory.InventoryBean;
import com.unisabaneta.builesgym.menu.MenuService;
import com.unisabaneta.builesgym.model.ServiceInventaryModel;
import com.unisabaneta.builesgym.service.ServiceBean;
import com.unisabaneta.builesgym.session.RolSessionBean;
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
import javax.inject.Inject;
import javax.inject.Named;
import resources.business.ResourcesMgr;

/**
 *
 * @author Andres
 */
@Named(value = "serviceInventaryBean")
@SessionScoped
public class ServiceInventaryBean implements Serializable {

    @Inject
    private ServiceBean serviceBean;
    @Inject
    private InventoryBean inventaryBean;
    @Inject
    private HashTools hashTools;
    @Inject
    private MessageTools messageTools;
    @Inject
    private ResourcesMgr resourcesMgr;
    @Inject
    private ServiceinventaryFacade serviceinventaryFacade;
    @Inject
    private ServiceFacade serviceFacade;
    @Inject
    private InventaryFacade inventaryFacade;
    @Inject
    private MenuService menuService;
    @Inject
    private RolSessionBean rolSessionBean;
    //VARIABLES

    //VARIABLES
    /**
     * Employee seleccionado
     */
    private Serviceinventary selectedServiceinventary;
    /**
     * toma la vista raiz para luego poder tomar el lenguaje local
     */
    private UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();

    /**
     * modelo de Employee para utilizar la tabla
     */
    private ServiceInventaryModel serviceInventaryModel;

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
     * Creates a new instance of EmployeeBean
     */
    public ServiceInventaryBean() {
    }

    public void initServiceInventary() {
        setSelectedServiceinventary(null);
        setPropertyPanelVisible(false);
        setPropertyPanelNoVisible(false);
        serviceInventaryModel = null;
    }

    //<editor-fold defaultstate="collapsed" desc="CRUD EMPLOYEE">
    /**
     * crea un "Employee y limpia el formulario para ingresar la información.
     */
    public void createServiceinventary() {
        Serviceinventary serviceinventary = new Serviceinventary();
        setSelectedServiceinventary(serviceinventary);
        setPropertyPanelVisible(true);
    }

    /**
     * edita el Employee seleccionado.
     */
    public void editServiceInventary() {
        try {
            serviceinventaryFacade.edit(getSelectedServiceinventary());
        } catch (Exception e) {
            Logger.getLogger(ServiceInventaryBean.class.getName()).log(Level.FINEST, "editServiceInventary", e);
        }

    }

    /**
     * crea un Employee siempre y cuando el Inventory id se encuentre vacio de
     * lo contrario modifica el Inventory seleccionado si no es capaz de
     * persistir y realizar cambios en bd saldá error.
     */
    public void saveServiceInventaryChanges() {
        boolean isNew = getSelectedServiceinventary().getIdServiceInventary() == null;
        boolean success = false;
        Exception ex;
        try {
            if (isNew) {
                serviceinventaryFacade.create(getSelectedServiceinventary());
                messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("msg_create_successfully", viewRoot.getLocale()), "");
                serviceInventaryModel = null;
            } else {
                editServiceInventary();
                messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("msg_edit_successfully", viewRoot.getLocale()), "");
                serviceInventaryModel = null;
            }
            success = true;

        } catch (Exception e) {
            success = false;
            ex = e;
            Logger.getLogger(ServiceInventaryBean.class.getName()).log(Level.FINEST, "saveServiceInventaryChanges", e);

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
     * remueve el Employee seleccionado, si no hay un Employee seleccionado le
     * pide por medio de un mensaje que seleccione un grupo.
     */
    public void removeServiceInventary() {
        boolean success;
        Exception ex;
        try {
            if (getSelectedServiceinventary().getIdServiceInventary() == null) {
                messageTools.displayMessage(FacesMessage.SEVERITY_WARN, resourcesMgr.getText("serviceInventary_msg_selected_deleted", viewRoot.getLocale()), "");
                return;
            } else {
                serviceinventaryFacade.remove(getSelectedServiceinventary());
                messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("msg_remove_successfully", viewRoot.getLocale()), "");
                setPropertyPanelVisible(false);
                selectedServiceinventary= null;
                serviceInventaryModel = null;
                success = true;
            }
        } catch (Exception e) {
            success = false;
            ex = e;
            Logger.getLogger(ServiceInventaryBean.class.getName()).log(Level.FINEST, "removeServiceInventary", e);

        }

        if (!success) {
            messageTools.displayMessage(FacesMessage.SEVERITY_ERROR, resourcesMgr.getText("employee_msg_error_deleted", viewRoot.getLocale()), "");
        }
    }

    /**
     * obtiene toda la lista de Employee
     *
     * @return
     */
    public List<Serviceinventary> getServiceinventaryList() {
        return serviceinventaryFacade.findAll();
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
     * @return the getEMployeeModel
     */
    public ServiceInventaryModel getServiceInventaryModel() {
        if (serviceInventaryModel == null) {
            serviceInventaryModel = new ServiceInventaryModel(getServiceinventaryList());
        }
        return serviceInventaryModel;
    }

    /**
     * @param serviceInventaryModel
     */
    public void setServiceInventaryModel(ServiceInventaryModel serviceInventaryModel) {
        this.serviceInventaryModel = serviceInventaryModel;
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
     * @return the Employee
     */
    public Serviceinventary getSelectedServiceinventary() {
        if (selectedServiceinventary == null) {
            Serviceinventary serviceinventary = new Serviceinventary();
            selectedServiceinventary = serviceinventary;
        }
        return selectedServiceinventary;
    }

    /**
     * @param serviceinventary
     */
    public void setSelectedServiceinventary(Serviceinventary serviceinventary) {
        if (serviceinventary != null) {
            setPropertyPanelVisible(true);
            setPropertyPanelNoVisible(true);
        }
        this.selectedServiceinventary = serviceinventary;
    }

    /**
     * @return obtiene toda la lista de rol
     */
    public List<Service> getServiceList() {
        return serviceFacade.findAll();
    }

    /**
     * @return obtiene toda la lista de rol
     */
    public List<Inventary> getInventaryList() {
        return inventaryFacade.findAll();
    }
    //</editor-fold>

}
