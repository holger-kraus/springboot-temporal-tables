# How to start this prototype-application

1. you should start the database with: docker compose up
2. Then you can start the application with: mvn spring-boot:run
3. You can start using the app at:http://localhost:8080/employees

# Employee Management System - A showcase for Temporal Tables in Postgres

## Overview

This application demonstrates the implementation of **temporal tables** (also known as system-versioned tables) using PostgreSQL and Spring Boot. The system maintains a full history of employee records, allowing you to track all changes over time and query the database as it existed at any point in time.

## Temporal Table Concept

### What are Temporal Tables?

Temporal tables are database tables that keep track of data changes over time. Unlike traditional tables that only store the current state of data, temporal tables retain the full history of changes made to each record. This approach is also known as:

- Historization
- System versioning
- Bi-temporal data modeling
- Time travel queries

### How it Works in This Prototype

This prototype implements temporal tables using the following approach:

1. **Main Table (`employees`)**: Contains the current state of all employee records
2. **History Table (`employees_history`)**: Contains all previous versions of employee records
3. **System Period Column (`sys_period`)**: A timestamp range (`tstzrange`) that defines when each record version was active
4. **Database Triggers**: Automatically manage the versioning process

When changes are made to employee records:

- **INSERT**: A new record is created in the `employees` table with `sys_period` starting at the current timestamp
- **UPDATE**: The existing record in `employees` is updated with a new `sys_period` start time, and the previous version is moved to `employees_history`
- **DELETE**: The record is removed from `employees` and its final state is preserved in `employees_history` with the end time of `sys_period` set to the deletion time

### Key Components

#### Database Structure

- **Range Type**: Uses PostgreSQL's `tstzrange` (timestamp with time zone range) for tracking validity periods
- **Versioning Trigger**: The `versioning_trigger` handles all the historization logic
- **Versioning Function**: The `versioning()` function (in `V1__versioning.sql`) implements the complex logic for maintaining history

#### Java Implementation

- **Entity Classes**: Both `Employee` and `EmployeesHistory` entities map to their respective tables
- **Range Type Handling**: Uses the `hypersistence-utils` library to handle PostgreSQL range types in Java
- **Repository Layer**: Provides methods to query both current and historical data
- **Time-Based Queries**: Supports retrieving employee data as it existed at a specific point in time

## Benefits of This Approach

1. **Complete Audit Trail**: Every change to employee records is tracked with its timestamp
2. **Point-in-Time Queries**: Ability to see how data looked at any moment in the past
3. **No Custom Audit Logic**: The database handles versioning automatically through triggers
4. **Data Integrity**: Historical data cannot be modified once recorded
5. **Simplified Application Logic**: No need for application-level audit tracking code

## Example Use Cases

- **Historical Reports**: Generate reports showing employee salaries or departments at specific dates
- **Compliance & Auditing**: Provide evidence of what data existed at a particular point for compliance reviews
- **Error Recovery**: Identify when and how data was changed if errors are discovered
- **Trend Analysis**: Analyze how employee data has evolved over time (e.g., salary progression)

## Technical Implementation Details

### Key Files

- `V1__versioning.sql`: Contains the PL/pgSQL function that handles temporal versioning
- `V2__tables.sql`: Creates the employee tables and sets up the versioning trigger
- `Employee.java` & `EmployeesHistory.java`: Entity classes for current and historical data
- `EmployeeController.java`: Manages CRUD operations on employee records
- `EmployeeHistoryController.java`: Provides access to historical employee data

### How Queries Work

#### Current Data
```java
// Get all current employees
List<Employee> employees = employeesRepository.findAll();

// Get specific current employee
Employee employee = employeesRepository.findByMitarbeiterID(employeeId).get();
```

#### Historical Data
```java
// Get all historical versions of an employee
List<EmployeesHistory> history = employeesHistoryRepository.findByMitarbeiterID(employeeId);

// Get employee data as it existed at a specific timestamp
EmployeesHistory pointInTime = employeesHistoryRepository.findEmployeesHistoryByTimestamp(
    "Employee Name", "2024-07-27 16:19:05.416386+00").get();
```

## Running the Application

1. Start the PostgreSQL database:
   ```
   docker compose up
   ```

2. Run the Spring Boot application:
   ```
   mvn spring-boot:run
   ```

3. Access the application at:
   ```
   http://localhost:8080/employees
   ```

## Best Practices for Using Temporal Tables

1. **Never Directly Modify History**: The history table should only be modified by the versioning trigger
2. **Use Appropriate Indexing**: Index the `sys_period` column for efficient time-based queries
3. **Consider Storage Requirements**: Historical data will increase database size over time
4. **Use Range-Based Queries**: Learn PostgreSQL's range operators to effectively query temporal data

