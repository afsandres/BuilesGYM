/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.client;

import com.unisabaneta.builesgym.dao.ClientFacade;
import com.unisabaneta.builesgym.entity.Client;
import com.unisabaneta.builesgym.tools.MessageTools;
import java.io.Serializable;
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
    private MessageTools messageTools;
    @Inject
    private ResourcesMgr resourcesMgr;

    /**
     * toma la vista raiz para luego poder tomar el lenguaje local
     */
    private UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();

    private Client client;

    private String document;

    /**
     * Indica si se debe renderizar el panel de propiedades para edicion del
     * grupo
     *
     */
    private boolean propertyPanelVisible;

    public void searchClient() {
        if (!document.isEmpty()) {
            int documentInt = Integer.parseInt(getDocument());
            Client findClientByDocuement = clientFacade.findClientByDocuement(documentInt);
            if (findClientByDocuement != null) {
                client = new Client();
                client = findClientByDocuement;
                propertyPanelVisible = true;
            } else {
                propertyPanelVisible = false;
                messageTools.displayMessage(FacesMessage.SEVERITY_ERROR, resourcesMgr.getText("client_not_found_msg", viewRoot.getLocale()), "");
                return;
            }
        } else {
            propertyPanelVisible = false;
            messageTools.displayMessage(FacesMessage.SEVERITY_WARN, resourcesMgr.getText("search_document_msg_fill", viewRoot.getLocale()), "");
            return;
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
//</editor-fold>
}
