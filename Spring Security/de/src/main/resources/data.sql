INSERT INTO USERS (USER_ID, USERNAME, PASSWORD, NICKNAME, ACTIVATED) values (1, 'admin', '$2a$12$CM6HGTErNF817RDApj0mZuksuhQbrbtJi6ZV8lj4p8IdVWyUjUmdi', 'admin', 1);

INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_USER');
INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_ADMIN');

INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_NAME) values (1, 'ROLE_USER');
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_NAME) values (1, 'ROLE_ADMIN');