select
b.id as book_id,
b.title as title,
a.id as author_id,
a.name as author_name

from books b

join author_books ab on ab.book_id = b.id
join authors a on a.id = ab.author_id

where b.title = '%s';