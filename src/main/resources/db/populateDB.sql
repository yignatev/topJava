DELETE
FROM user_role;
DELETE
FROM users;
DELETE
FROM meals;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (dateTime, description, calories, userid)
values -- 100001  Admin Meals
       ('2024-07-04 09:00:000', 'Amin_Завтрак', 500, 100001),
       ('2024-07-04 12:00:000', 'Admin_Обед', 1000, 100001),
       ('2024-07-04 18:00:000', 'Admin_Ужин', 500, 100001),
       ('2024-07-04 18:30:000', 'Admin_Еда на граничное значение', 100, 100001),
       ((now() - interval '1 day, 1 hour'), 'Admin_Завтрак', 1000, 100001),
       ((now() - interval '1 day'), 'Admin_Обед', 500, 100001),
       (((now() - interval '1 day') + interval '3 hours'), 'Admin_Ужин', 410, 100001),
       -- 100000  USER Meals
       ('2024-07-04 09:00:000', 'User_Завтрак', 500, 100000),
       ('2024-07-04 12:00:000', 'User_Обед', 1000, 100000),
       ('2024-07-04 18:00:000', 'User_Ужин', 500, 100000),
       ('2024-07-04 18:30:000', 'User_Еда на граничное значение', 100, 100000),
       ((now() - interval '1 day, 1 hour'), 'User_Завтрак', 1000, 100000),
       ((now() - interval '1 day'), 'User_Обед', 500, 100000),
       (((now() - interval '1 day') + interval '3 hours'), 'User_Ужин', 410, 100000);

