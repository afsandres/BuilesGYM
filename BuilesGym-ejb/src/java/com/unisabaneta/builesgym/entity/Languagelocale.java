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
@Table(name = "languagelocale")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Languagelocale.findAll", query = "SELECT l FROM Languagelocale l")
    , @NamedQuery(name = "Languagelocale.findByIdlanguageLocale", query = "SELECT l FROM Languagelocale l WHERE l.idlanguageLocale = :idlanguageLocale")
    , @NamedQuery(name = "Languagelocale.findByName", query = "SELECT l FROM Languagelocale l WHERE l.name = :name")
    , @NamedQuery(name = "Languagelocale.findByLocalCode", query = "SELECT l FROM Languagelocale l WHERE l.localCode = :localCode")})
public class Languagelocale implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idlanguageLocale")
    private Integer idlanguageLocale;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "localCode")
    private String localCode;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "languageLocaleidlanguageLocale", fetch = FetchType.LAZY)
    private List<Rol> rolList;

    public Languagelocale() {
    }

    public Languagelocale(Integer idlanguageLocale) {
        this.idlanguageLocale = idlanguageLocale;
    }

    public Languagelocale(Integer idlanguageLocale, String name, String localCode) {
        this.idlanguageLocale = idlanguageLocale;
        this.name = name;
        this.localCode = localCode;
    }

    public Integer getIdlanguageLocale() {
        return idlanguageLocale;
    }

    public void setIdlanguageLocale(Integer idlanguageLocale) {
        this.idlanguageLocale = idlanguageLocale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocalCode() {
        return localCode;
    }

    public void setLocalCode(String localCode) {
        this.localCode = localCode;
    }

    @XmlTransient
    public List<Rol> getRolList() {
        return rolList;
    }

    public void setRolList(List<Rol> rolList) {
        this.rolList = rolList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idlanguageLocale != null ? idlanguageLocale.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Languagelocale)) {
            return false;
        }
        Languagelocale other = (Languagelocale) object;
        if ((this.idlanguageLocale == null && other.idlanguageLocale != null) || (this.idlanguageLocale != null && !this.idlanguageLocale.equals(other.idlanguageLocale))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unisabaneta.builesgym.entity.Languagelocale[ idlanguageLocale=" + idlanguageLocale + " ]";
    }
    
}
