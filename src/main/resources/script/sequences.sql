CREATE SEQUENCE public.measurement_unit_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.measurement_unit_sequence
    OWNER TO postgres;
	
	
CREATE SEQUENCE public.article_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.article_sequence
    OWNER TO postgres;