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
  _id            BIGINT                  NOT NULL,
  _description   CHARACTER VARYING(2048) NOT NULL,
  _title         CHARACTER VARYING(255)  NOT NULL,
  _total_answers INTEGER                 NOT NULL,
  _user_id       INT8                    NOT NULL,
  CONSTRAINT question_pkey PRIMARY KEY (_id),
  CONSTRAINT FK_os8bn3xr2x2owjn69es4hcxgs FOREIGN KEY (_user_id) REFERENCES public.user
);

CREATE SEQUENCE question_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 6
  CACHE 1;

CREATE TABLE answer
(
  _id           BIGINT                  NOT NULL,
  _description  CHARACTER VARYING(2048) NOT NULL,
  _question__id BIGINT                  NOT NULL,
  _user_id      INT8                    NOT NULL,
  CONSTRAINT answer_pkey PRIMARY KEY (_id),
  CONSTRAINT FK_ilrlwe1trc8dyqaius89vprop FOREIGN KEY (_user_id) REFERENCES public.user,
  CONSTRAINT answer__question_question_fk FOREIGN KEY (_question__id)
  REFERENCES question (_id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE answer_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 5
  CACHE 1;

CREATE TABLE tag
(
  _id   BIGINT                 NOT NULL,
  _name CHARACTER VARYING(255) NOT NULL,
  CONSTRAINT tag_pkey PRIMARY KEY (_id)
);

CREATE SEQUENCE tag_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 7
  CACHE 1;
  
CREATE TABLE question__tags
(
  question__id BIGINT NOT NULL,
  _tags__id    BIGINT NOT NULL,
  CONSTRAINT question__tags_tag_fk FOREIGN KEY (_tags__id)
  REFERENCES tag (_id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT question__tags_question_fk FOREIGN KEY (question__id)
  REFERENCES question (_id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT question__tags_uk UNIQUE (question__id, _tags__id)
);
