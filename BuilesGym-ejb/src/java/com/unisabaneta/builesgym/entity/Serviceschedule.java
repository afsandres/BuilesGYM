/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.entity;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Andres
 */
@Entity
@Table(name = "serviceschedule")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Serviceschedule.findAll", query = "SELECT s FROM Serviceschedule s")
    , @NamedQuery(name = "Serviceschedule.findByIdServiceSchedule", query = "SELECT s FROM Serviceschedule s WHERE s.idServiceSchedule = :idServiceSchedule")
    , @NamedQuery(name = "Serviceschedule.findByActiveServiceSchedule", query = "SELECT s FROM Serviceschedule s WHERE s.activeServiceSchedule = :activeServiceSchedule")})
public class Serviceschedule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idServiceSchedule")
    private Integer idServiceSchedule;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activeServiceSchedule")
    private boolean activeServiceSchedule;
    @JoinColumn(name = "Client_idClient", referencedColumnName = "idClient")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Client clientidClient;
    @JoinColumn(name = "Service_idService", referencedColumnName = "idService")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Service serviceidService;

    public Serviceschedule() {
    }

    public Serviceschedule(Integer idServiceSchedule) {
        this.idServiceSchedule = idServiceSchedule;
    }

    public Serviceschedule(Integer idServiceSchedule, boolean activeServiceSchedule) {
        this.idServiceSchedule = idServiceSchedule;
        this.activeServiceSchedule = activeServiceSchedule;
    }

    public Integer getIdServiceSchedule() {
        return idServiceSchedule;
    }

    public void setIdServiceSchedule(Integer idServiceSchedule) {
        this.idServiceSchedule = idServiceSchedule;
    }

    public boolean getActiveServiceSchedule() {
        return activeServiceSchedule;
    }

    public void setActiveServiceSchedule(boolean activeServiceSchedule) {
        this.activeServiceSchedule = activeServiceSchedule;
    }

    public Client getClientidClient() {
        return clientidClient;
    }

    public void setClientidClient(Client clientidClient) {
        this.clientidClient = clientidClient;
    }

    public Service getServiceidService() {
        return serviceidService;
    }

    public void setServiceidService(Service serviceidService) {
        this.serviceidService = serviceidService;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idServiceSchedule != null ? idServiceSchedule.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Serviceschedule)) {
            return false;
        }
        Serviceschedule other = (Serviceschedule) object;
        if ((this.idServiceSchedule == null && other.idServiceSchedule != null) || (this.idServiceSchedule != null && !this.idServiceSchedule.equals(other.idServiceSchedule))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unisabaneta.builesgym.entity.Serviceschedule[ idServiceSchedule=" + idServiceSchedule + " ]";
    }
    
}
