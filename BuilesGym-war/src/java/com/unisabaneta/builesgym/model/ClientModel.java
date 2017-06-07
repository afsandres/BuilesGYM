/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unisabaneta.builesgym.model;

import com.unisabaneta.builesgym.entity.Client;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andres
 */
public class ClientModel extends LazyDataModel<Client>{

    List<Client> clientList = null;
    
    public ClientModel(List<Client> clientList) {
        this.clientList = clientList;
    }
    
    @Override
    public List<Client> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        setPageSize(pageSize);
        setRowCount(clientList.size());
    if (first == 0) {
            return clientList.subList(first, clientList.size());
        } else {
            return clientList.subList(first == clientList.size() ? first - 12 : first, clientList.size());
        }
    }
    
    
    @Override
    public Object getRowKey(Client client) {
        return client.getIdClient();
    }

    @Override
    public Client getRowData(String rowKey) {
        for (Client client : clientList) {
            if (client.getIdClient().toString().equals(rowKey)) {
                return client;
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
