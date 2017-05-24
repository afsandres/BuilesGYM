/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.entity;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Andres
 */
@Entity
@Table(name = "inventary")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inventary.findAll", query = "SELECT i FROM Inventary i")
    , @NamedQuery(name = "Inventary.findByIdInventary", query = "SELECT i FROM Inventary i WHERE i.idInventary = :idInventary")
    , @NamedQuery(name = "Inventary.findByNameObject", query = "SELECT i FROM Inventary i WHERE i.nameObject = :nameObject")
    , @NamedQuery(name = "Inventary.findByQuantity", query = "SELECT i FROM Inventary i WHERE i.quantity = :quantity")})
public class Inventary implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idInventary")
    private Integer idInventary;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nameObject")
    private String nameObject;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    private int quantity;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventaryidInventary", fetch = FetchType.LAZY)
    private List<Serviceinventary> serviceinventaryList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventaryidInventary", fetch = FetchType.LAZY)
    private List<Routineinventary> routineinventaryList;

    public Inventary() {
    }

    public Inventary(Integer idInventary) {
        this.idInventary = idInventary;
    }

    public Inventary(Integer idInventary, String nameObject, int quantity) {
        this.idInventary = idInventary;
        this.nameObject = nameObject;
        this.quantity = quantity;
    }

    public Integer getIdInventary() {
        return idInventary;
    }

    public void setIdInventary(Integer idInventary) {
        this.idInventary = idInventary;
    }

    public String getNameObject() {
        return nameObject;
    }

    public void setNameObject(String nameObject) {
        this.nameObject = nameObject;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @XmlTransient
    public List<Serviceinventary> getServiceinventaryList() {
        return serviceinventaryList;
    }

    public void setServiceinventaryList(List<Serviceinventary> serviceinventaryList) {
        this.serviceinventaryList = serviceinventaryList;
    }

    @XmlTransient
    public List<Routineinventary> getRoutineinventaryList() {
        return routineinventaryList;
    }

    public void setRoutineinventaryList(List<Routineinventary> routineinventaryList) {
        this.routineinventaryList = routineinventaryList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInventary != null ? idInventary.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inventary)) {
            return false;
        }
        Inventary other = (Inventary) object;
        if ((this.idInventary == null && other.idInventary != null) || (this.idInventary != null && !this.idInventary.equals(other.idInventary))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unisabaneta.builesgym.entity.Inventary[ idInventary=" + idInventary + " ]";
    }
    
}
