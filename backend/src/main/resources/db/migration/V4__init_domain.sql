
CREATE TABLE IF NOT EXISTS app_user (
  id BIGSERIAL PRIMARY KEY,
  email VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(32) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Klienci
CREATE TABLE IF NOT EXISTS client (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  birth_date DATE NOT NULL,
  CONSTRAINT fk_client_user
    FOREIGN KEY (user_id) REFERENCES app_user(id)
);

-- Typy polis
CREATE TABLE IF NOT EXISTS policy_type (
  id BIGSERIAL PRIMARY KEY,
  code VARCHAR(50) UNIQUE NOT NULL,
  name VARCHAR(255) NOT NULL,
  base_rate NUMERIC(10,2) NOT NULL
);

-- Polisy
CREATE TABLE IF NOT EXISTS policy (
  id BIGSERIAL PRIMARY KEY,
  client_id BIGINT NOT NULL,
  policy_type_id BIGINT NOT NULL,
  sum_insured NUMERIC(12,2) NOT NULL,
  risk_factor NUMERIC(5,2) NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  premium NUMERIC(12,2) NOT NULL,
  status VARCHAR(32) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  CONSTRAINT fk_policy_client
    FOREIGN KEY (client_id) REFERENCES client(id),
  CONSTRAINT fk_policy_type
    FOREIGN KEY (policy_type_id) REFERENCES policy_type(id)
);

-- Płatności
CREATE TABLE IF NOT EXISTS payment (
  id BIGSERIAL PRIMARY KEY,
  policy_id BIGINT NOT NULL,
  amount NUMERIC(12,2) NOT NULL,
  paid_at TIMESTAMP NOT NULL DEFAULT NOW(),
  method VARCHAR(50) NOT NULL,
  CONSTRAINT fk_payment_policy
    FOREIGN KEY (policy_id) REFERENCES policy(id)
);