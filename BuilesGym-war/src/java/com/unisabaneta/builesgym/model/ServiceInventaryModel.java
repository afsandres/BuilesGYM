/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unisabaneta.builesgym.model;

import com.unisabaneta.builesgym.entity.Serviceinventary;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andres
 */
public class ServiceInventaryModel extends LazyDataModel<Serviceinventary>{

    List<Serviceinventary> serviceinventaryList = null;
    
    public ServiceInventaryModel(List<Serviceinventary> serviceinventaryList) {
        this.serviceinventaryList = serviceinventaryList;
    }
    
    @Override
    public List<Serviceinventary> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        setPageSize(pageSize);
        setRowCount(serviceinventaryList.size());
    if (first == 0) {
            return serviceinventaryList.subList(first, serviceinventaryList.size());
        } else {
            return serviceinventaryList.subList(first == serviceinventaryList.size() ? first - 12 : first, serviceinventaryList.size());
        }
    }
    
    @Override
    public Object getRowKey(Serviceinventary serviceinventary) {
        return serviceinventary.getIdServiceInventary();
    }

    @Override
    public Serviceinventary getRowData(String rowKey) {
        for (Serviceinventary serviceinventary : serviceinventaryList) {
            if (serviceinventary.getIdServiceInventary().toString().equals(rowKey)) {
                return serviceinventary;
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
