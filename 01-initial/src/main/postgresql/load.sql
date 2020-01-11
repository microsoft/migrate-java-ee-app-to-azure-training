
CREATE SEQUENCE item_id_seq;

CREATE TABLE item (
    id SERIAL, 
    title VARCHAR(50),
    short_description VARCHAR(255)
);

INSERT INTO item(title, short_description) 
VALUES ('Ironman Blu-ray', 'Ironman Blu-ray');
