DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;
DROP TABLE IF EXISTS meals;


CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
    id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name             VARCHAR                           NOT NULL,
    email            VARCHAR                           NOT NULL,
    password         VARCHAR                           NOT NULL,
    registered       TIMESTAMP           DEFAULT now() NOT NULL,
    enabled          BOOL                DEFAULT TRUE  NOT NULL,
    calories_per_day INTEGER             DEFAULT 2000  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_role
(
    user_id INTEGER NOT NULL,
    role    VARCHAR NOT NULL,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE meals
(
    id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    dateTime    timestamp NOT NULL,
    description VARCHAR   NOT NULL,
    calories    INTEGER   NOT NULL,
    userid      INTEGER   NOT NULL,
    FOREIGN KEY (userid) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX meals_unique_date_time_and_userid ON meals (dateTime, userid);
CREATE INDEX meals_calories on meals (calories);
CREATE INDEX meals_userid on meals (userid);


