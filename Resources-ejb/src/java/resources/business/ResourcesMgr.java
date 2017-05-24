/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources.business;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

/**
 *
 * @author Andres
 */
@Singleton
@LocalBean
public class ResourcesMgr {

    //<editor-fold defaultstate="collapsed" desc="VARIABLES">    
    /**
     * Internationalization resources for user messages
     */
    private ResourceBundle textResourceBundle;
    /**
     * Internationalization resources for Logs messages
     */
    private ResourceBundle logTextResourceBundle;
    /**
     * System short datetime format
     */
    private SimpleDateFormat shortDateTimeFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    /**
     * System long datetime format
     */
    private SimpleDateFormat longDateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    /**
     * System short date format
     */
    private SimpleDateFormat shortDateFormat = new SimpleDateFormat("dd/MM/yy");
    /**
     * System long date format
     */
    private SimpleDateFormat longDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    /**
     * System short time format
     */
    private SimpleDateFormat shortTimeFormat = new SimpleDateFormat("HH:mm");
    /**
     * System long time format
     */
    private SimpleDateFormat longTimeFormat = new SimpleDateFormat("HH:mm:ss");

//</editor-fold>
    @PostConstruct
    public void init() {
        textResourceBundle = ResourceBundle.getBundle("resources.bundles.basicMessages");
        logTextResourceBundle = ResourceBundle.getBundle("resources.bundles.log");
        shortDateTimeFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        longDateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        shortDateFormat = new SimpleDateFormat("dd/MM/yy");
        longDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        shortTimeFormat = new SimpleDateFormat("HH:mm");
        longTimeFormat = new SimpleDateFormat("HH:mm:ss");
    }

    @PreDestroy
    public void terminate() {
        textResourceBundle = null;
        logTextResourceBundle = null;
        shortDateTimeFormat = null;
        longDateTimeFormat = null;
        shortDateFormat = null;
        longDateFormat = null;
        shortTimeFormat = null;
        longTimeFormat = null;
    }

    //<editor-fold defaultstate="collapsed" desc="INTERNATIONALIZATION">
    /**
     * Gets an user message from the Internationalization resources in the
     * default locale
     *
     * @param key message key
     * @return
     */
    public String getText(String key) {
        return textResourceBundle.getString(key);
    }
    
    /**
     * Gets an user message from the Internationalization resources in the
     * default locale
     *
     * @param key message key
     * @param locale desired locale
     * @return
     */
    public String getText(String key, Locale locale) {
        return ResourceBundle.getBundle("resources.bundles.basicMessages",locale).getString(key);
    }

    /**
     * Gets an user message from the Internationalization resources in the
     * default locale inserting the arguments within the string
     *
     * @param key message key
     * @param args Texts and Objects to insert within the String
     * @return
     */
    public String getText(String key, Object... args) {
        return String.format(textResourceBundle.getString(key), args);
    }

    /**
     * Gets a Log message from the Internationalization resources in the default
     * locale
     *
     * @param key message key
     * @return
     */
    public String getLogText(String key) {
        return logTextResourceBundle.getString(key);
    }

    /**
     * Gets a Log message from the Internationalization resources in the default
     * locale inserting the arguments within the string
     *
     * @param key message key
     * @param args Texts and Objects to insert within the String
     * @return
     */
    public String getLogText(String key, Object... args) {
        return String.format(logTextResourceBundle.getString(key), args);
    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="DATE TIME FORMATS">
    /**
     * System short datetime format
     *
     * @return the shortDateTimeFormat
     */
    public SimpleDateFormat getShortDateTimeFormat() {
        return shortDateTimeFormat;
    }

    /**
     * System long datetime format
     *
     * @return the longDateTimeFormat
     */
    public SimpleDateFormat getLongDateTimeFormat() {
        return longDateTimeFormat;
    }

    /**
     * System short date format
     *
     * @return the shortDateFormat
     */
    public SimpleDateFormat getShortDateFormat() {
        return shortDateFormat;
    }

    /**
     * System long date format
     *
     * @return the longDateFormat
     */
    public SimpleDateFormat getLongDateFormat() {
        return longDateFormat;
    }

    /**
     * System short time format
     *
     * @return the shortTimeFormat
     */
    public SimpleDateFormat getShortTimeFormat() {
        return shortTimeFormat;
    }

    /**
     * System long time format
     *
     * @return the longTimeFormat
     */
    public SimpleDateFormat getLongTimeFormat() {
        return longTimeFormat;
    }

    //</editor-fold>
}
