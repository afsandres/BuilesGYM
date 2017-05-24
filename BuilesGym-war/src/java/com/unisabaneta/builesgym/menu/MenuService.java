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

import com.unisabaneta.builesgym.session.RolSessionBean;
import com.unisabaneta.builesgym.entity.Rolsystemsectionprivilege;
import com.unisabaneta.builesgym.entity.type.Privilege;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

@Named(value = "menuService")
@SessionScoped
public class MenuService implements Serializable {

    private List<Section> sections;
    private List<Category> categories;
    private Section selectedSection = null;
    @Inject
    private RolSessionBean rolSessionBean;


    public MenuService() {
        loadMenu();
    }

    private void loadMenu() {
        ExternalContext exContext = FacesContext.getCurrentInstance().getExternalContext();
        Digester digester = new Digester();
        digester.setValidating(false);
        digester.setUseContextClassLoader(true);

        // 1º SE CONSTRUYE ESTRUCTURA ARBOL XML        
        //= MENU        
        digester.addObjectCreate("menu", ArrayList.class);//Lista de Categorias        
        //== CATEGORIAS        
        digester.addObjectCreate("menu/category", Category.class);//Categoria        
        digester.addObjectCreate("menu/category/sectionList", ArrayList.class);//Lista de secciones                
        //===SECCION
        digester.addObjectCreate("menu/category/sectionList/section", Section.class);  //Seccion              
        digester.addObjectCreate("menu/category/sectionList/section/pagePatterns", ArrayList.class);//PAtterns
        digester.addObjectCreate("menu/category/sectionList/section/keyFeatures", ArrayList.class); //KeyFeautures        
        digester.addObjectCreate("menu/category/sectionList/section/subsectionList", ArrayList.class);//Lista de Subsecciones        
        //====SUBSECCION
        digester.addObjectCreate("menu/category/sectionList/section/subsectionList/subsection", Subsection.class);
        digester.addObjectCreate("menu/category/sectionList/section/subsectionList/subsection/fragmentList", ArrayList.class);
        //====FRAGMENTO
        digester.addObjectCreate("menu/category/sectionList/section/subsectionList/subsection/fragmentList/fragment", Fragment.class);

        // 2º SE INDICA CON QUE METODOS SE POBLA LAS LISTAS 
        //Ingresar cada categoria
        digester.addSetNext("menu/category", "add");
        //Agregar lista de secciones, agregar cada sección, agregar keyfeautures
        digester.addSetNext("menu/category/sectionList", "setSectionList");
        digester.addSetNext("menu/category/sectionList/section", "add");
        digester.addSetNext("menu/category/sectionList/section/keyFeatures", "setFeatures");
        //digester.addSetNext("menu/category/sectionList/section/feature/", "add");        
        //Agregart lista de subsecciones y cada subseccion
        digester.addSetNext("menu/category/sectionList/section/subsectionList", "setSubsectionList");
        digester.addSetNext("menu/category/sectionList/section/subsectionList/subsection", "add");
        //Agregar Lista de Fragmentos y cada fragmento
        digester.addSetNext("menu/category/sectionList/section/subsectionList/subsection/fragmentList", "setFragmentList");
        digester.addSetNext("menu/category/sectionList/section/subsectionList/subsection/fragmentList/fragment", "add");

        //3º SE INDICA CADA OBJETO COMO CONFIGURA (SET) SUS PROPIEDADES
        //Por categoria
        Class[] clazzTypes = {Integer.class};
        digester.addCallMethod("*/category/name", "setName", 0);
        digester.addCallMethod("*/category/systemSectionCode", "setSystemSectionCode", 0);
        digester.addCallMethod("*/category/level", "setLevel", 0, clazzTypes);
        digester.addCallMethod("*/category/url", "setUrl", 0);
        //Por seccion
        digester.addCallMethod("*/section/name", "setName", 0);
        digester.addCallMethod("*/section/systemSectionCode", "setSystemSectionCode", 0);
        digester.addCallMethod("*/section/level", "setLevel", 0, clazzTypes);
        digester.addCallMethod("*/section/componentName", "setComponentName", 0);
        digester.addCallMethod("*/section/url", "setUrl", 0);
        digester.addCallMethod("*/section/image", "setImage", 0);
        digester.addCallMethod("*/section/selectedImage", "setSelectedImage", 0);
        //por KeyFeauture de seccion
        digester.addCallMethod("*/keyFeatures/feature", "add", 0);
        //por subsección
        digester.addCallMethod("*/subsection/systemSectionCode", "setSystemSectionCode", 0);
        digester.addCallMethod("*/subsection/level", "setLevel", 0, clazzTypes);
        digester.addCallMethod("*/subsection/name", "setName", 0);
        digester.addCallMethod("*/subsection/url", "setUrl", 0);
        //Por Fragmento
        digester.addCallMethod("*/fragmentList/fragment", "add", 0);
        digester.addCallMethod("*/fragment/systemSectionCode", "setSystemSectionCode", 0);
        digester.addCallMethod("*/fragment/level", "setLevel", 0, clazzTypes);
        digester.addCallMethod("*/fragment/name", "setName", 0);
        try {
            categories = (List<Category>) digester.parse(exContext.getResource("/WEB-INF/menu.xml").openStream());
            sections = new ArrayList<>();
            for (Category category : categories) {
                sections.addAll(category.getSectionList());
            }
            selectedSection = sections.get(0);
        } catch (IOException | SAXException ex) {
            System.out.println("Error categories" + ex);        }
    }

