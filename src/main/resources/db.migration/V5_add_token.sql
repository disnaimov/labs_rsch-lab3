create table if not exists tokens(
                                     user_id uuid not null,
                                     value varchar(255) not null,
    killed bool,
    primary key(user_id),
    unique (user_id),
    unique (value)
    );
alter table tokens add foreign key (user_id) references users(id)
