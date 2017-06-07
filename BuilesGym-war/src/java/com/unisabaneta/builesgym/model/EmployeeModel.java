/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unisabaneta.builesgym.model;

import com.unisabaneta.builesgym.entity.Employee;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Andres
 */
public class EmployeeModel extends LazyDataModel<Employee>{

    List<Employee> employeeList = null;
    
    public EmployeeModel(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
    
    @Override
    public List<Employee> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        setPageSize(pageSize);
        setRowCount(employeeList.size());
    if (first == 0) {
            return employeeList.subList(first, employeeList.size());
        } else {
            return employeeList.subList(first == employeeList.size() ? first - 12 : first, employeeList.size());
        }
    }
    
    @Override
    public Object getRowKey(Employee employee) {
        return employee.getIdEmployee();
    }

    @Override
    public Employee getRowData(String rowKey) {
        for (Employee employee : employeeList) {
            if (employee.getIdEmployee().toString().equals(rowKey)) {
                return employee;
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
