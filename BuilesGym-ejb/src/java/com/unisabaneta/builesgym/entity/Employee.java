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
@Table(name = "employee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e")
    , @NamedQuery(name = "Employee.findByIdEmployee", query = "SELECT e FROM Employee e WHERE e.idEmployee = :idEmployee")
    , @NamedQuery(name = "Employee.findByNameEmployee", query = "SELECT e FROM Employee e WHERE e.nameEmployee = :nameEmployee")
    , @NamedQuery(name = "Employee.findByBirthdayEmployee", query = "SELECT e FROM Employee e WHERE e.birthdayEmployee = :birthdayEmployee")
    , @NamedQuery(name = "Employee.findByAddressEmployee", query = "SELECT e FROM Employee e WHERE e.addressEmployee = :addressEmployee")
    , @NamedQuery(name = "Employee.findByEmailEmployee", query = "SELECT e FROM Employee e WHERE e.emailEmployee = :emailEmployee")
    , @NamedQuery(name = "Employee.findByCellPhoneEmployee", query = "SELECT e FROM Employee e WHERE e.cellPhoneEmployee = :cellPhoneEmployee")
    , @NamedQuery(name = "Employee.findBySpecializationEmployee", query = "SELECT e FROM Employee e WHERE e.specializationEmployee = :specializationEmployee")})
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEmployee")
    private Integer idEmployee;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nameEmployee")
    private String nameEmployee;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "birthdayEmployee")
    private String birthdayEmployee;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "addressEmployee")
    private String addressEmployee;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "emailEmployee")
    private String emailEmployee;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "cellPhoneEmployee")
    private String cellPhoneEmployee;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "specializationEmployee")
    private String specializationEmployee;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeidEmployee", fetch = FetchType.LAZY)
    private List<Rol> rolList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeidEmployee", fetch = FetchType.LAZY)
    private List<Service> serviceList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeidEmployee", fetch = FetchType.LAZY)
    private List<Employeeshift> employeeshiftList;

    public Employee() {
    }

    public Employee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public Employee(Integer idEmployee, String nameEmployee, String birthdayEmployee, String addressEmployee, String emailEmployee, String cellPhoneEmployee, String specializationEmployee) {
        this.idEmployee = idEmployee;
        this.nameEmployee = nameEmployee;
        this.birthdayEmployee = birthdayEmployee;
        this.addressEmployee = addressEmployee;
        this.emailEmployee = emailEmployee;
        this.cellPhoneEmployee = cellPhoneEmployee;
        this.specializationEmployee = specializationEmployee;
    }

    public Integer getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Integer idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getNameEmployee() {
        return nameEmployee;
    }

    public void setNameEmployee(String nameEmployee) {
        this.nameEmployee = nameEmployee;
    }

    public String getBirthdayEmployee() {
        return birthdayEmployee;
    }

    public void setBirthdayEmployee(String birthdayEmployee) {
        this.birthdayEmployee = birthdayEmployee;
    }

    public String getAddressEmployee() {
        return addressEmployee;
    }

    public void setAddressEmployee(String addressEmployee) {
        this.addressEmployee = addressEmployee;
    }

    public String getEmailEmployee() {
        return emailEmployee;
    }

    public void setEmailEmployee(String emailEmployee) {
        this.emailEmployee = emailEmployee;
    }

    public String getCellPhoneEmployee() {
        return cellPhoneEmployee;
    }

    public void setCellPhoneEmployee(String cellPhoneEmployee) {
        this.cellPhoneEmployee = cellPhoneEmployee;
    }

    public String getSpecializationEmployee() {
        return specializationEmployee;
    }

    public void setSpecializationEmployee(String specializationEmployee) {
        this.specializationEmployee = specializationEmployee;
    }

    @XmlTransient
    public List<Rol> getRolList() {
        return rolList;
    }

    public void setRolList(List<Rol> rolList) {
        this.rolList = rolList;
    }

    @XmlTransient
    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
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
        hash += (idEmployee != null ? idEmployee.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.idEmployee == null && other.idEmployee != null) || (this.idEmployee != null && !this.idEmployee.equals(other.idEmployee))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unisabaneta.builesgym.entity.Employee[ idEmployee=" + idEmployee + " ]";
    }
    
}
