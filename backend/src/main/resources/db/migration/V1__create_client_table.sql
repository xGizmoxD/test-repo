CREATE TABLE IF NOT EXISTS client (
  id           BIGSERIAL PRIMARY KEY,
  first_name   VARCHAR(100) NOT NULL,
  last_name    VARCHAR(100) NOT NULL,
  email        VARCHAR(255) UNIQUE NOT NULL,
  birth_date   DATE NOT NULL,
  created_at   TIMESTAMP NOT NULL DEFAULT NOW()
);