## Demo application to understand Vertx rest API flow.
## Configuration of postgres, hikari and JOOQ.
## Logging using log4j2.

We use following resources to build a application.

1. Vertx  3.7.0
2. slf4j 1.7.13
3. log4j 2.11.1
4. postgresql 9.3-1100-jdbc41
5. HikariCP 2.5.1
6. jooq 3.10.3

---

We are using hikari to execute queries in synchronous way.

---

**Student create table script**

-- Table: public."Student"

-- DROP TABLE public."Student";

CREATE TABLE public."Student"
(
  id bigint,
  name text,
  "className" text,
  address text,
  "phoneNumber" bigint,
  "creationDate" date
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public."Student"
  OWNER TO postgres;


INSERT INTO public."Student"
(id, name, "className", address, "phoneNumber", "creationDate")
VALUES(1, 'Ganesh', 'b', 'kanaka', 42434234, NULL);
