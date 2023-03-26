--changeset asoldatov:initial1
create sequence if not exists hibernate_sequence
    increment 1
    minvalue 1
    maxvalue 9223372036854775807
    start 1
    cache 1;
--rollback drop sequence hibernate_sequence;
--comment: Добавлен hibernate_sequence

--changeset asoldatov:cadet1
CREATE TABLE IF NOT EXISTS groups
(
    id     BIGINT                NOT NULL,
    school CHARACTER VARYING(30) NOT NULL,
    name   CHARACTER VARYING(64) NOT NULL,
    CONSTRAINT groups_pkey PRIMARY KEY (id)
);
--rollback DROP TABLE group;
--comment: Создана таблица group

--changeset asoldatov:cadet2
CREATE TABLE IF NOT EXISTS cadet
(
    id                         BIGINT                 NOT NULL,
    full_name                  CHARACTER VARYING(100) NOT NULL,
    gender                     CHARACTER VARYING(30)  NOT NULL,
    birthday                   TIMESTAMP              NOT NULL,
    age                        INT                    NOT NULL,
    ethnic_group               CHARACTER VARYING(64)  NOT NULL,
    place_of_birth             CHARACTER VARYING(64)  NOT NULL,
    region_of_residence        CHARACTER VARYING(64)  NOT NULL,
    preventive_actions         CHARACTER VARYING(64)  NOT NULL,
    preventive_actions_details CHARACTER VARYING(256) NOT NULL,
    health_group               INT                    NOT NULL,
    bite_type                  CHARACTER VARYING(64)  NOT NULL,
    vvk                        CHARACTER VARYING(256) NOT NULL,
    dental_anomalies           CHARACTER VARYING(256) NOT NULL,
    level_of_hygiene           INT                    NOT NULL,
    bite                       CHARACTER VARYING(64)  NOT NULL,
    tooth_erosion              INT                    NOT NULL,
    tooth_erosion_count        INT                    NOT NULL,
    dental_trauma              INT                    NOT NULL,
    dental_trauma_count        INT                    NOT NULL,
    group_id                   BIGINT                 NOT NULL,
    FOREIGN KEY (group_id) REFERENCES groups (id),
    CONSTRAINT cadet_pkey PRIMARY KEY (id)
);
--rollback DROP TABLE cadet;
--comment: Создана таблица cadet

--changeset asoldatov:cadet3
CREATE TABLE IF NOT EXISTS dental_condition
(
    id               BIGINT NOT NULL,
    cadet_id         BIGINT NOT NULL,
    enamel_fluorosis INT    NOT NULL,
    FOREIGN KEY (cadet_id) REFERENCES cadet (id),
    CONSTRAINT dental_condition_pkey PRIMARY KEY (id)
);
--rollback DROP TABLE dental_condition;
--comment: Создана таблица dental_condition

--changeset asoldatov:cadet4
CREATE TABLE IF NOT EXISTS temporomandibular_joint
(
    id                         BIGINT NOT NULL,
    cadet_id                   BIGINT NOT NULL,
    symptoms                   INT    NOT NULL,
    clicking                   INT    NOT NULL,
    soreness                   INT    NOT NULL,
    limitation_of_jaw_movement INT    NOT NULL,
    FOREIGN KEY (cadet_id) REFERENCES cadet (id),
    CONSTRAINT temporomandibular_joint_pkey PRIMARY KEY (id)
);
--rollback DROP TABLE temporomandibular_joint;
--comment: Создана таблица temporomandibular_joint

--changeset asoldatov:cadet5
CREATE TABLE IF NOT EXISTS year_dental_condition
(
    id                  BIGINT                NOT NULL,
    dental_condition_id BIGINT                NOT NULL,
    year                CHARACTER VARYING(10) NOT NULL,
    FOREIGN KEY (dental_condition_id) REFERENCES dental_condition (id),
    CONSTRAINT year_dental_condition_pkey PRIMARY KEY (id)
);
--rollback DROP TABLE year_dental_condition;
--comment: Создана таблица year_dental_condition

--changeset asoldatov:cadet6
CREATE TABLE IF NOT EXISTS tooth
(
    id                       BIGINT NOT NULL,
    year_dental_condition_id BIGINT NOT NULL,
    number                   INT    NOT NULL,
    value                    INT    NOT NULL,
    FOREIGN KEY (year_dental_condition_id) REFERENCES year_dental_condition (id),
    CONSTRAINT tooth_pkey PRIMARY KEY (id)
);
--rollback DROP TABLE tooth;
--comment: Создана таблица tooth

--changeset asoldatov:cadet7
CREATE TABLE IF NOT EXISTS periodontal_tissue
(
    id       BIGINT NOT NULL,
    cadet_id BIGINT NOT NULL,
    number   INT    NOT NULL,
    value    INT    NOT NULL,
    FOREIGN KEY (cadet_id) REFERENCES cadet (id),
    CONSTRAINT periodontal_tissue_pkey PRIMARY KEY (id)
);
--rollback DROP TABLE periodontal_tissue;
--comment: Создана таблица periodontal_tissue

--changeset asoldatov:cadet8
CREATE TABLE IF NOT EXISTS enamel_spotting
(
    id       BIGINT NOT NULL,
    cadet_id BIGINT NOT NULL,
    number   INT    NOT NULL,
    value    INT    NOT NULL,
    FOREIGN KEY (cadet_id) REFERENCES cadet (id),
    CONSTRAINT enamel_spotting_pkey PRIMARY KEY (id)
);
--rollback DROP TABLE enamel_spotting;
--comment: Создана таблица enamel_spotting
