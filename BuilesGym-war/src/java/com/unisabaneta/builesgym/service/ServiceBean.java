/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.service;

import com.unisabaneta.builesgym.dao.EmployeeFacade;
import com.unisabaneta.builesgym.dao.ServiceFacade;
import com.unisabaneta.builesgym.entity.Employee;
import com.unisabaneta.builesgym.entity.Service;
import com.unisabaneta.builesgym.model.ServiceModel;
import com.unisabaneta.builesgym.tools.MessageTools;
import java.io.Serializable;
import java.util.Date;
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
@Named(value = "serviceBean")
@SessionScoped
public class ServiceBean implements Serializable {

    //INJECT
    @Inject
    private ServiceFacade serviceFacade;
    @Inject
    private MessageTools messageTools;
    @Inject
    private ResourcesMgr resourcesMgr;
    @Inject
    private EmployeeFacade employeeFacade;

    //VARIABLES
    /**
     * Service seleccionado
     */
    private Service selectedService;
    /**
     * toma la vista raiz para luego poder tomar el lenguaje local
     */
    private UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();

    /**
     * modelo de Service para utilizar la tabla
     */
    private ServiceModel serviceModel;
    
    private Date minDate;

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
    public ServiceBean() {
    }

    public void initService() {
        minDate = new Date();
        setSelectedService(null);
        setPropertyPanelVisible(false);
        setPropertyPanelNoVisible(false);
        serviceModel = null;
    }

    //<editor-fold defaultstate="collapsed" desc="CRUD SERVICE">
    /**
     * crea un "Service y limpia el formulario para ingresar la información.
     */
    public void createService() {
        Service service = new Service();
        setSelectedService(service);
        setPropertyPanelVisible(true);
        messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("service_msg_fill", viewRoot.getLocale()), "");
    }

    /**
     * edita el Service seleccionado.
     */
    public void editService() {
        try {
            serviceFacade.edit(getSelectedService());
        } catch (Exception e) {
            Logger.getLogger(ServiceBean.class.getName()).log(Level.FINEST, "editService", e);
        }

    }

    /**
     * crea un Employee siempre y cuando el Inventory id se encuentre vacio de
     * lo contrario modifica el Inventory seleccionado si no es capaz de
     * persistir y realizar cambios en bd saldá error.
     */
    public void saveServiceChanges() {
        boolean isNew = getSelectedService().getIdService() == null;
        boolean success = false;
        Exception ex;
        try {
            if (isNew) {
                serviceFacade.create(getSelectedService());
                messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("msg_create_successfully", viewRoot.getLocale()), "");
                serviceModel = null;
            } else {
                editService();
                messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("msg_edit_successfully", viewRoot.getLocale()), "");
                serviceModel = null;
            }
            success = true;

        } catch (Exception e) {
            success = false;
            ex = e;
            Logger.getLogger(ServiceBean.class.getName()).log(Level.FINEST, "saveServiceChanges", e);

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
    public void removeService() {
        boolean success;
        Exception ex;
        try {
            if (getSelectedService().getIdService() == null) {
                messageTools.displayMessage(FacesMessage.SEVERITY_WARN, resourcesMgr.getText("service_msg_selected_deleted", viewRoot.getLocale()), "");
                return;
            } else {
                serviceFacade.remove(getSelectedService());
                messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("msg_remove_successfully", viewRoot.getLocale()), "");
                setPropertyPanelVisible(false);
                selectedService = null;
                serviceModel = null;
                success = true;
            }
        } catch (Exception e) {
            success = false;
            ex = e;
            Logger.getLogger(ServiceBean.class.getName()).log(Level.FINEST, "removeService", e);

        }

        if (!success) {
            messageTools.displayMessage(FacesMessage.SEVERITY_ERROR, resourcesMgr.getText("service_msg_error_deleted", viewRoot.getLocale()), "");
        }
    }

    /**
     * obtiene toda la lista de Service
     *
     * @return
     */
    public List<Service> getServiceList() {
        return serviceFacade.findAll();
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
    public ServiceModel getServiceModel() {
        if (serviceModel == null) {
            serviceModel = new ServiceModel(getServiceList());
        }
        return serviceModel;
    }

    /**
     * @param serviceModel the ServiceModel to set
     */
    public void setServiceModel(ServiceModel serviceModel) {
        this.serviceModel = serviceModel;
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
     * @return the Service
     */
    public Service getSelectedService() {
        if (selectedService == null) {
            Service service = new Service();
            selectedService = service;
        }
        return selectedService;
    }

    /**
     * @param employee
     */
    public void setSelectedService(Service service) {
        if (service != null) {
            setPropertyPanelVisible(true);
            setPropertyPanelNoVisible(true);
        }
        this.selectedService = service;
    }
    
    /**
     * @return obtiene toda la lista de employee
     */
    public List<Employee> getEmployeeList() {
        return employeeFacade.findAll();
    }
    //</editor-fold>

    /**
     * @return the minDate
     */
    public Date getMinDate() {
        return minDate;
    }

    /**
     * @param minDate the minDate to set
     */
    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

}
