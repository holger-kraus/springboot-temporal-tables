CREATE SEQUENCE IF NOT EXISTS employees_mitarbeiter_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE employees
(
    mitarbeiter_id BIGINT    NOT NULL,
    name           TEXT      NOT NULL,
    department     TEXT,
    salary         DECIMAL(20, 2),
    CONSTRAINT pk_employees PRIMARY KEY (mitarbeiter_id)
);
ALTER TABLE employees  ADD COLUMN sys_period tstzrange NOT NULL DEFAULT tstzrange(current_timestamp, null);

CREATE TRIGGER versioning_trigger BEFORE INSERT OR UPDATE OR DELETE ON employees FOR EACH ROW EXECUTE PROCEDURE versioning('sys_period', 'employees_history', true);

CREATE TABLE employees_history
(
    id             INTEGER   NOT NULL,
    name           TEXT      NOT NULL,
    department     TEXT,
    salary         DECIMAL(20, 2),
    mitarbeiter_id BIGINT    NOT NULL,
    sys_period     TSTZRANGE NOT NULL,
    CONSTRAINT pk_employees_history PRIMARY KEY (id)
);