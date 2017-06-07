/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unisabaneta.builesgym.model;

import com.unisabaneta.builesgym.entity.Service;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andres
 */
public class ServiceModel extends LazyDataModel<Service>{

    List<Service> serviceList = null;
    
    public ServiceModel(List<Service> serviceList) {
        this.serviceList = serviceList;
    }
    
    @Override
    public List<Service> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        setPageSize(pageSize);
        setRowCount(serviceList.size());
    if (first == 0) {
            return serviceList.subList(first, serviceList.size());
        } else {
            return serviceList.subList(first == serviceList.size() ? first - 12 : first, serviceList.size());
        }
    }
    
    @Override
    public Object getRowKey(Service service) {
        return service.getIdService();
    }

    @Override
    public Service getRowData(String rowKey) {
        for (Service service : serviceList) {
            if (service.getIdService().toString().equals(rowKey)) {
                return service;
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
