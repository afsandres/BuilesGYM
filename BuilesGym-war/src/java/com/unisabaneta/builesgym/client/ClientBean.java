/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.client;

import com.unisabaneta.builesgym.dao.ClientFacade;
import com.unisabaneta.builesgym.dao.TicketFacade;
import com.unisabaneta.builesgym.entity.Client;
import com.unisabaneta.builesgym.entity.Ticket;
import com.unisabaneta.builesgym.model.ClientModel;
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
@Named(value = "clientBean")
@SessionScoped
public class ClientBean implements Serializable {

    //INJECT
    @Inject
    private ClientFacade clientFacade;
    @Inject
    private TicketFacade ticketFacade;
    @Inject
    private MessageTools messageTools;
    @Inject
    private ResourcesMgr resourcesMgr;

    //VARIABLES
    /**
     * client seleccionado
     */
    private Client selectedClient;

    private Ticket selectedTicket;

    /**
     * toma la vista raiz para luego poder tomar el lenguaje local
     */
    private UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();

    /**
     * modelo de clientModel para utilizar la tabla
     */
    private ClientModel clientModel;

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

    private Date maxDate;

    /**
     * Creates a new instance of ClientBean
     */
    public ClientBean() {
    }

    public void initClient() {
        setMaxDate(new Date());
        setSelectedClient(null);
        setSelectedTicket(null);
        setPropertyPanelVisible(false);
        setPropertyPanelNoVisible(false);
        clientModel = null;
    }

    //<editor-fold defaultstate="collapsed" desc="CRUD CLIENT">
    /**
     * crea un "Inventory y limpia el formulario para ingresar la información.
     */
    public void createClient() {
        Client client = new Client();
        Ticket ticket = new Ticket();
        setSelectedClient(client);
        setSelectedTicket(ticket);
        setPropertyPanelVisible(true);
        messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("client_msg_fill", viewRoot.getLocale()), "");
    }

    /**
     * edita el client seleccionado.
     */
    public void editClient() {
        try {
            clientFacade.edit(getSelectedClient());
            selectedTicket.setClientidClient(selectedClient);
            ticketFacade.edit(selectedTicket);
        } catch (Exception e) {
            Logger.getLogger(ClientBean.class.getName()).log(Level.FINEST, "editClient", e);
        }

    }

    /**
     * crea un Inventory siempre y cuando el Inventory id se encuentre vacio de
     * lo contrario modifica el Inventory seleccionado si no es capaz de
     * persistir y realizar cambios en bd saldá error.
     */
    public void saveClientChanges() {
        boolean isNew = getSelectedClient().getIdClient() == null;
        boolean success = false;
        Exception ex;
        try {

            if (selectedClient.getWeightClient() < 30) {
                messageTools.displayMessage(FacesMessage.SEVERITY_ERROR, resourcesMgr.getText("msg_error_create_validate_weightClient", viewRoot.getLocale()), "");
                return;
            }
            if (isNew) {
                clientFacade.create(getSelectedClient());
                selectedTicket.setClientidClient(selectedClient);
                ticketFacade.create(selectedTicket);
                messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("msg_create_successfully", viewRoot.getLocale()), "");
                clientModel = null;
            } else {
                editClient();
                messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("msg_edit_successfully", viewRoot.getLocale()), "");
                clientModel = null;
            }
            success = true;

        } catch (Exception e) {
            success = false;
            ex = e;
            Logger.getLogger(ClientBean.class.getName()).log(Level.FINEST, "saveClientChanges", e);

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
     * remueve el Client seleccionado, si no hay un Inventory seleccionado le
     * pide por medio de un mensaje que seleccione un grupo.
     */
    public void removeClient() {
        boolean success;
        Exception ex;
        try {
            if (getSelectedClient().getIdClient() == null) {
                messageTools.displayMessage(FacesMessage.SEVERITY_WARN, resourcesMgr.getText("client_msg_selected_deleted", viewRoot.getLocale()), "");
                return;
            } else {
                ticketFacade.remove(getSelectedTicket());
                clientFacade.remove(getSelectedClient());
                messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("msg_remove_successfully", viewRoot.getLocale()), "");
                setPropertyPanelVisible(false);
                selectedClient = null;
                setSelectedTicket(null);
                clientModel = null;
                success = true;
            }
        } catch (Exception e) {
            success = false;
            ex = e;
            Logger.getLogger(ClientBean.class.getName()).log(Level.FINEST, "removeClient", e);

        }

        if (!success) {
            messageTools.displayMessage(FacesMessage.SEVERITY_ERROR, resourcesMgr.getText("client_msg_error_deleted", viewRoot.getLocale()), "");
        }
    }

    /**
     * obtiene toda la lista de Client
     *
     * @return
     */
    public List<Client> getClientList() {
        return clientFacade.findAll();
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
     * @return the getClientModel
     */
    public ClientModel getClientModel() {
        if (clientModel == null) {
            clientModel = new ClientModel(getClientList());
        }
        return clientModel;
    }

    /**
     * @param clientModel the ClientModel to set
     */
    public void setClientModel(ClientModel clientModel) {
        this.clientModel = clientModel;
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
    public Client getSelectedClient() {
        if (selectedClient == null) {
            Client client = new Client();
            selectedClient = client;
        }
        return selectedClient;
    }

    /**
     * @param client
     */
    public void setSelectedClient(Client client) {
        if (client != null) {
            setPropertyPanelVisible(true);
            setPropertyPanelNoVisible(true);
        }
        this.selectedClient = client;
    }

    /**
     * @return the selectedTicket
     */
    public Ticket getSelectedTicket() {
        if (selectedTicket == null) {
            selectedTicket = findTicket();
        }
        return selectedTicket;
    }

    /**
     * @param ticket
     */
    public void setSelectedTicket(Ticket ticket) {
        this.selectedTicket = ticket;
    }
    //</editor-fold>

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

    private Ticket findTicket() {
        Ticket ticket = new Ticket();
        List<Ticket> findAll = ticketFacade.findAll();
        if (findAll != null && !findAll.isEmpty()) {
            for (Ticket ticketa : findAll) {
                if (ticket.getClientidClient().getIdClient() == selectedClient.getIdClient()) {
                    ticket = ticketa;
                }
            }
        }
        return ticket;
    }

}
