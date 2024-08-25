package com.example.temporaltabletest;

import io.hypersistence.utils.hibernate.type.range.Range;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
@Table(name = "employees_history")
public class EmployeesHistory {
    @Id
    @Column(name = "id", nullable = false, length = Integer.MAX_VALUE)
    private Integer id;

    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "department", length = Integer.MAX_VALUE)
    private String department;

    @Column(name = "salary", precision = 20, scale = 2)
    private BigDecimal salary;

    @Column(name = "mitarbeiter_id", nullable = false, length = Integer.MAX_VALUE)
    private Long mitarbeiterID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Long getMitarbeiterID() {
        return mitarbeiterID;
    }

    public void setMitarbeiterID(Long mitarbeiterID) {
        this.mitarbeiterID = mitarbeiterID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Range<ZonedDateTime> getSysPeriod() {
        return sysPeriod;
    }

    public void setSysPeriod(Range<ZonedDateTime> sysPeriod) {
        this.sysPeriod = sysPeriod;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Column(name = "sys_period", columnDefinition = "tstzrange not null")
    private Range<ZonedDateTime> sysPeriod;
}