CREATE USER admin WITH PASSWORD 'passwd';

-- Tornar o usuário um superusuário (tem todas as permissões)
ALTER USER admin WITH SUPERUSER;

CREATE DATABASE contest_db;
\connect contest_db;
CREATE SCHEMA contest_sch AUTHORIZATION admin;

GRANT CONNECT ON DATABASE contest_db TO admin;
GRANT ALL PRIVILEGES ON SCHEMA contest_sch TO admin;
	