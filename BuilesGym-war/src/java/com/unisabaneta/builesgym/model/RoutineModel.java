/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unisabaneta.builesgym.model;

import com.unisabaneta.builesgym.entity.Routine;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andres
 */
public class RoutineModel extends LazyDataModel<Routine>{

    List<Routine> routineList = null;
    
    public RoutineModel(List<Routine> routineList) {
        this.routineList = routineList;
    }
    
    @Override
    public List<Routine> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        setPageSize(pageSize);
        setRowCount(routineList.size());
    if (first == 0) {
            return routineList.subList(first, routineList.size());
        } else {
            return routineList.subList(first == routineList.size() ? first - 12 : first, routineList.size());
        }
    }
    
    @Override
    public Object getRowKey(Routine routine) {
        return routine.getIdRoutine();
    }

    @Override
    public Routine getRowData(String rowKey) {
        for (Routine routine : routineList) {
            if (routine.getIdRoutine().toString().equals(rowKey)) {
                return routine;
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
