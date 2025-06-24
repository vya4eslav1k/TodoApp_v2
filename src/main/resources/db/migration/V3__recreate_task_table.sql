DROP TABLE IF EXISTS todo_app.task CASCADE;

CREATE TABLE todo_app.task (
                               id SERIAL PRIMARY KEY,
                               title VARCHAR(50),
                               description VARCHAR(500),
                               due_date DATE,
                               status VARCHAR(255),
                               user_id INT,
                               CONSTRAINT fk_user
                                   FOREIGN KEY (user_id)
                                       REFERENCES todo_app.app_user(id)
                                       ON DELETE SET NULL
);
