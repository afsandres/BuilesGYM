/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.menu;

import java.io.Serializable;

/**
 *
 * @author Andres
 */
public class Fragment implements SystemSection, Serializable{
    
    private String name;
    private String systemSectionCode;
    private int level;
    
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param systemSectionCode the systemSectionCode to set
     */
    public void setSystemSectionCode(String systemSectionCode) {
        this.systemSectionCode = systemSectionCode;
    }
    
    @Override
    public String getSystemSectionCode() {
        return systemSectionCode;
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }
    
}
