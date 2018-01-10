---- Database: votador

--DROP DATABASE votador;
--CREATE DATABASE votador
--  WITH OWNER = postgres
--       ENCODING = 'UTF8'
--       TABLESPACE = pg_default
--       LC_COLLATE = 'Portuguese_Brazil.1252'
--       LC_CTYPE = 'Portuguese_Brazil.1252'
--       CONNECTION LIMIT = -1;

DROP TABLE IF EXISTS public.voto;
DROP TABLE IF EXISTS public.funcionario;
DROP TABLE IF EXISTS public.recurso;

DROP SEQUENCE IF EXISTS public."funcionario_id_seq";
DROP SEQUENCE IF EXISTS public."recurso_id_seq";
DROP SEQUENCE IF EXISTS public."voto_id_seq";

-- Table: public.funcionario

CREATE SEQUENCE public."funcionario_id_seq"
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public."funcionario_id_seq"
  OWNER TO postgres;

CREATE TABLE public.funcionario
(
  id bigint NOT NULL DEFAULT nextval('funcionario_id_seq'::regclass),
  nome character varying(100) NOT NULL,
  email character varying(150) NOT NULL,
  senha character varying(120) NOT NULL,
  CONSTRAINT funcionario_pkey PRIMARY KEY (id),
  CONSTRAINT funcionario_email_key UNIQUE (email)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.funcionario
  OWNER TO postgres;

-- Table: public.recurso

CREATE SEQUENCE public."recurso_id_seq"
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public."recurso_id_seq"
  OWNER TO postgres;


CREATE TABLE public.recurso
(
  id bigint NOT NULL DEFAULT nextval('recurso_id_seq'::regclass),
  descricao character varying(255) NOT NULL,
  CONSTRAINT recurso_pkey PRIMARY KEY (id),
  CONSTRAINT recurso_descricao_key UNIQUE (descricao)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.recurso
  OWNER TO postgres;

-- Table: public.voto

CREATE SEQUENCE public."voto_id_seq"
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public."voto_id_seq"
  OWNER TO postgres;

CREATE TABLE public.voto
(
  id bigint NOT NULL DEFAULT nextval('voto_id_seq'::regclass), -- Identificador do voto
  comentario character varying(1024) NOT NULL, -- Comentário obrigatório para o voto
  datahora timestamp with time zone NOT NULL, -- Data e hora do voto (com timezone)
  "funcionario_id" bigint NOT NULL, -- Chave estrangeira para funcionário
  "recurso_id" bigint NOT NULL, -- Chave estrangeira para recurso
  CONSTRAINT voto_pkey PRIMARY KEY (id),
  CONSTRAINT funcionario_fkey FOREIGN KEY ("funcionario_id")
      REFERENCES public.funcionario (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT recurso_fkey FOREIGN KEY ("recurso_id")
      REFERENCES public.recurso (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT funcionario_recurso_ukey UNIQUE ("funcionario_id", "recurso_id")
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.voto
  OWNER TO postgres;
COMMENT ON TABLE public.voto
  IS 'Tabela de controle de recursos mais votados';
COMMENT ON COLUMN public.voto.id IS 'Identificador do voto';
COMMENT ON COLUMN public.voto.comentario IS 'Comentário obrigatório para o voto';
COMMENT ON COLUMN public.voto.datahora IS 'Data e hora do voto (com timezone)';
COMMENT ON COLUMN public.voto."funcionario_id" IS 'Chave estrangeira para funcionário';
COMMENT ON COLUMN public.voto."recurso_id" IS 'Chave estrangeira para recurso';
