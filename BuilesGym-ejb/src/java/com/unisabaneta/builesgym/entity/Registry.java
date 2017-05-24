/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Andres
 */
@Entity
@Table(name = "registry")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Registry.findAll", query = "SELECT r FROM Registry r")
    , @NamedQuery(name = "Registry.findByIdRegistry", query = "SELECT r FROM Registry r WHERE r.idRegistry = :idRegistry")
    , @NamedQuery(name = "Registry.findByStartDate", query = "SELECT r FROM Registry r WHERE r.startDate = :startDate")
    , @NamedQuery(name = "Registry.findByEndDate", query = "SELECT r FROM Registry r WHERE r.endDate = :endDate")})
public class Registry implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRegistry")
    private Integer idRegistry;
    @Basic(optional = false)
    @NotNull
    @Column(name = "startDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "endDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @JoinColumn(name = "Client_idClient", referencedColumnName = "idClient")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Client clientidClient;

    public Registry() {
    }

    public Registry(Integer idRegistry) {
        this.idRegistry = idRegistry;
    }

    public Registry(Integer idRegistry, Date startDate, Date endDate) {
        this.idRegistry = idRegistry;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getIdRegistry() {
        return idRegistry;
    }

    public void setIdRegistry(Integer idRegistry) {
        this.idRegistry = idRegistry;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Client getClientidClient() {
        return clientidClient;
    }

    public void setClientidClient(Client clientidClient) {
        this.clientidClient = clientidClient;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegistry != null ? idRegistry.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Registry)) {
            return false;
        }
        Registry other = (Registry) object;
        if ((this.idRegistry == null && other.idRegistry != null) || (this.idRegistry != null && !this.idRegistry.equals(other.idRegistry))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unisabaneta.builesgym.entity.Registry[ idRegistry=" + idRegistry + " ]";
    }
    
}
