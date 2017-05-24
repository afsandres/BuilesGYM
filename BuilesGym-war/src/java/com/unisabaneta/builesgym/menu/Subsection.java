/*
 * OpenFaces - JSF Component Library 3.0
 * Copyright (C) 2007-2010, TeamDev Ltd.
 * licensing@openfaces.org
 * Unless agreed in writing the contents of this file are subject to
 * the GNU Lesser General Public License Version 2.1 (the "LGPL" License).
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * Please visit http://openfaces.org/licensing/ for more details.
 */

package com.unisabaneta.builesgym.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Subsection implements Serializable, SystemSection{
    private String name;
    private String url;
    private String systemSectionCode;
    private int level;
    private List<Fragment> fragmentList = new ArrayList<>();

    public Subsection() {
    }

    @Override
    public String toString() {
        return getName();
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

    public void setSystemSectionCode(String systemSectionCode) {
        this.systemSectionCode = systemSectionCode;
    }
    
    @Override
    public String getSystemSectionCode() {
        return systemSectionCode;
    }

    /**
     * @return the fragmentList
     */
    public List<Fragment> getFragmentList() {
        return fragmentList;
    }

    /**
     * @param fragmentList the fragmentList to set
     */
    public void setFragmentList(List<Fragment> fragmentList) {
        this.fragmentList = fragmentList;
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
