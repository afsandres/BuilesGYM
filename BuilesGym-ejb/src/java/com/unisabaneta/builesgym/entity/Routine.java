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
@Table(name = "routine")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Routine.findAll", query = "SELECT r FROM Routine r")
    , @NamedQuery(name = "Routine.findByIdRoutine", query = "SELECT r FROM Routine r WHERE r.idRoutine = :idRoutine")
    , @NamedQuery(name = "Routine.findByNameRoutine", query = "SELECT r FROM Routine r WHERE r.nameRoutine = :nameRoutine")})
public class Routine implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRoutine")
    private Integer idRoutine;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nameRoutine")
    private String nameRoutine;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "routineidRoutine", fetch = FetchType.LAZY)
    private List<Routineplan> routineplanList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "routineidRoutine", fetch = FetchType.LAZY)
    private List<Routineinventary> routineinventaryList;

    public Routine() {
    }

    public Routine(Integer idRoutine) {
        this.idRoutine = idRoutine;
    }

    public Routine(Integer idRoutine, String nameRoutine) {
        this.idRoutine = idRoutine;
        this.nameRoutine = nameRoutine;
    }

    public Integer getIdRoutine() {
        return idRoutine;
    }

    public void setIdRoutine(Integer idRoutine) {
        this.idRoutine = idRoutine;
    }

    public String getNameRoutine() {
        return nameRoutine;
    }

    public void setNameRoutine(String nameRoutine) {
        this.nameRoutine = nameRoutine;
    }

    @XmlTransient
    public List<Routineplan> getRoutineplanList() {
        return routineplanList;
    }

    public void setRoutineplanList(List<Routineplan> routineplanList) {
        this.routineplanList = routineplanList;
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
        hash += (idRoutine != null ? idRoutine.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Routine)) {
            return false;
        }
        Routine other = (Routine) object;
        if ((this.idRoutine == null && other.idRoutine != null) || (this.idRoutine != null && !this.idRoutine.equals(other.idRoutine))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unisabaneta.builesgym.entity.Routine[ idRoutine=" + idRoutine + " ]";
    }
    
}
