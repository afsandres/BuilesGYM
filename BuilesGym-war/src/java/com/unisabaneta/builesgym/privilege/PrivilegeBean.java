/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.privilege;

import com.unisabaneta.builesgym.dao.RolFacade;
import com.unisabaneta.builesgym.dao.RolsystemsectionprivilegeFacade;
import com.unisabaneta.builesgym.entity.Rol;
import com.unisabaneta.builesgym.entity.Rolsystemsectionprivilege;
import com.unisabaneta.builesgym.entity.type.Privilege;
import com.unisabaneta.builesgym.menu.Category;
import com.unisabaneta.builesgym.menu.Fragment;
import com.unisabaneta.builesgym.menu.MenuService;
import com.unisabaneta.builesgym.menu.Section;
import com.unisabaneta.builesgym.menu.Subsection;
import com.unisabaneta.builesgym.menu.SystemSection;
import com.unisabaneta.builesgym.rol.RolBean;
import com.unisabaneta.builesgym.session.RolSessionBean;
import com.unisabaneta.builesgym.tools.HashTools;
import com.unisabaneta.builesgym.tools.MessageTools;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import resources.business.ResourcesMgr;

/**
 *
 * @author Andres
 */
@Named(value = "privilegeBean")
@SessionScoped
public class PrivilegeBean implements Serializable {

    @Inject
    private RolBean rolBean;
    @Inject
    private HashTools hashTools;
    @Inject
    private MessageTools messageTools;
    @Inject
    private ResourcesMgr resourcesMgr;
    @Inject
    private RolFacade rolFacade;
    @Inject
    private RolsystemsectionprivilegeFacade rolsystemsectionprivilegeFacade;
    @Inject
    private MenuService menuService;
    @Inject
    private RolSessionBean rolSessionBean;
    //VARIABLES
    /**
     * Arbol encargado de almacenar categorias, secciones y subsecciones del
     * menu
     */
    private TreeNode root;
    /*
     * Lista de privilegios del sistema
     */
    private List<Rolsystemsectionprivilege> rolsystemsectionprivilegeList = null;
    /**
     * Rol seleccionado
     */
    private Rol selectedRol;
    /**
     * Privilegio seleccionado
     */
    private Privilege selectPrivilege;
    /**
     * toma la vista raiz para luego poder tomar el lenguaje local
     */
    private UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();

    /**
     *
     * /**
     * Creates a new instance of PrivilegeBean
     */
    public PrivilegeBean() {
    }

    public void initPrivileges() {
        root = null;
        setSelectedRol(rolBean.getSelectedRol());
        buildTreePrivilege();
    }

    // <editor-fold defaultstate="collapsed" desc="WORKFLOW - construcción Treetable Privilegios">
    public void buildTreePrivilege() {
        if (getSelectedRol() == null || getSelectedRol().getIdRol() == null) {
            return;
        }
        setRoot(new DefaultTreeNode("root", null));
        root.setExpanded(true);
        List<Category> categoryList = menuService.getCategories();
        for (Category category : categoryList) {
            TreeNode categoryNode = new DefaultTreeNode(findOrCreateSystemSectionPrivilege(category), getRoot());
            categoryNode.setExpanded(true);

            List<Section> sectionList = category.getSectionList();

            for (Section section : sectionList) {

                TreeNode sectionNode = new DefaultTreeNode(findOrCreateSystemSectionPrivilege(section), categoryNode);
                sectionNode.setExpanded(true);

                List<Subsection> subSectionList = section.getSubsectionList();

                for (Subsection subsection : subSectionList) {

                    TreeNode subSectionNode = new DefaultTreeNode(findOrCreateSystemSectionPrivilege(subsection), sectionNode);
                    subSectionNode.setExpanded(true);

                    List<Fragment> fragmentList = subsection.getFragmentList();
                    for (Fragment fragment : fragmentList) {
                        TreeNode fragmentNode = new DefaultTreeNode(findOrCreateSystemSectionPrivilege(fragment), subSectionNode);
                        fragmentNode.setExpanded(true);
                    }

                }

            }

        }
        rolFacade.edit(getSelectedRol());
    }

