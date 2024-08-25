package com.example.temporaltabletest.mapper;

import com.example.temporaltabletest.EmployeesHistory;
import io.hypersistence.utils.hibernate.type.range.Range;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeHistoryRowMapper implements RowMapper<EmployeesHistory> {

    @Override
    public EmployeesHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
        EmployeesHistory employeeHistory = new EmployeesHistory();
        employeeHistory.setId(rs.getInt("id"));
        employeeHistory.setName(rs.getString("name"));
        employeeHistory.setDepartment(rs.getString("department"));
        employeeHistory.setSalary(rs.getBigDecimal("salary"));
        employeeHistory.setSysPeriod(Range.zonedDateTimeRange(rs.getString("sys_period")));
        return employeeHistory;
    }
}
