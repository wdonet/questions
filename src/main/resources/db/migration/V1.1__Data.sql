-- Users

INSERT INTO users (id, email, first_name, last_name, role)
VALUES (1, 'nsq@nearsoft.com', 'system', 'default', 'ROLE_USER');

-- Questions

INSERT INTO question (_id, _description, _title, _total_answers, _user_id)
VALUES (1, 'Where can I find the credentials for courses and other resources for training',
        'Where are the credentials for online trainning?', 0, 1);

INSERT INTO question (_id, _description, _title, _total_answers, _user_id)
VALUES (2, 'Who is the person I should contact in case I require insurance health information',
        'What is the insurance health contact?', 1, 1);

INSERT INTO question (_id, _description, _title, _total_answers, _user_id)
VALUES (3, 'How can I watch the Friday Talk streaming', 'What is the info to connect to friday talks?', 1, 1);

INSERT INTO question (_id, _description, _title, _total_answers, _user_id)
VALUES (4, 'I want to take a PTO tomorrow but I do not know how is supposed to notify or how should I do this',
        'What is the procedure to ask for a PTO?', 1, 1);

-- Answers

INSERT INTO question (_id, _description, _title, _total_answers, _user_id)
VALUES (5, 'Where can I find the quality service document?', 'Where is quality service document?', 1, 1);

INSERT INTO answer (_id, _description, _question__id, _user_id)
VALUES (1, 'https://docs.google.com/document/d/1JbMiYhO4W7eqolhdUqOAlRSgPEN3t7X3WsRweJOPE-w/edit#', 1, 1);

INSERT INTO answer (_id, _description, _question__id, _user_id)
VALUES (2, '1. Go to the Meeting Server:
    http://189.198.250.228\n
2. Click the Join Meeting button.\n
3. Enter the Meeting ID: 1171-6803\n
4. Meeting Password: No password needed\n
5. Conference Call:  +52 554-160-7902 (Mexico) Access Code: 1171-6803', 3, 1);

INSERT INTO answer (_id, _description, _question__id, _user_id)
VALUES (3, 'https://docs.google.com/document/d/1JbMiYhO4W7eqolhdUqOAlRSgPEN3t7X3WsRweJOPE-w/edit#', 4, 1);

INSERT INTO answer (_id, _description, _question__id, _user_id)
VALUES (4, 'In order to give you the best service and we can fulfill our daily tasks,
we present you our Finance Quality of Service
(https://docs.google.com/document/d/1yBF73pzX85NZ7c2Abep4SGq5zmwQSu2LATPMk8KfTNw/edit#heading=h.voi8fltteo8t).
This document defines the expectation and the quality of service that the different areas of the finance function at
Nearsoft and to organize our workflow.\n Please keep it reachable and feel free to read it every time to
have a question, so you can manage your timeframes.',
        5, 1);

-- Tags

INSERT INTO tag (_id, _name) VALUES (1, 'finance');
INSERT INTO tag (_id, _name) VALUES (2, 'training');
INSERT INTO tag (_id, _name) VALUES (3, 'vacations');
INSERT INTO tag (_id, _name) VALUES (4, 'pto');
INSERT INTO tag (_id, _name) VALUES (5, 'insurance');
INSERT INTO tag (_id, _name) VALUES (6, 'talk');

-- Question Tags

INSERT INTO question__tags (question__id, _tags__id)
VALUES (1, 2);

INSERT INTO question__tags (question__id, _tags__id)
VALUES (2, 5);

INSERT INTO question__tags (question__id, _tags__id)
VALUES (3, 6);

INSERT INTO question__tags (question__id, _tags__id)
VALUES (4, 4);

INSERT INTO question__tags (question__id, _tags__id)
VALUES (4, 3);

INSERT INTO question__tags (question__id, _tags__id)
VALUES (5, 1);
