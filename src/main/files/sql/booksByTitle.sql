SELECT books.id, authors.name as author FROM books
JOIN author_books ON books.id = author_books.book_id
JOIN authors ON author_books.author_id = authors.id
WHERE books.title = '%s';