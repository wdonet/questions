--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.4
-- Dumped by pg_dump version 9.4.0
-- Started on 2015-12-14 15:00:54 CST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 180 (class 3079 OID 12123)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2318 (class 0 OID 0)
-- Dependencies: 180
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_with_oids = false;

--
-- TOC entry 174 (class 1259 OID 26630)
-- Name: answer; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE answer (
    _id bigint NOT NULL,
    _description character varying(255) NOT NULL,
    _question__id bigint NOT NULL
);


--
-- TOC entry 177 (class 1259 OID 26653)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 172 (class 1259 OID 26575)
-- Name: profiles; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE profiles (
    id bigint NOT NULL,
    location character varying(255),
    photo_uri character varying(255),
    reputation integer
);


--
-- TOC entry 175 (class 1259 OID 26635)
-- Name: question; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE question (
    _id bigint NOT NULL,
    _description character varying(255) NOT NULL,
    _title character varying(255) NOT NULL,
    _total_answers integer NOT NULL
);


--
-- TOC entry 176 (class 1259 OID 26643)
-- Name: question__tags; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE question__tags (
    question__id bigint NOT NULL,
    _tags__id bigint NOT NULL
);


--
-- TOC entry 178 (class 1259 OID 26656)
-- Name: tag; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE tag (
    _id bigint NOT NULL,
    _name character varying(255) NOT NULL
);


--
-- TOC entry 179 (class 1259 OID 26672)
-- Name: userconnection; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE userconnection (
    userid character varying(255) NOT NULL,
    providerid character varying(255) NOT NULL,
    provideruserid character varying(255) NOT NULL,
    rank integer NOT NULL,
    displayname character varying(255),
    profileurl character varying(512),
    imageurl character varying(512),
    accesstoken character varying(512) NOT NULL,
    secret character varying(512),
    refreshtoken character varying(512),
    expiretime bigint
);


--
-- TOC entry 173 (class 1259 OID 26599)
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE users (
    id bigint NOT NULL,
    email character varying(100) NOT NULL,
    first_name character varying(100) NOT NULL,
    last_name character varying(100) NOT NULL,
    role character varying(20) NOT NULL,
    sign_in_provider character varying(20),
    profile_id bigint
);


--
-- TOC entry 2306 (class 0 OID 26630)
-- Dependencies: 174
-- Data for Name: answer; Type: TABLE DATA; Schema: public; Owner: -
--

COPY answer (_id, _description, _question__id) FROM stdin;
\.


--
-- TOC entry 2319 (class 0 OID 0)
-- Dependencies: 177
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('hibernate_sequence', 1, false);


--
-- TOC entry 2304 (class 0 OID 26575)
-- Dependencies: 172
-- Data for Name: profiles; Type: TABLE DATA; Schema: public; Owner: -
--

COPY profiles (id, location, photo_uri, reputation) FROM stdin;
\.


--
-- TOC entry 2307 (class 0 OID 26635)
-- Dependencies: 175
-- Data for Name: question; Type: TABLE DATA; Schema: public; Owner: -
--

COPY question (_id, _description, _title, _total_answers) FROM stdin;
\.


--
-- TOC entry 2308 (class 0 OID 26643)
-- Dependencies: 176
-- Data for Name: question__tags; Type: TABLE DATA; Schema: public; Owner: -
--

COPY question__tags (question__id, _tags__id) FROM stdin;
\.


--
-- TOC entry 2310 (class 0 OID 26656)
-- Dependencies: 178
-- Data for Name: tag; Type: TABLE DATA; Schema: public; Owner: -
--

COPY tag (_id, _name) FROM stdin;
\.


--
-- TOC entry 2311 (class 0 OID 26672)
-- Dependencies: 179
-- Data for Name: userconnection; Type: TABLE DATA; Schema: public; Owner: -
--

COPY userconnection (userid, providerid, provideruserid, rank, displayname, profileurl, imageurl, accesstoken, secret, refreshtoken, expiretime) FROM stdin;
\.


--
-- TOC entry 2305 (class 0 OID 26599)
-- Dependencies: 173
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--

COPY users (id, email, first_name, last_name, role, sign_in_provider, profile_id) FROM stdin;
\.


--
-- TOC entry 2181 (class 2606 OID 26634)
-- Name: answer_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY answer
    ADD CONSTRAINT answer_pkey PRIMARY KEY (_id);


--
-- TOC entry 2175 (class 2606 OID 26582)
-- Name: profiles_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY profiles
    ADD CONSTRAINT profiles_pkey PRIMARY KEY (id);


--
-- TOC entry 2183 (class 2606 OID 26642)
-- Name: question_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY question
    ADD CONSTRAINT question_pkey PRIMARY KEY (_id);


--
-- TOC entry 2187 (class 2606 OID 26660)
-- Name: tag_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tag
    ADD CONSTRAINT tag_pkey PRIMARY KEY (_id);


--
-- TOC entry 2177 (class 2606 OID 26607)
-- Name: uk_6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- TOC entry 2185 (class 2606 OID 26647)
-- Name: uk_a5a3q5xc4cwk51ywg5s3trakm; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY question__tags
    ADD CONSTRAINT uk_a5a3q5xc4cwk51ywg5s3trakm UNIQUE (question__id, _tags__id);


--
-- TOC entry 2189 (class 2606 OID 26679)
-- Name: userconnection_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY userconnection
    ADD CONSTRAINT userconnection_pkey PRIMARY KEY (userid, providerid, provideruserid);


--
-- TOC entry 2179 (class 2606 OID 26603)
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 2190 (class 1259 OID 26680)
-- Name: userconnectionrank; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX userconnectionrank ON userconnection USING btree (userid, providerid, rank);


--
-- TOC entry 2191 (class 2606 OID 26623)
-- Name: fk_7s5nlreekaxdbfml4ofky7utw; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT fk_7s5nlreekaxdbfml4ofky7utw FOREIGN KEY (profile_id) REFERENCES profiles(id);


--
-- TOC entry 2192 (class 2606 OID 26661)
-- Name: fk_925mn0ji18ututsrf5yr0lwb3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY answer
    ADD CONSTRAINT fk_925mn0ji18ututsrf5yr0lwb3 FOREIGN KEY (_question__id) REFERENCES question(_id);


--
-- TOC entry 2194 (class 2606 OID 26666)
-- Name: fk_a5a3q5xc4cwk51ywg5s3trakm; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY question__tags
    ADD CONSTRAINT fk_a5a3q5xc4cwk51ywg5s3trakm FOREIGN KEY (_tags__id) REFERENCES tag(_id);


--
-- TOC entry 2193 (class 2606 OID 26648)
-- Name: fk_kq78vj03a09yb7rawf4052ls3; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY question__tags
    ADD CONSTRAINT fk_kq78vj03a09yb7rawf4052ls3 FOREIGN KEY (question__id) REFERENCES question(_id);


-- Completed on 2015-12-14 15:00:55 CST

--
-- PostgreSQL database dump complete
--

