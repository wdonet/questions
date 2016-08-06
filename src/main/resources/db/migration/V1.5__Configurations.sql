CREATE SEQUENCE configuration_id_seq;

CREATE TABLE public.configuration (
    id INT NOT NULL DEFAULT nextval('configuration_id_seq'),
    config_name VARCHAR NOT NULL,
    value VARCHAR
);

ALTER SEQUENCE configuration_id_seq OWNED BY configuration.id;

INSERT INTO configuration (config_name, value) VALUES ('show_only_one_answer', 'false');
