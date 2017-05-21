select
b.id as book_id,
b.title as title,
a.id as author_id,
a.name as author_name,
c.id as city_id,
c.name as city_name,
c.latitude as city_latitude,
c.longitude as city_longitude,
c.country_code as city_country_code,
c.population as city_population,
c.timezone as city_timezone,
c.position as city_position

from books b

join author_books ab on ab.book_id = b.id
join authors a on a.id = ab.author_id
join book_cities bc on bc.book_id = b.id
join cities c on c.id = bc.city_id

where b.id = '%d';