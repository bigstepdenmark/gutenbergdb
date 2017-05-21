CREATE TABLE books(
	id INT NOT NULL PRIMARY KEY,
	title VARCHAR(255)
) ENGINE=MyISAM;

CREATE TABLE authors(
	id INT NOT NULL PRIMARY KEY,
	name VARCHAR(255)
) ENGINE=MyISAM;

CREATE TABLE author_books(
	book_id int NOT NULL,
	author_id int NOT NULL,
	PRIMARY KEY (book_id, author_id),
	CONSTRAINT fk_book_id FOREIGN KEY (book_id) REFERENCES books(id),
	CONSTRAINT fk_author_id FOREIGN KEY (author_id) REFERENCES authors(id)
) ENGINE=MyISAM;

CREATE TABLE cities(
	id INT NOT NULL PRIMARY KEY,
	name VARCHAR(255),
	latitude DECIMAL(11, 8) NOT NULL,
	longitude DECIMAL(11, 8) NOT NULL,
	country_code VARCHAR(50),
	population VARCHAR(255),
	timezone VARCHAR(255)
) ENGINE=MyISAM;

CREATE TABLE book_cities(
book_id int NOT NULL,
city_id int NOT NULL,
PRIMARY KEY (book_id, city_id),
CONSTRAINT fk_book_cities_book_id FOREIGN KEY (book_id) REFERENCES books(id),
CONSTRAINT fk_book_cities_city_id FOREIGN KEY (city_id) REFERENCES cities(id)
)ENGINE=MyISAM;

LOAD DATA LOCAL INFILE '/Users/ismailcam/IdeaProjects/gutenbergdb/src/main/files/output/authors_21-05-2017_00-37-54.csv'
INTO TABLE authors
FIELDS TERMINATED BY ';'
LINES TERMINATED BY '\n'
(id,name);

LOAD DATA LOCAL INFILE '/Users/ismailcam/IdeaProjects/gutenbergdb/src/main/files/output/titles_21-05-2017_20-39-52.csv'
INTO TABLE books
FIELDS TERMINATED BY ';'
LINES TERMINATED BY '\n'
(id,title);

LOAD DATA LOCAL INFILE '/Users/ismailcam/IdeaProjects/gutenbergdb/src/main/files/output/bookAuthorPivot_21-05-2017_23-49-20.csv'
INTO TABLE author_books
FIELDS TERMINATED BY ';'
LINES TERMINATED BY '\n'
(book_id,author_id);

LOAD DATA LOCAL INFILE '/Users/ismailcam/IdeaProjects/gutenbergdb/src/main/files/csvfiles/cities15000.csv'
INTO TABLE cities
FIELDS TERMINATED BY ';'
LINES TERMINATED BY '\n'
(id,name,latitude,longitude,country_code,population,timezone);

ALTER TABLE cities ADD position Point;
UPDATE  cities
SET     position = Point(longitude, latitude);

ALTER TABLE cities ADD SPATIAL INDEX(position);

LOAD DATA LOCAL INFILE '/Users/ismailcam/IdeaProjects/gutenbergdb/src/main/files/output/bookcitypivot.csv'
INTO TABLE book_cities
FIELDS TERMINATED BY ';'
LINES TERMINATED BY '\n'
(book_id,city_id);