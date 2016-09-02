ALTER TABLE notification ADD CONSTRAINT notification_pkey PRIMARY KEY (id);

CREATE TABLE temp_notification
(
  id                BIGINT                  NOT NULL,
  description       CHARACTER VARYING(2048) NOT NULL,
  question_id       INT8                    NOT NULL,
  notification_type INT8                    NOT NULL
);

INSERT INTO temp_notification (id, description, question_id, notification_type)
  SELECT nextval('notification_seq'), description, question_id, notification_type
  FROM notification
  GROUP BY description, question_id, notification_type;

CREATE TABLE user_notification
(
  id                BIGINT                  NOT NULL,
  notification_id   BIGINT                  NOT NULL,
  user_id           INT8                    NOT NULL,
  email_delivered   BOOLEAN                 NOT NULL DEFAULT FALSE,
  ui_notified       BOOLEAN                 NOT NULL DEFAULT FALSE,
  CONSTRAINT user_notification_user_fk FOREIGN KEY (user_id) REFERENCES public.user,
  CONSTRAINT user_notification_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE user_notification_seq
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 7
CACHE 1;

INSERT INTO user_notification (id, notification_id, user_id, email_delivered, ui_notified)
  SELECT nextval('user_notification_seq'), tmp.id, n.user_id, n.email_delivered, n.ui_notified FROM temp_notification tmp
    INNER JOIN notification n ON
                                (tmp.description = n.description AND tmp.question_id = n.question_id AND tmp.notification_type = n.notification_type);

TRUNCATE TABLE notification;

ALTER TABLE notification DROP CONSTRAINT notification_user_fk;

ALTER TABLE notification DROP COLUMN email_delivered;
ALTER TABLE notification DROP COLUMN ui_notified;
ALTER TABLE notification DROP COLUMN user_id;

ALTER TABLE notification ADD COLUMN created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT current_timestamp;

ALTER TABLE notification ALTER COLUMN description TYPE text;

ALTER TABLE answer ALTER COLUMN description TYPE text;
ALTER TABLE question ALTER COLUMN description TYPE text;

INSERT INTO notification (id, description, question_id, notification_type)
  SELECT id, description, question_id, notification_type FROM temp_notification;

ALTER TABLE user_notification ADD CONSTRAINT user_notification_id_fk FOREIGN KEY (notification_id) REFERENCES public.notification;

DROP TABLE temp_notification;
