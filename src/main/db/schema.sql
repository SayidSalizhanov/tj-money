create table Users (
                       id serial primary key,
                       username varchar unique,
                       email varchar unique,
                       password varchar,
                       telegram_id varchar,
                       sending_to_telegram boolean,
                       sending_to_email boolean
);

create table Groups (
                        id serial primary key,
                        name varchar unique,
                        created_at timestamp,
                        description varchar
);

create table Group_Members (
                               id serial primary key,
                               user_id int,
                               foreign key (user_id) references Users (id) on delete cascade,
                               group_id int,
                               foreign key (group_id) references Groups (id) on delete cascade,
                               joined_at timestamp,
                               role varchar
);

create table Goals (
                       id serial primary key,
                       user_id int,
                       foreign key (user_id) references Users (id) on delete cascade,
                       group_id int,
                       foreign key (group_id) references Groups (id) on delete cascade,
                       title varchar,
                       description varchar,
                       progress int
);

create table Applications (
                              id serial primary key,
                              user_id int,
                              foreign key (user_id) references Users (id) on delete cascade,
                              group_id int,
                              foreign key (group_id) references Groups (id) on delete cascade,
                              send_at timestamp,
                              status varchar
);

create table Records (
                         id serial primary key,
                         user_id int,
                         foreign key (user_id) references Users (id) on delete cascade,
                         group_id int,
                         foreign key (group_id) references Groups (id) on delete cascade,
                         title varchar,
                         content varchar,
                         created_at timestamp,
                         updated_at timestamp
);

create table Reminders (
                           id serial primary key,
                           user_id int,
                           foreign key (user_id) references Users (id) on delete cascade,
                           group_id int,
                           foreign key (group_id) references Groups (id) on delete cascade,
                           title varchar,
                           message varchar,
                           send_at timestamp,
                           status varchar
);

create table Transactions (
                              id serial primary key,
                              user_id int,
                              foreign key (user_id) references Users (id) on delete cascade,
                              group_id int,
                              foreign key (group_id) references Groups (id) on delete cascade,
                              amount int,
                              category varchar,
                              type varchar,
                              date_time timestamp,
                              description varchar
);

create table Articles (
                          id serial primary key,
                          title varchar,
                          content varchar,
                          author varchar,
                          published_at timestamp
);

create table avatars(
                        id serial primary key,
                        user_id int,
                        url varchar,
                        foreign key (user_id) references users(id) on delete cascade
);