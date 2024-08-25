package com.example.temporaltabletest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmployeesHistoryRepository extends JpaRepository<EmployeesHistory, Integer> {



    public List<EmployeesHistory> findByMitarbeiterID(Integer id);

//    @Query(value="SELECT * FROM employees_history WHERE name = ':NAME' AND sys_period @> '2024-07-27 16:19:05.416386+00'::timestamptz", nativeQuery=true)
//    Optional<ResultSet> findEmployeesHistoryByTimestamp(@Param("NAME") String name);

    @Query(value="SELECT * FROM employees_history WHERE name = :NAME AND sys_period @> :ZEIT ::timestamptz", nativeQuery=true)
    Optional<EmployeesHistory> findEmployeesHistoryByTimestamp(@Param("NAME") String name, @Param("ZEIT") String timestamp);
}

