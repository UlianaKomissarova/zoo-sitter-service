DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'user_role_enum') THEN
        CREATE TYPE user_role_enum AS ENUM ('ADMIN', 'SITTER', 'USER');
    END IF;
END $$;

CREATE TABLE IF NOT EXISTS app_user (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name VARCHAR(127) NOT NULL,
    last_name VARCHAR(127) NOT NULL,
    user_login VARCHAR(127) UNIQUE NOT NULL,
    user_password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    user_birthdate DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS user_role (
    user_id UUID NOT NULL REFERENCES app_user(id),
    role user_role_enum NOT NULL,
    PRIMARY KEY (user_id, role)
);