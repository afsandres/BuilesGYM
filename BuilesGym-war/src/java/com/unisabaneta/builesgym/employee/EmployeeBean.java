/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.employee;

import com.unisabaneta.builesgym.dao.EmployeeFacade;
import com.unisabaneta.builesgym.dao.RolFacade;
import com.unisabaneta.builesgym.entity.Employee;
import com.unisabaneta.builesgym.entity.Rol;
import com.unisabaneta.builesgym.model.EmployeeModel;
import com.unisabaneta.builesgym.tools.MessageTools;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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
@Named(value = "employeeBean")
@SessionScoped
public class EmployeeBean implements Serializable {

    /**
     * @return the maxDate
     */
    public Date getMaxDate() {
        return maxDate;
    }

    /**
     * @param maxDate the maxDate to set
     */
    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    /**
     * @return the birthDay
     */
    public Date getBirthDay() {
        return birthDay;
    }

    /**
     * @param birthDay the birthDay to set
     */
    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    //INJECT
    @Inject
    private EmployeeFacade employeeFacade;
    @Inject
    private MessageTools messageTools;
    @Inject
    private ResourcesMgr resourcesMgr;
    @Inject
    private RolFacade rolFacade;

    //VARIABLES
    /**
     * Employee seleccionado
     */
    private Employee selectedEmployee;
    /**
     * toma la vista raiz para luego poder tomar el lenguaje local
     */
    private UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();

    /**
     * modelo de Employee para utilizar la tabla
     */
    private EmployeeModel employeeModel;

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
    
    private Date birthDay;    
    
    private Date maxDate;

    /**
     * Creates a new instance of EmployeeBean
     */
    public EmployeeBean() {
    }
    
    public void initEmployee() {
        setMaxDate(new Date());
        setSelectedEmployee(null);
        setPropertyPanelVisible(false);
        setPropertyPanelNoVisible(false);
        employeeModel = null;
    }

    //<editor-fold defaultstate="collapsed" desc="CRUD EMPLOYEE">
    /**
     * crea un "Employee y limpia el formulario para ingresar la información.
     */
    public void createEmployee() {
        Employee employee = new Employee();
        setSelectedEmployee(employee);
        setPropertyPanelVisible(true);
        messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("employee_msg_fill", viewRoot.getLocale()), "");
    }

    /**
     * edita el Employee seleccionado.
     */
    public void editEmployee() {
        try {
            employeeFacade.edit(getSelectedEmployee());
        } catch (Exception e) {
            Logger.getLogger(EmployeeBean.class.getName()).log(Level.FINEST, "editEmployee", e);
        }
        
    }

    /**
     * crea un Employee siempre y cuando el Inventory id se encuentre vacio de
     * lo contrario modifica el Inventory seleccionado si no es capaz de
     * persistir y realizar cambios en bd saldá error.
     */
    public void saveEmployeeChanges() {
        boolean isNew = getSelectedEmployee().getIdEmployee() == null;
        boolean success = false;
        Exception ex;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YY");
            selectedEmployee.setBirthdayEmployee(dateFormat.format(birthDay));
            if (isNew) {
                employeeFacade.create(getSelectedEmployee());
                messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("msg_create_successfully", viewRoot.getLocale()), "");
                employeeModel = null;
            } else {
                editEmployee();
                messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("msg_edit_successfully", viewRoot.getLocale()), "");
                employeeModel = null;
            }
            success = true;
            
        } catch (Exception e) {
            success = false;
            ex = e;
            Logger.getLogger(EmployeeBean.class.getName()).log(Level.FINEST, "saveEmployeeChanges", e);
            
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
    public void removeEmployee() {
        boolean success;
        Exception ex;
        try {
            if (getSelectedEmployee().getIdEmployee() == null) {
                messageTools.displayMessage(FacesMessage.SEVERITY_WARN, resourcesMgr.getText("employee_msg_selected_deleted", viewRoot.getLocale()), "");
                return;
            } else {
                employeeFacade.remove(getSelectedEmployee());
                messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("msg_remove_successfully", viewRoot.getLocale()), "");
                setPropertyPanelVisible(false);
                selectedEmployee = null;
                employeeModel = null;
                success = true;
            }
        } catch (Exception e) {
            success = false;
            ex = e;
            Logger.getLogger(EmployeeBean.class.getName()).log(Level.FINEST, "removeEmployee", e);
            
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
    public List<Employee> getEmployeeList() {
        return employeeFacade.findAll();
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
    public EmployeeModel getEmployeeModel() {
        if (employeeModel == null) {
            employeeModel = new EmployeeModel(getEmployeeList());
        }
        return employeeModel;
    }

    /**
     * @param employeeModel the EmployeeModel to set
     */
    public void setEmployeeModel(EmployeeModel employeeModel) {
        this.employeeModel = employeeModel;
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
    public Employee getSelectedEmployee() {
        if (selectedEmployee == null) {
            Employee employee = new Employee();
            selectedEmployee = employee;
        }
        return selectedEmployee;
    }

    /**
     * @param employee
     */
    public void setSelectedEmployee(Employee employee) {
        if (employee != null) {
            setPropertyPanelVisible(true);
            setPropertyPanelNoVisible(true);
        }
        this.selectedEmployee = employee;
    }

    /**
     * @return obtiene toda la lista de rol
     */
    public List<Rol> getRolList() {
        return rolFacade.findAll();
    }
    //</editor-fold>

}
