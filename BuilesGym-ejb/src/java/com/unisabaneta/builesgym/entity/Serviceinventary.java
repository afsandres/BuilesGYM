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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Andres
 */
@Entity
@Table(name = "serviceinventary")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Serviceinventary.findAll", query = "SELECT s FROM Serviceinventary s")
    , @NamedQuery(name = "Serviceinventary.findByIdServiceInventary", query = "SELECT s FROM Serviceinventary s WHERE s.idServiceInventary = :idServiceInventary")})
public class Serviceinventary implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idServiceInventary")
    private Integer idServiceInventary;
    @JoinColumn(name = "Inventary_idInventary", referencedColumnName = "idInventary")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Inventary inventaryidInventary;
    @JoinColumn(name = "Service_idService", referencedColumnName = "idService")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Service serviceidService;

    public Serviceinventary() {
    }

    public Serviceinventary(Integer idServiceInventary) {
        this.idServiceInventary = idServiceInventary;
    }

    public Integer getIdServiceInventary() {
        return idServiceInventary;
    }

    public void setIdServiceInventary(Integer idServiceInventary) {
        this.idServiceInventary = idServiceInventary;
    }

    public Inventary getInventaryidInventary() {
        return inventaryidInventary;
    }

    public void setInventaryidInventary(Inventary inventaryidInventary) {
        this.inventaryidInventary = inventaryidInventary;
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
        hash += (idServiceInventary != null ? idServiceInventary.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Serviceinventary)) {
            return false;
        }
        Serviceinventary other = (Serviceinventary) object;
        if ((this.idServiceInventary == null && other.idServiceInventary != null) || (this.idServiceInventary != null && !this.idServiceInventary.equals(other.idServiceInventary))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unisabaneta.builesgym.entity.Serviceinventary[ idServiceInventary=" + idServiceInventary + " ]";
    }
    
}
