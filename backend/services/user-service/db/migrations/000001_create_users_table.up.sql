CREATE TABLE users (
  user_id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  username text NOT NULL UNIQUE,
  google_id text UNIQUE,
  apple_id text UNIQUE,
  name text NOT NULL,
  email text,
  avatar text
);