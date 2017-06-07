/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.entity;

import com.unisabaneta.builesgym.entity.type.Privilege;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Andres
 */
@Entity
@Table(name = "rolsystemsectionprivilege")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rolsystemsectionprivilege.findAll", query = "SELECT r FROM Rolsystemsectionprivilege r")
    , @NamedQuery(name = "Rolsystemsectionprivilege.findByIdRolSystemSectionPrivilege", query = "SELECT r FROM Rolsystemsectionprivilege r WHERE r.idRolSystemSectionPrivilege = :idRolSystemSectionPrivilege")
    , @NamedQuery(name = "Rolsystemsectionprivilege.findBySystemSection", query = "SELECT r FROM Rolsystemsectionprivilege r WHERE r.systemSection = :systemSection")
    , @NamedQuery(name = "Rolsystemsectionprivilege.findByPrivilege", query = "SELECT r FROM Rolsystemsectionprivilege r WHERE r.privilege = :privilege")})
public class Rolsystemsectionprivilege implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idRolSystemSectionPrivilege")
    private Integer idRolSystemSectionPrivilege;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "systemSection")
    private String systemSection;
    @Basic(optional = false)
    @NotNull
    @Column(name = "privilege")
    @Enumerated(EnumType.STRING)
    private Privilege privilege;
    @JoinColumn(name = "Rol_idRol", referencedColumnName = "idRol")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Rol rolidRol;

    public Rolsystemsectionprivilege() {
    }

    public Rolsystemsectionprivilege(Integer idRolSystemSectionPrivilege) {
        this.idRolSystemSectionPrivilege = idRolSystemSectionPrivilege;
    }

    public Rolsystemsectionprivilege(Integer idRolSystemSectionPrivilege, String systemSection, Privilege privilege) {
        this.idRolSystemSectionPrivilege = idRolSystemSectionPrivilege;
        this.systemSection = systemSection;
        this.privilege = privilege;
    }

     public Rolsystemsectionprivilege(String systemSection, Privilege privilege) {
        this.systemSection = systemSection;
        this.privilege = privilege;
    }

    public Integer getIdRolSystemSectionPrivilege() {
        return idRolSystemSectionPrivilege;
    }

    public void setIdRolSystemSectionPrivilege(Integer idRolSystemSectionPrivilege) {
        this.idRolSystemSectionPrivilege = idRolSystemSectionPrivilege;
    }

    public String getSystemSection() {
        return systemSection;
    }

    public void setSystemSection(String systemSection) {
        this.systemSection = systemSection;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    public Rol getRolidRol() {
        return rolidRol;
    }

    public void setRolidRol(Rol rolidRol) {
        this.rolidRol = rolidRol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRolSystemSectionPrivilege != null ? idRolSystemSectionPrivilege.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rolsystemsectionprivilege)) {
            return false;
        }
        Rolsystemsectionprivilege other = (Rolsystemsectionprivilege) object;
        if ((this.idRolSystemSectionPrivilege == null && other.idRolSystemSectionPrivilege != null) || (this.idRolSystemSectionPrivilege != null && !this.idRolSystemSectionPrivilege.equals(other.idRolSystemSectionPrivilege))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unisabaneta.builesgym.entity.Rolsystemsectionprivilege[ idRolSystemSectionPrivilege=" + idRolSystemSectionPrivilege + " ]";
    }
    
}
