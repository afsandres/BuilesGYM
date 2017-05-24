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
@Table(name = "employeeshift")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employeeshift.findAll", query = "SELECT e FROM Employeeshift e")
    , @NamedQuery(name = "Employeeshift.findByIdEmployeeShift", query = "SELECT e FROM Employeeshift e WHERE e.idEmployeeShift = :idEmployeeShift")})
public class Employeeshift implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEmployeeShift")
    private Integer idEmployeeShift;
    @JoinColumn(name = "Employee_idEmployee", referencedColumnName = "idEmployee")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Employee employeeidEmployee;
    @JoinColumn(name = "ShiftSchedule_idShiftSchedule", referencedColumnName = "idShiftSchedule")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Shiftschedule shiftScheduleidShiftSchedule;

    public Employeeshift() {
    }

    public Employeeshift(Integer idEmployeeShift) {
        this.idEmployeeShift = idEmployeeShift;
    }

    public Integer getIdEmployeeShift() {
        return idEmployeeShift;
    }

    public void setIdEmployeeShift(Integer idEmployeeShift) {
        this.idEmployeeShift = idEmployeeShift;
    }

    public Employee getEmployeeidEmployee() {
        return employeeidEmployee;
    }

    public void setEmployeeidEmployee(Employee employeeidEmployee) {
        this.employeeidEmployee = employeeidEmployee;
    }

    public Shiftschedule getShiftScheduleidShiftSchedule() {
        return shiftScheduleidShiftSchedule;
    }

    public void setShiftScheduleidShiftSchedule(Shiftschedule shiftScheduleidShiftSchedule) {
        this.shiftScheduleidShiftSchedule = shiftScheduleidShiftSchedule;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmployeeShift != null ? idEmployeeShift.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employeeshift)) {
            return false;
        }
        Employeeshift other = (Employeeshift) object;
        if ((this.idEmployeeShift == null && other.idEmployeeShift != null) || (this.idEmployeeShift != null && !this.idEmployeeShift.equals(other.idEmployeeShift))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unisabaneta.builesgym.entity.Employeeshift[ idEmployeeShift=" + idEmployeeShift + " ]";
    }
    
}
