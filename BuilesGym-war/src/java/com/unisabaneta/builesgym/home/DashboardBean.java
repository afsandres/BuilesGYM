/*
 * Copyright 2009 Prime Technology.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.unisabaneta.builesgym.home;

import com.unisabaneta.builesgym.session.RolSessionBean;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

@Named(value = "dashboardBean")
@SessionScoped
public class DashboardBean implements Serializable {

    @Inject
    private RolSessionBean rolSessionBean;

    private DashboardModel model;

    private static final int OK = 1;
    private static final int WARNING = 2;
    private static final int ERROR = 3;
    private static final int NONE = 0;

    public void initDashboard() {
        buidDashboard();
    }

    public DashboardBean() {
    }

    public void buidDashboard() {
        model = new DefaultDashboardModel();
        DashboardColumn column1 = new DefaultDashboardColumn();
        column1.addWidget("dashBoardPanel");

        model.addColumn(column1);
    }

    public DashboardModel getModel() {
        return model;
    }

    //<editor-fold defaultstate="collapsed" desc="Setter && Getter">
    /**
     * @return the rolSessionBean
     */
    public RolSessionBean getRolSessionBean() {
        return rolSessionBean;
    }

    /**
     * @param rolSessionBean the rolSessionBean to set
     */
    public void setRolSessionBean(RolSessionBean rolSessionBean) {
        this.rolSessionBean = rolSessionBean;
    }

    //</editor-fold>
}
