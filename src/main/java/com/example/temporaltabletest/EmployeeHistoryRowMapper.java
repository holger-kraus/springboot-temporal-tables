package com.example.temporaltabletest;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EmployeeHistoryRowMapper implements RowMapper<EmployeesHistory> {

    @Override
    public EmployeesHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
        EmployeesHistory employeeHistory = new EmployeesHistory();
        employeeHistory.setId(rs.getInt("id"));
        employeeHistory.setName(rs.getString("name"));
        employeeHistory.setDepartment(rs.getString("department"));
        employeeHistory.setSalary(rs.getBigDecimal("salary"));
//        employeeHistory.setSysPeriod(LocalDate.parse(rs.getString("sys_period")));
        return employeeHistory;
    }
}
