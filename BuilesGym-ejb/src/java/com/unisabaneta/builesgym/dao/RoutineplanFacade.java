/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.dao;

import com.unisabaneta.builesgym.entity.Routineplan;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Andres
 */
@Stateless
public class RoutineplanFacade extends AbstractFacade<Routineplan> {

    @PersistenceContext(unitName = "BuilesGym-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RoutineplanFacade() {
        super(Routineplan.class);
    }
    
}
