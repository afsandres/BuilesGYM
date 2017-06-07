/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unisabaneta.builesgym.appinfo;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Andres
 */
@Named(value = "appInfo")
@SessionScoped
public class AppInfo implements Serializable {

    private String vendor = "UNISABANETA    ";
    private String appName = "BUILES GYM";
    private String version = "17.05.21.1 Alpha";
    private String appShortName = "GYM";

    public String getAppInfo() {
        return appName + " " + version + " by " + vendor;
    }

    public String getAppShortName() {
        return appShortName;
    }

    public String getAppName() {
        return appName;
    }
}
