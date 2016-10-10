DELETE FROM question_tags WHERE question_id IN (1,2,3,4,5,6,7,8,9,10);
DELETE FROM tag WHERE id in (1,2,3,4,5,6);
DELETE FROM answer_comments where answer_id in (1,2);
DELETE FROM answer WHERE id IN (1,2,3,4,5,6,7,8);
DELETE FROM question_comments where question_id in (1);
DELETE FROM question WHERE id IN (1,2,3,4,5,6,7,8,9,10);