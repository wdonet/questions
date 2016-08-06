ALTER TABLE public.configuration ADD COLUMN description VARCHAR;

UPDATE configuration set description = 'Possible values true or false. It shows and lets adds only one answer per question.'
    where config_name = 'show_only_one_answer';
INSERT INTO configuration (config_name, description) VALUES ('open_for_domains', 'if set, reestricts to those domains, otherwise is open for all');
