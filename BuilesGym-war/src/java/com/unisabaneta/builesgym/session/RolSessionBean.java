/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.session;

import com.unisabaneta.builesgym.menu.Category;
import com.unisabaneta.builesgym.menu.Fragment;
import com.unisabaneta.builesgym.menu.MenuService;
import com.unisabaneta.builesgym.menu.Section;
import com.unisabaneta.builesgym.menu.Subsection;
import com.unisabaneta.builesgym.menu.SystemSection;
import com.unisabaneta.builesgym.dao.RolFacade;
import com.unisabaneta.builesgym.entity.Rol;
import com.unisabaneta.builesgym.entity.Rolsystemsectionprivilege;
import com.unisabaneta.builesgym.entity.type.Privilege;
import com.unisabaneta.builesgym.tools.HashTools;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.primefaces.component.layout.LayoutUnit;
import org.primefaces.event.ToggleEvent;

/**
 *
 * @author Andres
 */
@Named(value = "rolSessionBean")
@SessionScoped
public class RolSessionBean implements Serializable {

    /**
     * Rol que ha iniciado sesión en el Sistema
     */
    private Rol rol;

    /**
     * Mapa de Secciones del sistema VS Privelegio del rol en ella
     */
    private HashMap<String, Privilege> systemSectionPrivilegeMap = new HashMap<>();

    /**
     * PAgina de index
     */
    private static final String INDEX_PAGE = "/index.jsp";

    /**
     * Pagina principal - home
     */
    private static final String HOME_PAGE = "/home/home.jsf";

    ////////  INJECTIONS ///////
    @Inject
    private RolFacade rolFacade;

    @Inject
    private HashTools hashTools;

    @Inject
    private MenuService menuService;

    ///////////////////////////
    /**
     * Entidad utilizada para trasnportar los datos de login y contraseña desde
     * l formulario de login hasta el presente BEAN
     */
    private LoginFormRol loginFormRol = new LoginFormRol();

    private boolean visibleMenuWest = false;

    private boolean visibleNotificationEast = false;

