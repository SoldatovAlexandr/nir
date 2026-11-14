--changeset asoldatov:cadet11
CREATE TABLE IF NOT EXISTS ohis(
    id  BIGINT  NOT NULL,
    cadet_id    BIGINT NOT NULL,
    t16 INT,
    t11 INT,
    t26 INT,
    t46 INT,
    t31 INT,
    t36 INT,
    FOREIGN KEY (cadet_id) REFERENCES cadet (id),
    CONSTRAINT ohis_pkey PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS mucous_membrane(
    id  BIGINT  NOT NULL,
    cadet_id    BIGINT NOT NULL,
    value INT,
    FOREIGN KEY (cadet_id) REFERENCES cadet (id),
    CONSTRAINT mucous_membrane_pkey PRIMARY KEY (id)
);

--comment: Добавлены столбцы в таблицу cadet
