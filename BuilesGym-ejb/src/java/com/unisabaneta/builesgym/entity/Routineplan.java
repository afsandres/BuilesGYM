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
@Table(name = "routineplan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Routineplan.findAll", query = "SELECT r FROM Routineplan r")
    , @NamedQuery(name = "Routineplan.findByIdRoutinePlan", query = "SELECT r FROM Routineplan r WHERE r.idRoutinePlan = :idRoutinePlan")
    , @NamedQuery(name = "Routineplan.findByExecutionTimeRoutinePlan", query = "SELECT r FROM Routineplan r WHERE r.executionTimeRoutinePlan = :executionTimeRoutinePlan")})
public class Routineplan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRoutinePlan")
    private Integer idRoutinePlan;
    @Basic(optional = false)
    @NotNull
    @Column(name = "executionTimeRoutinePlan")
    @Temporal(TemporalType.TIME)
    private Date executionTimeRoutinePlan;
    @JoinColumn(name = "Client_idClient", referencedColumnName = "idClient")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Client clientidClient;
    @JoinColumn(name = "Routine_idRoutine", referencedColumnName = "idRoutine")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Routine routineidRoutine;

    public Routineplan() {
    }

    public Routineplan(Integer idRoutinePlan) {
        this.idRoutinePlan = idRoutinePlan;
    }

    public Routineplan(Integer idRoutinePlan, Date executionTimeRoutinePlan) {
        this.idRoutinePlan = idRoutinePlan;
        this.executionTimeRoutinePlan = executionTimeRoutinePlan;
    }

    public Integer getIdRoutinePlan() {
        return idRoutinePlan;
    }

    public void setIdRoutinePlan(Integer idRoutinePlan) {
        this.idRoutinePlan = idRoutinePlan;
    }

    public Date getExecutionTimeRoutinePlan() {
        return executionTimeRoutinePlan;
    }

    public void setExecutionTimeRoutinePlan(Date executionTimeRoutinePlan) {
        this.executionTimeRoutinePlan = executionTimeRoutinePlan;
    }

    public Client getClientidClient() {
        return clientidClient;
    }

    public void setClientidClient(Client clientidClient) {
        this.clientidClient = clientidClient;
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
        hash += (idRoutinePlan != null ? idRoutinePlan.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Routineplan)) {
            return false;
        }
        Routineplan other = (Routineplan) object;
        if ((this.idRoutinePlan == null && other.idRoutinePlan != null) || (this.idRoutinePlan != null && !this.idRoutinePlan.equals(other.idRoutinePlan))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unisabaneta.builesgym.entity.Routineplan[ idRoutinePlan=" + idRoutinePlan + " ]";
    }
    
}
