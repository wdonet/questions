drop sequence rule_seq;

ALTER TABLE public.rule
 ADD COLUMN reputation_needed INTEGER NOT NULL DEFAULT 0;

UPDATE public.rule SET reputation_needed = 5 where rule_name = 'VOTED_UP_ANSWER';
UPDATE public.rule SET reputation_needed = 5 where rule_name = 'VOTED_UP_QUESTION';
UPDATE public.rule SET reputation_needed = 25 where rule_name = 'VOTED_DOWN_ANSWER';
UPDATE public.rule SET reputation_needed = 25 where rule_name = 'VOTED_DOWN_QUESTION';

INSERT INTO rule (id, rule_name, points, reputation_needed)
VALUES
(11, 'NEW_TAG', 0, 10),
(12, 'TAG_SUBSCRIPTION', 0, 10),
(13, 'EDIT_OTHER_QUESTIONS', 0, 50),
(14, 'EDIT_OTHER_ANSWERS', 0, 80);
