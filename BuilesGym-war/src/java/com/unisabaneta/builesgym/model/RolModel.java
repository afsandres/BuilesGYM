/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unisabaneta.builesgym.model;

import com.unisabaneta.builesgym.entity.Rol;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andres
 */
public class RolModel extends LazyDataModel<Rol>{

    List<Rol> rolList = null;
    
    public RolModel(List<Rol> rolList) {
        this.rolList = rolList;
    }
    
    @Override
    public List<Rol> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        setPageSize(pageSize);
        setRowCount(rolList.size());
    if (first == 0) {
            return rolList.subList(first, rolList.size());
        } else {
            return rolList.subList(first == rolList.size() ? first - 12 : first, rolList.size());
        }
    }
    
    @Override
    public Object getRowKey(Rol rol) {
        return rol.getIdRol();
    }

    @Override
    public Rol getRowData(String rowKey) {
        for (Rol rol : rolList) {
            if (rol.getIdRol().toString().equals(rowKey)) {
                return rol;
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
