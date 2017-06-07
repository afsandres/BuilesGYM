/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.client;

import com.unisabaneta.builesgym.dao.ClientFacade;
import com.unisabaneta.builesgym.dao.RegistryFacade;
import com.unisabaneta.builesgym.entity.Client;
import com.unisabaneta.builesgym.entity.Registry;
import com.unisabaneta.builesgym.tools.MessageTools;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
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
@Named(value = "searchClientBean")
@SessionScoped
public class SearchClientBean implements Serializable {

    /**
     * @return the registry
     */
    public Registry getRegistry() {
        return registry;
    }

    /**
     * @param registry the registry to set
     */
    public void setRegistry(Registry registry) {
        this.registry = registry;
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

    @Inject
    private ClientFacade clientFacade;
    @Inject
    private RegistryFacade registryFacade;
    @Inject
    private MessageTools messageTools;
    @Inject
    private ResourcesMgr resourcesMgr;

    /**
     * toma la vista raiz para luego poder tomar el lenguaje local
     */
    private UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();

    private Client client;
    private Registry registry;
    private String document;
    private Date minDate;

    /**
     * Indica si se debe renderizar el panel de propiedades para edicion del
     * grupo
     *
     */
    private boolean propertyPanelVisible;

    @PostConstruct
    private void init() {
        document = "";
        client = new Client();
        registry = new Registry();
        minDate = new Date();
    }

    public void searchClient() {
        if (!document.isEmpty()) {
            int documentInt = Integer.parseInt(getDocument());
            Client findClientByDocuement = clientFacade.findClientByDocuement(documentInt);
            if (findClientByDocuement != null) {
                client = findClientByDocuement;
                if (client.getActiveClient()) {
                    propertyPanelVisible = true;
                }
            }
            messageTools.displayMessage(FacesMessage.SEVERITY_INFO, String.format(resourcesMgr.getText("client_found_msg"), viewRoot.getLocale()), client.getActiveClient() ? "Activo" : "Bloqueado" + "");
        } else {
            propertyPanelVisible = false;
            messageTools.displayMessage(FacesMessage.SEVERITY_WARN, resourcesMgr.getText("search_document_msg_fill", viewRoot.getLocale()), "");
            return;
        }
    }

    public void registryClient() {
        try {
            registry.setClientidClient(client);
            registry.setEndDate(new Date());
            registryFacade.create(registry);
            messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("registry_full_msg", viewRoot.getLocale()), "");
        } catch (Exception e) {
             messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("error_registry_msg", viewRoot.getLocale()), "");
        }

    }

    //<editor-fold defaultstate="collapsed" desc="Setters and Getters">
    /**
     * @return the document
     */
    public String getDocument() {
        return document;
    }

    /**
     * @param document the document to set
     */
    public void setDocument(String document) {
        this.document = document;
    }

    /**
     * @return the client
     */
    public Client getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(Client client) {
        this.client = client;
    }

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
    //</editor-fold>
}
