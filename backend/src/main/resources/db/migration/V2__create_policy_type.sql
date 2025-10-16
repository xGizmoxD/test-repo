CREATE TABLE IF NOT EXISTS policy_type (
  id           BIGSERIAL PRIMARY KEY,
  code         VARCHAR(50) UNIQUE NOT NULL,
  name         VARCHAR(150) NOT NULL,
  base_premium NUMERIC(12,2) NOT NULL
);

INSERT INTO policy_type(code, name, base_premium) VALUES
 ('TPL', 'OC komunikacyjne', 100.00),
 ('CASCO', 'Autocasco', 250.00),
 ('TRAVEL', 'Ubezpieczenie podróży', 60.00)
ON CONFLICT (code) DO NOTHING;