CREATE USER pocopay PASSWORD 'test';

ALTER DATABASE pocopay OWNER TO pocopay;
ALTER DATABASE pocopay SET search_path = "$user", pocopay, public;
