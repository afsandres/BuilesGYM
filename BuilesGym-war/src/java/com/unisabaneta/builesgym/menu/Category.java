/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andres
 */
public class Category implements SystemSection, Serializable{

    private String name;
    private String url;
    private List<Section> sectionList;
    private String systemSectionCode;
    private int level;

    public void addSection(Section section) {
        if (getSectionList() == null) {
            setSectionList(new ArrayList());
        }
        getSectionList().add(section);
    }

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
     * @return the sectionList
     */
    public List<Section> getSectionList() {
        return sectionList;
    }

    /**
     * @param sectionList the sectionList to set
     */
    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the systemSectionCode
     */
    @Override
    public String getSystemSectionCode() {
        return systemSectionCode;
    }

    /**
     * @param systemSectionCode the systemSectionCode to set
     */
    public void setSystemSectionCode(String systemSectionCode) {
        this.systemSectionCode = systemSectionCode;
    }

    @Override
    public String toString() {
        return getName();
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
