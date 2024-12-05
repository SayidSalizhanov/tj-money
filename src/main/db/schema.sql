create table Users (
                       id serial primary key,
                       username varchar(50) unique,
                       email varchar(50) unique,
                       password varchar,
                       telegram_id varchar(50),
                       sending_to_telegram boolean,
                       sending_to_email boolean
);

create table Groups (
                        id serial primary key,
                        name varchar(50) unique,
                        created_at timestamp,
                        description varchar(400)
);

create table Group_Members (
                               id serial primary key,
                               user_id int,
                               foreign key (user_id) references Users (id) on delete cascade,
                               group_id int,
                               foreign key (group_id) references Groups (id) on delete set null,
                               joined_at timestamp,
                               role varchar
);

create table Goals (
                       id serial primary key,
                       user_id int,
                       foreign key (user_id) references Users (id) on delete cascade,
                       group_id int,
                       foreign key (group_id) references Groups (id) on delete set null,
                       title varchar(50),
                       description varchar(400),
                       progress int
);

create table Applications (
                              id serial primary key,
                              user_id int,
                              foreign key (user_id) references Users (id) on delete cascade,
                              group_id int,
                              foreign key (group_id) references Groups (id) on delete set null,
                              send_at timestamp,
                              status varchar
);

create table Records (
                         id serial primary key,
                         user_id int,
                         foreign key (user_id) references Users (id) on delete cascade,
                         group_id int,
                         foreign key (group_id) references Groups (id) on delete set null,
                         title varchar(50),
                         content varchar(400),
                         created_at timestamp,
                         updated_at timestamp
);

create table Reminders (
                           id serial primary key,
                           user_id int,
                           foreign key (user_id) references Users (id) on delete cascade,
                           group_id int,
                           foreign key (group_id) references Groups (id) on delete set null,
                           title varchar(50),
                           message varchar(200),
                           send_at timestamp,
                           status varchar
);

create table Transactions (
                              id serial primary key,
                              user_id int,
                              foreign key (user_id) references Users (id) on delete cascade,
                              group_id int,
                              foreign key (group_id) references Groups (id) on delete set null,
                              amount int,
                              category varchar,
                              type varchar,
                              date_time timestamp,
                              description varchar(200)
);

create table Articles (
                          id serial primary key,
                          title varchar(50),
                          content varchar(400),
                          author varchar(50),
                          published_at timestamp
);