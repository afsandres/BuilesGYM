/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.dao;

import com.unisabaneta.builesgym.entity.Rolsystemsectionprivilege;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Andres
 */
@Stateless
public class RolsystemsectionprivilegeFacade extends AbstractFacade<Rolsystemsectionprivilege> {

    @PersistenceContext(unitName = "BuilesGym-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RolsystemsectionprivilegeFacade() {
        super(Rolsystemsectionprivilege.class);
    }
    
}
