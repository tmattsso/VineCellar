--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.2
-- Dumped by pg_dump version 9.3.2
-- Started on 2014-09-23 18:32:51 EEST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 175 (class 3079 OID 12018)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2231 (class 0 OID 0)
-- Dependencies: 175
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- TOC entry 173 (class 1259 OID 88485)
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_id_seq OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 174 (class 1259 OID 88496)
-- Name: appusers; Type: TABLE; Schema: public; Owner: vine; Tablespace: 
--

CREATE TABLE appusers (
    id smallint DEFAULT nextval('user_id_seq'::regclass) NOT NULL,
    email character varying(128) NOT NULL,
    hashedpass bytea NOT NULL
);


ALTER TABLE public.appusers OWNER TO vine;

--
-- TOC entry 171 (class 1259 OID 88083)
-- Name: type; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE type (
    name character varying(16),
    id integer NOT NULL
);


ALTER TABLE public.type OWNER TO postgres;

--
-- TOC entry 170 (class 1259 OID 88075)
-- Name: wines; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE wines (
    name character varying(256) NOT NULL,
    comment character varying(2048),
    producer character varying(128),
    type integer,
    amount integer,
    country character varying(64),
    year integer,
    id integer NOT NULL,
    area character varying(64),
    drinkfrom character varying(16),
    drinklast character varying(16),
    drinkbest character varying(16),
    grapes character varying(128),
    user_id smallint
);


ALTER TABLE public.wines OWNER TO postgres;

--
-- TOC entry 172 (class 1259 OID 88093)
-- Name: wines_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE wines_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.wines_id_seq OWNER TO postgres;

--
-- TOC entry 2232 (class 0 OID 0)
-- Dependencies: 172
-- Name: wines_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE wines_id_seq OWNED BY wines.id;


--
-- TOC entry 2101 (class 2604 OID 88095)
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY wines ALTER COLUMN id SET DEFAULT nextval('wines_id_seq'::regclass);


--
-- TOC entry 2223 (class 0 OID 88496)
-- Dependencies: 174
-- Data for Name: appusers; Type: TABLE DATA; Schema: public; Owner: vine
--

COPY appusers (id, email, hashedpass) FROM stdin;
\.


--
-- TOC entry 2220 (class 0 OID 88083)
-- Dependencies: 171
-- Data for Name: type; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY type (name, id) FROM stdin;
rött	0
vitt	1
rose	2
champagne	3
mousserat	4
sött	5
\.


--
-- TOC entry 2233 (class 0 OID 0)
-- Dependencies: 173
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('user_id_seq', 1, false);


--
-- TOC entry 2219 (class 0 OID 88075)
-- Dependencies: 170
-- Data for Name: wines; Type: TABLE DATA; Schema: public; Owner: postgres
--

--
-- TOC entry 2234 (class 0 OID 0)
-- Dependencies: 172
-- Name: wines_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('wines_id_seq', 677, true);


--
-- TOC entry 2108 (class 2606 OID 88506)
-- Name: appusers_email_key; Type: CONSTRAINT; Schema: public; Owner: vine; Tablespace: 
--

ALTER TABLE ONLY appusers
    ADD CONSTRAINT appusers_email_key UNIQUE (email);


--
-- TOC entry 2110 (class 2606 OID 88504)
-- Name: pk; Type: CONSTRAINT; Schema: public; Owner: vine; Tablespace: 
--

ALTER TABLE ONLY appusers
    ADD CONSTRAINT pk PRIMARY KEY (id);


--
-- TOC entry 2106 (class 2606 OID 88087)
-- Name: prim; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY type
    ADD CONSTRAINT prim PRIMARY KEY (id);


--
-- TOC entry 2104 (class 2606 OID 88097)
-- Name: wines_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY wines
    ADD CONSTRAINT wines_pkey PRIMARY KEY (id);


--
-- TOC entry 2111 (class 2606 OID 88088)
-- Name: vines_type_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY wines
    ADD CONSTRAINT vines_type_fkey FOREIGN KEY (type) REFERENCES type(id);


--
-- TOC entry 2230 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--


ALTER TABLE wines
  ADD CONSTRAINT users FOREIGN KEY (user_id)
      REFERENCES appusers (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2014-09-23 18:32:51 EEST

--
-- PostgreSQL database dump complete
--

