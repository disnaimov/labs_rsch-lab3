create table if not exists kinds
(
    name        varchar     not null,
    UNIQUE      (name),
    PRIMARY KEY (name)
);

create table if not exists animals
(
    animal_id       uuid            not null,
    animal_name     varchar(25)     not null,
    kind            varchar,
    UNIQUE          (animal_id),
    PRIMARY KEY     (animal_id)
)