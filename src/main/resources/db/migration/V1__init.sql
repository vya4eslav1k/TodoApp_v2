-- Создание схемы
CREATE SCHEMA IF NOT EXISTS todo_app;

-- Создание таблицы user
CREATE TABLE todo_app."user" (
                                 id SERIAL PRIMARY KEY,
                                 login VARCHAR(50) NOT NULL UNIQUE,
                                 password VARCHAR(300) NOT NULL,
                                 email VARCHAR(50),
                                 bio VARCHAR(100)
);

-- Создание таблицы task
CREATE TABLE todo_app.task (
                               id INT PRIMARY KEY,
                               title VARCHAR(50),
                               description VARCHAR(500),
                               due_date DATE,
                               status VARCHAR(255),
                               user_id INT,
                               CONSTRAINT fk_user
                                   FOREIGN KEY (user_id)
                                       REFERENCES todo_app."user"(id)
                                       ON DELETE SET NULL
);
