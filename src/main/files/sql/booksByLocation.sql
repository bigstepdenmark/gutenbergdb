select
b.id as book_id,
b.title as title,
a.id as author_id,
a.name as author_name,
c.latitude as city_latitude,
c.longitude as city_longitude
from books b
join author_books ab on ab.book_id = b.id
join authors a on a.id = ab.author_id
join book_cities bc on bc.book_id = b.id
join cities c on c.id = bc.city_id
WHERE 6371 * acos( cos( radians(c.latitude) ) * cos( radians(%1$s) ) * cos( radians(%2$s) - radians(c.longitude) ) + sin ( radians(c.latitude) ) * sin( radians( %1$s ) ) ) < %3$s
GROUP BY(b.id);