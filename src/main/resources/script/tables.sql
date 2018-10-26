-- Table: public.measurement_unit

-- DROP TABLE public.measurement_unit;

CREATE TABLE public.measurement_unit
(
    id bigint PRIMARY KEY,
    name character varying(20) NOT NULL UNIQUE
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.measurement_unit
    OWNER to postgres;



-- Table: public.article

-- DROP TABLE public.article;

CREATE TABLE public.article
(
    id bigint PRIMARY KEY,
    title character varying(200) NOT NULL UNIQUE,
    description character varying(400) NOT NULL,
    measurement_unit bigint NOT NULL,
    weight double precision NOT NULL,
    CONSTRAINT measurement_unit_fk FOREIGN KEY (measurement_unit)
        REFERENCES public.measurement_unit (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.article
    OWNER to postgres;

-- Index: fki_measurement_unit_fk

-- DROP INDEX public.fki_measurement_unit_fk;

CREATE INDEX fki_measurement_unit_fk
    ON public.article USING btree
    (measurement_unit)
    TABLESPACE pg_default;