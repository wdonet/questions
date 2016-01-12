-- public.user

INSERT INTO public.user (id, email, first_name, last_name, role)
VALUES (1, 'nsq@nearsoft.com', 'system', 'default', 'ROLE_USER');

-- Questions

INSERT INTO question (id, description, title, total_answers, user_id)
VALUES (1, 'Where can I find the credentials for courses and other resources for training',
        'Where are the credentials for online trainning?', 1, 1);

INSERT INTO question (id, description, title, total_answers, user_id)
VALUES (2, 'Who is the person I should contact in case I require insurance health information',
        'What is the insurance health contact?', 0, 1);

INSERT INTO question (id, description, title, total_answers, user_id)
VALUES (3, 'How can I watch the Friday Talk streaming', 'What is the info to connect to friday talks?', 1, 1);

INSERT INTO question (id, description, title, total_answers, user_id)
VALUES (4, 'I want to take a PTO tomorrow but I do not know how is supposed to notify or how should I do this',
        'What is the procedure to ask for a PTO?', 1, 1);

INSERT INTO question (id, description, title, total_answers, user_id)
VALUES (5, 'Where can I find the quality service document?', 'Where is quality service document?', 1, 1);

INSERT INTO question (id, description, title, total_answers, user_id)
VALUES (6, 'I want to receive the prima payment on the day I will go on vacation',
        'What is the procedure to receive my prima vacacional?', 1, 1);

INSERT INTO question (id, description, title, total_answers, user_id)
VALUES (7, 'I will take a workshop in Chicago next month. What should I do to request the travel procedure?',
        'What is the travel expense procedure?', 1, 1);

INSERT INTO question (id, description, title, total_answers, user_id)
VALUES (8, 'DF office sponsored an event. We paid for pizzas and soda. What do I need to send you for reimbursement
            of the expense',
        'How can I request an invoice payment?', 1, 1);

INSERT INTO question (id, description, title, total_answers, user_id)
VALUES (9, 'In my previous job I did need to do tax declaration. Why I need to do it here?',
        'Why do I need to do tax declaration?', 0, 1);

INSERT INTO question (id, description, title, total_answers, user_id)
VALUES (10, 'I want to do my tax declaration online but I need my SAT password. How can I get it?',
        'How can I get my SAT password?', 1, 1);

-- Answers

INSERT INTO answer (id, description, question_id, user_id)
VALUES (1, 'https://docs.google.com/document/d/1JbMiYhO4W7eqolhdUqOAlRSgPEN3t7X3WsRweJOPE-w/edit#', 1, 1);

INSERT INTO answer (id, description, question_id, user_id)
VALUES (2, '1. Go to the Meeting Server:
    http://189.198.250.228\n
2. Click the Join Meeting button.\n
3. Enter the Meeting ID: 1171-6803\n
4. Meeting Password: No password needed\n
5. Conference Call:  +52 554-160-7902 (Mexico) Access Code: 1171-6803', 3, 1);

INSERT INTO answer (id, description, question_id, user_id)
VALUES (3, 'https://docs.google.com/document/d/1JbMiYhO4W7eqolhdUqOAlRSgPEN3t7X3WsRweJOPE-w/edit#', 4, 1);

INSERT INTO answer (id, description, question_id, user_id)
VALUES (4, 'In order to give you the best service and we can fulfill our daily tasks,
we present you our Finance Quality of Service
(https://docs.google.com/document/d/1yBF73pzX85NZ7c2Abep4SGq5zmwQSu2LATPMk8KfTNw/edit#heading=h.voi8fltteo8t).
This document defines the expectation and the quality of service that the different areas of the finance function at
Nearsoft and to organize our workflow.\n Please keep it reachable and feel free to read it every time to
have a question, so you can manage your timeframes.',
        5, 1);

INSERT INTO answer (id, description, question_id, user_id)
VALUES (5, 'In this document you can look for the answers about the Vacation Payment Procedure. https://goo.gl/8H1iI2',
        6, 1);

INSERT INTO answer (id, description, question_id, user_id)
VALUES (6, 'You can check the Travel Expenses Procedure document to know how to proceed with your travel. Good luck. https://goo.gl/p3cm8P',
        7, 1);

INSERT INTO answer (id, description, question_id, user_id)
VALUES (7, 'Please check the Expenses Procedure Document. It contains the answer for this question. https://goo.gl/odIuU9',
        8, 1);

INSERT INTO answer (id, description, question_id, user_id)
VALUES (8, 'You can use this link https://goo.gl/aao8IE for that',
        10, 1);

-- Tags

INSERT INTO tag (id, name) VALUES (1, 'finance');
INSERT INTO tag (id, name) VALUES (2, 'training');
INSERT INTO tag (id, name) VALUES (3, 'vacations');
INSERT INTO tag (id, name) VALUES (4, 'pto');
INSERT INTO tag (id, name) VALUES (5, 'insurance');
INSERT INTO tag (id, name) VALUES (6, 'talk');

-- Question Tags

INSERT INTO question_tags (question_id, tags_id)
VALUES (1, 2);

INSERT INTO question_tags (question_id, tags_id)
VALUES (2, 5);

INSERT INTO question_tags (question_id, tags_id)
VALUES (3, 6);

INSERT INTO question_tags (question_id, tags_id)
VALUES (4, 4);

INSERT INTO question_tags (question_id, tags_id)
VALUES (4, 3);

INSERT INTO question_tags (question_id, tags_id)
VALUES (5, 1);

INSERT INTO question_tags (question_id, tags_id)
VALUES (6, 1);

INSERT INTO question_tags (question_id, tags_id)
VALUES (6, 3);

INSERT INTO question_tags (question_id, tags_id)
VALUES (7, 1);

INSERT INTO question_tags (question_id, tags_id)
VALUES (8, 1);

INSERT INTO question_tags (question_id, tags_id)
VALUES (9, 1);

INSERT INTO question_tags (question_id, tags_id)
VALUES (10, 1);

