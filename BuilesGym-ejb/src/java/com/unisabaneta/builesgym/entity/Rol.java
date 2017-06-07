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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "rol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rol.findAll", query = "SELECT r FROM Rol r")
    , @NamedQuery(name = "Rol.findByIdRol", query = "SELECT r FROM Rol r WHERE r.idRol = :idRol")
    , @NamedQuery(name = "Rol.findByNameRol", query = "SELECT r FROM Rol r WHERE r.nameRol = :nameRol")
    , @NamedQuery(name = "Rol.findByUserRol", query = "SELECT r FROM Rol r WHERE r.userRol = :userRol")
    , @NamedQuery(name = "Rol.findByPasswordRol", query = "SELECT r FROM Rol r WHERE r.passwordRol = :passwordRol")
    , @NamedQuery(name = "Rol.findByUserRolAndPassword", query = "SELECT r FROM Rol r WHERE r.userRol = :userRol AND r.passwordRol = :passwordRol")
    , @NamedQuery(name = "Rol.findRolByUserRol", query = "SELECT r FROM Rol r WHERE LOWER(r.userRol) = :userRol")
    , @NamedQuery(name = "Rol.findByActiveRol", query = "SELECT r FROM Rol r WHERE r.activeRol = :activeRol")})
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRol")
    private Integer idRol;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nameRol")
    private String nameRol;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "userRol")
    private String userRol;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "passwordRol")
    private String passwordRol;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activeRol")
    private boolean activeRol;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rolidRol", fetch = FetchType.LAZY)
    private List<Rolsystemsectionprivilege> rolsystemsectionprivilegeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rolidRol", fetch = FetchType.LAZY)
    private List<Employee> employeeList;
    @JoinColumn(name = "languageLocale_idlanguageLocale", referencedColumnName = "idlanguageLocale")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Languagelocale languageLocaleidlanguageLocale;

    public Rol() {
    }

    public Rol(Integer idRol) {
        this.idRol = idRol;
    }

    public Rol(Integer idRol, String nameRol, String userRol, String passwordRol, boolean activeRol) {
        this.idRol = idRol;
        this.nameRol = nameRol;
        this.userRol = userRol;
        this.passwordRol = passwordRol;
        this.activeRol = activeRol;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getNameRol() {
        return nameRol;
    }

    public void setNameRol(String nameRol) {
        this.nameRol = nameRol;
    }

    public String getUserRol() {
        return userRol;
    }

    public void setUserRol(String userRol) {
        this.userRol = userRol;
    }

    public String getPasswordRol() {
        return passwordRol;
    }

    public void setPasswordRol(String passwordRol) {
        this.passwordRol = passwordRol;
    }

    public boolean getActiveRol() {
        return activeRol;
    }

    public void setActiveRol(boolean activeRol) {
        this.activeRol = activeRol;
    }

    @XmlTransient
    public List<Rolsystemsectionprivilege> getRolsystemsectionprivilegeList() {
        return rolsystemsectionprivilegeList;
    }

    public void setRolsystemsectionprivilegeList(List<Rolsystemsectionprivilege> rolsystemsectionprivilegeList) {
        this.rolsystemsectionprivilegeList = rolsystemsectionprivilegeList;
    }

    @XmlTransient
    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public Languagelocale getLanguageLocaleidlanguageLocale() {
        return languageLocaleidlanguageLocale;
    }

    public void setLanguageLocaleidlanguageLocale(Languagelocale languageLocaleidlanguageLocale) {
        this.languageLocaleidlanguageLocale = languageLocaleidlanguageLocale;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRol != null ? idRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rol)) {
            return false;
        }
        Rol other = (Rol) object;
        if ((this.idRol == null && other.idRol != null) || (this.idRol != null && !this.idRol.equals(other.idRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unisabaneta.builesgym.entity.Rol[ idRol=" + idRol + " ]";
    }
    
}
