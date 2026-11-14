--changeset asoldatov:report1
CREATE TABLE IF NOT EXISTS report
(
    id                                       BIGINT                 NOT NULL,
    school                                   CHARACTER VARYING(128) NOT NULL,
    year_start                               INTEGER                NOT NULL,
    total_count_cases_of_diseases            INTEGER                NOT NULL,
    total_count_first_cases_of_diseases      INTEGER                NOT NULL,
    total_count_treatment_mp                 INTEGER                NOT NULL,
    total_count_treatment_military_hospitals INTEGER                NOT NULL,
    total_count_treatment_civilian_hospitals INTEGER                NOT NULL,
    total_count_labor_losses_day             INTEGER                NOT NULL,
    total_count_labor_losses_cases           INTEGER                NOT NULL,
    first_year_cases_of_diseases             INTEGER                NOT NULL,
    first_year_first_cases_of_diseases       INTEGER                NOT NULL,
    first_year_treatment_mp                  INTEGER                NOT NULL,
    first_year_treatment_military_hospitals  INTEGER                NOT NULL,
    first_year_treatment_civilian_hospitals  INTEGER                NOT NULL,
    first_year_labor_losses_day              INTEGER                NOT NULL,
    first_year_labor_losses_cases            INTEGER                NOT NULL,
    all_class_primary_incidence_absolute     INTEGER                NOT NULL,
    all_class_primary_incidence_ppm          NUMERIC(15, 2)         NOT NULL,
    all_class_primary_incidence_percent      NUMERIC(15, 2)         NOT NULL,
    oral_diseases_primary_incidence_absolute INTEGER                NOT NULL,
    oral_diseases_primary_incidence_ppm      NUMERIC(15, 2)         NOT NULL,
    oral_diseases_primary_incidence_percent  NUMERIC(15, 2)         NOT NULL,
    CONSTRAINT report_pkey PRIMARY KEY (id)
);
--rollback DROP TABLE report;
--comment: Создана таблица report

--changeset asoldatov:indicator1
CREATE TABLE IF NOT EXISTS indicator
(
    id             BIGINT                 NOT NULL,
    number         CHARACTER VARYING(128) NOT NULL,
    name           CHARACTER VARYING(128) NOT NULL,
    previous_value NUMERIC(15, 2)         NOT NULL,
    current_value  NUMERIC(15, 2)         NOT NULL,
    report_id      BIGINT                 NOT NULL,
    FOREIGN KEY (report_id) REFERENCES report (id),
    CONSTRAINT indicator_pkey PRIMARY KEY (id)
);
--rollback DROP TABLE indicator;
--comment: Создана таблица indicator

--changeset asoldatov:report_class1
CREATE TABLE IF NOT EXISTS report_class
(
    id                                BIGINT                 NOT NULL,
    category                          CHARACTER VARYING(128) NOT NULL,
    medical_supervision               INTEGER                NOT NULL,
    first_health_group                INTEGER                NOT NULL,
    second_health_group               INTEGER                NOT NULL,
    third_health_group                INTEGER                NOT NULL,
    normal_nutrition_group            INTEGER                NOT NULL,
    low_nutrition_group               INTEGER                NOT NULL,
    high_nutrition_group              INTEGER                NOT NULL,
    unfit_for_health_study_school     INTEGER                NOT NULL,
    unfit_for_health_study_university INTEGER                NOT NULL,
    report_id                         BIGINT                 NOT NULL,
    FOREIGN KEY (report_id) REFERENCES report (id),
    CONSTRAINT report_class_pkey PRIMARY KEY (id)
);
--rollback DROP TABLE report_class;
--comment: Создана таблица report_class

--changeset asoldatov:dental_office_scope1
CREATE TABLE IF NOT EXISTS dental_office_scope
(
    id                               BIGINT                 NOT NULL,
    contingents_of_patients          CHARACTER VARYING(128) NOT NULL,
    total_number_of_visits           INTEGER                NOT NULL,
    first_number_of_visits           INTEGER                NOT NULL,
    count_inspected                  INTEGER                NOT NULL,
    count_inspected_need_sanitation  INTEGER                NOT NULL,
    count_inspected_sanitized        INTEGER                NOT NULL,
    count_inspected_need_prosthetics INTEGER                NOT NULL,
    count_inspected_prosthetic       INTEGER                NOT NULL,
    report_id                        BIGINT                 NOT NULL,
    FOREIGN KEY (report_id) REFERENCES report (id),
    CONSTRAINT dental_office_scope_pkey PRIMARY KEY (id)
);
--rollback DROP TABLE dental_office_scope;
--comment: Создана таблица dental_office_scope

