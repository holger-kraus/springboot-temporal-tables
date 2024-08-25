package com.example.temporaltabletest;

import io.hypersistence.utils.hibernate.type.range.Range;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMPLOYEES_SEQ")
    @SequenceGenerator(name = "EMPLOYEES_SEQ", allocationSize=1, sequenceName = "employees_mitarbeiter_id_seq")
    @Column(name = "mitarbeiter_id", nullable = false, length = Integer.MAX_VALUE)
    private Long mitarbeiterID;

    @Column(name = "name", nullable = false, length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "department", length = Integer.MAX_VALUE)
    private String department;

    @Column(name = "salary", precision = 20, scale = 2)
    private BigDecimal salary;

    @Column(name = "sys_period", columnDefinition = "tstzrange not null")
    private Range<LocalDate> sysPeriod;

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

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Long getMitarbeiterID() {
        return mitarbeiterID;
    }

    public void setMitarbeiterID(Long mitarbeiterID) {
        this.mitarbeiterID = mitarbeiterID;
    }

    public Range<LocalDate>  getSysPeriod() {
        return sysPeriod;
    }

    public void setSysPeriod(Range<LocalDate>  sysPeriod) {
        this.sysPeriod = sysPeriod;
    }
}