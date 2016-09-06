UPDATE notification SET description = '{"text":"' || description || '", "questionId" : ' || question_id || '}';

ALTER TABLE notification DROP COLUMN question_id;

ALTER TABLE notification ALTER column description SET NOT NULL;