select

c.id as city_id,
c.name as city_name,
c.latitude as city_latitude,
c.longitude as city_longitude,
c.country_code as city_country_code,
c.population as city_population,
c.timezone as city_timezone,
c.position as city_position

from cities c

join book_cities bc on bc.city_id = c.id
join books b on b.id = bc.book_id

where b.id = '%s';