    /**
     * Busca en la lista de privilegios del usuario seleccionado un
     * systemSectionPrivilege de acuerdo al systemCode, en caso de no
     * encontrarlo crea un systemSectionPrivilege con valores default
     * (Privilege: None)
     *
     * @param systemSection
     * @return Rolsystemsectionprivilege
     */
    private Rolsystemsectionprivilege findOrCreateSystemSectionPrivilege(SystemSection systemSection) {
        Rolsystemsectionprivilege rolsystemsectionprivilege = findSystemSectionPrivilege(systemSection.getSystemSectionCode());
        if (rolsystemsectionprivilege == null) {
            rolsystemsectionprivilege = createDefaultPrivilege(systemSection.getSystemSectionCode());
        }
        return rolsystemsectionprivilege;
    }

    /**
     * Busca en la lista de los privilegios del usuario seleccionado, el
     * userSystemSectionPrivilege según el systemSectionCode, de no encontrarlo
     * retorna null.
     *
     * @param systemSectionCode
     * @return
     */
    private Rolsystemsectionprivilege findSystemSectionPrivilege(String systemSectionCode) {
        for (Rolsystemsectionprivilege rolsystemsectionprivilege : getSelectedRol().getRolsystemsectionprivilegeList()) {
            if (rolsystemsectionprivilege.getSystemSection().equals(systemSectionCode)) {
                return rolsystemsectionprivilege;
            }
        }
        return null;
    }

    /**
     * crea y persiste un systemSectionPrivilege con valores por defectos.
     *
     * @param systemSectionCode
     * @return UserSystemSectionPrivilege
     */
    private Rolsystemsectionprivilege createDefaultPrivilege(String systemSectionCode) {
        Rolsystemsectionprivilege rolsystemsectionprivilege = new Rolsystemsectionprivilege();
        rolsystemsectionprivilege.setIdRolSystemSectionPrivilege(0);
        rolsystemsectionprivilege.setPrivilege(Privilege.NONE);
        rolsystemsectionprivilege.setRolidRol(getSelectedRol());
        rolsystemsectionprivilege.setSystemSection(systemSectionCode);
        getSelectedRol().getRolsystemsectionprivilegeList().add(rolsystemsectionprivilege);
        rolsystemsectionprivilegeFacade.create(rolsystemsectionprivilege);
        return rolsystemsectionprivilege;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="WORKFLOW - Modificar Privilegios nodos hijos">
    /**
     * Controla los nodos del arbol para que un nodo hijo no tenga un nuvbel de
     * privilegios mayor a la del nodo padre.
     *
     * @param rolsystemsectionprivilege
     */
    public void handleUserSystemPrivileges(Rolsystemsectionprivilege rolsystemsectionprivilege) {
        findParentNode(root.getChildren(), rolsystemsectionprivilege, Privilege.ADMIN);
    }

    /**
     * Busca y cambia el nivel de privilegios de los nodos hijos del arbol de
     * privilegios según el objeto userSystemSectionPrivilege
     *
     *
     * @param treeNodeList
     * @param rolsystemsectionprivilege
     */
    public void findParentNode(List<TreeNode> treeNodeList, Rolsystemsectionprivilege rolsystemsectionprivilege, Privilege parentLevelPrivileges) {
        Privilege privilege = rolsystemsectionprivilege.getPrivilege();

        for (TreeNode treeNode : treeNodeList) {

            boolean nodoFound = ((Rolsystemsectionprivilege) treeNode.getData()).
                    getSystemSection().equals(rolsystemsectionprivilege.getSystemSection());

            if (nodoFound) {
                boolean isLevelPrivilegesInvalid = parentLevelPrivileges.ordinal() < privilege.ordinal();

                if (isLevelPrivilegesInvalid) {
                    ((Rolsystemsectionprivilege) treeNode.getData()).setPrivilege(parentLevelPrivileges);
                    messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("privilege_title"), resourcesMgr.getText("privilege_msg_level",viewRoot.getLocale()));
                    return;
                }
                changePrivilegesChildNodes(treeNode.getChildren(), privilege);

                break;
            } else {
                findParentNode(treeNode.getChildren(), rolsystemsectionprivilege, ((Rolsystemsectionprivilege) treeNode.getData()).getPrivilege());
            }
        }
    }

