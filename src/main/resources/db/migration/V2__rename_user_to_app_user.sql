ALTER TABLE "user" RENAME TO app_user;
ALTER TABLE task RENAME COLUMN user_id TO app_user_id;