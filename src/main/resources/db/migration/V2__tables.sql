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
create table employees_history
(
    id INTEGER generated always as identity
        constraint pk_employees_history
            primary key,
    name           text      not null,
    department     text,
    salary         numeric(20, 2),
    mitarbeiter_id bigint    not null,
    sys_period     tstzrange not null
);