    /**
     * Creates a new instance of RolSessionBean
     */
    public RolSessionBean() {
    }

//<editor-fold defaultstate="collapsed" desc="LOGIN WORKFLOW">
    /**
     * Realiza la comprobación y login de un rol en el sistema
     *
     * @param user login de rol
     * @param password contraseña en texto plano
     */
    public void doLogIn() {
        //Si ya se encuentra logeado en el sistema
        //no es necesario volver a logear
        if (getRol() != null) {
            navigateToHome();
        }
        //buscamos el rol en la BD
        rol = rolFacade.findByRolPassword(loginFormRol.getUserName(), getHashMD5(loginFormRol.getPassword()));
        //En caso de no ser encontrado se retorna y muestra el mensaje al usaurio
        if (getRol() == null) {
            displayUserMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("login_fail"), "");
            return;
        }
        if (!rol.getActiveRol()) {
            rol = null;
            displayUserMessage(FacesMessage.SEVERITY_ERROR, getResourceMessage("user_msg_inactive"), "");
            return;
        }
        //Se debe popular el Mapa de privelegios
        loadSystemSectionPrivilegeMap();
        //En caso de que sea la primera vez que un Rol
        //se logea en el sistema, no cuente con ningun privilegio
        //y el nombre de rol sea "admin", se le debe dar todos
        //los privilegios de forma predeterminada
        if (systemSectionPrivilegeMap.isEmpty() && getRol().getUserRol().equals("administrator")) {
            setDefaultAdminPrivileges();
            rolFacade.edit(rol);
        }
        //Debemos cambiar el LOCALE al del rol        
        setRolLocale();
        //Finalmente Re-direccionamos el sitio al Home
        navigateToHome();

    }

    public void doLogOut() {
        if (invalidateSession()) {
            navigateToLogIn();
        }
    }

    /**
     * invalida la seccion cuando el timeUp ya se a cumplido
     *
     * @return
     */
    public boolean invalidateSession() {
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        Object session = ctx.getSession(false);
        if (session == null) {
            return false;
        }
        ((HttpSession) session).invalidate();
        return true;
    }

    /**
     * Verifica que un rol esta logeado en el sistema, si la verificación
     * falla el sistio es re-direccionado al Login
     */
    public void verifyRolLoggedIn() {
        boolean logged = rol != null;
        if (!logged) {
            navigateToLogIn();
        }
    }

    /**
     * Verifica que un rol NO esta logeado en el sistema, si la verificacion
     * falla el sitio es re-direccionado al Home
     */
    public void verifyRolNotLoggedIn() {
        boolean logged = rol != null;
        if (logged) {
            navigateToHome();
        }
    }

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="TOOLS">
    /**
     * Calcula cadena MD5 de la cadena dada
     *
     * @param text
     * @return
     */
    private String getHashMD5(String text) {
        return hashTools.calculateMD5(text);
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="MESSAGE">
    private String getResourceMessage(String key) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "basicMsg");
        return bundle.getString(key);
    }

    /**
     * Encola un mensaje para ser visualizado por el rol
     *
     * @param severity FacesMessage.Severity
     * @param summary Resumen ó Titulo
     * @param detail Descripción detallada
     */
    private void displayUserMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="NAVIGATION">
    /**
     * Redirecciona el browser a la pagina de LogIn
     */
    public void navigateToLogIn() {
        navigateToPath(INDEX_PAGE);
    }

    /**
     * Redirecciona el Browser a la pagina inicial
     */
    public void navigateToHome() {
        navigateToPath(HOME_PAGE);
    }

    /**
     * Redirecciona el browser a la ruta inidicada dentro del app
     *
     * @param path ruta relativa desde la carpeta "Web Pages"
     */
    private void navigateToPath(String path) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        try {
            externalContext.redirect(externalContext.getRequestContextPath() + path);
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        } finally {
            context.responseComplete();
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">
    /**
     * Entidad utilizada para trasnportar los datos de login y contraseña desde
     * l formulario de login hasta el presente BEAN
     *
     * @return the loginFormRol
     */
    public LoginFormRol getLoginFormRol() {
        return loginFormRol;
    }

    /**
     * Entidad utilizada para trasnportar los datos de login y contraseña desde
     * l formulario de login hasta el presente BEAN
     *
     * @param loginFormRol the loginFormRol to set
     */
    public void setLoginFormRol(LoginFormRol loginFormRol) {
        this.loginFormRol = loginFormRol;
    }

    public boolean isVisibleMenuWest() {
        return visibleMenuWest;
    }

    public void setVisibleMenuWest(boolean visibleMenuWest) {
        this.visibleMenuWest = visibleMenuWest;
    }

    public boolean isVisibleNotificationEast() {
        return visibleNotificationEast;
    }

    public void setVisibleNotificationEast(boolean visibleNotificationEast) {
        this.visibleNotificationEast = visibleNotificationEast;
    }

    /**
     * En caso de que el Locale del sistio este configurado en uno diferente al
     * del rol, se cambia el Locale
     */
    private void setRolLocale() {
        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
        if (!viewRoot.getLocale().getLanguage().equalsIgnoreCase(rol.getLanguageLocaleidlanguageLocale().getLocalCode())) {
            viewRoot.setLocale(new Locale(getRol().getLanguageLocaleidlanguageLocale().getLocalCode()));
        }
    }

    public String getLocale() {
        return rol.getLanguageLocaleidlanguageLocale().getLocalCode();
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="PRIVILEGES">
    /**
     * Configura todos los privilegios por defecto del admin general del sistema
     */
    private void setDefaultAdminPrivileges() {
        List<Category> categories = menuService.getCategories();
        for (Category category : categories) {
            addRolPrivilege(category, Privilege.ADMIN);
            List<Section> sectionList = category.getSectionList();
            for (Section section : sectionList) {
                addRolPrivilege(section, Privilege.ADMIN);
                List<Subsection> subsectionList = section.getSubsectionList();
                for (Subsection subsection : subsectionList) {
                    addRolPrivilege(subsection, Privilege.ADMIN);
                    List<Fragment> fragmentList = subsection.getFragmentList();
                    for (Fragment fragment : fragmentList) {
                        addRolPrivilege(fragment, Privilege.ADMIN);
                    }
                }
            }
        }
    }

    /**
     * Conmfigura un privilegio para el rol legeado
     *
     * @param systemSectionCode
     * @param privilege
     */
    private void addRolPrivilege(SystemSection systemSection, Privilege privilege) {
        Rolsystemsectionprivilege rolSystemSectionPrivilege = new Rolsystemsectionprivilege(systemSection.getSystemSectionCode(), privilege);
        rolSystemSectionPrivilege.setRolidRol(rol);
        getRol().getRolsystemsectionprivilegeList().add(rolSystemSectionPrivilege);
        systemSectionPrivilegeMap.put(systemSection.getSystemSectionCode(), privilege);
    }

    /**
     * Pobla el mapa de priovelegios del rol desde la BD
     */
    private void loadSystemSectionPrivilegeMap() {
        List<Rolsystemsectionprivilege> rolSystemSectionPrivilegeList = getRol().getRolsystemsectionprivilegeList();
        for (Rolsystemsectionprivilege userSystemSectionPrivilege : rolSystemSectionPrivilegeList) {
            systemSectionPrivilegeMap.put(userSystemSectionPrivilege.getSystemSection(), userSystemSectionPrivilege.getPrivilege());
        }
    }

    /**
     * Obtiene el privilegio establecido para una seccion del sistema dada
     *
     * @param systemSection
     * @return
     */
    public Privilege getSystemSectionPrivilege(SystemSection systemSection) {
        return getSystemSectionPrivilegeForCode(systemSection.getSystemSectionCode());
    }

    /**
     * Obtiene el privilegio establecido para un codigo de seccion del sistema
     * dada
     *
     * @param systemSection
     * @return
     */
    public Privilege getSystemSectionPrivilegeForCode(String systemSectionCode) {
        return (systemSectionPrivilegeMap.containsKey(systemSectionCode)
                ? (systemSectionPrivilegeMap.get(systemSectionCode)) : Privilege.NONE);
    }

    /**
     * Verifica que un rol tenga algun grado de acceso al sistio interno que
     * desea ingresa, si la verificacion falla el sitio es re-direccionado al
     * Home, en caso de que la url obtenida desde el contexto sea diferente a la
     * url de la subseccion seleccionada se redirecciona a la página correcta.
     *
     * @param systemSectionCode
     */
    public void verifyRolInnerSiteAccess(String systemSectionCode) {
        HttpServletRequest httpRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        boolean isEqualURL = menuService.getSelectedSection().getSelectedSubsection().getUrl().equals(httpRequest.getServletPath());
        boolean accessGranted = systemSectionCode.contains("HOME") ? true : isInnerSiteAllowedForUser(systemSectionCode);
        if (!accessGranted) {
            navigateToHome();
        }
        //Si las URLs comparadas no son iguales se redirecciona la página hacia
        // la url de la subsection seleccionada.
        if (!isEqualURL) {
            navigateToPath(menuService.getSelectedSection().getSelectedSubsection().getUrl());
        }
    }

    /**
     * Verifica que un rol tenga algun grado de acceso al sistio interno que
     * desea ingresa
     *
     * @param systemSectionCode
     * @return true en caso de tener un grado de acceso
     */
    public boolean isInnerSiteAllowedForUser(String systemSectionCode) {
        return (systemSectionPrivilegeMap.containsKey(systemSectionCode)
                ? (systemSectionPrivilegeMap.get(systemSectionCode).ordinal() >= Privilege.READ.ordinal())
                : false);
    }

    /**
     * Verifica que en la subseccion actual en la que esta el rol tenga al
     * menos privilegios de lecto/escritura
     *
     * @return
     */
    public boolean verifyCurrentSubsectionReadWritePrivilege() {
        Subsection selectedSubsection = menuService.getSelectedSection().getSelectedSubsection();
        return getSystemSectionPrivilege(selectedSubsection).compareTo(Privilege.READ_WRITE) >= 0;
    }

    /**
     * Verifica que en la subseccion actual en la que esta el rol tenga al
     * menos privilegios de lecto/escritura
     *
     * @return
     */
    public boolean verifyCurrentSubsectionAdminPrivilege() {
        Subsection selectedSubsection = menuService.getSelectedSection().getSelectedSubsection();
        return getSystemSectionPrivilege(selectedSubsection).compareTo(Privilege.ADMIN) >= 0;
    }

    /**
     * Indica si una o mas subsecciones de toda la categoria tienen niveles de
     * privilegios mayores a NONE
     *
     * @param Category categoria del menú
     * @return true
     */
    public boolean verifyCategoryPrivileges(Category category) {
        List<Section> sectionList = category.getSectionList();

        for (Section section : sectionList) {
            if (verifySubsectionPrivileges(section)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Indica si una o mas subsecciones de la seccion tienen niveles de
     * privilegios mayores a NONE
     *
     * @param Section seccion del menú
     * @return true
     */
    public boolean verifySubsectionPrivileges(Section section) {
        List<Subsection> subsectionList = section.getSubsectionList();

        for (Subsection subsection : subsectionList) {
            if (getSystemSectionPrivilegeForCode(subsection.getSystemSectionCode()).ordinal() > Privilege.NONE.ordinal()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the rol
     */
    public Rol getRol() {
        return rol;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="STATE LAYOUT COMPONENT">
    public void handleToggle(ToggleEvent event) {
        if (event.getVisibility().name().equals("HIDDEN")) {
            if (((LayoutUnit) event.getComponent()).getPosition().equals("west")) {
                setVisibleMenuWest(true);
            } else {
                setVisibleNotificationEast(true);
            }
        } else if (((LayoutUnit) event.getComponent()).getPosition().equals("west")) {
            setVisibleMenuWest(false);
        } else {
            setVisibleNotificationEast(false);
        }
    }

    //</editor-fold>    
    /**
     * Entidad utilizada para trasnportar los datos de login y contraseña desde
     * el formulario de login hasta el presente BEAN
     */
    public class LoginFormRol implements Serializable {

        @NotNull
        @Size(min = 8, max = 100)
        private String userName;
        @NotNull
        @Size(min = 8, max = 50)
        private String password;

        /**
         * @return the userName
         */
        public String getUserName() {
            return userName;
        }

        /**
         * @param userName the userName to set
         */
        public void setUserName(String userName) {
            this.userName = userName;
        }

        /**
         * @return the password
         */
        public String getPassword() {
            return password;
        }

        /**
         * @param password the password to set
         */
        public void setPassword(String password) {
            this.password = password;
        }
    }

}
