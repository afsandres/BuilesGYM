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
@Table(name = "routineinventary")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Routineinventary.findAll", query = "SELECT r FROM Routineinventary r")
    , @NamedQuery(name = "Routineinventary.findByIdRoutineInventary", query = "SELECT r FROM Routineinventary r WHERE r.idRoutineInventary = :idRoutineInventary")})
public class Routineinventary implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRoutineInventary")
    private Integer idRoutineInventary;
    @JoinColumn(name = "Inventary_idInventary", referencedColumnName = "idInventary")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Inventary inventaryidInventary;
    @JoinColumn(name = "Routine_idRoutine", referencedColumnName = "idRoutine")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Routine routineidRoutine;

    public Routineinventary() {
    }

    public Routineinventary(Integer idRoutineInventary) {
        this.idRoutineInventary = idRoutineInventary;
    }

    public Integer getIdRoutineInventary() {
        return idRoutineInventary;
    }

    public void setIdRoutineInventary(Integer idRoutineInventary) {
        this.idRoutineInventary = idRoutineInventary;
    }

    public Inventary getInventaryidInventary() {
        return inventaryidInventary;
    }

    public void setInventaryidInventary(Inventary inventaryidInventary) {
        this.inventaryidInventary = inventaryidInventary;
    }

    public Routine getRoutineidRoutine() {
        return routineidRoutine;
    }

    public void setRoutineidRoutine(Routine routineidRoutine) {
        this.routineidRoutine = routineidRoutine;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRoutineInventary != null ? idRoutineInventary.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Routineinventary)) {
            return false;
        }
        Routineinventary other = (Routineinventary) object;
        if ((this.idRoutineInventary == null && other.idRoutineInventary != null) || (this.idRoutineInventary != null && !this.idRoutineInventary.equals(other.idRoutineInventary))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unisabaneta.builesgym.entity.Routineinventary[ idRoutineInventary=" + idRoutineInventary + " ]";
    }
    
}
