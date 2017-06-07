/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unisabaneta.builesgym.model;

import com.unisabaneta.builesgym.entity.Rolsystemsectionprivilege;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andres
 */
public class PrivilegeModel extends LazyDataModel<Rolsystemsectionprivilege>{

    List<Rolsystemsectionprivilege> rolsystemsectionprivilegeList = null;
    
    public PrivilegeModel(List<Rolsystemsectionprivilege> RolsystemsectionprivilegeList) {
        this.rolsystemsectionprivilegeList = RolsystemsectionprivilegeList;
    }
    
    @Override
    public List<Rolsystemsectionprivilege> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        setPageSize(pageSize);
        setRowCount(rolsystemsectionprivilegeList.size());
    if (first == 0) {
            return rolsystemsectionprivilegeList.subList(first, rolsystemsectionprivilegeList.size());
        } else {
            return rolsystemsectionprivilegeList.subList(first == rolsystemsectionprivilegeList.size() ? first - 12 : first, rolsystemsectionprivilegeList.size());
        }
    }
    
    @Override
    public Object getRowKey(Rolsystemsectionprivilege rolsystemsectionprivilege) {
        return rolsystemsectionprivilege.getIdRolSystemSectionPrivilege();
    }

    @Override
    public Rolsystemsectionprivilege getRowData(String rowKey) {
        for (Rolsystemsectionprivilege rolsystemsectionprivilege : rolsystemsectionprivilegeList) {
            if (rolsystemsectionprivilege.getIdRolSystemSectionPrivilege().toString().equals(rowKey)) {
                return rolsystemsectionprivilege;
            }
        }
        return null;
    }

    @Override
    public void setRowIndex(int rowIndex) {
        if (rowIndex == -1 || getPageSize() == 0) {
            super.setRowIndex(-1);
        } else {
            super.setRowIndex(rowIndex % getPageSize());
        }
    }
}
