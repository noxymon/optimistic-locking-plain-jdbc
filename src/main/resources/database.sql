--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2 (Debian 12.2-2.pgdg100+1)
-- Dumped by pg_dump version 12.2 (Ubuntu 12.2-2.pgdg18.04+1)

-- Started on 2020-04-13 09:31:11 WIB

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- TOC entry 2930 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 206 (class 1259 OID 25436)
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 204 (class 1259 OID 18424)
-- Name: tb_transaction_count; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_transaction_count (
    id integer NOT NULL,
    usage integer DEFAULT 0 NOT NULL,
    version bigint DEFAULT 1 NOT NULL
);


ALTER TABLE public.tb_transaction_count OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 18414)
-- Name: tb_transaction_detail; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_transaction_detail (
    id bigint NOT NULL,
    created_date timestamp without time zone DEFAULT now() NOT NULL,
    content character varying
);


ALTER TABLE public.tb_transaction_detail OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 18412)
-- Name: tb_transcation_detail_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tb_transcation_detail_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tb_transcation_detail_id_seq OWNER TO postgres;

--
-- TOC entry 2931 (class 0 OID 0)
-- Dependencies: 202
-- Name: tb_transcation_detail_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tb_transcation_detail_id_seq OWNED BY public.tb_transaction_detail.id;


--
-- TOC entry 205 (class 1259 OID 18430)
-- Name: transcation_count_version_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.transcation_count_version_sequence
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.transcation_count_version_sequence OWNER TO postgres;

--
-- TOC entry 2932 (class 0 OID 0)
-- Dependencies: 205
-- Name: transcation_count_version_sequence; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.transcation_count_version_sequence OWNED BY public.tb_transaction_count.version;


--
-- TOC entry 2786 (class 2604 OID 18417)
-- Name: tb_transaction_detail id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_transaction_detail ALTER COLUMN id SET DEFAULT nextval('public.tb_transcation_detail_id_seq'::regclass);


--
-- TOC entry 2922 (class 0 OID 18424)
-- Dependencies: 204
-- Data for Name: tb_transaction_count; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2921 (class 0 OID 18414)
-- Dependencies: 203
-- Data for Name: tb_transaction_detail; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2933 (class 0 OID 0)
-- Dependencies: 206
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 900, true);


--
-- TOC entry 2934 (class 0 OID 0)
-- Dependencies: 202
-- Name: tb_transcation_detail_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_transcation_detail_id_seq', 1, false);


--
-- TOC entry 2935 (class 0 OID 0)
-- Dependencies: 205
-- Name: transcation_count_version_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.transcation_count_version_sequence', 1, false);


--
-- TOC entry 2793 (class 2606 OID 18429)
-- Name: tb_transaction_count tb_transaction_count_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_transaction_count
    ADD CONSTRAINT tb_transaction_count_pk PRIMARY KEY (id);


--
-- TOC entry 2791 (class 2606 OID 18423)
-- Name: tb_transaction_detail tb_transcation_detail_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_transaction_detail
    ADD CONSTRAINT tb_transcation_detail_pk PRIMARY KEY (id);


-- Completed on 2020-04-13 09:31:12 WIB

--
-- PostgreSQL database dump complete
--

