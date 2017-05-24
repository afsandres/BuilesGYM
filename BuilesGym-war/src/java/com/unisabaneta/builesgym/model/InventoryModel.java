/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unisabaneta.builesgym.model;

import com.unisabaneta.builesgym.entity.Inventary;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andres
 */
public class InventoryModel extends LazyDataModel<Inventary>{

    List<Inventary> inventaryList = null;
    
    public InventoryModel(List<Inventary> inventaryList) {
        this.inventaryList = inventaryList;
    }
    
    @Override
    public List<Inventary> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        setPageSize(pageSize);
        setRowCount(inventaryList.size());
        
    if (first == 0) {
            return inventaryList.subList(first, inventaryList.size());
        } else {
            return inventaryList.subList(first == inventaryList.size() ? first - 12 : first, inventaryList.size());
        }
    }
    
    
    @Override
    public Object getRowKey(Inventary inventary) {
        return inventary.getIdInventary();
    }

    @Override
    public Inventary getRowData(String rowKey) {
        for (Inventary inventary : inventaryList) {
            if (inventary.getIdInventary().toString().equals(rowKey)) {
                return inventary;
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
