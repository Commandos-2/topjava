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

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100001, '2020-12-19 10:00:00', 'Admin_Break', 400),
       (100001, '2021-07-11 06:51:10', 'Admin_Breakfast', 2128),
       (100001, '2020-11-12 05:31:33', 'Admin_Afternoon snack', 1084),
       (100001, '2021-04-15 07:37:50', 'Admin_Afternoon snack', 1907),
       (100000, '2021-03-12 10:28:08', 'User_Breakfast', 637),
       (100001, '2021-04-27 17:25:54', 'Admin_Lunch', 1021),
       (100000, '2020-11-06 20:52:52', 'User_Afternoon snack', 1016),
       (100000, '2021-03-23 13:29:11', 'User_Lunch', 1488),
       (100001, '2021-03-24 18:05:52', 'Admin_Breakfast', 1885),
       (100000, '2021-07-31 05:01:50', 'User_Dinner', 1806),
       (100000, '2021-04-02 03:11:45', 'User_Lunch', 2141),
       (100000, '2020-11-04 16:57:43', 'User_Lunch', 1516),
       (100001, '2021-09-02 14:08:47', 'Admin_Afternoon snack', 1223),
       (100001, '2021-03-21 18:27:00', 'Admin_Dinner', 1151),
       (100001, '2021-06-21 03:23:57', 'Admin_Dinner', 2311),
       (100000, '2021-10-20 15:00:41', 'User_Afternoon snack', 188),
       (100001, '2021-02-23 13:02:57', 'Admin_Lunch', 2424),
       (100001, '2020-12-11 08:33:31', 'Admin_Afternoon snack', 630),
       (100000, '2021-07-26 20:14:39', 'User_Breakfast', 124),
       (100000, '2021-01-28 02:36:48', 'User_Breakfast', 193),
       (100000, '2020-10-30 15:17:21', 'User_Lunch', 393),
       (100001, '2021-01-14 16:20:49', 'Admin_Dinner', 1763),
       (100001, '2021-08-21 02:26:31', 'Admin_Dinner', 915),
       (100002, '2021-01-10 10:20:00', 'User2_Dinner', 1200),
       (100002, '2021-08-20 02:00:00', 'User2_Lunch', 900);
