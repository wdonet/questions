CREATE TABLE profiles
(
  id         BIGINT NOT NULL,
  location   CHARACTER VARYING(255),
  photo_uri  CHARACTER VARYING(255),
  reputation INTEGER,
  CONSTRAINT profiles_pkey PRIMARY KEY (id)
);

CREATE TABLE userconnection
(
  userid         CHARACTER VARYING(255) NOT NULL,
  providerid     CHARACTER VARYING(255) NOT NULL,
  provideruserid CHARACTER VARYING(255) NOT NULL,
  rank           INTEGER                NOT NULL,
  displayname    CHARACTER VARYING(255),
  profileurl     CHARACTER VARYING(512),
  imageurl       CHARACTER VARYING(512),
  accesstoken    CHARACTER VARYING(512) NOT NULL,
  secret         CHARACTER VARYING(512),
  refreshtoken   CHARACTER VARYING(512),
  expiretime     BIGINT,
  CONSTRAINT userconnection_pkey PRIMARY KEY (userid, providerid, provideruserid)
);

CREATE UNIQUE INDEX userconnectionrank
ON userconnection
USING BTREE
(userid COLLATE pg_catalog."default", providerid COLLATE pg_catalog."default", rank);

CREATE TABLE public.user
(
  id               BIGINT                 NOT NULL,
  email            CHARACTER VARYING(100) NOT NULL,
  first_name       CHARACTER VARYING(100) NOT NULL,
  last_name        CHARACTER VARYING(100) NOT NULL,
  role             CHARACTER VARYING(20)  NOT NULL,
  sign_in_provider CHARACTER VARYING(20),
  CONSTRAINT user_pkey PRIMARY KEY (id),
  CONSTRAINT user_email_ukey UNIQUE (email)
);

CREATE SEQUENCE user_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 2
  CACHE 1;

CREATE TABLE question
(
  id            BIGINT                  NOT NULL,
  description   CHARACTER VARYING(2048) NOT NULL,
  title         CHARACTER VARYING(255)  NOT NULL,
  total_answers INTEGER                 NOT NULL,
  user_id       INT8                    NOT NULL,
  CONSTRAINT question_pkey PRIMARY KEY (id),
  CONSTRAINT FK_os8bn3xr2x2owjn69es4hcxgs FOREIGN KEY (user_id) REFERENCES public.user
);

CREATE SEQUENCE question_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 11
  CACHE 1;

CREATE TABLE answer
(
  id           BIGINT                  NOT NULL,
  description  CHARACTER VARYING(2048) NOT NULL,
  question_id BIGINT                  NOT NULL,
  user_id      INT8                    NOT NULL,
  CONSTRAINT answer_pkey PRIMARY KEY (id),
  CONSTRAINT FK_ilrlwe1trc8dyqaius89vprop FOREIGN KEY (user_id) REFERENCES public.user,
  CONSTRAINT answer__question_question_fk FOREIGN KEY (question_id)
  REFERENCES question (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE answer_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 9
  CACHE 1;

CREATE TABLE tag
(
  id   BIGINT                 NOT NULL,
  name CHARACTER VARYING(255) NOT NULL,
  user_id       INT8                    NOT NULL,
  CONSTRAINT fk_tag_user FOREIGN KEY (user_id) REFERENCES public.user,
  CONSTRAINT tag_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE tag_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 7
  CACHE 1;

CREATE TABLE question_tags
(
  question_id BIGINT NOT NULL,
  tags_id    BIGINT NOT NULL,
  CONSTRAINT question_tags_tag_fk FOREIGN KEY (tags_id)
  REFERENCES tag (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT question_tags_question_fk FOREIGN KEY (question_id)
  REFERENCES question (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT question_tags_uk UNIQUE (question_id, tags_id)
);