    /**
     * Cambia el nivel de privilegios de toda la lista de nodos según el valor
     * de privilegio enviado como parametro.
     *
     * @param treeNodeInternoList
     * @param privilege
     */
    public void changePrivilegesChildNodes(List<TreeNode> treeNodeInternoList, Privilege privilege) {
        for (TreeNode treeNodeInterno : treeNodeInternoList) {
            ((Rolsystemsectionprivilege) treeNodeInterno.getData()).setPrivilege(privilege);
            changePrivilegesChildNodes(treeNodeInterno.getChildren(), privilege);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Guardar cambios en privilegios">
    /**
     * Persiste los cambios realizados en el arbol de privilegios
     */
    public void saveChanges() {
        rolsystemsectionprivilegeList = new ArrayList<>();
        recorrerTreeNode(root.getChildren());

        boolean succes = false;
        try {
           selectedRol.getRolsystemsectionprivilegeList().clear();
            selectedRol.getRolsystemsectionprivilegeList().addAll(rolsystemsectionprivilegeList);
            rolFacade.edit(selectedRol);
            succes = true;
        } catch (Exception ex) {
            succes = false;
            Logger
                    .getLogger(PrivilegeBean.class
                            .getName()).log(Level.FINEST, "saveChanges", ex);
        }

        if (succes) {
            messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("privilege_title",viewRoot.getLocale()), resourcesMgr.getText("privilege_msg_save",viewRoot.getLocale()));
        } else {
            messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("privilege_title",viewRoot.getLocale()), resourcesMgr.getText("privileges_msg_errorSave",viewRoot.getLocale()));

        }

    }

    /**
     * Recorre el arbol de nodos y agrega cada uno a una lista
     *
     * @param privilegeTreeNode
     */
    public void recorrerTreeNode(List<TreeNode> privilegeTreeNode) {
        for (TreeNode treeNode : privilegeTreeNode) {
            Rolsystemsectionprivilege rolsystemsectionprivilege = (Rolsystemsectionprivilege) treeNode.getData();
            rolsystemsectionprivilegeList.add(rolsystemsectionprivilege);
            recorrerTreeNode(treeNode.getChildren());
        }
    }

    /**
     * Restablece los privilegios del arbol en caso de que hallan sido
     * modificados y aun no se hayan persistido estos cambios.
     */
    public void restorePrivilegesTree() {
        root = null;

        setSelectedRol(rolFacade.find(getSelectedRol().getIdRol()));
        buildTreePrivilege();
        messageTools.displayMessage(FacesMessage.SEVERITY_INFO, resourcesMgr.getText("privilege_title",viewRoot.getLocale()), resourcesMgr.getText("privilege_msg_restore",viewRoot.getLocale()));

    }

    // </editor-fold>
    /**
     * Retorna true en caso de que el fragmento de parametros de usuario del
     * sitio web tenga privilegios mayores o iguales al del privilegio enviado
     * como parámetro
     *
     * @param privilegeString
     * @return
     */
    public boolean verifyUserParamPrivilege(String privilegeString) {
        Privilege privilege = rolSessionBean.getSystemSectionPrivilegeForCode("ADMIN_ROLES_PRIVILEGES_PRIVILEGES");
        if (privilege.ordinal() >= Privilege.valueOf(privilegeString).ordinal()) {
            return true;
        }
        return false;
    }

