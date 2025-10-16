CREATE TABLE IF NOT EXISTS payment (
  id          BIGSERIAL PRIMARY KEY,
  policy_id   BIGINT NOT NULL REFERENCES policy(id),
  amount      NUMERIC(12,2) NOT NULL,
  paid_at     TIMESTAMP     NOT NULL DEFAULT NOW(),
  method      VARCHAR(50)   NOT NULL
);