    public List getSections() {
        getSelectedSection();
        return sections;
    }

    /**
     * @return the categories
     */
    public List<Category> getCategories() {
        return categories;
    }

    public Section getSelectedSection() {
        return selectedSection;
    }

    public void setSelectedSection(Section section) {
        this.selectedSection = section;

        if (section != null) {
            Subsection subsection = (Subsection) firstSystemSectionWithPrivileges(selectedSection.getSubsectionList());
            selectedSection.setSelectedSubsection(subsection);
        }
    }

    /**
     * Recorre la lista de systemSection y retorna el primer SystemSection con
     * nivel de privilegio mayor a Privilege.NONE de no encontrar ninguno
     * retorna
     *
     * @param systemSectionList
     * @return
     */
    public SystemSection firstSystemSectionWithPrivileges(List systemSectionList) {
        for (Object systemSection : systemSectionList) {
            Privilege privilege = rolSessionBean.getSystemSectionPrivilege((SystemSection) systemSection);

                if (privilege.ordinal() > Privilege.NONE.ordinal()) {
                    return (SystemSection) systemSection;
                }
            }
        return (SystemSection) systemSectionList.get(0);
    }

    /**
     * Filtra el menu segun el nivel de privilegios
     * @param treeLevel
     * @return
     */
    public List<Rolsystemsectionprivilege> filterTreePrivileges(int treeLevel) {
        List<Rolsystemsectionprivilege> rolsystemsectionprivilegeList = new ArrayList<>();
        List<Category> categoryList = getCategories();
        for (Category category : categoryList) {
            buildRolSystemSectionPrivilege(treeLevel, category, rolsystemsectionprivilegeList);
            List<Section> sectionList = category.getSectionList();
            for (Section section : sectionList) {
                buildRolSystemSectionPrivilege(treeLevel, section, rolsystemsectionprivilegeList);
                List<Subsection> subsectionList = section.getSubsectionList();
                for (Subsection subsection : subsectionList) {
                    buildRolSystemSectionPrivilege(treeLevel, subsection, rolsystemsectionprivilegeList);
                    List<Fragment> fragmentList = subsection.getFragmentList();
                    for (Fragment fragment : fragmentList) {
                        buildRolSystemSectionPrivilege(treeLevel, fragment, rolsystemsectionprivilegeList);
                    }
                }
            }
        }
        return rolsystemsectionprivilegeList;
    }

    /**
     * Construye un privilegio de rol de acuerdo al nivel de las categorias,
     * secciones y subsecciones
     *
     * @param treeLevel
     * @param systemSection
     * @return
     */
    private void buildRolSystemSectionPrivilege(int treeLevel, SystemSection systemSection, List<Rolsystemsectionprivilege> rolsystemsectionprivilegeList) {
        Rolsystemsectionprivilege rolsystemsectionprivilege = new Rolsystemsectionprivilege();
        rolsystemsectionprivilege.setSystemSection(systemSection.getSystemSectionCode());
        if (treeLevel <= systemSection.getLevel()) {
            rolsystemsectionprivilege.setPrivilege(Privilege.READ_WRITE);
        } else {
            rolsystemsectionprivilege.setPrivilege(Privilege.NONE);
        }
        rolsystemsectionprivilegeList.add(rolsystemsectionprivilege);
    }

}