    /**
     * Se verifica si el administrador de privilegios solo tiene permisos de
     * lectura (Read only). El administrador de privilegios solo tendrá permisos
     * de lectura cuando vaya a editar sus propios privilegios o cuando el
     * privilegio a administrar es mayor al privilegio del administrador (ej:
     * ADMIN > READ_WRITE).
     *
     * param userSystemSectionPrivilege privilegio del usuario a editar
     *
     * @param rolsystemsectionprivilege
     * @return true en caso de que solo tenga permisos de lectura.
     */
    public boolean verifyPrivilegeReadOnly(Rolsystemsectionprivilege rolsystemsectionprivilege) {

        if (selectedRol.getIdRol() == rolSessionBean.getRol().getIdRol()) {
            return true;
        }

        List<Rolsystemsectionprivilege> userSystemSectionPrivileges = rolSessionBean.getRol().getRolsystemsectionprivilegeList();

        for (Rolsystemsectionprivilege rolsystemsectionprivilegeAdmin : userSystemSectionPrivileges) {
            if (rolsystemsectionprivilege.getSystemSection().equals(rolsystemsectionprivilegeAdmin.getSystemSection())) {
                return rolsystemsectionprivilegeAdmin.getPrivilege().ordinal() < rolsystemsectionprivilege.getPrivilege().ordinal();
            }
        }

        return false;
    }

    // <editor-fold defaultstate="collapsed" desc="SETTER & GETTERS">
    /**
     * @return the privilegeList
     */
    public List<Privilege> getPrivilegeList() {
        return Arrays.asList(Privilege.values());
    }

    /**
     * @param rolsystemsectionprivilege
     * @return the privilegeList
     */
    public List<Privilege> getPrivilegeList(Rolsystemsectionprivilege rolsystemsectionprivilege) {
        List<Privilege> privilegeList = new ArrayList<>();
        //Si solo tiene privilegios de lectura se carga toda la lista de privilegios
        if (verifyPrivilegeReadOnly(rolsystemsectionprivilege)) {
            privilegeList = Arrays.asList(Privilege.values());
            return privilegeList;
        } else {
            //En caso de que tenga permisos de administración del privilegio solo se cargaran a la lista
            //los privilegios menores o iguales a mi nivel de privilegio.
            //Ej: nivel de privilegio READ_WRITE = lista de privilegios (NONE, READ, READ_WRITE)

            List<Rolsystemsectionprivilege> rolsystemsectionprivileges = rolSessionBean.getRol().getRolsystemsectionprivilegeList();

            for (Rolsystemsectionprivilege rolsystemsectionprivilegeAdmin : rolsystemsectionprivileges) {
                if (rolsystemsectionprivilege.getSystemSection().equals(rolsystemsectionprivilegeAdmin.getSystemSection())) {

                    for (Privilege privilege : Arrays.asList(Privilege.values())) {
                        if (rolsystemsectionprivilegeAdmin.getPrivilege().ordinal() >= privilege.ordinal()) {
                            privilegeList.add(privilege);
                        }
                    }
                }
            }

            if (privilegeList.isEmpty()) {
                privilegeList.add(Privilege.values()[0]);
            }

            return privilegeList;
        }
    }

    /**
     * @return the selectPrivilege
     */
    public Privilege getSelectPrivilege() {
        return selectPrivilege;
    }

    /**
     * @param selectPrivilege the selectPrivilege to set
     */
    public void setSelectPrivilege(Privilege selectPrivilege) {
        this.selectPrivilege = selectPrivilege;
    }

    /**
     * @return the root
     */
    public TreeNode getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(TreeNode root) {
        this.root = root;
    }

    /**
     * @return the selectedUser
     */
    public Rol getSelectedRol() {
        return selectedRol;
    }

    /**
     * @param selectedRol
     */
    public void setSelectedRol(Rol selectedRol) {
        this.selectedRol = selectedRol;
    }
    // </editor-fold>

}
