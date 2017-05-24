/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.dao;

import com.unisabaneta.builesgym.entity.Rol;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Andres
 */
@Stateless
public class RolFacade extends AbstractFacade<Rol> {

    @PersistenceContext(unitName = "BuilesGym-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RolFacade() {
        super(Rol.class);
    }
    
    /**
     * Busca un Rol por el login y contraseña
     *
     * @param userRol
     * @param passwordRol
     * @return Null en caso de no encontrarlo
     */
    public Rol findByRolPassword(String userRol, String passwordRol) {
        TypedQuery<Rol> query = em.createNamedQuery("Rol.findByUserRolAndPassword", Rol.class);
        query.setParameter("userRol", userRol);
        query.setParameter("passwordRol", passwordRol);
        List<Rol> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    /**
     * Busca la lista de roles registrados
     
     * @param showRolInactive Indica si se deben consultar los roles inactivos
     * @return List<Rol>
     */
    public List<Rol> findRolList(boolean showRolInactive) {
        TypedQuery<Rol> query = null;
        if (!showRolInactive) {
            query = em.createNamedQuery("Rol.findByActiveRol", Rol.class);
            //Se envia este parámetro con el fin de que solo sean consultados los roles activos
            query.setParameter("activeRol", true);
        } else {
            query = em.createNamedQuery("Rol.findAll", Rol.class);
        }
        List<Rol> resultList = query.getResultList();
        return resultList;
    }
    
    /**
     * Busca la lista de roles que contengan un userRol en especifico.
     *
     * @param userRol
     * @return
     */
    public List<Rol> findRolByUserRol(String userRol) {
        TypedQuery<Rol> query = em.createNamedQuery("Rol.findRolByUserRol", Rol.class);
        query.setParameter("userRol", userRol.trim().toLowerCase());
        List<Rol> resultList = query.getResultList();
        return resultList;
    }
    
}
