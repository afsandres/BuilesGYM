/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Andres
 */
@Entity
@Table(name = "client")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c")
    , @NamedQuery(name = "Client.findByIdClient", query = "SELECT c FROM Client c WHERE c.idClient = :idClient")
    , @NamedQuery(name = "Client.findByNameClient", query = "SELECT c FROM Client c WHERE c.nameClient = :nameClient")
    , @NamedQuery(name = "Client.findByBirthdayClient", query = "SELECT c FROM Client c WHERE c.birthdayClient = :birthdayClient")
    , @NamedQuery(name = "Client.findByAddressClient", query = "SELECT c FROM Client c WHERE c.addressClient = :addressClient")
    , @NamedQuery(name = "Client.findByEmailClient", query = "SELECT c FROM Client c WHERE c.emailClient = :emailClient")
    , @NamedQuery(name = "Client.findByCellPhoneClient", query = "SELECT c FROM Client c WHERE c.cellPhoneClient = :cellPhoneClient")
    , @NamedQuery(name = "Client.findByDocumentClient", query = "SELECT c FROM Client c WHERE c.documentClient = :documentClient")
    , @NamedQuery(name = "Client.findByWeightClient", query = "SELECT c FROM Client c WHERE c.weightClient = :weightClient")
    , @NamedQuery(name = "Client.findByHeightClient", query = "SELECT c FROM Client c WHERE c.heightClient = :heightClient")
    , @NamedQuery(name = "Client.findByActiveClient", query = "SELECT c FROM Client c WHERE c.activeClient = :activeClient")})
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idClient")
    private Integer idClient;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nameClient")
    private String nameClient;
    @Basic(optional = false)
    @NotNull
    @Column(name = "birthdayClient")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthdayClient;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "addressClient")
    private String addressClient;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "emailClient")
    private String emailClient;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "cellPhoneClient")
    private String cellPhoneClient;
    @Basic(optional = false)
    @NotNull
    @Column(name = "documentClient")
    private int documentClient;
    @Basic(optional = false)
    @NotNull
    @Column(name = "weightClient")
    private long weightClient;
    @Basic(optional = false)
    @NotNull
    @Column(name = "heightClient")
    private long heightClient;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activeClient")
    private boolean activeClient;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientidClient", fetch = FetchType.LAZY)
    private List<Registry> registryList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientidClient", fetch = FetchType.LAZY)
    private List<Ticket> ticketList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientidClient", fetch = FetchType.LAZY)
    private List<Routineplan> routineplanList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientidClient", fetch = FetchType.LAZY)
    private List<Serviceschedule> servicescheduleList;

    public Client() {
    }

    public Client(Integer idClient) {
        this.idClient = idClient;
    }

    public Client(Integer idClient, String nameClient, Date birthdayClient, String addressClient, String emailClient, String cellPhoneClient, int documentClient, long weightClient, long heightClient, boolean activeClient) {
        this.idClient = idClient;
        this.nameClient = nameClient;
        this.birthdayClient = birthdayClient;
        this.addressClient = addressClient;
        this.emailClient = emailClient;
        this.cellPhoneClient = cellPhoneClient;
        this.documentClient = documentClient;
        this.weightClient = weightClient;
        this.heightClient = heightClient;
        this.activeClient = activeClient;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public Date getBirthdayClient() {
        return birthdayClient;
    }

    public void setBirthdayClient(Date birthdayClient) {
        this.birthdayClient = birthdayClient;
    }

    public String getAddressClient() {
        return addressClient;
    }

    public void setAddressClient(String addressClient) {
        this.addressClient = addressClient;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    public String getCellPhoneClient() {
        return cellPhoneClient;
    }

    public void setCellPhoneClient(String cellPhoneClient) {
        this.cellPhoneClient = cellPhoneClient;
    }

    public int getDocumentClient() {
        return documentClient;
    }

    public void setDocumentClient(int documentClient) {
        this.documentClient = documentClient;
    }

    public long getWeightClient() {
        return weightClient;
    }

    public void setWeightClient(long weightClient) {
        this.weightClient = weightClient;
    }

    public long getHeightClient() {
        return heightClient;
    }

    public void setHeightClient(long heightClient) {
        this.heightClient = heightClient;
    }

    public boolean getActiveClient() {
        return activeClient;
    }

    public void setActiveClient(boolean activeClient) {
        this.activeClient = activeClient;
    }

    @XmlTransient
    public List<Registry> getRegistryList() {
        return registryList;
    }

    public void setRegistryList(List<Registry> registryList) {
        this.registryList = registryList;
    }

    @XmlTransient
    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    @XmlTransient
    public List<Routineplan> getRoutineplanList() {
        return routineplanList;
    }

    public void setRoutineplanList(List<Routineplan> routineplanList) {
        this.routineplanList = routineplanList;
    }

    @XmlTransient
    public List<Serviceschedule> getServicescheduleList() {
        return servicescheduleList;
    }

    public void setServicescheduleList(List<Serviceschedule> servicescheduleList) {
        this.servicescheduleList = servicescheduleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClient != null ? idClient.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.idClient == null && other.idClient != null) || (this.idClient != null && !this.idClient.equals(other.idClient))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unisabaneta.builesgym.entity.Client[ idClient=" + idClient + " ]";
    }
    
}
