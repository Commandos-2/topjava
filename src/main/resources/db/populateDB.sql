DELETE
FROM meals;
DELETE
FROM user_roles;
DELETE
FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('User2', 'user2@yandex.ru', 'password2');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
       ('USER', 100002);

insert into meals (userid, datetime, description, calories)
values (100001, '2020-12-19 10:00:00', 'Break', 400);
insert into meals (userid, datetime, description, calories)
values (100001, '2021-07-11 06:51:10', 'Breakfast', 2128);
insert into meals (userid, datetime, description, calories)
values (100001, '2020-11-12 05:31:33', 'Afternoon snack', 1084);
insert into meals (userid, datetime, description, calories)
values (100001, '2021-04-15 07:37:50', 'Afternoon snack', 1907);
insert into meals (userid, datetime, description, calories)
values (100000, '2021-03-12 10:28:08', 'Breakfast', 637);
insert into meals (userid, datetime, description, calories)
values (100001, '2021-04-27 17:25:54', 'Lunch', 1021);
insert into meals (userid, datetime, description, calories)
values (100000, '2020-11-06 20:52:52', 'Afternoon snack', 1016);
insert into meals (userid, datetime, description, calories)
values (100000, '2021-03-23 13:29:11', 'Lunch', 1488);
insert into meals (userid, datetime, description, calories)
values (100001, '2021-03-24 18:05:52', 'Breakfast', 1885);
insert into meals (userid, datetime, description, calories)
values (100000, '2021-07-31 05:01:50', 'Dinner', 1806);
insert into meals (userid, datetime, description, calories)
values (100000, '2021-04-02 03:11:45', 'Lunch', 2141);
insert into meals (userid, datetime, description, calories)
values (100000, '2020-11-04 16:57:43', 'Lunch', 1516);
insert into meals (userid, datetime, description, calories)
values (100001, '2021-09-02 14:08:47', 'Afternoon snack', 1223);
insert into meals (userid, datetime, description, calories)
values (100001, '2021-03-21 18:27:00', 'Dinner', 1151);
insert into meals (userid, datetime, description, calories)
values (100001, '2021-06-21 03:23:57', 'Dinner', 2311);
insert into meals (userid, datetime, description, calories)
values (100000, '2021-10-20 15:00:41', 'Afternoon snack', 188);
insert into meals (userid, datetime, description, calories)
values (100001, '2021-02-23 13:02:57', 'Lunch', 2424);
insert into meals (userid, datetime, description, calories)
values (100001, '2020-12-11 08:33:31', 'Afternoon snack', 630);
insert into meals (userid, datetime, description, calories)
values (100000, '2021-07-26 20:14:39', 'Breakfast', 124);
insert into meals (userid, datetime, description, calories)
values (100000, '2021-01-28 02:36:48', 'Breakfast', 193);
insert into meals (userid, datetime, description, calories)
values (100000, '2020-10-30 15:17:21', 'Lunch', 393);
insert into meals (userid, datetime, description, calories)
values (100001, '2021-01-14 16:20:49', 'Dinner', 1763);
insert into meals (userid, datetime, description, calories)
values (100001, '2021-08-21 02:26:31', 'Dinner', 915);
insert into meals (userid, datetime, description, calories)
values (100002, '2021-01-10 10:20:00', 'Dinner', 1200);
insert into meals (userid, datetime, description, calories)
values (100002, '2021-08-20 02:00:00', 'Lunch', 900);
