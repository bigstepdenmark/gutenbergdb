CREATE TABLE books(
	id INT NOT NULL PRIMARY KEY,
	title VARCHAR(255)
) ENGINE=InnoDB;

CREATE TABLE authors(
	id INT NOT NULL PRIMARY KEY,
	name VARCHAR(255)
) ENGINE=InnoDB;

CREATE TABLE author_books(
	book_id int NOT NULL,
	author_id int NOT NULL,
	PRIMARY KEY (book_id, author_id),
	CONSTRAINT fk_book_id FOREIGN KEY (book_id) REFERENCES books(id),
	CONSTRAINT fk_author_id FOREIGN KEY (author_id) REFERENCES authors(id)
) ENGINE=InnoDB;

CREATE TABLE cities(
	id INT NOT NULL PRIMARY KEY,
	name VARCHAR(255),
	latitude DECIMAL(11, 8) NOT NULL,
	longitude DECIMAL(11, 8) NOT NULL,
	country_code VARCHAR(50),
	population VARCHAR(255),
	timezone VARCHAR(255)
) ENGINE=InnoDB;

LOAD DATA LOCAL INFILE '/Users/ismailcam/IdeaProjects/gutenbergDatabase/src/main/resources/csvfiles/authors.csv'
INTO TABLE authors
FIELDS TERMINATED BY ';'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(id,name);

LOAD DATA LOCAL INFILE '/Users/ismailcam/IdeaProjects/gutenbergDatabase/src/main/resources/csvfiles/titles.csv'
INTO TABLE books
FIELDS TERMINATED BY ';'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(id,title);

LOAD DATA LOCAL INFILE '/Users/ismailcam/IdeaProjects/gutenbergDatabase/src/main/resources/csvfiles/author_books.csv'
INTO TABLE author_books
FIELDS TERMINATED BY ';'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(book_id,author_id);

LOAD DATA LOCAL INFILE '/Users/ismailcam/IdeaProjects/gutenbergDatabase/src/main/resources/csvfiles/cities15000.csv'
INTO TABLE cities
FIELDS TERMINATED BY ';'
LINES TERMINATED BY '\n'
IGNORE 1 LINES
(id,name,latitude,longitude,country_code,population,timezone);

ALTER TABLE cities ADD position Point;
UPDATE  cities
SET     position = Point(longitude, latitude);