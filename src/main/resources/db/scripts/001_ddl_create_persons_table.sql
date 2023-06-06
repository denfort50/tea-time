CREATE TABLE IF NOT EXISTS persons (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    email VARCHAR NOT NULL
);

COMMENT ON TABLE persons IS 'Таблица адресатов';
COMMENT ON COLUMN persons.id IS 'Идентификатор';
COMMENT ON COLUMN persons.first_name IS 'Имя';
COMMENT ON COLUMN persons.last_name IS 'Фамилия';
COMMENT ON COLUMN persons.email IS 'Электронная почта';