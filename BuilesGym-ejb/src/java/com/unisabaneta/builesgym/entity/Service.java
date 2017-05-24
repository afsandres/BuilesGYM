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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "service")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Service.findAll", query = "SELECT s FROM Service s")
    , @NamedQuery(name = "Service.findByIdService", query = "SELECT s FROM Service s WHERE s.idService = :idService")
    , @NamedQuery(name = "Service.findByDetailService", query = "SELECT s FROM Service s WHERE s.detailService = :detailService")
    , @NamedQuery(name = "Service.findByStartTime", query = "SELECT s FROM Service s WHERE s.startTime = :startTime")
    , @NamedQuery(name = "Service.findByEndTime", query = "SELECT s FROM Service s WHERE s.endTime = :endTime")
    , @NamedQuery(name = "Service.findByQuantityPerson", query = "SELECT s FROM Service s WHERE s.quantityPerson = :quantityPerson")
    , @NamedQuery(name = "Service.findByWeekday", query = "SELECT s FROM Service s WHERE s.weekday = :weekday")})
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idService")
    private Integer idService;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "detailService")
    private String detailService;
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
    @Column(name = "quantityPerson")
    private int quantityPerson;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "weekday")
    private String weekday;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serviceidService", fetch = FetchType.LAZY)
    private List<Serviceschedule> servicescheduleList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serviceidService", fetch = FetchType.LAZY)
    private List<Serviceinventary> serviceinventaryList;
    @JoinColumn(name = "Employee_idEmployee", referencedColumnName = "idEmployee")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Employee employeeidEmployee;

    public Service() {
    }

    public Service(Integer idService) {
        this.idService = idService;
    }

    public Service(Integer idService, String detailService, Date startTime, Date endTime, int quantityPerson, String weekday) {
        this.idService = idService;
        this.detailService = detailService;
        this.startTime = startTime;
        this.endTime = endTime;
        this.quantityPerson = quantityPerson;
        this.weekday = weekday;
    }

    public Integer getIdService() {
        return idService;
    }

    public void setIdService(Integer idService) {
        this.idService = idService;
    }

    public String getDetailService() {
        return detailService;
    }

    public void setDetailService(String detailService) {
        this.detailService = detailService;
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

    public int getQuantityPerson() {
        return quantityPerson;
    }

    public void setQuantityPerson(int quantityPerson) {
        this.quantityPerson = quantityPerson;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    @XmlTransient
    public List<Serviceschedule> getServicescheduleList() {
        return servicescheduleList;
    }

    public void setServicescheduleList(List<Serviceschedule> servicescheduleList) {
        this.servicescheduleList = servicescheduleList;
    }

    @XmlTransient
    public List<Serviceinventary> getServiceinventaryList() {
        return serviceinventaryList;
    }

    public void setServiceinventaryList(List<Serviceinventary> serviceinventaryList) {
        this.serviceinventaryList = serviceinventaryList;
    }

    public Employee getEmployeeidEmployee() {
        return employeeidEmployee;
    }

    public void setEmployeeidEmployee(Employee employeeidEmployee) {
        this.employeeidEmployee = employeeidEmployee;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idService != null ? idService.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Service)) {
            return false;
        }
        Service other = (Service) object;
        if ((this.idService == null && other.idService != null) || (this.idService != null && !this.idService.equals(other.idService))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unisabaneta.builesgym.entity.Service[ idService=" + idService + " ]";
    }
    
}
