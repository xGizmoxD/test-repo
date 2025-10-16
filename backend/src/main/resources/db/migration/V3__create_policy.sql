CREATE TABLE IF NOT EXISTS policy (
  id              BIGSERIAL PRIMARY KEY,
  client_id       BIGINT NOT NULL REFERENCES client(id),
  policy_type_id  BIGINT NOT NULL REFERENCES policy_type(id),
  start_date      DATE   NOT NULL,
  end_date        DATE   NOT NULL,
  premium         NUMERIC(12,2) NOT NULL,
  status          VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE',
  created_at      TIMESTAMP     NOT NULL DEFAULT NOW()
);