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

INSERT INTO meals (date_time, description, calories, user_id)
VALUES -- 100001  Admin Meals
       ('2024-07-04 09:00', 'Amin_Завтрак', 500, 100001),
       ('2024-07-04 12:00', 'Admin_Обед', 1000, 100001),
       ('2024-07-04 18:00', 'Admin_Ужин', 500, 100001),
       ('2024-07-04 18:30', 'Admin_Еда на граничное значение', 100, 100001),
       ('2024-07-05 09:00', 'Admin_Завтрак', 1000, 100001),
       ('2024-07-05 12:00', 'Admin_Обед', 500, 100001),
       ('2024-07-05 18:00', 'Admin_Ужин', 410, 100001),
       -- 100000  USER Meals
       ('2024-07-04 09:00', 'User_Завтрак', 500, 100000),
       ('2024-07-04 12:00', 'User_Обед', 1000, 100000),
       ('2024-07-04 18:00', 'User_Ужин', 500, 100000),
       ('2024-07-04 18:30', 'User_Еда на граничное значение', 100, 100000),
       ('2024-07-05 09:00', 'User_Завтрак', 1000, 100000),
       ('2024-07-05 12:00', 'User_Обед', 500, 100000),
       ('2024-07-05 18:00', 'User_Ужин', 410, 100000);

