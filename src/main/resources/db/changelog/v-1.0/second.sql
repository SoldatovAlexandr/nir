--changeset asoldatov:cadet9
CREATE TABLE IF NOT EXISTS attachment_loss
(
    id       BIGINT NOT NULL,
    cadet_id BIGINT NOT NULL,
    number   INT    NOT NULL,
    value    INT    NOT NULL,
    FOREIGN KEY (cadet_id) REFERENCES cadet (id),
    CONSTRAINT attachment_loss_pkey PRIMARY KEY (id)
);
--rollback DROP TABLE attachment_loss;
--comment: Создана таблица attachment_loss

--changeset asoldatov:cadet10
CREATE TABLE IF NOT EXISTS demage_oral_musoca
(
    id                  BIGINT NOT NULL,
    cadet_id            BIGINT NOT NULL,
    first_state         INT    NOT NULL,
    second_state        INT    NOT NULL,
    third_state         INT    NOT NULL,
    first_localization  INT    NOT NULL,
    second_localization INT    NOT NULL,
    third_localization  INT    NOT NULL,
    FOREIGN KEY (cadet_id) REFERENCES cadet (id),
    CONSTRAINT demage_oral_musoca_pkey PRIMARY KEY (id)
);
--rollback DROP TABLE demage_oral_musoca;
--comment: Создана таблица demage_oral_musoca

--changeset asoldatov:cadet11
CREATE TABLE IF NOT EXISTS hygiene_index
(
    id       BIGINT NOT NULL,
    cadet_id BIGINT NOT NULL,
    number   INT    NOT NULL,
    index    INT    NOT NULL,
    FOREIGN KEY (cadet_id) REFERENCES cadet (id),
    CONSTRAINT hygiene_index_pkey PRIMARY KEY (id)
);
--rollback DROP TABLE hygiene_index;
--comment: Создана таблица hygiene_index

--changeset asoldatov:cadet12
ALTER TABLE cadet
    ADD COLUMN emergency_help INT NOT NULL DEFAULT 0;
--rollback ALTER TABLE cadet DROP COLUMN emergency_help;
--comment: Добавлен столбец emergency_help
