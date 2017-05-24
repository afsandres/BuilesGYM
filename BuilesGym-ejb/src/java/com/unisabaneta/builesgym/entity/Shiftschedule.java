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
@Table(name = "shiftschedule")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Shiftschedule.findAll", query = "SELECT s FROM Shiftschedule s")
    , @NamedQuery(name = "Shiftschedule.findByIdShiftSchedule", query = "SELECT s FROM Shiftschedule s WHERE s.idShiftSchedule = :idShiftSchedule")
    , @NamedQuery(name = "Shiftschedule.findByStartTime", query = "SELECT s FROM Shiftschedule s WHERE s.startTime = :startTime")
    , @NamedQuery(name = "Shiftschedule.findByEndTime", query = "SELECT s FROM Shiftschedule s WHERE s.endTime = :endTime")
    , @NamedQuery(name = "Shiftschedule.findByWeekday", query = "SELECT s FROM Shiftschedule s WHERE s.weekday = :weekday")})
public class Shiftschedule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idShiftSchedule")
    private Integer idShiftSchedule;
    @Basic(optional = false)
    @NotNull
    @Column(name = "startTime")
    @Temporal(TemporalType.TIME)
    private Date startTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "endTime")
    @Temporal(TemporalType.TIME)
    private Date endTime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "weekday")
    private String weekday;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shiftScheduleidShiftSchedule", fetch = FetchType.LAZY)
    private List<Employeeshift> employeeshiftList;

    public Shiftschedule() {
    }

    public Shiftschedule(Integer idShiftSchedule) {
        this.idShiftSchedule = idShiftSchedule;
    }

    public Shiftschedule(Integer idShiftSchedule, Date startTime, Date endTime, String weekday) {
        this.idShiftSchedule = idShiftSchedule;
        this.startTime = startTime;
        this.endTime = endTime;
        this.weekday = weekday;
    }

    public Integer getIdShiftSchedule() {
        return idShiftSchedule;
    }

    public void setIdShiftSchedule(Integer idShiftSchedule) {
        this.idShiftSchedule = idShiftSchedule;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    @XmlTransient
    public List<Employeeshift> getEmployeeshiftList() {
        return employeeshiftList;
    }

    public void setEmployeeshiftList(List<Employeeshift> employeeshiftList) {
        this.employeeshiftList = employeeshiftList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idShiftSchedule != null ? idShiftSchedule.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Shiftschedule)) {
            return false;
        }
        Shiftschedule other = (Shiftschedule) object;
        if ((this.idShiftSchedule == null && other.idShiftSchedule != null) || (this.idShiftSchedule != null && !this.idShiftSchedule.equals(other.idShiftSchedule))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unisabaneta.builesgym.entity.Shiftschedule[ idShiftSchedule=" + idShiftSchedule + " ]";
    }
    
}
