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

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class Section implements Serializable, SystemSection {

    private String name;
    private String url;
    private String image;
    private String selectedImage;
    private String headerImage;
    private List<String> features;
    private String componentName;
    private List<Subsection> subsectionList;
    private Subsection selectedSubsection;
    private List patterns;
    private int selectedTabIndex;
    private String systemSectionCode;
    private int level;

    public Section() {
    }

    public Subsection getSelectedSubsection() {
        if (selectedSubsection == null && getSubsectionList() != null && getSubsectionList().size() > 0) {
            selectedSubsection = getSubsectionList().get(0);
        }
        return selectedSubsection;
    }

    public void addSubsection(Subsection subsection) {
        if (getSubsectionList() == null) {
            setSubsectionList(new ArrayList<Subsection>());
        }
        getSubsectionList().add(subsection);
    }

    public boolean isSubsectionsAvailable() {
        return getSubsectionList() != null && getSubsectionList().size() > 0;
    }

    public int getSubsectionCount() {
        return getSubsectionList() == null ? 0 : getSubsectionList().size();
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

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the selectedImage
     */
    public String getSelectedImage() {
        return selectedImage;
    }

    /**
     * @param selectedImage the selectedImage to set
     */
    public void setSelectedImage(String selectedImage) {
        this.selectedImage = selectedImage;
    }

    /**
     * @return the headerImage
     */
    public String getHeaderImage() {
        return headerImage;
    }

    /**
     * @param headerImage the headerImage to set
     */
    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    /**
     * @return the features
     */
    public List<String> getFeatures() {
        return features;
    }

    /**
     * @param features the features to set
     */
    public void setFeatures(List<String> features) {
        this.features = features;
    }

    /**
     * @return the componentName
     */
    public String getComponentName() {
        return componentName;
    }

    /**
     * @param componentName the componentName to set
     */
    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    /**
     * @param selectedSubsection the selectedSubsection to set
     */
    public void setSelectedSubsection(Subsection selectedSubsection) {
        this.selectedSubsection = selectedSubsection;
        if (selectedSubsection != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();
            try {
                externalContext.redirect(externalContext.getRequestContextPath() + selectedSubsection.getUrl());

            } catch (IOException e1) {
                throw new RuntimeException(e1);
            } finally {
                context.responseComplete();
            }
        }
    }

    /**
     * @return the patterns
     */
    public List getPatterns() {
        return patterns;
    }

    /**
     * @param patterns the patterns to set
     */
    public void setPatterns(List patterns) {
        this.patterns = patterns;
    }

    /**
     * @return the selectedTabIndex
     */
    public int getSelectedTabIndex() {
        return selectedTabIndex;
    }

    /**
     * @param selectedTabIndex the selectedTabIndex to set
     */
    public void setSelectedTabIndex(int selectedTabIndex) {
        this.selectedTabIndex = selectedTabIndex;
    }

    /**
     * @return the subsectionList
     */
    public List<Subsection> getSubsectionList() {
        return subsectionList;
    }

    /**
     * @param subsectionList the subsectionList to set
     */
    public void setSubsectionList(List<Subsection> subsectionList) {
        this.subsectionList = subsectionList;
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
