--question_comments
CREATE SEQUENCE question_comments_seq;
CREATE TABLE question_comments
(
  id          BIGINT                  NOT NULL DEFAULT nextval('question_comments_seq'),
  description CHARACTER VARYING(2048) NOT NULL,
  question_id BIGINT                  NOT NULL,
  user_id     INT8                    NOT NULL,
  created_at  TIMESTAMP WITHOUT TIME ZONE,
  updated_at  TIMESTAMP WITHOUT TIME ZONE,
  CONSTRAINT  question_comment_pkey PRIMARY KEY (id),
  CONSTRAINT  FK_question_comments__user_id FOREIGN KEY (user_id) REFERENCES public.user,
  CONSTRAINT  FK_question_comments__question_id FOREIGN KEY (question_id)
  REFERENCES  question (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
);
ALTER SEQUENCE question_comments_seq OWNED BY question_comments.id;


--answer_comments
CREATE SEQUENCE answer_comments_seq;
CREATE TABLE answer_comments
(
  id          BIGINT                  NOT NULL DEFAULT nextval('answer_comments_seq'),
  description CHARACTER VARYING(2048) NOT NULL,
  answer_id BIGINT                  NOT NULL,
  user_id     INT8                    NOT NULL,
  created_at  TIMESTAMP WITHOUT TIME ZONE,
  updated_at  TIMESTAMP WITHOUT TIME ZONE,
  CONSTRAINT  answer_comment_pkey PRIMARY KEY (id),
  CONSTRAINT  FK_answer_comments__user_id FOREIGN KEY (user_id) REFERENCES public.user,
  CONSTRAINT  FK_answer_comments__answer_id FOREIGN KEY (answer_id)
  REFERENCES  answer (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
);
ALTER SEQUENCE answer_comments_seq OWNED BY answer_comments.id;